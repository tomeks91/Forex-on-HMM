package hmm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class HMMUtils {

    public static List <Double> getOutputRange(List <Double> outputToNowDiv, int numberOfClassifications){
        return getRange(outputToNowDiv, numberOfClassifications);
    }

    public static List <Double> getInputRange(List<List <Double>> inputValues, int numberOfSymbols){
        List<Double> allValues = new ArrayList<>();
        inputValues.stream().forEach(list -> allValues.addAll(list));
        return getRange(allValues, numberOfSymbols);
    }

    private static List<Double> getRange(List<Double> allValues, int partitions) {
        Collections.sort(allValues);
        int size = allValues.size();
        List <Double> inputRange = new ArrayList<>();
        IntStream.range(1, partitions)
                .forEach(i -> inputRange.add(allValues.get((i*size)/partitions)));
        return inputRange;
    }

    private static Integer getPartition(Double value, List<Double> range){
        return (int)range.stream().filter(rangeValue -> rangeValue < value).count();
    }

}
