package hmm;

import java.util.List;

public interface HMMClassify {
    public HMMClassify buildHmms(List<HmmData> data);
    public int classify(Sequence seq);
    public double efficiencyOfClassification();
}
