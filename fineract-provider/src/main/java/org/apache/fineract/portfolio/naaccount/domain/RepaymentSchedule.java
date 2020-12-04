package org.apache.fineract.portfolio.naaccount.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class RepaymentSchedule implements Comparable<RepaymentSchedule> {

    private LocalDate repaymentDate;
    @JsonProperty
    private Boolean isPrincipalDue;
    @JsonProperty
    private Boolean isInterestDue;
    private String date;

    public RepaymentSchedule() {}

    public RepaymentSchedule(LocalDate repaymentDate, Boolean isPrincipalDue, Boolean isInterestDue) {
        this.repaymentDate = repaymentDate;
        this.isPrincipalDue = isPrincipalDue;
        this.isInterestDue = isInterestDue;
    }

    public LocalDate getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(LocalDate repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public Boolean getPrincipalDue() {
        return isPrincipalDue;
    }

    public void setPrincipalDue(Boolean principalDue) {
        isPrincipalDue = principalDue;
    }

    public Boolean getInterestDue() {
        return isInterestDue;
    }

    public void setInterestDue(Boolean interestDue) {
        isInterestDue = interestDue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int compareTo(RepaymentSchedule o) {
        return repaymentDate.compareTo(o.repaymentDate);
    }
}
