package org.apache.fineract.portfolio.naaccount.service;

import static org.apache.fineract.portfolio.naaccount.utils.DateHelper.getNextPaymentDate;

import java.time.LocalDate;
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
        List<RepaymentSchedule> repaymentSchedule = new ArrayList<>();
        LocalDate finalDate = request.getStartDate().plusMonths(request.getTenor());

        if (request.getCustomInterestRepayments() == null || request.getCustomInterestRepayments().isEmpty())
            generateInterestRepaymentDates(request.getInterestStartDate(), finalDate, request, repaymentSchedule);
        else
            request.getCustomInterestRepayments().forEach(repayment -> repaymentSchedule.add(new RepaymentSchedule(repayment)));

        if (request.getCustomPrincipalRepayments() == null || request.getCustomPrincipalRepayments().isEmpty())
            generatePrincipalRepaymentDates(request.getPrincipalStartDate(), finalDate, request, repaymentSchedule);
        else
            request.getCustomPrincipalRepayments().forEach(repayment -> repaymentSchedule.add(new RepaymentSchedule(repayment)));

        if (request.getFloatingInterestRates() != null && !request.getFloatingInterestRates().isEmpty())
            request.getFloatingInterestRates().forEach(interestRate -> repaymentSchedule.add(new RepaymentSchedule(interestRate)));

        removeDuplicatesIfExists(repaymentSchedule);
        return repaymentSchedule;
    }

    private void removeDuplicatesIfExists(List<RepaymentSchedule> repaymentSchedule) {
        Collections.sort(repaymentSchedule);
        int i = 1;
        while (i < repaymentSchedule.size()) {
            if (repaymentSchedule.get(i).getRepaymentDate().equals(repaymentSchedule.get(i - 1).getRepaymentDate())
                    && repaymentSchedule.get(i).getInterestRateChange().equals(Boolean.FALSE)
                    && repaymentSchedule.get(i - 1).getInterestRateChange().equals(Boolean.FALSE)) {
                repaymentSchedule.get(i - 1).setInterestDue(Boolean.TRUE);
                repaymentSchedule.get(i - 1).setPrincipalDue(Boolean.TRUE);
                repaymentSchedule.get(i - 1).setInterestRateChange(Boolean.FALSE);
                repaymentSchedule.remove(i);
                i--;
            }
            i++;
        }
    }

    private void generateInterestRepaymentDates(LocalDate firstInterestRepaymentDate, LocalDate finalDate, Request request,
            List<RepaymentSchedule> interestRepaymentList) {
        LocalDate nextPaymentDate = firstInterestRepaymentDate;
        while (!nextPaymentDate.isAfter(finalDate)) {
            interestRepaymentList.add(new RepaymentSchedule(nextPaymentDate, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE));
            nextPaymentDate = getNextPaymentDate(nextPaymentDate, request, Boolean.TRUE);

        }
    }

    private void generatePrincipalRepaymentDates(LocalDate firstPrincipalRepaymentDate, LocalDate finalDate, Request request,
            List<RepaymentSchedule> principalRepayments) {
        LocalDate nextPaymentDate = firstPrincipalRepaymentDate;
        while (!nextPaymentDate.isAfter(finalDate)) {
            principalRepayments
                    .add(new RepaymentSchedule(nextPaymentDate, Boolean.TRUE, request.isInterestDueWithPrincipal(), Boolean.FALSE));
            nextPaymentDate = getNextPaymentDate(nextPaymentDate, request, Boolean.FALSE);
        }
    }

}
