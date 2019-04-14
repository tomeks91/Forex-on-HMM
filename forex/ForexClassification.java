package forex;

import hmm.HMMClassification;
import hmm.HMMClassify;
import hmm.HmmData;
import hmm.Sequence;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class ForexClassification implements HMMClassify, Serializable{
    private final int sequenceLength;
    private final double inputMultipler;
    private final int firstDistanceInputPoints;
    private final double outputMultipler;
    private final int numberOfSymbols;
    private final int numberOfClassifications;
    private final Currency currency;
    @EqualsAndHashCode.Exclude private HMMClassify hmmClassify;

    @Override
    public HMMClassify buildHmms(List<HmmData> hmmDatas){
        hmmDatas = CurrencyToDataHmm.builder().currency(currency)
                .inputMultipler(inputMultipler)
                .outputMultipler(outputMultipler)
                .numberOfClassifications(numberOfClassifications)
                .numberOfSymbols(numberOfSymbols)
                .sequenceLength(sequenceLength)
                .firstDistanceInputPoints(firstDistanceInputPoints)
                .build().convert();

        hmmClassify = HMMClassification.builder()
                .numberOfSymbols(numberOfSymbols)
                .build()
                .buildHmms(hmmDatas);

        return hmmClassify;
    }

    @Override
    public int classify(Sequence seq) {
        return hmmClassify.classify(seq);
    }

    @Override
    public double efficiencyOfClassification() {
        return hmmClassify.efficiencyOfClassification();
    }

    public boolean isHmmInitialized(){
        return hmmClassify != null;
    }

}
