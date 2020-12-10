package org.apache.fineract.portfolio.naaccount.service;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.fineract.portfolio.naaccount.constants.Constants;
import org.apache.fineract.portfolio.naaccount.constants.NumberOfDaysInMonth;
import org.apache.fineract.portfolio.naaccount.domain.PaymentPerMonth;
import org.apache.fineract.portfolio.naaccount.domain.RepaymentSchedule;
import org.apache.fineract.portfolio.naaccount.model.Request;
import org.apache.fineract.portfolio.naaccount.utils.DateHelper;
import org.springframework.stereotype.Service;

@Service
public class RepaymentCalculator {

    public List<PaymentPerMonth> generateRepayments(final Request request, List<RepaymentSchedule> repaymentScheduleList) {
        List<PaymentPerMonth> monthlyPayments = new ArrayList<>();
        LocalDate receivedDay = request.getStartDate();

        PaymentPerMonth prevPaymentPerMonth = new PaymentPerMonth(receivedDay, BigDecimal.ZERO, BigDecimal.ZERO,
                BigDecimal.valueOf(request.getPrincipal()), BigDecimal.ZERO);
        BigDecimal principalPerMonth = getPrincipalPerMonth(request);
        BigDecimal interestRatePerMonth = getInterestRate(request.getNominalInterestRate());

        Map<LocalDate, BigDecimal> customPrincipalRepayments = new HashMap<>();
        if (request.getCustomPrincipalRepayments() != null && !request.getCustomPrincipalRepayments().isEmpty()) {
            request.getCustomPrincipalRepayments().forEach(customPrincipalRepayment -> customPrincipalRepayments
                    .put(customPrincipalRepayment.getDate(), customPrincipalRepayment.getAmount()));
        }

        Map<LocalDate, Double> floatingInterestRates = new HashMap<>();
        if (request.getFloatingInterestRates() != null && !request.getFloatingInterestRates().isEmpty()) {
            request.getFloatingInterestRates()
                    .forEach(interestRate -> floatingInterestRates.put(interestRate.getDate(), interestRate.getInterestRate()));
        }

        for (int counter = 0; counter < repaymentScheduleList.size(); counter++) {
            if (customPrincipalRepayments.containsKey(repaymentScheduleList.get(counter).getRepaymentDate())) {
                principalPerMonth = customPrincipalRepayments.get(repaymentScheduleList.get(counter).getRepaymentDate());
            }
            prevPaymentPerMonth = getCurrentPayment(prevPaymentPerMonth, repaymentScheduleList, counter, request, principalPerMonth,
                    interestRatePerMonth, floatingInterestRates);
            monthlyPayments.add(prevPaymentPerMonth);
        }
        monthlyPayments = removeDuplicates(monthlyPayments);
        return monthlyPayments;
    }

    @SuppressWarnings("BigDecimalEquals")
    private List<PaymentPerMonth> removeDuplicates(List<PaymentPerMonth> monthlyPayments) {
        return monthlyPayments.stream().filter(paymentPerMonth -> !paymentPerMonth.getPrincipalDue().equals(BigDecimal.ZERO)
                || !paymentPerMonth.getInterestDue().equals(BigDecimal.ZERO)).collect(Collectors.toList());
    }

    private BigDecimal getPrincipalPerMonth(Request request) {
        LocalDate receivedDay = request.getStartDate().plusMonths(request.getPrincipalRepaymentFrequency());
        LocalDate firstPrincipalRepaymentDate = request.getPrincipalStartDate();
        Long initialPrincipalMoratorium = MONTHS.between(receivedDay, firstPrincipalRepaymentDate);
        return BigDecimal.valueOf(request.getPrincipal()).divide(
                BigDecimal.valueOf((request.getTenor() - initialPrincipalMoratorium) / request.getPrincipalRepaymentFrequency()),
                request.getNoOfDecimal(), RoundingMode.HALF_UP);
    }

    private BigDecimal getInterestRate(Double interestRate) {
        return BigDecimal.valueOf(interestRate).divide(BigDecimal.valueOf(100d), Constants.NO_OF_DECIMALS, RoundingMode.HALF_UP);
    }

    @SuppressWarnings("BigDecimalEquals")
    private PaymentPerMonth getCurrentPayment(PaymentPerMonth prevPayment, List<RepaymentSchedule> repaymentSchedule, int index,
            Request request, BigDecimal principalPerMonth, BigDecimal interestRatePerMonth, Map<LocalDate, Double> floatingInterestRates) {
        BigDecimal noOfDaysFromLastPayment;
        PaymentPerMonth currentPayment = new PaymentPerMonth();
        currentPayment.setPaymentDate(repaymentSchedule.get(index).getRepaymentDate());

        if (floatingInterestRates.containsKey(currentPayment.getPaymentDate())) {
            interestRatePerMonth = getInterestRate(floatingInterestRates.get(currentPayment.getPaymentDate()));
        }

        if (Boolean.TRUE.equals(repaymentSchedule.get(index).getPrincipalDue())) {
            currentPayment.setPrincipalDue(principalPerMonth);
            if (Boolean.TRUE.equals(request.isDiminishingPrincipal())) {
                currentPayment.setOutstandingPrincipal(prevPayment.getOutstandingPrincipal().subtract(principalPerMonth));
            } else
                currentPayment.setOutstandingPrincipal(prevPayment.getOutstandingPrincipal());
        } else {
            currentPayment.setPrincipalDue(BigDecimal.ZERO);
            currentPayment.setOutstandingPrincipal(prevPayment.getOutstandingPrincipal());
        }

        if (request.getNoOfDaysInMonth().equalsIgnoreCase(NumberOfDaysInMonth.ACTUAL.toString())) {
            noOfDaysFromLastPayment = BigDecimal.valueOf(DAYS.between(prevPayment.getPaymentDate(), currentPayment.getPaymentDate()));
        } else {
            noOfDaysFromLastPayment = BigDecimal
                    .valueOf(DateHelper.dateDiff360(false, prevPayment.getPaymentDate(), currentPayment.getPaymentDate()));
        }

        if (Boolean.TRUE.equals(repaymentSchedule.get(index).getInterestDue()) || (index == repaymentSchedule.size() - 1)) {
            currentPayment
                    .setInterestDue(prevPayment.getOutstandingPrincipal().multiply(interestRatePerMonth).multiply(noOfDaysFromLastPayment)
                            .divide(BigDecimal.valueOf(request.getNoOfDaysInYear()), request.getNoOfDecimal(), RoundingMode.HALF_UP));
            currentPayment.setInterestDue(currentPayment.getInterestDue().add(prevPayment.getCarryOverInterest()));
            currentPayment.setCarryOverInterest(BigDecimal.ZERO);
        } else {
            currentPayment.setInterestDue(BigDecimal.ZERO);
            BigDecimal currentCarryOverInterest = prevPayment.getOutstandingPrincipal().multiply(interestRatePerMonth)
                    .multiply(noOfDaysFromLastPayment)
                    .divide(BigDecimal.valueOf(request.getNoOfDaysInYear()), request.getNoOfDecimal(), RoundingMode.HALF_UP);
            currentPayment.setCarryOverInterest(prevPayment.getCarryOverInterest().add(currentCarryOverInterest));
        }
        return currentPayment;
    }
}
