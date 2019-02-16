import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Currency currency = ImportForex.main();
        TestClassification classification = new TestClassification();
        classification.buildHmms();
        IntStream.range(0,100).forEach(i->System.out.println(classification.classify(TestData.getTestData(5, 100, 1).get(0))));
        /*DoubleStream generate = DoubleStream.generate(()
                -> {
            return 1.0;
        });*/
        /*List<Double> collect = generate.limit(300).boxed().collect(Collectors.toList());
        HMMUtils manageParameters = new HMMUtils();
        manageParameters.getOutputRange(collect);*/

    }
}
