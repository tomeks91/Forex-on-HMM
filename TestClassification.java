import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Created by tomek on 09.12.18.
 */
public class TestClassification extends HMMClassification{

    @Override
    Map<Integer, List<List<Integer>>> getData() {
        return TestData.getTestDataForBuildHmms(numberOfSymbols, 100, 100, numberOfClassifications);
    }

}
