package org.apache.fineract.portfolio.naaccount.service;

import static org.apache.fineract.portfolio.naaccount.utils.DateHelper.getNextPaymentDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.fineract.portfolio.naaccount.domain.RepaymentSchedule;
import org.apache.fineract.portfolio.naaccount.model.Request;
import org.springframework.stereotype.Service;

/**
 * DateCalculator is used the generate the interest and principal repayment dates based on the start date(s) and their
 * corresponding frequencies.
 */
@Service
public class DateCalculator {

    public List<RepaymentSchedule> generateRepaymentSchedule(Request request) {
        List<RepaymentSchedule> repaymentScheduleList = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(request.getDateFormat());
        LocalDate receivedDay = LocalDate.parse(request.getStartDate(), dateTimeFormatter);
        LocalDate firstInterestRepaymentDate = LocalDate.parse(request.getInterestStartDate(), dateTimeFormatter);
        LocalDate firstPrincipalRepaymentDate = LocalDate.parse(request.getPrincipalStartDate(), dateTimeFormatter);
        LocalDate finalDate = receivedDay.plusMonths(request.getDuration());

        if (request.getInterestDueDates() == null || request.getInterestDueDates().isEmpty()) {
            generateInterestRepaymentDates(firstInterestRepaymentDate, finalDate, request, repaymentScheduleList);
        } else {
            request.getInterestDueDates()
                    .forEach(schedule -> schedule.setRepaymentDate(LocalDate.parse(schedule.getDate(), dateTimeFormatter)));
            repaymentScheduleList.addAll(request.getInterestDueDates());
        }

        if (request.getPrincipalDueDates() == null || request.getPrincipalDueDates().isEmpty())
            generatePrincipalRepaymentDates(firstPrincipalRepaymentDate, finalDate, request, repaymentScheduleList);
        else {
            request.getPrincipalDueDates()
                    .forEach(schedule -> schedule.setRepaymentDate(LocalDate.parse(schedule.getDate(), dateTimeFormatter)));
            repaymentScheduleList.addAll(request.getPrincipalDueDates());
        }

        Collections.sort(repaymentScheduleList);

        for (int i = 1; i < repaymentScheduleList.size(); i++) {
            if (repaymentScheduleList.get(i).getRepaymentDate().equals(repaymentScheduleList.get(i - 1).getRepaymentDate())) {
                repaymentScheduleList.get(i - 1).setInterestDue(Boolean.TRUE);
                repaymentScheduleList.get(i - 1).setPrincipalDue(Boolean.TRUE);
                repaymentScheduleList.remove(i);
                i--;
            }
        }

        return repaymentScheduleList;
    }

    private void generateInterestRepaymentDates(LocalDate firstInterestRepaymentDate, LocalDate finalDate, Request request,
            List<RepaymentSchedule> interestRepaymentList) {
        LocalDate nextPaymentDate = firstInterestRepaymentDate;
        while (!nextPaymentDate.isAfter(finalDate)) {
            interestRepaymentList.add(new RepaymentSchedule(nextPaymentDate, request.isPrincipalDueWithInterest(), true));
            nextPaymentDate = getNextPaymentDate(nextPaymentDate, request, true);

        }
    }

    private void generatePrincipalRepaymentDates(LocalDate firstPrincipalRepaymentDate, LocalDate finalDate, Request request,
            List<RepaymentSchedule> principalRepaymentList) {
        LocalDate nextPaymentDate = firstPrincipalRepaymentDate;
        while (!nextPaymentDate.isAfter(finalDate)) {
            principalRepaymentList.add(new RepaymentSchedule(nextPaymentDate, true, request.isInterestDueWithPrincipal()));
            nextPaymentDate = getNextPaymentDate(nextPaymentDate, request, false);
        }
    }

}
