package tests;

import com.google.common.collect.ImmutableList;
import forex.Currency;
import forex.ForexClassification;
import forex.ImportForex;
import hmm.HMMUtils;
import org.junit.Test;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    @Test
    public void write() throws IOException {
        ImportForex importForex = new ImportForex(
                ImmutableList.of("DAT_XLSX_EURUSD_M1_2017_03.xlsx",
                        "DAT_XLSX_EURUSD_M1_2017_02.xlsx",
                        "DAT_XLSX_EURUSD_M1_2017_01.xlsx",
                        "DAT_XLSX_EURUSD_M1_2017_04.xlsx",
                        "DAT_XLSX_EURUSD_M1_2017_05.xlsx"
                ), "eur/usd");
        Currency currency = importForex.doImport(Optional.empty());
        try(FileOutputStream f = new FileOutputStream(new File("currency.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f) ) {
            o.writeObject(currency);
        }
    }

    @Test
    public void test2() throws IOException, ClassNotFoundException {
        Currency currency = getCurrencyFromFile();
        System.out.println("Wczytano currency");
        Set<ForexClassification> forexSet = getForexFromFile();
        boolean added = false;
        ForexClassification forexClassification = ForexClassification.builder()
                .numberOfSymbols(5)
                .numberOfClassifications(5)
                .inputMultipler(1.18)
                .outputMultipler(0.2)
                .sequenceLength(17)
                .firstDistanceInputPoints(4)
                .currency(currency)
                .build();
        added = forexSet.add(forexClassification) || added;
        forexClassification = ForexClassification.builder()
                .numberOfSymbols(5)
                .numberOfClassifications(5)
                .inputMultipler(1.17)
                .outputMultipler(0.2)
                .sequenceLength(17)
                .firstDistanceInputPoints(4)
                .currency(currency)
                .build();
        added = forexSet.add(forexClassification) || added;
        forexSet.forEach(forex -> doForexClassification(forex));
        if(added) {
            saveForexSet(forexSet);
        }
    }

    private void doForexClassification(ForexClassification forex) {
        if(!forex.isHmmInitialized()){
            forex.buildHmms(null);
        }
        System.out.println(forex);
        System.out.println(forex.efficiencyOfClassification());
    }

    private Currency getCurrencyFromFile() throws IOException, ClassNotFoundException {
        try(FileInputStream fi = new FileInputStream(new File("currency.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);){
            return (Currency) oi.readObject();
        }
    }

    private void saveForexSet(Set<ForexClassification> forexClassificationSet) throws IOException {
        try(FileOutputStream f = new FileOutputStream(new File("forex.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f)) {
            o.writeObject(forexClassificationSet);
        }
    }

    private Set<ForexClassification> getForexFromFile() throws IOException, ClassNotFoundException {
        try(FileInputStream fi = new FileInputStream(new File("forex.txt"));
        ObjectInputStream oi = new ObjectInputStream(fi);){
            return (Set<ForexClassification>) oi.readObject();
        } catch (FileNotFoundException e){
            return new HashSet<>();
        }
    }

}
