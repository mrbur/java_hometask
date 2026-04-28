package hometask2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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

    public static <T> Map<T, Integer> arrayCount(T[] arrayToCount) {
        if(arrayToCount == null || arrayToCount.length == 0) {
            throw new IllegalArgumentException("array is null or empty");
        }
        Map<T, Integer> resultMap = new HashMap<>(arrayToCount.length);

        for (int i = 0; i < arrayToCount.length; i++) {

            if(!resultMap.containsKey(arrayToCount[i])) {
                resultMap.put(arrayToCount[i], 1);
            }
            else {
                resultMap.put(arrayToCount[i], resultMap.get(arrayToCount[i]) + 1);
            }
        }
        return resultMap;
    }


    public static void main(String[] args) {
        Integer[] array = {1, 2, 3, 4, 5};
        System.out.println(Arrays.toString(ArrayMapping.arrayMapping(array, i -> i + 1)));

        System.out.println(arrayCount(new Object[]{1,2,2,3, new Object()}));
    }
}


