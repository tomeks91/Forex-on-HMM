package hmm;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static java.util.Comparator.comparingDouble;

@RequiredArgsConstructor
public abstract class HMMClassification {
    List<HmmInstance> hmmInstances = new ArrayList<>();
    protected final int numberOfClassifications;
    protected final int numberOfSymbols;
    Map<Integer, List<List<Integer>>> data;

    abstract Map<Integer, List<List<Integer>>> getData();

    public HMMClassification buildHmms(){
        this.data = getData();
        IntStream.range(0, numberOfClassifications).forEach(i -> initHmm(data.get(i)));
        return this;
    }

    private void initHmm(List<List<Integer>> data) {
        HmmInstance hmmInstance = new HmmInstance();
        hmmInstance.initHmm(data, numberOfSymbols);
        hmmInstances.add(hmmInstance);
    }

    public int classify(List<Integer> seq){
        List<Double> listOfPropabilities = hmmInstances.stream().map(hmmInstance -> hmmInstance.getProbability(seq)).collect(Collectors.toList());
        return IntStream.range(0,listOfPropabilities.size()).boxed()
                .min(comparingDouble(listOfPropabilities::get))
                .get();
    }
}
