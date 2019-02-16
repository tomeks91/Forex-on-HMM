import java.util.List;
import java.util.Map;

/**
 * Created by tomek on 16.02.19.
 */
public class ForexClassification extends HMMClassification{
    Currency currency;

    ForexClassification(Currency currency){
        this.currency = currency;
    }

    @Override
    Map<Integer, List<List<Integer>>> getData() {
        return null;
    }
}
