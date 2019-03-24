package tests;

import hmm.HMMUtils;
import org.junit.Test;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class TestUtils {

    @Test
    public void test(){
        DoubleStream generate = DoubleStream.generate(() -> { return 1.0; });
        List<Double> collect = generate.limit(300).boxed().collect(Collectors.toList());
        List<Double> outputRange = HMMUtils.getRanges(collect, 5);
        assertThat(outputRange, contains(1.0, 1.0, 1.0, 1.0));
    }

}
