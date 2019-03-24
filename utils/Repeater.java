package utils;

import java.util.function.BooleanSupplier;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class Repeater {
    public static <R> void perform(int number, Runnable executor){
        IntStream.range(0, number).forEach(i -> executor.run());
    }

    public static <R> void perform(int number, BooleanSupplier executor){
        IntStream.range(0, number).forEach(i -> executor.getAsBoolean());
    }

    public static <R> void perform(int number, IntConsumer executor){
        IntStream.range(0, number).forEach(i -> executor.accept(i));
    }

    public static <R> void performInRange(int from , int to, IntConsumer executor){
        IntStream.range(from, to).forEach(i -> executor.accept(i));
    }
}
