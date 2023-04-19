package com.store.controller;

import com.store.dto.OrderItemDto;
import com.store.service.OrderItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value = "/orderItems", tags = "Order Items")
@RequestMapping("/orderItems")
public class OrderItemController {

    @Autowired
    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/{orderId}")
    @ApiOperation(value = "Get an order item", notes = "Get an order item based on the Id received in the request")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "The order item with the entered Id does not exist!")
    })
    public ResponseEntity<List<OrderItemDto>>getOrderItemsForOrder(@PathVariable Long orderId){
        return ResponseEntity
                .ok()
                .body(orderItemService.getOrderItemsForOrder(orderId));
    }
}
