package hmm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class HMMUtils {

    public static List <Double> getOutputRange(List <Double> outputToNowDiv, int numberOfClassifications){
        return getRange(outputToNowDiv, numberOfClassifications);
    }

    public static List <Double> getInputRange(List<List <Double>> inputValues, int numberOfSymbols){
        List<Double> allValues = new ArrayList<>();
        inputValues.stream().forEach(inputList -> allValues.addAll(inputList));
        return getRange(allValues, numberOfSymbols);
    }

    private static List<Double> getRange(List<Double> allValues, int partitions) {
        Collections.sort(allValues);
        List <Double> inputRange = new ArrayList<>();
        repeatInRange(1, partitions, i -> inputRange.add(allValues.get((i*allValues.size())/partitions)));
        return inputRange;
    }

    private static Integer getPartition(Double value, List<Double> range){
        return (int)range.stream().filter(rangeValue -> rangeValue < value).count();
    }

    public static <R> void repeatInRange(int from , int to, IntConsumer executor){
        IntStream.range(from, to).forEach(i -> executor.accept(i));
    }

}
