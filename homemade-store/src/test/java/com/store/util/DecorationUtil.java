package com.store.util;

import com.store.domain.Decoration;
import com.store.domain.DecorationCategory;

public class DecorationUtil {
    public static Decoration aDecoration(long id) {
        return Decoration.builder()
                .decorationId(id)
                .decorationName("Globe")
                .category(DecorationCategory.CHRISTMAS)
                .description("-")
                .price(20)
                .build();
    }
}
