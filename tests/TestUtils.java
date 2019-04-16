package tests;

import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import de.javakaffee.kryoserializers.KryoReflectionFactorySupport;
import forex.Currency;
import forex.ForexClassification;
import hmm.*;
import org.junit.Test;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class TestUtils {

    private static Kryo kryo = new KryoReflectionFactorySupport();

    static {
        kryo.setReferences(false);
        kryo.register(ForexClassification.class);
        kryo.register(java.util.HashSet.class);
        kryo.register(ObservationInteger.class);
        kryo.register(forex.Currency.class);
        kryo.register(HMMClassification.class);
        kryo.register(HmmModel.class);
        kryo.register(HmmTests.class);
        kryo.register(Sequence.class);
        kryo.register(java.util.TreeMap.class);
        kryo.register(java.util.Date.class);
        kryo.register(java.util.ArrayList.class);
        kryo.register(be.ac.ulg.montefiore.run.jahmm.Hmm.class);
        kryo.register(double[][].class);
        kryo.register(double[].class);
        kryo.register(be.ac.ulg.montefiore.run.jahmm.OpdfInteger.class);
    }

    @Test
    public void test(){
        DoubleStream generate = DoubleStream.generate(() -> { return 1.0; });
        List<Double> collect = generate.limit(300).boxed().collect(Collectors.toList());
        List<Double> outputRange = HMMUtils.getRanges(collect, 5);
        assertThat(outputRange, contains(1.0, 1.0, 1.0, 1.0));
    }

    @Test
    public void write() throws IOException, ClassNotFoundException {
        Currency currency = getCurrencyFromFile();
        Output output = new Output(new FileOutputStream("currency.txt"));
        kryo.writeObject(output, currency);
        output.close();
    }

    @Test
    public void test2() throws IOException, ClassNotFoundException {
        Currency currency = getCurrencyFromFile();
        System.out.println("Wczytano currency");
        Set<ForexClassification> forexSet = getForexFromFile();
        boolean added = false;
        ForexClassification forexClassification = ForexClassification.builder()
                .numberOfSymbols(5)
                .numberOfClassifications(2)
                .inputMultipler(1.13)
                .outputMultipler(0.2)
                .sequenceLength(17)
                .firstDistanceInputPoints(4)
                .currency(currency)
                .build();
        added = forexSet.add(forexClassification) || added;
        forexClassification = ForexClassification.builder()
                .numberOfSymbols(5)
                .numberOfClassifications(5)
                .inputMultipler(1.16)
                .outputMultipler(0.2)
                .sequenceLength(20)
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
            forex.buildHmms();
        }
        System.out.println(forex);
        System.out.println(forex.efficiencyOfClassification());
    }

    private Currency getCurrencyFromFile() throws IOException, ClassNotFoundException {
        try {
            Input input = new Input(new FileInputStream("currency.txt"));
            Currency object2 = kryo.readObject(input, Currency.class);
            input.close();
            return object2;
        } catch (FileNotFoundException e){
            return null;
        }
    }

    private void saveForexSet(Set<ForexClassification> forexClassificationSet) throws IOException {
        Output output = new Output(new FileOutputStream("forex.txt"));
        kryo.writeObject(output, forexClassificationSet);
        output.close();
    }

    private Set<ForexClassification> getForexFromFile() throws IOException, ClassNotFoundException {
        try {
            Input input = new Input(new FileInputStream("forex.txt"));
            Set<ForexClassification> object2 = kryo.readObject(input, HashSet.class);
            input.close();
            return object2;
        } catch (FileNotFoundException e){
            return new HashSet<>();
        }
    }


    /**
     TODO
     1.ForexClassification(sequenceLength=17, inputMultipler=1.18, firstDistanceInputPoints=4, outputMultipler=0.2, numberOfSymbols=5, numberOfClassifications=5, currency=forex.Currency@4bb3c56, hmmClassify=hmm.HMMClassification@6be46e8f)
    Caching results for max speed
     19132 36551 0.5234330114087166
            3341 16671 0.20040789394757363
            4974 17031 0.2920556632023956
            4364 19677 0.22178177567718657
            21206 42466 0.4993641972401451
            0.4004426115592616
     2. Other numberOfClassifications / Symbols learning
     3. Multiple HMMs - one with 3 numberOfClassifications one with 5 cojunction
     4. Exact results 0 1 2 3 4 percentage display/caching

     */

}
