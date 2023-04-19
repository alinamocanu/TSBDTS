package com.store.controller;

import com.store.dto.BankAccountDto;
import com.store.service.BankAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@Api(value = "/bankAccounts", tags = "BankAccounts")
@RequestMapping("/bankAccounts")
public class BankAccountController {
    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @ApiOperation(value = "Create new bank account", notes = "Creates a new bank account based on the information received in the request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Bank Account was successfully created based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request")
    })
    @PostMapping("/{customerId}")
    public ResponseEntity<BankAccountDto> createBankAccount(@RequestBody BankAccountDto bankAccountDto, @PathVariable Long customerId){
        return ResponseEntity
                .ok()
                .body(bankAccountService.addBankAccount(bankAccountDto, customerId));
    }

    @ApiOperation(value = "Get the bank accounts for a specific customer", notes = "Get all bank accounts for a specific customer based on the id received in the request")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "The customer with the entered Id does not exist!")
    })
    @GetMapping("/{customerId}")
    public ResponseEntity<List<BankAccountDto>> getBankAccountsForCustomer(@PathVariable Long customerId) {
        return ResponseEntity
                .ok()
                .body(bankAccountService.getBankAccountsForCustomer(customerId));
    }

    @ApiOperation(value = "Delete bank account for a customer",
            notes = "Delete bank account for a specific ustomer based on the Id and cardNumber received in the request")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "The cardNumber for this customer does not exist!")
    })
    @DeleteMapping("/{customerId}/{cardNumber}")
    @Transactional
    public String deleteBankAccount(@PathVariable Long customerId, @PathVariable String cardNumber, HttpServletResponse response){
        boolean result = bankAccountService.delete(customerId, cardNumber);
        if (result) {
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            return String.format("Bank account for customer with id %s was removed", customerId);
        } else {
            response.setStatus(404);
            return String.format("Bank account for customer with id %s was not removed", customerId);
        }
    }
}
