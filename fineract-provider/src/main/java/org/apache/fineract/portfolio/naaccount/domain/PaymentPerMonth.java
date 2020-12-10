package org.apache.fineract.portfolio.naaccount.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentPerMonth {

    private LocalDate paymentDate;
    private BigDecimal principalDue;
    private BigDecimal interestDue;
    private BigDecimal outstandingPrincipal;
    private BigDecimal carryOverInterest;

    public PaymentPerMonth() {}

    public PaymentPerMonth(LocalDate paymentDate, BigDecimal principalDue, BigDecimal interestDue, BigDecimal outstandingPrincipal,
            BigDecimal carryOverInterest) {
        this.paymentDate = paymentDate;
        this.principalDue = principalDue;
        this.interestDue = interestDue;
        this.outstandingPrincipal = outstandingPrincipal;
        this.carryOverInterest = carryOverInterest;
    }

    public PaymentPerMonth(PaymentPerMonth oldPaymentPerMonth) {
        this.paymentDate = oldPaymentPerMonth.paymentDate;
        this.principalDue = oldPaymentPerMonth.principalDue;
        this.interestDue = oldPaymentPerMonth.interestDue;
        this.outstandingPrincipal = oldPaymentPerMonth.outstandingPrincipal;
        this.carryOverInterest = oldPaymentPerMonth.carryOverInterest;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
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

    public BigDecimal getOutstandingPrincipal() {
        return outstandingPrincipal;
    }

    public void setOutstandingPrincipal(BigDecimal outstandingPrincipal) {
        this.outstandingPrincipal = outstandingPrincipal;
    }

    public BigDecimal getCarryOverInterest() {
        return carryOverInterest;
    }

    public void setCarryOverInterest(BigDecimal carryOverInterest) {
        this.carryOverInterest = carryOverInterest;
    }
}
