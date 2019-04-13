package hmm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class LearningData {
    final private List<Sequence> sequences;
    private final String id;

    public static LearningData createFromSequncesIntegers(List<List<Integer>> list, String id){
        return new LearningData(list.stream().map(elem -> new Sequence(elem)).collect(Collectors.toList()), id);
    }

    public LearningData addToSequences(Sequence sequence){
        sequences.add(sequence);
        return this;
    }
}
