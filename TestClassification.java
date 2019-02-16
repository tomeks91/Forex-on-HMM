import java.util.List;
import java.util.Map;

/**
 * Created by tomek on 09.12.18.
 */
public class TestClassification extends HMMClassification{

    @Override
    Map<Integer, List<List<Integer>>> getData() {
        return TestDataUtil.getTestDataForBuildHmms(numberOfSymbols, 100, 100, numberOfClassifications);
    }

}
