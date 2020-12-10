package org.apache.fineract.portfolio.naaccount.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.fineract.portfolio.naaccount.constants.ErrorConstants;
import org.apache.fineract.portfolio.naaccount.domain.PaymentPerMonth;
import org.apache.fineract.portfolio.naaccount.domain.RepaymentSchedule;
import org.apache.fineract.portfolio.naaccount.exception.ApiException;
import org.apache.fineract.portfolio.naaccount.model.Request;
import org.apache.fineract.portfolio.naaccount.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * RepaymentSchedulerService can validate the repayment scheduler request and generate the corresponding repayment
 * schedule and provide the corresponding response.
 */
@Service
public class RepaymentSchedulerService {

    @Autowired
    private DateCalculator dateCalculator;

    @Autowired
    private RepaymentCalculator repaymentCalculator;

    public List<Response> generateSchedule(Request request) {
        validateRequest(request);
        final List<PaymentPerMonth> paymentPerMonthList = generateMonthlyScheduleList(request);
        return generateResponseList(paymentPerMonthList);
    }

    private List<PaymentPerMonth> generateMonthlyScheduleList(Request request) {
        List<RepaymentSchedule> repaymentScheduleList = dateCalculator.generateRepaymentSchedule(request);
        return repaymentCalculator.generateRepayments(request, repaymentScheduleList);
    }

    private List<Response> generateResponseList(final List<PaymentPerMonth> paymentPerMonthList) {
        final List<Response> responseList = new ArrayList<>();
        for (final PaymentPerMonth paymentPerMonth : paymentPerMonthList) {
            responseList.add(new Response(paymentPerMonth));
        }
        return responseList;
    }

    private void validateRequest(Request request) {
        if (request.getPrincipal() <= 0) throw new ApiException(ErrorConstants.INVALID_LOAN_AMOUNT, HttpStatus.BAD_REQUEST);
        if (request.getPrincipalRepaymentFrequency() <= 0 || request.getInterestRepaymentFrequency() <= 0)
            throw new ApiException(ErrorConstants.INVALID_REPAYMENT_FREQUENCY, HttpStatus.BAD_REQUEST);
        if (request.getNominalInterestRate() <= 0) throw new ApiException(ErrorConstants.INVALID_NOMINAL_INTEREST, HttpStatus.BAD_REQUEST);
        if (request.getNoOfDecimal() < 0) throw new ApiException(ErrorConstants.INVALID_SCALE, HttpStatus.BAD_REQUEST);
        if (request.getInterestStartDate().isBefore(request.getStartDate())
                || request.getPrincipalStartDate().isBefore(request.getStartDate()))
            throw new ApiException(ErrorConstants.INVALID_DATE, HttpStatus.BAD_REQUEST);
    }
}
