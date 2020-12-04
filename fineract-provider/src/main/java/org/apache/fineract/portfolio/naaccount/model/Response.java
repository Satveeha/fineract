package org.apache.fineract.portfolio.naaccount.model;

import java.math.BigDecimal;
import org.apache.fineract.portfolio.naaccount.domain.PaymentPerMonth;

public class Response {

    private String paymentDate;
    private BigDecimal principalDue;
    private BigDecimal interestDue;
    private BigDecimal totalAmountDue;

    public Response(final PaymentPerMonth paymentPerMonth) {
        this.paymentDate = paymentPerMonth.getPaymentDate().toString();
        this.principalDue = paymentPerMonth.getPrincipalDue();
        this.interestDue = paymentPerMonth.getInterestDue();
        this.totalAmountDue = paymentPerMonth.getPrincipalDue().add(paymentPerMonth.getInterestDue());
    }

    public Response(String paymentDate, BigDecimal principalDue, BigDecimal interestDue, BigDecimal totalAmountDue) {
        this.paymentDate = paymentDate;
        this.principalDue = principalDue;
        this.interestDue = interestDue;
        this.totalAmountDue = totalAmountDue;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getPrincipalDue() {
        return principalDue;
    }

    public void setPrincipalDue(BigDecimal principalDue) {
        this.principalDue = principalDue;
    }

    public BigDecimal getInterestDue() {
        return interestDue;
    }

    public void setInterestDue(BigDecimal interestDue) {
        this.interestDue = interestDue;
    }

    public BigDecimal getTotalAmountDue() {
        return totalAmountDue;
    }

    public void setTotalAmountDue(BigDecimal totalAmountDue) {
        this.totalAmountDue = totalAmountDue;
    }
}
