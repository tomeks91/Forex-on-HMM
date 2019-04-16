package hmm;

import utils.Repeater;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.DoubleStream;

import static symbols.Wczytaj.numberOfSymbols;

public class HMMUtils {

    public static List <Double> getOutputRanges(List <Double> allValues, int partitions){
        Double lastValue = allValues.get(0);
        double min = getDoubleStream(allValues).min().getAsDouble();
        double max = getDoubleStream(allValues).max().getAsDouble();
        if((lastValue.doubleValue()-min) > (max-lastValue))
            max = min+(lastValue.doubleValue()-min)*2;
        else
            min = max-(max-lastValue)*2;
        double newMin = min;
        double newMax = max;
        List<Double> ranges = new ArrayList<>();
        Repeater.performInRange(1, partitions,
                i -> ranges.add(calculateValue(i, partitions, newMin, newMax))
        );
        return ranges;
    }

    public static List<Double> getRanges(List<Double> allValues, int partitions) {
        double min = getDoubleStream(allValues).min().getAsDouble();
        double max = getDoubleStream(allValues).max().getAsDouble();
        List<Double> inputRange = new ArrayList<>();
        Repeater.performInRange(1, partitions,
                i -> inputRange.add(calculateValue(i, partitions, min, max))
        );
        return inputRange;
    }

    private static DoubleStream getDoubleStream(List<Double> allValues) {
        return allValues.stream().mapToDouble(v -> v);
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
