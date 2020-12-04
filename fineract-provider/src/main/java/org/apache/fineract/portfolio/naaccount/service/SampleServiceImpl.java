package org.apache.fineract.portfolio.naaccount.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.fineract.infrastructure.core.service.RoutingDataSourceServiceFactory;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleInstallment;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleInstallmentRepository;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepository;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepositoryWrapper;
import org.apache.fineract.portfolio.loanaccount.rescheduleloan.service.LoanRescheduleRequestWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SampleServiceImpl implements SampleService {

    private final LoanRescheduleRequestWritePlatformService loanRescheduleRequestWritePlatformService;
    private final LoanRepository repository;
    private final LoanRepaymentScheduleInstallmentRepository repaymentScheduleInstallmentRepository;
    private final RoutingDataSourceServiceFactory dataSourceServiceFactory;
    private final LoanRepositoryWrapper loanRepositoryWrapper;

    @Autowired
    public SampleServiceImpl(LoanRescheduleRequestWritePlatformService loanRescheduleRequestWritePlatformService,
            final LoanRepository repository, final LoanRepaymentScheduleInstallmentRepository repaymentScheduleInstallmentRepository,
            RoutingDataSourceServiceFactory dataSourceServiceFactory, final LoanRepositoryWrapper loanRepositoryWrapper) {
        this.loanRescheduleRequestWritePlatformService = loanRescheduleRequestWritePlatformService;
        this.repository = repository;
        this.repaymentScheduleInstallmentRepository = repaymentScheduleInstallmentRepository;
        this.dataSourceServiceFactory = dataSourceServiceFactory;
        this.loanRepositoryWrapper = loanRepositoryWrapper;
    }

    @Transactional
    @Override
    public Boolean insertLoan(Loan loan) {
        Boolean returnValue = false;

        try {
            List<LoanRepaymentScheduleInstallment> installments = loan.getRepaymentScheduleInstallments();
            for (LoanRepaymentScheduleInstallment installment : installments) {
                if (installment.getId() == null) {
                    this.repaymentScheduleInstallmentRepository.save(installment);
                }
            }

            this.loanRepositoryWrapper.saveAndFlush(loan);
            returnValue = true;
        } catch (final JpaSystemException | DataIntegrityViolationException e) {
            return false;
        }

        return returnValue;
    }

    @Override
    public Optional<Loan> retrieveLoanById(Long id) {
        // TODO Auto-generated method stub
        return this.repository.findById(id);
    }

    @Override
    public List<LoanRepaymentScheduleInstallment> retrieveRepaymentScheduleInstallmentByLoanId(Loan loan) {
        // TODO Auto-generated method stub

        final JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSourceServiceFactory.determineDataSourceService().retrieveDataSource());

        final String FETCH_BY_LOANID = "select id,loan_id,fromdate,duedate,installment,principal_amount,principal_completed_derived,principal_writtenoff_derived,interest_amount,"
                + "interest_completed_derived,interest_writtenoff_derived,interest_waived_derived,"
                + "accrual_interest_derived,fee_charges_amount,fee_charges_completed_derived,"
                + "fee_charges_writtenoff_derived,fee_charges_waived_derived,accrual_fee_charges_derived,"
                + "penalty_charges_amount,penalty_charges_completed_derived,penalty_charges_writtenoff_derived,"
                + "penalty_charges_waived_derived,accrual_penalty_charges_derived,total_paid_in_advance_derived,"
                + "total_paid_late_derived,completed_derived,obligations_met_on_date,createdby_id,created_date,lastmodified_date,lastmodifiedby_id,"
                + "recalculated_interest_component from m_loan_repayment_schedule where loan_id = " + loan.getId();

        List<LoanRepaymentScheduleInstallment> returnData = new ArrayList<LoanRepaymentScheduleInstallment>();

        try {
            returnData = jdbcTemplate.queryForList(FETCH_BY_LOANID, LoanRepaymentScheduleInstallment.class);
        } catch (Exception e) {
            System.out.println(e);
            return returnData;
        }

        return returnData;
    }

    @Override
    public Optional<LoanRepaymentScheduleInstallment> retrieveLoanRepaymentScheduleInstallmentById(Long id) {
        // TODO Auto-generated method stub
        return this.repaymentScheduleInstallmentRepository.findById(id);
    }

    @Override
    public Boolean saveLoanRepaymentScheduleInstallment(LoanRepaymentScheduleInstallment loanRepaymentScheduleInstallment) {
        Boolean returnValue = false;

        try {
            this.repaymentScheduleInstallmentRepository.save(loanRepaymentScheduleInstallment);
            returnValue = true;
        } catch (Exception e) {
            return returnValue;
        }
        return returnValue;
    }

    @Override
    public Boolean insertLoanRepaymentScheduleInstallment(List<LoanRepaymentScheduleInstallment> installments) {
        Boolean returnValue = false;

        try {
            for (LoanRepaymentScheduleInstallment installment : installments) {
                if (installment.getId() == null) {
                    this.repaymentScheduleInstallmentRepository.save(installment);
                }
            }
            returnValue = true;
        } catch (final JpaSystemException | DataIntegrityViolationException e) {
            return false;
        }

        return returnValue;
    }

}
