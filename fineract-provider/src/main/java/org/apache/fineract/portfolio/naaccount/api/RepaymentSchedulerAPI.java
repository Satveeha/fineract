package org.apache.fineract.portfolio.naaccount.api;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.fineract.portfolio.naaccount.exception.ApiException;
import org.apache.fineract.portfolio.naaccount.model.Request;
import org.apache.fineract.portfolio.naaccount.model.Response;
import org.apache.fineract.portfolio.naaccount.service.RepaymentSchedulerService;
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

    @Autowired
    public RepaymentSchedulerAPI(RepaymentSchedulerService repaymentSchedulerService) {
        this.repaymentSchedulerService = repaymentSchedulerService;
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @ApiResponses(@ApiResponse(responseCode = "200", description = "OK"))
    public List<Response> generateNewPaymentPlan(@Parameter Request request) throws ApiException {
        return repaymentSchedulerService.generateSchedule(request);
    }
}
