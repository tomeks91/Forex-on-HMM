import be.ac.ulg.montefiore.run.jahmm.ForwardBackwardCalculator;
import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;
import be.ac.ulg.montefiore.run.jahmm.OpdfIntegerFactory;
import be.ac.ulg.montefiore.run.jahmm.learn.BaumWelchScaledLearner;
import be.ac.ulg.montefiore.run.jahmm.learn.KMeansLearner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomek on 09.12.18.
 */
public class MyHmm {

    private Hmm<ObservationInteger > hmm = null;

    public void initHmm(int numberOfStates, int numberOfSymbols, List<List<Integer>> seqData) {
        List<List<ObservationInteger>> sequencesHmm = new ArrayList<List<ObservationInteger>>();
        seqData.stream().forEach(seq -> sequencesHmm.add(getSequenceHmmFromIntegerSequence(seq)));
        KMeansLearner<ObservationInteger> kml =
                new KMeansLearner <ObservationInteger >(numberOfStates ,
                        new OpdfIntegerFactory(numberOfSymbols) , sequencesHmm);
        hmm = kml.learn() ;
        BaumWelchScaledLearner bwl = new BaumWelchScaledLearner();
        hmm = bwl.learn(hmm, sequencesHmm);
    }

    public double getProbability(List<Integer> seq){
        List<ObservationInteger> sequenceHmm = getSequenceHmmFromIntegerSequence(seq);
        ForwardBackwardCalculator cal = new ForwardBackwardCalculator(sequenceHmm, hmm);
        return cal.probability();
    }

    private List<ObservationInteger> getSequenceHmmFromIntegerSequence(List<Integer> seq) {
        List<ObservationInteger> sequenceHmm = new ArrayList<ObservationInteger>();
        seq.stream().forEach(integer -> sequenceHmm.add(new ObservationInteger(integer)));
        return sequenceHmm;
    }

}
