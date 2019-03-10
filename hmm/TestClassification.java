package hmm;

import java.util.List;
import java.util.Map;

public class TestClassification extends HMMClassification{
    public static final int NUMBER_OF_SYMBOLS = 6;
    public static final int SEQUENCE_LENGTH = 100;
    public static final int COUNT_SEQUENCES = 100;
    public static final int NUMBER_OF_CLASSIFICATIONS = 5;

    public TestClassification() {
        super(NUMBER_OF_CLASSIFICATIONS, NUMBER_OF_SYMBOLS);
    }

    @Override
    Map<Integer, List<List<Integer>>> getData() {
        return TestDataUtil.getTestDataForBuildHmms(NUMBER_OF_SYMBOLS, SEQUENCE_LENGTH, COUNT_SEQUENCES, NUMBER_OF_CLASSIFICATIONS);
    }

}
