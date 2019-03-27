package hmm;

import lombok.Builder;
import utils.Repeater;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparingDouble;

@Builder
public final class HMMClassification implements HMMClassify {
    private final List<HmmModel> hmmModels = new ArrayList<>();
    private final int numberOfClassifications;
    private final int numberOfSymbols;
    private final List<LearningData> data;

    public HMMClassify buildHmms(){
        Repeater.perform(numberOfClassifications, i -> initHmm(data.get(i)));
        return this;
    }

    @Override
    public int classify(Sequence seq) {
        List<Double> listOfPropabilities = hmmModels.stream()
                .map(hmmInstance -> hmmInstance.getProbability(seq))
                .collect(Collectors.toList());
        return Stream.iterate(0, n -> n + 1)
                .limit(listOfPropabilities.size())
                .min(comparingDouble(listOfPropabilities::get))
                .get();
    }

    private void initHmm(LearningData data) {
        hmmModels.add(HmmModel.getInstance(data, numberOfSymbols));
    }
}
