package org.apache.fineract.portfolio.naaccount.utils;

import java.time.LocalDate;
import java.time.Month;
import org.apache.fineract.portfolio.naaccount.model.Request;

public class DateHelper {

    private DateHelper() {

    }

    public static LocalDate getLastDayOfMonth(LocalDate nextPaymentDate) {
        return nextPaymentDate.withDayOfMonth(nextPaymentDate.getMonth().length(nextPaymentDate.isLeapYear()));
    }

    public static LocalDate getNextPaymentDate(LocalDate nextPaymentDate, Request request, Boolean isInterest) {
        nextPaymentDate = nextPaymentDate.plusMonths(
                Boolean.TRUE.equals(isInterest) ? request.getInterestRepaymentFrequency() : request.getPrincipalRepaymentFrequency());
        if (Boolean.TRUE.equals(isInterest))
            return nextPaymentDate.withDayOfMonth(
                    Math.min(request.getInterestRepaymentDayOfMonth(), nextPaymentDate.getMonth().length(nextPaymentDate.isLeapYear())));
        else
            return nextPaymentDate.withDayOfMonth(
                    Math.min(request.getPrincipalRepaymentDayOfMonth(), nextPaymentDate.getMonth().length(nextPaymentDate.isLeapYear())));
    }

    public static Boolean isLeapYear(Integer year) {
        return ((((year % 4) == 0) && ((year % 100) != 0)) || ((year % 400) == 0));
    }

    public static Integer dateDiff360(Boolean methodUS, LocalDate startDate, LocalDate endDate) {
        if (startDate.getDayOfMonth() == 31) {
            startDate = startDate.minusDays(1);
        } else if (Boolean.TRUE.equals(methodUS) && (startDate.getMonth().equals(Month.FEBRUARY)
                && (startDate.getDayOfMonth() == 29 || (startDate.getDayOfMonth() == 28 && !isLeapYear(startDate.getYear()))))) {
            startDate = startDate.withDayOfMonth(30);
        }
        if (endDate.getDayOfMonth() == 31) {
            if (Boolean.TRUE.equals(methodUS) && startDate.getDayOfMonth() != 30) {
                endDate = endDate.withDayOfMonth(1);
                if (endDate.getMonth().equals(Month.DECEMBER)) {
                    endDate = endDate.plusYears(1).withDayOfMonth(1);
                } else {
                    endDate = endDate.plusMonths(1);
                }
            } else {
                endDate = endDate.withDayOfMonth(30);
            }
        }
        return endDate.getDayOfMonth() + endDate.getMonthValue() * 30 + endDate.getYear() * 360 - startDate.getDayOfMonth()
                - startDate.getMonthValue() * 30 - startDate.getYear() * 360;
    }
}
