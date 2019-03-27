package hmm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class LearningData {
    final private List<Sequence> sequences;
}
