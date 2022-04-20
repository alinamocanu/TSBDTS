package com.store.controller;

import com.store.domain.Cart;
import com.store.domain.Customer;
import com.store.dto.*;
import com.store.mapper.CustomerMapper;
import com.store.service.CartService;
import com.store.service.CustomerService;
import com.store.service.DecorationService;
import com.store.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Api(value = "/carts", tags = "Cart")
@RequestMapping("/carts")
public class CartController {




}
