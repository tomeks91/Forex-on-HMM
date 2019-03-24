package hmm;

import com.google.common.collect.Lists;
import utils.Repeater;

import java.util.*;

public class TestDataUtil {

    private static Random random = new Random();

    public static List<List<Integer>> getTestData(int numberOfSymbols, int sequenceLength, int countSequences){
        List<List<Integer>> testData = new ArrayList<>();
        Repeater.perform(countSequences, () -> addSequenceToList(numberOfSymbols, sequenceLength, testData));
        return testData;
    }

    private static void addSequenceToList(int numberOfSymbols, int sequenceLength, List<List<Integer>> testData) {
        ArrayList<Integer> seq = new ArrayList<Integer>();
        Repeater.perform(sequenceLength, () -> seq.add(random.nextInt(numberOfSymbols)));
        testData.add(seq);
    }

    public static Map<Integer, List<List<Integer>>> getTestDataForBuildHmms(int numberOfSymbols, int sequenceLength, int countSequences, int numberOfClassfications){
        List<List<Integer>> testData = getTestData(numberOfSymbols, sequenceLength, countSequences);
        List<List<List<Integer>>> partition = Lists.partition(testData, testData.size()/numberOfClassfications);
        Map<Integer, List<List<Integer>>> sequences = new TreeMap<>();
        Repeater.perform(numberOfClassfications, i -> sequences.put(i, partition.get(i)));
        return sequences;
    }

}
