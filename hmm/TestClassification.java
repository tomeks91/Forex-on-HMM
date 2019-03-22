package hmm;

import java.util.List;

public class TestClassification implements HMMClassify{
    public static final int NUMBER_OF_SYMBOLS = 6;
    public static final int SEQUENCE_LENGTH = 100;
    public static final int COUNT_SEQUENCES = 100;
    public static final int NUMBER_OF_CLASSIFICATIONS = 5;
    private final HMMClassification hmmClassification;

    public TestClassification() {
        hmmClassification = HMMClassification.builder()
                .numberOfClassifications(NUMBER_OF_CLASSIFICATIONS)
                .numberOfSymbols(NUMBER_OF_SYMBOLS)
                .data(TestDataUtil.getTestDataForBuildHmms(NUMBER_OF_SYMBOLS, SEQUENCE_LENGTH, COUNT_SEQUENCES, NUMBER_OF_CLASSIFICATIONS))
                .build();
    }

    @Override
    public HMMClassify buildHmms() {
        return hmmClassification.buildHmms();
    }

    @Override
    public int classify(List<Integer> seq) {
        return hmmClassification.classify(seq);
    }
}
