package hometask2;

import java.util.Arrays;
import java.util.function.Function;

public class ArrayMapping {
    public static  <T> T[] arrayMapping(T[] arrayToApply, Function<T, T> func) {
        if(arrayToApply == null || arrayToApply.length == 0) {
            throw new IllegalArgumentException("array is null or empty");
        }
        if(func == null) {
            throw new IllegalArgumentException("function to apply is null");
        }
        var processedArray = (T[]) new Object[arrayToApply.length];
        for (int i = 0; i < arrayToApply.length; i++) {
            processedArray[i] = func.apply(arrayToApply[i]);
        }
        return processedArray;
    }


    public static void main(String[] args) {
        Integer[] array = {1, 2, 3, 4, 5};
        System.out.println(Arrays.toString(ArrayMapping.arrayMapping(array, i -> i + 1)));
    }
}


