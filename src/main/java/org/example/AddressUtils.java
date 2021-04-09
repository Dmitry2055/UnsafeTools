package org.example;

import java.lang.reflect.InvocationTargetException;

import static org.example.UnsafeGetter.unsafe;

public class AddressUtils {

    public static long toAddress(Object obj) {
        Object[] array = new Object[]{obj};
        long baseOffset = unsafe().arrayBaseOffset(Object[].class);
        return unsafe().getLong(array, baseOffset);

    }

    public static long classAddress(Object obj) {
        return unsafe().getLong(obj, 8);
    }

    public static long classAddress(Class<?> obj) {
        try {
            return unsafe().getLong(obj.getDeclaredConstructor().newInstance(), 8);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static long[] arrayAt(long address) {
        var length = unsafe().getInt(address);
        var arr = new long[length];
        var size = 8;
        for (int i = 0; i < length; i++) {
            arr[i] = unsafe().getLong(address + size + i * size);
        }
        return arr;
    }
}
