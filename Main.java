import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Classification classification = new Classification();
        classification.buildHmms();
        IntStream.range(0,100).forEach(i->System.out.println(classification.classify()));
        DoubleStream generate = DoubleStream.generate(()
                -> {
            return 1.0;
        });
        List<Double> collect = generate.limit(300).boxed().collect(Collectors.toList());
        ManageParameters manageParameters = new ManageParameters();
        manageParameters.getOutputRange(collect);

    }
}
