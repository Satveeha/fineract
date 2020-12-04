package org.apache.fineract.portfolio.naaccount.service;

import java.util.List;
import java.util.Optional;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleInstallment;

public interface SampleService {

    Boolean insertLoan(Loan loan);

    Boolean insertLoanRepaymentScheduleInstallment(List<LoanRepaymentScheduleInstallment> installments);

    Optional<Loan> retrieveLoanById(Long id);

    Optional<LoanRepaymentScheduleInstallment> retrieveLoanRepaymentScheduleInstallmentById(Long id);

    Boolean saveLoanRepaymentScheduleInstallment(LoanRepaymentScheduleInstallment loanRepaymentScheduleInstallment);

    List<LoanRepaymentScheduleInstallment> retrieveRepaymentScheduleInstallmentByLoanId(Loan loan);
}
