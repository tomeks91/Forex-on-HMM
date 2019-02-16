import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Created by tomek on 16.02.19.
 */
public abstract class HMMClassification {
    List<HmmInstance> hmmInstances = new ArrayList<>();
    int numberOfStates = 3;
    int numberOfSymbols = 6;
    int numberOfClassifications = 5;
    Map<Integer, List<List<Integer>>> data;

    abstract Map<Integer, List<List<Integer>>> getData();

    public void buildHmms(){
        this.data = getData();
        IntStream.range(0, numberOfClassifications).forEach(i -> initHmm(numberOfStates, numberOfSymbols, data.get(i)));
    }

    private void initHmm(int numberOfStates, int numberOfSymbols, List<List<Integer>> data) {
        HmmInstance hmmInstance = new HmmInstance();
        hmmInstance.initHmm(numberOfStates, numberOfSymbols, data);
        hmmInstances.add(hmmInstance);
    }

    public int classify(List<Integer> seq){
        int rozpoznany = 0;
        double prop = 0;
        double max = -1;
        for(int i = 0; i < numberOfClassifications; i++){
            prop = hmmInstances.get(i).getProbability(seq);
            System.out.println(prop);
            if(prop > max){
                max = prop;
                rozpoznany = i;
            }
        }
        return rozpoznany;
    }
}
