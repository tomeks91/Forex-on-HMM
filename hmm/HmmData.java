package hmm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
@Getter
public class HmmData implements Serializable {
    private final LearningData learningData;
    private final HmmTests hmmTests;
    private final String id;
}
