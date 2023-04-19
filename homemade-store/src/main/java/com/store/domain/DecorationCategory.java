package com.store.domain;

public enum DecorationCategory {
    CHRISTMAS, EASTER, MARCH;

    public static boolean contains(String test) {
        for (DecorationCategory c : DecorationCategory.values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }
        return false;
    }
}
