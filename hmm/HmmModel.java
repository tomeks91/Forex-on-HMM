package hmm;

import be.ac.ulg.montefiore.run.jahmm.ForwardBackwardCalculator;
import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;
import be.ac.ulg.montefiore.run.jahmm.OpdfIntegerFactory;
import be.ac.ulg.montefiore.run.jahmm.learn.BaumWelchScaledLearner;
import be.ac.ulg.montefiore.run.jahmm.learn.KMeansLearner;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class HmmModel {

    private final Hmm<ObservationInteger > hmm;
    private static final int NUMBER_OF_STATES = 5;

    public double getProbability(List<Integer> seq){
        List<ObservationInteger> sequenceHmm = getSequenceHmmFromIntegerSequence(seq);
        return new ForwardBackwardCalculator(sequenceHmm, hmm).probability();
    }

    public static HmmModel getInstance(List<List<Integer>> seqData, int numberOfSymbols) {
        List<List<ObservationInteger>> sequencesHmm = mapSequencesIntegersToObservationIntegers(seqData);
        KMeansLearner<ObservationInteger> kml =
                new KMeansLearner <ObservationInteger >(NUMBER_OF_STATES ,
                        new OpdfIntegerFactory(numberOfSymbols) , sequencesHmm);
        Hmm<ObservationInteger > hmm = kml.learn() ;
        BaumWelchScaledLearner bwl = new BaumWelchScaledLearner();
        hmm = bwl.learn(hmm, sequencesHmm);
        return new HmmModel(hmm);
    }

    private static List<List<ObservationInteger>> mapSequencesIntegersToObservationIntegers(List<List<Integer>> seqData) {
        List<List<ObservationInteger>> sequencesHmm = new ArrayList<List<ObservationInteger>>();
        seqData.stream().forEach(seq -> sequencesHmm.add(getSequenceHmmFromIntegerSequence(seq)));
        return sequencesHmm;
    }

    private static List<ObservationInteger> getSequenceHmmFromIntegerSequence(List<Integer> seq) {
        List<ObservationInteger> sequenceHmm = new ArrayList<ObservationInteger>();
        seq.stream().forEach(integer -> sequenceHmm.add(new ObservationInteger(integer)));
        return sequenceHmm;
    }

}
