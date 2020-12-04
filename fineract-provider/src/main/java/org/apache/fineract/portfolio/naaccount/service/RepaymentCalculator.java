package org.apache.fineract.portfolio.naaccount.service;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.fineract.portfolio.naaccount.constants.Constants;
import org.apache.fineract.portfolio.naaccount.domain.PaymentPerMonth;
import org.apache.fineract.portfolio.naaccount.domain.RepaymentSchedule;
import org.apache.fineract.portfolio.naaccount.model.Request;
import org.apache.fineract.portfolio.naaccount.utils.DateHelper;
import org.springframework.stereotype.Service;

@Service
public class RepaymentCalculator {

    public List<PaymentPerMonth> generateRepayments(final Request request, List<RepaymentSchedule> repaymentScheduleList) {
        final List<PaymentPerMonth> paymentPerMonthList = new ArrayList<>();
        LocalDate receivedDay = LocalDate.parse(request.getStartDate(), DateTimeFormatter.ofPattern(request.getDateFormat()));
        PaymentPerMonth prevPaymentPerMonth = new PaymentPerMonth(receivedDay, BigDecimal.ZERO, BigDecimal.ZERO,
                BigDecimal.valueOf(request.getLoanAmount()), BigDecimal.ZERO);
        BigDecimal principalPerMonth = getPrincipalPerMonth(request);
        BigDecimal interestRatePerMonth = getInterestRate(request);

        for (int counter = 0; counter < repaymentScheduleList.size(); counter++) {
            prevPaymentPerMonth = getCurrentPayment(prevPaymentPerMonth, repaymentScheduleList, counter, request, principalPerMonth,
                    interestRatePerMonth);
            paymentPerMonthList.add(prevPaymentPerMonth);
        }
        return paymentPerMonthList;
    }

    private BigDecimal getPrincipalPerMonth(Request request) {
        LocalDate receivedDay = LocalDate.parse(request.getStartDate(), DateTimeFormatter.ofPattern(request.getDateFormat()))
                .plusMonths(request.getPrincipalRepaymentFrequency());
        LocalDate firstPrincipalRepaymentDate = LocalDate.parse(request.getPrincipalStartDate(),
                DateTimeFormatter.ofPattern(request.getDateFormat()));
        Long initialPrincipalMoratorium = MONTHS.between(receivedDay, firstPrincipalRepaymentDate);
        return BigDecimal.valueOf(request.getLoanAmount()).divide(
                BigDecimal.valueOf((request.getDuration() - initialPrincipalMoratorium) / request.getPrincipalRepaymentFrequency()),
                request.getNoOfDecimal(), RoundingMode.HALF_UP);
    }

    private BigDecimal getInterestRate(Request request) {
        return BigDecimal.valueOf(request.getNominalInterestRate()).divide(BigDecimal.valueOf(100d), Constants.NO_OF_DECIMALS,
                RoundingMode.HALF_UP);
    }

    private PaymentPerMonth getCurrentPayment(PaymentPerMonth prevPaymentPerMonth, List<RepaymentSchedule> repaymentScheduleList, int index,
            Request request, BigDecimal principalPerMonth, BigDecimal interestRatePerMonth) {
        BigDecimal noOfDaysFromLastPayment;
        PaymentPerMonth currentPaymentPerMonth = new PaymentPerMonth();
        currentPaymentPerMonth.setPaymentDate(repaymentScheduleList.get(index).getRepaymentDate());
        if (Boolean.TRUE.equals(repaymentScheduleList.get(index).getPrincipalDue())) {
            currentPaymentPerMonth.setPrincipalDue(principalPerMonth);
            if (Boolean.TRUE.equals(request.isDiminishingPrincipal())) {
                currentPaymentPerMonth.setRemainingOutstandingPrincipal(
                        prevPaymentPerMonth.getRemainingOutstandingPrincipal().subtract(principalPerMonth));
            } else {
                currentPaymentPerMonth.setRemainingOutstandingPrincipal(prevPaymentPerMonth.getRemainingOutstandingPrincipal());
            }
        } else {
            currentPaymentPerMonth.setPrincipalDue(BigDecimal.ZERO);
            currentPaymentPerMonth.setRemainingOutstandingPrincipal(prevPaymentPerMonth.getRemainingOutstandingPrincipal());
        }

        if (request.getNoOfDaysInMonthMethod() == 1)
            noOfDaysFromLastPayment = BigDecimal
                    .valueOf(DAYS.between(prevPaymentPerMonth.getPaymentDate(), currentPaymentPerMonth.getPaymentDate()));
        else
            noOfDaysFromLastPayment = BigDecimal
                    .valueOf(DateHelper.dateDiff360(false, prevPaymentPerMonth.getPaymentDate(), currentPaymentPerMonth.getPaymentDate()));

        if (Boolean.TRUE.equals(repaymentScheduleList.get(index).getInterestDue()) || (index == repaymentScheduleList.size() - 1)) {
            currentPaymentPerMonth.setInterestDue(
                    prevPaymentPerMonth.getRemainingOutstandingPrincipal().multiply(interestRatePerMonth).multiply(noOfDaysFromLastPayment)
                            .divide(BigDecimal.valueOf(request.getNoOfDaysInYear()), request.getNoOfDecimal(), RoundingMode.HALF_UP));
            currentPaymentPerMonth.setInterestDue(currentPaymentPerMonth.getInterestDue().add(prevPaymentPerMonth.getCarryOverInterest()));
            currentPaymentPerMonth.setCarryOverInterest(BigDecimal.ZERO);
        } else {
            currentPaymentPerMonth.setInterestDue(BigDecimal.ZERO);
            BigDecimal currentCarryOverInterest = prevPaymentPerMonth.getRemainingOutstandingPrincipal().multiply(interestRatePerMonth)
                    .multiply(noOfDaysFromLastPayment)
                    .divide(BigDecimal.valueOf(request.getNoOfDaysInYear()), request.getNoOfDecimal(), RoundingMode.HALF_UP);
            currentPaymentPerMonth.setCarryOverInterest(prevPaymentPerMonth.getCarryOverInterest().add(currentCarryOverInterest));
        }
        return currentPaymentPerMonth;
    }
}
