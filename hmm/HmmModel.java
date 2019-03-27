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
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class HmmModel {

    private final Hmm<ObservationInteger > hmm;
    private static final int NUMBER_OF_STATES = 5;

    public double getProbability(Sequence seq){
        return new ForwardBackwardCalculator(seq.getSequenceObservation(), hmm).probability();
    }

    public static HmmModel getInstance(LearningData learningData, int numberOfSymbols) {
        List<List<ObservationInteger>> sequencesHmm = learningData.getSequences().stream().map(seq -> seq.getSequenceObservation()).collect(Collectors.toList());
        KMeansLearner<ObservationInteger> kml =
                new KMeansLearner <ObservationInteger >(NUMBER_OF_STATES ,
                        new OpdfIntegerFactory(numberOfSymbols) , sequencesHmm);
        Hmm<ObservationInteger > hmm = kml.learn() ;
        BaumWelchScaledLearner bwl = new BaumWelchScaledLearner();
        hmm = bwl.learn(hmm, sequencesHmm);
        return new HmmModel(hmm);
    }

}
