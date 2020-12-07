package org.apache.fineract.portfolio.naaccount.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * User: Vigneshwara.Prakash Date: 02-12-2020 Time: 14:30
 * <p>
 * CustomInterestRepayment will contain principal repayment information given by the user that is varying from the
 * model.
 */
public class CustomPrincipalRepayment {

    @JsonFormat(pattern = "yyyy-MM-dd")
    // @JsonDeserialize(using = LocalDateDeserializer.class)
    // @JsonSerialize(using = LocalDateSerializer.class)
    @JsonProperty("date")
    private LocalDate date;
    private BigDecimal amount;
    @JsonProperty
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
