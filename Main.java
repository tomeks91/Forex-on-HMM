import forex.Currency;
import forex.ForexClassification;
import forex.ImportForex;
import hmm.*;
import utils.Repeater;

public class Main {

    public static void main(String[] args) {
        HMMClassify testClassification = TestClassification.build();
        Repeater.perform(100, () -> System.out.println(testClassification.classify(TestDataUtil.getTestData(5, 100, 1).get(0))));
        ImportForex importForex = new ImportForex("DAT_XLSX_EURUSD_M1_201812.xlsx", "eur/usd");
        Currency currency = importForex.doImport();
        HMMClassify forexClassification = ForexClassification.build(5, 5, currency);
    }

}
