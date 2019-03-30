package forex;

import hmm.HMMClassification;
import hmm.HMMClassify;

public class ForexClassification {

    public static HMMClassify build(int numberOfClassifications, int numberOfSymbols, Currency currency){
        return HMMClassification.builder()
        .numberOfSymbols(numberOfSymbols)
        .data(CurrencyToDataHmm.convert(currency, numberOfClassifications))
        .build()
        .buildHmms();
    }

}
