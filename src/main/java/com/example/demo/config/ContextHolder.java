package com.example.demo.config;

public class ContextHolder {
    private static final ThreadLocal<Integer> CONTEXT = new ThreadLocal<>();

    public static void set(Integer index) {
        CONTEXT.set(index);
    }

    public static Integer get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
