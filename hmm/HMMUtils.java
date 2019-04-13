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
        ArrayList<Double> allValuesCopy = new ArrayList<>(allValues);
        Collections.sort(allValuesCopy);
        List<Double> inputRange = new ArrayList<>();
        Double min = allValuesCopy.get(0);
        Double max = allValuesCopy.get(allValuesCopy.size()-1);
        Repeater.performInRange(1, partitions,
                i -> inputRange.add(calculateValue(i, partitions, min, max))
        );
        return inputRange;
    }

    private static double calculateValue(int i, int partitions, double min, double max) {
        double intersecting = (double) i / partitions;
        double oneSubtractIntersecting = 1 - intersecting;
        return intersecting * max + oneSubtractIntersecting * min;
    }

    public static Integer getPartition(Double value, List<Double> range){
        return (int)range.stream()
                .filter(rangeValue -> rangeValue < value)
                .count();
    }

}
