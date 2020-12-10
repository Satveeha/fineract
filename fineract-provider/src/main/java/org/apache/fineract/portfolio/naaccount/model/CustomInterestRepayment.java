package org.apache.fineract.portfolio.naaccount.model;

import java.time.LocalDate;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * <p>
 * User: Vigneshwara.Prakash Date: 03-12-2020 Time: 12:45
 * <p>
 * CustomInterestRepayment will contain interest repayment information given by the user that is varying from the model.
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomInterestRepayment {

    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }
}
