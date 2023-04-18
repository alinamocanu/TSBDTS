package com.store.dto;

import com.store.domain.DecorationCategory;
import com.store.validator.OnlyLetters;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DecorationDto {

    private Long decorationId;

    @OnlyLetters
    @Size(min = 1, max = 30)
    private String decorationName;

    @Min(0)
    private double price;

    @Size(min = 0, max = 1000)
    private String description;

    private DecorationCategory category;
}
