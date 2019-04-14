package hmm;

import lombok.Builder;
import utils.Repeater;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparingDouble;

@Builder
public final class HMMClassification implements HMMClassify, Serializable {
    private final List<HmmModel> hmmModels = new ArrayList<>();
    private final int numberOfSymbols;

    public HMMClassify buildHmms(List<HmmData> data){
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
            int goodOneModel = 0;
            HmmModel hmmModel = hmmModels.get(i);
            HmmTests tests = hmmModel.getTests();
            int allOneModel = tests.getSequences().size();
            countOfSequences += tests.getSequences().size();
            for (Sequence seq : tests.getSequences()) {
                if (classify(seq) == i) {
                    good++;
                    goodOneModel++;
                }
            }
            System.out.println(goodOneModel+" "+allOneModel+" "+(double)goodOneModel/allOneModel);
        }
        return (double)good/countOfSequences;
    }
}
