import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by tomek on 09.12.18.
 */
public class Classification {

    List<MyHmm> myHmms = new ArrayList<>();
    int numberOfStates = 3;
    int numberOfSymbols = 6;
    int numberOfClassifications = 5;
    int sequenceLength = 5;

    public void buildHmms(){
        IntStream.range(0, numberOfClassifications).forEach(i -> initHmm(numberOfStates, numberOfSymbols));
    }

    private void initHmm(int numberOfStates, int numberOfSymbols) {
        MyHmm myHmm = new MyHmm();
        myHmm.initHmm(numberOfStates, numberOfSymbols, TestData.getTestData(numberOfSymbols, sequenceLength, 100));
        myHmms.add(myHmm);
    }

    public int classify(){
        List<List<Integer>> testData = TestData.getTestData(numberOfSymbols, sequenceLength, 1);
        List<Integer> seq = testData.get(0);
        int rozpoznany = 0;
        double prop = 0;
        double max = -1;
        for(int i = 0; i < numberOfClassifications; i++){
            prop = myHmms.get(i).getProbability(seq);
            System.out.println(prop);
            if(prop > max){
                max = prop;
                rozpoznany = i;
            }
        }
        return rozpoznany;
    }

}
