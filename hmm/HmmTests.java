package hmm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class HmmTests {
    final private List<Sequence> sequences;
    private final String id;

    public static HmmTests createFromSequncesIntegers(List<List<Integer>> list, String id){
        return new HmmTests(list.stream().map(elem -> new Sequence(elem)).collect(Collectors.toList()), id);
    }

    public HmmTests addToSequences(Sequence sequence){
        sequences.add(sequence);
        return this;
    }
}
