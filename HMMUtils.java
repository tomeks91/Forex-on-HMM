import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by tomek on 09.12.18.
 */
public class HMMUtils {

    public List <Double> getOutputRange(List <Double> outputToNowDiv, int numberOfClassifications){
        return getRange(outputToNowDiv, numberOfClassifications);
    }

    public List <Double> getInputRange(List<List <Double>> inputValues, int numberOfSymbols){
        List<Double> allValues = new ArrayList<>();
        inputValues.stream().forEach(list -> allValues.addAll(list));
        return getRange(allValues, numberOfSymbols);
    }

    private List<Double> getRange(List<Double> allValues, int partitions) {
        Collections.sort(allValues);
        int size = allValues.size();
        List <Double> inputRange = new ArrayList<>();
        IntStream.range(1, partitions)
                .forEach(i -> System.out.println((i*size)/partitions));
        IntStream.range(1, partitions)
                .forEach(i -> inputRange.add(allValues.get((i*size)/partitions)));
        return inputRange;
    }

    private Integer getPartition(Double value, List<Double> range){
        return (int)range.stream().filter(rangeValue -> rangeValue < value).count();
    }

}
