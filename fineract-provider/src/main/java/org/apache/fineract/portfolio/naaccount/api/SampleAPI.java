package org.apache.fineract.portfolio.naaccount.api;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.apache.fineract.organisation.monetary.domain.MonetaryCurrency;
import org.apache.fineract.portfolio.common.domain.PeriodFrequencyType;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.LoanInterestRecalcualtionAdditionalDetails;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleInstallment;
import org.apache.fineract.portfolio.loanaccount.domain.LoanSummary;
import org.apache.fineract.portfolio.loanproduct.domain.AmortizationMethod;
import org.apache.fineract.portfolio.loanproduct.domain.InterestCalculationPeriodMethod;
import org.apache.fineract.portfolio.loanproduct.domain.InterestMethod;
import org.apache.fineract.portfolio.loanproduct.domain.LoanProductRelatedDetail;
import org.apache.fineract.portfolio.naaccount.service.SampleService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Path("/sampleNewApi")
@Component
@Scope("singleton")
public class SampleAPI {

    private final SampleService sampleService;

    @Autowired
    public SampleAPI(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @GET
    @Path("sampleUpdate")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @ApiResponses(@ApiResponse(responseCode = "200", description = "OK"))
    public String update() {
        String returnValue = "Not Updated";

        Optional<Loan> loanData = this.sampleService.retrieveLoanById(17L);
        if (!loanData.isEmpty()) {
            Loan loan = loanData.get();
            // List<LoanRepaymentScheduleInstallment> installments =
            // this.sampleService.retrieveRepaymentScheduleInstallmentByLoanId(loan);
            List<LoanRepaymentScheduleInstallment> installments = loan.getRepaymentScheduleInstallments();

            if (!installments.isEmpty()) {
                installments.get(0).updatePrincipal(new BigDecimal(50.0));

                if (this.sampleService.insertLoan(loan)) {
                    returnValue = "Updated";
                }
            }
        }

        return returnValue;
    }

    @GET
    @Path("sampleNew")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @ApiResponses(@ApiResponse(responseCode = "200", description = "OK"))
    public String sample() {
        Boolean returnValue = false;
        String accountNo = "";
        Integer loanStatus = 1;
        Integer loanType = 1;
        BigDecimal proposedPrincipal = new BigDecimal(1.0);

        MonetaryCurrency currency = new MonetaryCurrency("USD", 2, 0);
        final BigDecimal defaultPrincipal = new BigDecimal(1.0);
        final BigDecimal defaultNominalInterestRatePerPeriod = new BigDecimal(1.0);
        final PeriodFrequencyType interestPeriodFrequencyType = PeriodFrequencyType.fromInt(1);
        final BigDecimal defaultAnnualNominalInterestRate = new BigDecimal(1.0);
        final InterestMethod interestMethod = InterestMethod.fromInt(1);
        final InterestCalculationPeriodMethod interestCalculationPeriodMethod = InterestCalculationPeriodMethod.fromInt(1);
        final boolean allowPartialPeriodInterestCalcualtion = true;
        final Integer repayEvery = 1;
        final PeriodFrequencyType repaymentFrequencyType = PeriodFrequencyType.fromInt(1);
        ;
        final Integer defaultNumberOfRepayments = 1;
        final Integer graceOnPrincipalPayment = 1;
        final Integer recurringMoratoriumOnPrincipalPeriods = 1;
        final Integer graceOnInterestPayment = 1;
        final Integer graceOnInterestCharged = 1;
        final AmortizationMethod amortizationMethod = AmortizationMethod.fromInt(1);
        final BigDecimal inArrearsTolerance = new BigDecimal(1.0);
        final Integer graceOnArrearsAgeing = 1;
        final Integer daysInMonthType = 1;
        final Integer daysInYearType = 1;
        final boolean isInterestRecalculationEnabled = true;
        final boolean isEqualAmortization = true;

        LoanProductRelatedDetail loanProductRelatedDetail = new LoanProductRelatedDetail(currency, defaultPrincipal,
                defaultNominalInterestRatePerPeriod, interestPeriodFrequencyType, defaultAnnualNominalInterestRate, interestMethod,
                interestCalculationPeriodMethod, allowPartialPeriodInterestCalcualtion, repayEvery, repaymentFrequencyType,
                defaultNumberOfRepayments, graceOnPrincipalPayment, recurringMoratoriumOnPrincipalPeriods, graceOnInterestPayment,
                graceOnInterestCharged, amortizationMethod, inArrearsTolerance, graceOnArrearsAgeing, daysInMonthType, daysInYearType,
                isInterestRecalculationEnabled, isEqualAmortization);

        BigDecimal approvedPrincipal = new BigDecimal(1.0);
        Integer termFrequency = 1;
        Integer termPeriodFrequencyType = 1;

        LoanSummary loanSummary = new LoanSummary();

        Loan loan = new Loan(accountNo, loanStatus, loanType, proposedPrincipal, loanProductRelatedDetail, approvedPrincipal, termFrequency,
                termPeriodFrequencyType, loanSummary);

        Integer installmentNumber = 1;
        LocalDate dueDate = new LocalDate();
        boolean obligationsMet = true;
        boolean recalculatedInterestComponent = true;
        LoanInterestRecalcualtionAdditionalDetails loanInterestRecalcualtionAdditionalDetails = new LoanInterestRecalcualtionAdditionalDetails(
                new LocalDate(), new BigDecimal(1.0));
        Set<LoanInterestRecalcualtionAdditionalDetails> loanCompoundingDetails = new HashSet<LoanInterestRecalcualtionAdditionalDetails>();
        loanCompoundingDetails.add(loanInterestRecalcualtionAdditionalDetails);

        LoanRepaymentScheduleInstallment installment = new LoanRepaymentScheduleInstallment(loan, installmentNumber, dueDate,
                obligationsMet, recalculatedInterestComponent, loanCompoundingDetails, new BigDecimal(1.0), new BigDecimal(1.0));

        loan.addLoanRepaymentScheduleInstallment(installment);

        if (this.sampleService.insertLoan(loan)) {
            returnValue = true;
        }

        return returnValue.toString();
    }

    @GET
    @Path("loanRepaymentScheduleUpdate")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @ApiResponses(@ApiResponse(responseCode = "200", description = "OK"))
    public String loanRepaymentScheduleUpdate(
            @QueryParam("principalAmount") @Parameter(name = "principalAmount") BigDecimal principalAmount,
            @QueryParam("interestAmount") @Parameter(name = "interestAmount") BigDecimal interestAmount,
            @QueryParam("id") @Parameter(name = "id") Long id) {
        String returnValue = "Not Updated";

        Optional<LoanRepaymentScheduleInstallment> loanRepaymentScheduleInstallmentData = this.sampleService
                .retrieveLoanRepaymentScheduleInstallmentById(id);
        if (!loanRepaymentScheduleInstallmentData.isEmpty()) {
            LoanRepaymentScheduleInstallment loanRepaymentScheduleInstallment = loanRepaymentScheduleInstallmentData.get();
            loanRepaymentScheduleInstallment.updatePrincipal(principalAmount);
            loanRepaymentScheduleInstallment.updateInterestCharged(interestAmount);

            if (this.sampleService.saveLoanRepaymentScheduleInstallment(loanRepaymentScheduleInstallment)) {
                returnValue = "Updated";
            }
        }

        return returnValue;
    }
}
