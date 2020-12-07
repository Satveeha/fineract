package org.apache.fineract.portfolio.naaccount.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

/**
 * <p>
 * User: Vigneshwara.Prakash Date: 03-12-2020 Time: 12:45
 * <p>
 * CustomInterestRepayment will contain interest repayment information given by the user that is varying from the model.
 */
public class CustomInterestRepayment {

    @JsonFormat(pattern = "yyyy-MM-dd")
    // @JsonDeserialize(using = LocalDateDeserializer.class)
    // @JsonSerialize(using = LocalDateSerializer.class)
    @JsonProperty("date")
    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }
}
