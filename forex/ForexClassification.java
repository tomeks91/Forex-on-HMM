package forex;

import hmm.HMMClassification;
import hmm.HMMClassify;
import hmm.HmmData;

import java.util.List;

public class ForexClassification {

    public static HMMClassify build(int numberOfClassifications, int numberOfSymbols, Currency currency){
        List<HmmData> hmmDatas = CurrencyToDataHmm.builder().currency(currency)
                .inputMultipler(1.18)
                .outputMultipler(0.2)
                .numberOfClassifications(numberOfClassifications)
                .numberOfSymbols(numberOfSymbols)
                .sequenceLength(17)
                .firstDistanceInputPoints(4)
                .build().convert();

        return HMMClassification.builder()
        .numberOfSymbols(numberOfSymbols)
        .data(hmmDatas)
        .build()
        .buildHmms();
    }

}
