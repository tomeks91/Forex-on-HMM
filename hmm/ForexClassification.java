package hmm;

import java.util.List;

public class ForexClassification implements HMMClassify{
    private static final int NUMBER_OF_SYMBOLS = 6;
    private static final int NUMBER_OF_CLASSIFICATIONS = 5;
    private final Currency currency;
    private final HMMClassification hmmClassification;

    public ForexClassification(Currency currency) {
        this.currency = currency;
        hmmClassification = HMMClassification.builder()
                .numberOfClassifications(NUMBER_OF_CLASSIFICATIONS)
                .numberOfSymbols(NUMBER_OF_SYMBOLS)
                .data(null)
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
