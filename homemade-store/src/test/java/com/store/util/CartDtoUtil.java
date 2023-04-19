package com.store.util;

import com.store.dto.CartDto;

public class CartDtoUtil {
    public static CartDto aCartDto(Long id){
        return CartDto.builder()
                .cartId(id)
                .totalAmount(200)
                .build();
    }
}
