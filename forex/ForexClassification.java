package forex;

import hmm.HMMClassification;
import hmm.HMMClassify;
import hmm.HmmData;

import java.util.List;

public class ForexClassification {

    public static HMMClassify build(int numberOfClassifications, int numberOfSymbols, Currency currency){
        List<HmmData> hmmDatas = CurrencyToDataHmm.builder().currency(currency)
                .multipler(1.1)
                .numberOfClassifications(numberOfClassifications)
                .numberOfSymbols(numberOfSymbols)
                .sequenceLength(10)
                .build().convert();

        return HMMClassification.builder()
        .numberOfSymbols(numberOfSymbols)
        .data(hmmDatas)
        .build()
        .buildHmms();
    }

}
