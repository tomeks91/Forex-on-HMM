package hmm;

public class ForexClassification extends HMMClassification{
    private static final int NUMBER_OF_SYMBOLS = 6;
    private static final int NUMBER_OF_CLASSIFICATIONS = 5;
    private final Currency currency;

    public ForexClassification(Currency currency) {
        super(NUMBER_OF_CLASSIFICATIONS, NUMBER_OF_SYMBOLS, null);
        this.currency = currency;
    }
}
