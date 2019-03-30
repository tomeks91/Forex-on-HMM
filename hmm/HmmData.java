package hmm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class HmmData {
    private final LearningData learningData;
    private final HmmTests hmmTests;
    private final String id;
}
