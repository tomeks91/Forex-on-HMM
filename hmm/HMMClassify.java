package hmm;

public interface HMMClassify {
    public HMMClassify buildHmms();
    public int classify(Sequence seq);
    public double efficiencyOfClassification();
}
