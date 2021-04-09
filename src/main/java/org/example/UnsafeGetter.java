package org.example;

import sun.misc.Unsafe;

public class UnsafeGetter {
    public static Unsafe unsafe() {
        try {
            var field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Unable to get Unsafe", e);
        }
    }
}
