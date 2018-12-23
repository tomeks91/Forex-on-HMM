import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by tomek on 09.12.18.
 */
public class TestData {

    private static Random random = new Random();

    public static List<List<Integer>> getTestData(int numberOfSymbols, int sequenceLength, int countSequences){
        List<List<Integer>> testData = new ArrayList<>();
        IntStream.range(0, countSequences).forEach(i -> addSequenceToList(numberOfSymbols, sequenceLength, testData));
        return testData;
    }

    private static void addSequenceToList(int numberOfSymbols, int sequenceLength, List<List<Integer>> testData) {
        ArrayList<Integer> seq = new ArrayList<Integer>();
        IntStream.range(0, sequenceLength)
                .forEach(i -> seq.add(random.nextInt(numberOfSymbols)));
        testData.add(seq);
    }
}
