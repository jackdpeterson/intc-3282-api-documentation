package com.sparrowlogic.apiexample.tasks.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

@Validated
public record TaskRequest (
        @NotNull @NotEmpty @Length(min = 5, max=255) String title,
        @NotNull @Length(min = 0, max = 1000) String description,
        @NotNull
        @Min(value = 5, message = "The minimum amount for a task is $5.")
        @Max(value = 999, message = "The largest task value is $999.") Integer willingToPay) {

}
