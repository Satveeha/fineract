package org.apache.fineract.portfolio.naaccount.domain;

import java.time.LocalDate;
import org.apache.fineract.portfolio.naaccount.model.CustomInterestRepayment;
import org.apache.fineract.portfolio.naaccount.model.CustomPrincipalRepayment;
import org.apache.fineract.portfolio.naaccount.model.InterestRateInfo;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepaymentSchedule implements Comparable<RepaymentSchedule> {

    /*
     * @JsonFormat(pattern = "yyyy-MM-dd")
     *
     * @JsonDeserialize(using = LocalDateDeserializer.class)
     *
     * @JsonSerialize(using = LocalDateSerializer.class)
     */
    private LocalDate repaymentDate;
    private Boolean isPrincipalDue;
    private Boolean isInterestDue;
    private Boolean isInterestRateChange;

    public RepaymentSchedule() {}

    public RepaymentSchedule(LocalDate repaymentDate, Boolean isPrincipalDue, Boolean isInterestDue, Boolean isInterestRateChange) {
        this.repaymentDate = repaymentDate;
        this.isPrincipalDue = isPrincipalDue;
        this.isInterestDue = isInterestDue;
        this.isInterestRateChange = isInterestRateChange;
    }

    public RepaymentSchedule(CustomPrincipalRepayment repayment) {
        this.repaymentDate = repayment.getDate();
        this.isPrincipalDue = Boolean.TRUE;
        this.isInterestDue = repayment.getCoupled();
        this.isInterestRateChange = Boolean.FALSE;
    }

    public RepaymentSchedule(CustomInterestRepayment repayment) {
        this.repaymentDate = repayment.getDate();
        this.isPrincipalDue = Boolean.FALSE;
        this.isInterestDue = Boolean.TRUE;
        this.isInterestRateChange = Boolean.FALSE;
    }

    public RepaymentSchedule(InterestRateInfo interestRateInfo) {
        this.repaymentDate = interestRateInfo.getDate();
        this.isPrincipalDue = Boolean.FALSE;
        this.isInterestDue = Boolean.FALSE;
        this.isInterestRateChange = Boolean.TRUE;
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

    public Boolean getInterestRateChange() {
        return isInterestRateChange;
    }

    public void setInterestRateChange(Boolean interestRateChange) {
        isInterestRateChange = interestRateChange;
    }

    @Override
    public int compareTo(RepaymentSchedule repaymentSchedule) {
        if (this.repaymentDate.equals(repaymentSchedule.getRepaymentDate())) {
            if (this.isInterestRateChange.equals(Boolean.TRUE))
                return -1;
            else if (repaymentSchedule.isInterestRateChange.equals(Boolean.TRUE))
                return 1;
            else
                return 0;
        }
        return repaymentDate.compareTo(repaymentSchedule.repaymentDate);
    }

    // @Override
    // public boolean equals(Object obj) {
    // if (obj == null) {
    // return false;
    // }
    //
    // if (obj.getClass() != this.getClass()) {
    // return false;
    // }
    //
    // final RepaymentSchedule other = (RepaymentSchedule) obj;
    // return this.equals(other);
    // }

}
