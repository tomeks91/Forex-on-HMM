package hmm;

import com.google.common.collect.Lists;
import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class TestDataUtil {

    private static Random random = new Random();

    public static List<List<Integer>> getTestData(int numberOfSymbols, int sequenceLength, int countSequences){
        List<List<Integer>> testData = new ArrayList<>();
        repeat(countSequences, () -> addSequenceToList(numberOfSymbols, sequenceLength, testData));
        return testData;
    }

    private static void addSequenceToList(int numberOfSymbols, int sequenceLength, List<List<Integer>> testData) {
        ArrayList<Integer> seq = new ArrayList<Integer>();
        repeat(sequenceLength, () -> seq.add(random.nextInt(numberOfSymbols)));
        testData.add(seq);
    }

    public static Map<Integer, List<List<Integer>>> getTestDataForBuildHmms(int numberOfSymbols, int sequenceLength, int countSequences, int numberOfClassfications){
        List<List<Integer>> testData = getTestData(numberOfSymbols, sequenceLength, countSequences);
        List<List<List<Integer>>> partition = Lists.partition(testData, testData.size()/numberOfClassfications);
        Map<Integer, List<List<Integer>>> sequences = new TreeMap<>();
        repeat(numberOfClassfications, i -> sequences.put(i, partition.get(i)));
        return sequences;
    }

    public static <R> void repeat(int number, Runnable executor){
        IntStream.range(0, number).forEach(i -> executor.run());
    }

    public static <R> void repeat(int number, BooleanSupplier executor){
        IntStream.range(0, number).forEach(i -> executor.getAsBoolean());
    }

    public static <R> void repeat(int number, IntConsumer executor){
        IntStream.range(0, number).forEach(i -> executor.accept(i));
    }

}
