package hmm;

import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Sequence implements Serializable{
    final private List<Integer> sequence;
    final private List<ObservationInteger> sequenceObservation;

    public Sequence(List<Integer> sequence){
        this.sequence = sequence;
        this.sequenceObservation =  new ArrayList<ObservationInteger>();
        sequence.stream().forEach(integer -> sequenceObservation.add(new ObservationInteger(integer)));
    }
}
