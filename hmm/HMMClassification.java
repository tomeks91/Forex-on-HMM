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
    private final int numberOfSymbols;
    private final List<HmmData> data;

    public HMMClassify buildHmms(){
        Repeater.perform(data.size(), i -> initHmm(data.get(i)));
        return this;
    }

    @Override
    public int classify(Sequence seq) {
        List<Double> listOfPropabilities = hmmModels.stream()
                .map(hmmInstance -> hmmInstance.getProbability(seq))
                .collect(Collectors.toList());
        return Stream.iterate(0, n -> n + 1)
                .limit(listOfPropabilities.size())
                .max(comparingDouble(listOfPropabilities::get))
                .get();
    }

    private void initHmm(HmmData data) {
        hmmModels.add(HmmModel.getInstance(data, numberOfSymbols));
    }

    @Override
    public double efficiencyOfClassification(){
        int countOfSequences = 0;
        int good = 0;
        for(int i = 0; i < hmmModels.size(); i++) {
            HmmModel hmmModel = hmmModels.get(i);
            HmmTests tests = hmmModel.getTests();
            countOfSequences += tests.getSequences().size();
            for (Sequence seq : tests.getSequences()) {
                if (classify(seq) == i) {
                    good++;
                }
            }
        }
        return (double)good/countOfSequences;
    }
}
