package org.apache.fineract.portfolio.naaccount.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * <p>
 * User: Vigneshwara.Prakash Date: 02-12-2020 Time: 14:30
 * <p>
 * CustomInterestRepayment will contain principal repayment information given by the user that is varying from the
 * model.
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomPrincipalRepayment {

    private LocalDate date;
    private BigDecimal amount;
    private Boolean isCoupled;

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Boolean getCoupled() {
        return isCoupled;
    }
}
