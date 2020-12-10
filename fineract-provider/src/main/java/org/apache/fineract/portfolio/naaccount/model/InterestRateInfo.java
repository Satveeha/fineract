package org.apache.fineract.portfolio.naaccount.model;

import java.time.LocalDate;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class InterestRateInfo {

    private LocalDate date;
    private Double interestRate;

    public LocalDate getDate() {
        return date;
    }

    public Double getInterestRate() {
        return interestRate;
    }
}
