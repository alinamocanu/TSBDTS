package com.store.util;

import com.store.domain.DecorationCategory;
import com.store.dto.DecorationDto;

public class DecorationDtoUtil {
    public static DecorationDto aDecorationDto(Long id) {
        return DecorationDto.builder()
                .decorationId(id)
                .decorationName("Globe")
                .category(DecorationCategory.CHRISTMAS)
                .description("-")
                .price(20)
                .build();
    }
}
