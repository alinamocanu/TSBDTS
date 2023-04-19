package com.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    @NotEmpty
    @NotNull
    private int quantity;

    @NotEmpty
    @NotNull
    private double price;

    private Long decorationId;
}
