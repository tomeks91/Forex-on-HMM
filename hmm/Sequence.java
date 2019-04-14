package hmm;

import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Sequence implements Serializable{
    final private List<Integer> sequence;

    public Sequence(List<Integer> sequence){
        this.sequence = sequence;
    }

    public List<ObservationInteger> getSequenceObservation(){
        List<ObservationInteger> sequenceObservation =  new ArrayList<ObservationInteger>();
        sequence.stream().forEach(integer -> sequenceObservation.add(new ObservationInteger(integer)));
        return sequenceObservation;
    }
}
