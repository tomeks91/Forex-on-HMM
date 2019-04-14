package hmm;

import be.ac.ulg.montefiore.run.jahmm.ForwardBackwardCalculator;
import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;
import be.ac.ulg.montefiore.run.jahmm.OpdfIntegerFactory;
import be.ac.ulg.montefiore.run.jahmm.learn.BaumWelchScaledLearner;
import be.ac.ulg.montefiore.run.jahmm.learn.KMeansLearner;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class HmmModel implements Serializable {
    private final Hmm<ObservationInteger > hmm;
    private final HmmTests tests;
    private static final int NUMBER_OF_STATES = 2;

    public double getProbability(Sequence seq){
        return new ForwardBackwardCalculator(seq.getSequenceObservation(), hmm).probability();
    }

    public static HmmModel getInstance(HmmData hmmData, int numberOfSymbols) {
        List<List<ObservationInteger>> sequencesHmm = hmmData.getLearningData().getSequences().stream()
                .map(seq -> seq.getSequenceObservation()).collect(Collectors.toList());
        KMeansLearner<ObservationInteger> kml =
                new KMeansLearner <ObservationInteger >(NUMBER_OF_STATES ,
                        new OpdfIntegerFactory(numberOfSymbols) , sequencesHmm);
        return new HmmModel(new BaumWelchScaledLearner().learn(kml.learn(), sequencesHmm), hmmData.getHmmTests());
    }

}
