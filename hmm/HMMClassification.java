package hmm;

import lombok.Builder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static java.util.Comparator.comparingDouble;

@Builder
public class HMMClassification implements HMMClassify {
    private final List<HmmModel> hmmModels = new ArrayList<>();
    private final int numberOfClassifications;
    private final int numberOfSymbols;
    private final Map<Integer, List<List<Integer>>> data;

    public HMMClassify buildHmms(){
        IntStream.range(0, numberOfClassifications).forEach(i -> initHmm(data.get(i)));
        return this;
    }

    private void initHmm(List<List<Integer>> data) {
        hmmModels.add(HmmModel.initHmm(data, numberOfSymbols));
    }

    public int classify(List<Integer> seq){
        List<Double> listOfPropabilities = hmmModels.stream().map(hmmInstance -> hmmInstance.getProbability(seq)).collect(Collectors.toList());
        return IntStream.range(0,listOfPropabilities.size()).boxed()
                .min(comparingDouble(listOfPropabilities::get))
                .get();
    }
}
