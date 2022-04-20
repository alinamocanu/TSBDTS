package com.store.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.store.domain.BankAccount;
import com.store.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long orderId;

    @Min(0)
    private double totalAmount;

    @NotEmpty
    private LocalDateTime orderPlaced;

    @JsonIgnore
    private Customer customer;

    @JsonIgnore
    private BankAccount bankAccount;
}
