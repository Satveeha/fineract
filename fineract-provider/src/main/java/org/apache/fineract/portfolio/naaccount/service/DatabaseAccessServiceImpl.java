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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DatabaseAccessServiceImpl implements DatabaseAccessService {

    private final LoanRepository repository;
    private final LoanRepaymentScheduleInstallmentRepository repaymentScheduleInstallmentRepository;
    private final RoutingDataSourceServiceFactory dataSourceServiceFactory;
    private final LoanRepositoryWrapper loanRepositoryWrapper;

    @Autowired
    public DatabaseAccessServiceImpl(final LoanRepository repository, final LoanRepaymentScheduleInstallmentRepository repaymentScheduleInstallmentRepository,
            RoutingDataSourceServiceFactory dataSourceServiceFactory, final LoanRepositoryWrapper loanRepositoryWrapper) {
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
