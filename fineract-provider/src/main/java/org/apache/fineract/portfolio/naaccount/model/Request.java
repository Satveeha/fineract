package org.apache.fineract.portfolio.naaccount.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class Request {

    private Long loanId;
    private int installment;
    private Double principal;
    private Integer tenor;
    private String startDate;
    private String interestStartDate;
    private String principalStartDate;
    private Integer noOfDecimal;
    private boolean isDiminishingPrincipal;
    private Integer interestRepaymentFrequency;
    private Integer principalRepaymentFrequency;
    private Double nominalInterestRate;
    private boolean isInterestDueWithPrincipal;
    private Integer principalRepaymentDayOfMonth;
    private Integer interestRepaymentDayOfMonth;
    private Integer noOfDaysInYear;
    private String noOfDaysInMonth;
    private List<CustomInterestRepayment> customInterestRepayments;
    private List<CustomPrincipalRepayment> customPrincipalRepayments;
    private List<InterestRateInfo> floatingInterestRates;

    public Long getLoanId() {
        return loanId;
    }

    public int getInstallment() {
        return installment;
    }

    public Double getPrincipal() {
        return principal;
    }

    public Integer getTenor() {
        return tenor;
    }

    public LocalDate getStartDate() {
        return LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public LocalDate getInterestStartDate() {
        return LocalDate.parse(interestStartDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public LocalDate getPrincipalStartDate() {
        return LocalDate.parse(principalStartDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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

    public Integer getPrincipalRepaymentDayOfMonth() {
        return principalRepaymentDayOfMonth;
    }

    public Integer getInterestRepaymentDayOfMonth() {
        return interestRepaymentDayOfMonth;
    }

    public Integer getNoOfDaysInYear() {
        return noOfDaysInYear;
    }

    public String getNoOfDaysInMonth() {
        return noOfDaysInMonth;
    }

    public List<CustomInterestRepayment> getCustomInterestRepayments() {
        return customInterestRepayments;
    }

    public List<CustomPrincipalRepayment> getCustomPrincipalRepayments() {
        return customPrincipalRepayments;
    }

    public List<InterestRateInfo> getFloatingInterestRates() {
        return floatingInterestRates;
    }
}
