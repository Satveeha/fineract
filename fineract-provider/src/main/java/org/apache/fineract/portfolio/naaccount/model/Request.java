package org.apache.fineract.portfolio.naaccount.model;

import java.util.List;
import org.apache.fineract.portfolio.naaccount.domain.RepaymentSchedule;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Request {

    private Double loanAmount;
    private Integer duration;
    private String startDate;
    private String interestStartDate;
    private String principalStartDate;
    private Integer noOfDecimal;
    private boolean isDiminishingPrincipal;
    private Integer interestRepaymentFrequency;
    private Integer principalRepaymentFrequency;
    private Double nominalInterestRate;
    private boolean isInterestDueWithPrincipal;
    private boolean isPrincipalDueWithInterest;
    private Integer principalRepaymentDayOfMonth;
    private Integer interestRepaymentDayOfMonth;
    private Integer noOfDaysInYear;
    private Integer noOfDaysInMonthMethod;
    private String dateFormat;
    private List<RepaymentSchedule> principalDueDates;
    private List<RepaymentSchedule> interestDueDates;

    public Double getLoanAmount() {
        return loanAmount;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getInterestStartDate() {
        return interestStartDate;
    }

    public String getPrincipalStartDate() {
        return principalStartDate;
    }

    public Integer getNoOfDecimal() {
        return noOfDecimal;
    }

    public boolean isDiminishingPrincipal() {
        return isDiminishingPrincipal;
    }

    public Integer getInterestRepaymentFrequency() {
        return interestRepaymentFrequency;
    }

    public Integer getPrincipalRepaymentFrequency() {
        return principalRepaymentFrequency;
    }

    public Double getNominalInterestRate() {
        return nominalInterestRate;
    }

    public boolean isInterestDueWithPrincipal() {
        return isInterestDueWithPrincipal;
    }

    public boolean isPrincipalDueWithInterest() {
        return isPrincipalDueWithInterest;
    }

    public Integer getPrincipalRepaymentDayOfMonth() {
        return principalRepaymentDayOfMonth;
    }

    public Integer getInterestRepaymentDayOfMonth() {
        return interestRepaymentDayOfMonth;
    }

    public Integer getNoOfDaysInYear() {
        return noOfDaysInYear;
    }

    public Integer getNoOfDaysInMonthMethod() {
        return noOfDaysInMonthMethod;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public List<RepaymentSchedule> getPrincipalDueDates() {
        return principalDueDates;
    }

    public List<RepaymentSchedule> getInterestDueDates() {
        return interestDueDates;
    }
}
