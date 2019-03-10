package hmm;

import java.util.List;
import java.util.Map;

public class ForexClassification extends HMMClassification{
    public static final int NUMBER_OF_SYMBOLS = 6;
    public static final int NUMBER_OF_CLASSIFICATIONS = 5;
    private Currency currency;

    public ForexClassification(Currency currency) {
        super(NUMBER_OF_CLASSIFICATIONS, NUMBER_OF_SYMBOLS);
    }

    @Override
    Map<Integer, List<List<Integer>>> getData() {
        return null;
    }
}
