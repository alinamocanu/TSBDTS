package com.store.controller;

import com.store.dto.OrderDto;
import com.store.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value = "/orders", tags = "Orders")
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService service) {
        this.orderService = service;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get an order", notes = "Get an order based on the Id received in the request")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "The order with the entered Id does not exist!")
    })
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity
                .ok()
                .body(orderService.getOne(id));
    }

    @GetMapping("/all/{customerId}")
    @ApiOperation(value = "Get orders for a customer", notes = "Retrieve all orders for a specified customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The data was retrieved successfully"),
            @ApiResponse(code = 404, message = "Customer with the specified Id was not found!")
    })
    public ResponseEntity<List<OrderDto>> getOrdersFromCustomer(@PathVariable Long customerId) {
        return ResponseEntity
                .ok()
                .body(orderService.getOrdersByCustomer(customerId));
    }
}
