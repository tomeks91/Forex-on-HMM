package tests;

import com.google.common.collect.Lists;
import hmm.HmmData;
import hmm.LearningData;
import hmm.Sequence;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestDataUtil {

    private static Random random = new Random();

    public static List<Sequence> getTestData(int numberOfSymbols, int sequenceLength, int countSequences){
        return IntStream.range(0, countSequences).boxed().map(i -> getSequence(numberOfSymbols, sequenceLength))
               .collect(Collectors.toList());
    }

    private static Sequence getSequence(int numberOfSymbols, int sequenceLength) {
        return new Sequence(generateSequence(numberOfSymbols, sequenceLength));
    }

    private static List<Integer> generateSequence(int numberOfSymbols, int sequenceLength) {
        return IntStream.generate(() -> random.nextInt(numberOfSymbols)).limit(sequenceLength).boxed().collect(Collectors.toList());
    }

    public static List<HmmData> getTestDataForBuildHmms(int numberOfSymbols, int sequenceLength, int countSequences, int numberOfClassfications){
        List<Sequence> testData = getTestData(numberOfSymbols, sequenceLength, countSequences);
        List<List<Sequence>> partition = Lists.partition(testData, testData.size() / numberOfClassfications);
        //to fix UUID.randomUUID().toString()
        return partition.stream().map(learn -> new HmmData(new LearningData(learn, UUID.randomUUID().toString()), null, UUID.randomUUID().toString())).collect(Collectors.toList());
    }

}
