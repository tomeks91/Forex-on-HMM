import hmm.*;

import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        HMMClassification testClassification = new TestClassification().buildHmms();
        IntStream.range(0, 100).forEach(i -> System.out.println(testClassification.classify(TestDataUtil.getTestData(5, 100, 1).get(0))));
        ImportForex importForex = new ImportForex("DAT_XLSX_EURUSD_M1_201812.xlsx", "eur/usd");
        Currency currency = importForex.doImport();
        HMMClassification forexClassification = new ForexClassification(currency).buildHmms();
    }

}
