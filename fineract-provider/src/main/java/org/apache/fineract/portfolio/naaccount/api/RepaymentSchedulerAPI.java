package org.apache.fineract.portfolio.naaccount.api;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleInstallment;
import org.apache.fineract.portfolio.naaccount.exception.ApiException;
import org.apache.fineract.portfolio.naaccount.model.Request;
import org.apache.fineract.portfolio.naaccount.model.Response;
import org.apache.fineract.portfolio.naaccount.service.RepaymentSchedulerService;
import org.apache.fineract.portfolio.naaccount.service.SampleService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * The RepaymentSchedulerController is used to generate the repayment schedule.
 */
@Path("/generate-repayment-schedule")
@Component
@Scope("singleton")
public class RepaymentSchedulerAPI {

    private final RepaymentSchedulerService repaymentSchedulerService;
    private final SampleService sampleService;

    @Autowired
    public RepaymentSchedulerAPI(RepaymentSchedulerService repaymentSchedulerService, SampleService sampleService) {
        this.repaymentSchedulerService = repaymentSchedulerService;
        this.sampleService = sampleService;
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @ApiResponses(@ApiResponse(responseCode = "200", description = "OK"))
    public List<Response> generateNewPaymentPlan(@Parameter Request request) throws ApiException {
        List<Response> response = repaymentSchedulerService.generateSchedule(request);

        Optional<Loan> loanData = this.sampleService.retrieveLoanById(request.getLoanId());
        if (!loanData.isEmpty()) {
            Loan loan = loanData.get();
            // List<LoanRepaymentScheduleInstallment> installments =
            // this.sampleService.retrieveRepaymentScheduleInstallmentByLoanId(loan);
            List<LoanRepaymentScheduleInstallment> installments = loan.getRepaymentScheduleInstallments();
            int installment = request.getInstallment();

            for (Response repaymentCalculatedData : response) {
                for (LoanRepaymentScheduleInstallment repaymentSchedule : installments) {
                    if (repaymentSchedule.getInstallmentNumber() == installment) {
                        repaymentSchedule.updatePrincipal(repaymentCalculatedData.getPrincipalDue());
                        repaymentSchedule.updateInterestCharged(repaymentCalculatedData.getInterestDue());
                        repaymentSchedule.updateDueDate(LocalDate.parse(repaymentCalculatedData.getPaymentDate()));
                        this.sampleService.saveLoanRepaymentScheduleInstallment(repaymentSchedule);
                        break;
                    }
                }
                installment++;
            }
        }

        return repaymentSchedulerService.generateSchedule(request);
    }
}
