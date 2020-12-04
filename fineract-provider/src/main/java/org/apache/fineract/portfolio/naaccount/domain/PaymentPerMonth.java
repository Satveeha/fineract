package org.apache.fineract.portfolio.naaccount.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentPerMonth {

    private LocalDate paymentDate;
    private BigDecimal principalDue;
    private BigDecimal interestDue;
    private BigDecimal remainingOutstandingPrincipal;
    private BigDecimal carryOverInterest;

    public PaymentPerMonth() {}

    public PaymentPerMonth(LocalDate paymentDate, BigDecimal principalDue, BigDecimal interestDue, BigDecimal remainingOutstandingPrincipal,
            BigDecimal carryOverInterest) {
        this.paymentDate = paymentDate;
        this.principalDue = principalDue;
        this.interestDue = interestDue;
        this.remainingOutstandingPrincipal = remainingOutstandingPrincipal;
        this.carryOverInterest = carryOverInterest;
    }

    public PaymentPerMonth(PaymentPerMonth oldPaymentPerMonth) {
        this.paymentDate = oldPaymentPerMonth.paymentDate;
        this.principalDue = oldPaymentPerMonth.principalDue;
        this.interestDue = oldPaymentPerMonth.interestDue;
        this.remainingOutstandingPrincipal = oldPaymentPerMonth.remainingOutstandingPrincipal;
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

    public BigDecimal getRemainingOutstandingPrincipal() {
        return remainingOutstandingPrincipal;
    }

    public void setRemainingOutstandingPrincipal(BigDecimal remainingOutstandingPrincipal) {
        this.remainingOutstandingPrincipal = remainingOutstandingPrincipal;
    }

    public BigDecimal getCarryOverInterest() {
        return carryOverInterest;
    }

    public void setCarryOverInterest(BigDecimal carryOverInterest) {
        this.carryOverInterest = carryOverInterest;
    }
}
