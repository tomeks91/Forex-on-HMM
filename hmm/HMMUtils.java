package hmm;

import utils.Repeater;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HMMUtils {

    public static List <Double> getInputRanges(List<List <Double>> inputValues, int numberOfSymbols){
        List<Double> allValues = new ArrayList<>();
        inputValues.stream().forEach(inputList -> allValues.addAll(inputList));
        return getRanges(allValues, numberOfSymbols);
    }

    public static List<Double> getRanges(List<Double> allValues, int partitions) {
        Collections.sort(allValues);
        List <Double> inputRange = new ArrayList<>();
        Repeater.performInRange(1, partitions, i -> inputRange.add(allValues.get((i*allValues.size())/partitions)));
        return inputRange;
    }

    public static Integer getPartition(Double value, List<Double> range){
        return (int)range.stream().filter(rangeValue -> rangeValue < value).count();
    }

}
