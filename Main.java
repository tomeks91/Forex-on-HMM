import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        ImportForex importForex = new ImportForex("DAT_XLSX_EURUSD_M1_201812.xlsx", "eur/usd");
        Currency currency = importForex.doImport();
        TestClassification classification = new TestClassification();
        classification.buildHmms();
        IntStream.range(0,100).forEach(i->System.out.println(classification.classify(TestDataUtil.getTestData(5, 100, 1).get(0))));
        /*DoubleStream generate = DoubleStream.generate(()
                -> {
            return 1.0;
        });*/
        /*List<Double> collect = generate.limit(300).boxed().collect(Collectors.toList());
        HMMUtils manageParameters = new HMMUtils();
        manageParameters.getOutputRange(collect);*/

    }
}
