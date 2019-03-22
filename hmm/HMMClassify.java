package hmm;

import java.util.List;

public interface HMMClassify {
    public HMMClassify buildHmms();
    public int classify(List<Integer> seq);
}
