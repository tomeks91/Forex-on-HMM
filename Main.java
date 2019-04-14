import hmm.*;
import symbols.Wczytaj;
import symbols.WczytajTesty;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        List<LearningData> learningDatas = Wczytaj.wczytaj();
        Map<String, HmmTests> hmmTestsMap = WczytajTesty.wczytaj();

        List<HmmData> hmmClassificationData = learningDatas.stream()
                .map(learningData ->
                        new HmmData(learningData, hmmTestsMap.get(learningData.getId()), learningData.getId()))
                .collect(Collectors.toList());

        HMMClassify hmmClassify = HMMClassification.builder().numberOfSymbols(Wczytaj.numberOfSymbols)
                    .build()
                    .buildHmms(hmmClassificationData);

        System.out.println(hmmClassify.efficiencyOfClassification());
    }

}
