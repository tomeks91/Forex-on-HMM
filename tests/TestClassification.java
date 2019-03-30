package tests;

import hmm.HMMClassification;
import hmm.HMMClassify;

public class TestClassification{
    public static final int NUMBER_OF_SYMBOLS = 6;
    public static final int SEQUENCE_LENGTH = 100;
    public static final int COUNT_SEQUENCES = 100;
    public static final int NUMBER_OF_CLASSIFICATIONS = 5;

    public static HMMClassify build(){
        return HMMClassification.builder()
                .numberOfSymbols(NUMBER_OF_SYMBOLS)
                .data(TestDataUtil.getTestDataForBuildHmms(NUMBER_OF_SYMBOLS, SEQUENCE_LENGTH, COUNT_SEQUENCES, NUMBER_OF_CLASSIFICATIONS))
                .build()
                .buildHmms();
    }
}
