package org.apache.fineract.portfolio.naaccount.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class InterestRateInfo {

    @JsonFormat(pattern = "yyyy-MM-dd")
    // @JsonDeserialize(using = LocalDateDeserializer.class)
    // @JsonSerialize(using = LocalDateSerializer.class)
    @JsonProperty("date")
    private LocalDate date;
    private Double interestRate;

    public LocalDate getDate() {
        return date;
    }

    public Double getInterestRate() {
        return interestRate;
    }
}
