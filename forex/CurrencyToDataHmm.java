package forex;

import hmm.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.*;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
public class CurrencyToDataHmm {
    private final int sequenceLength;
    private final double inputMultipler;
    private final int firstDistanceInputPoints;
    private final double outputMultipler;
    private final int numberOfSymbols;
    private final int numberOfClassifications;
    private final Currency currency;
    private TreeMap<Date, Double> results;
    private List<Date> objects;
    private final Map<String, HmmData> hmmDataMap = new HashMap<>();
    private static Random random = new Random();

    public List<HmmData> convert(){
        results = (TreeMap)currency.getResults();
        objects = new ArrayList(results.keySet());
        List<Integer> awayPoints = getAwayPoints();
        int lastIndAwayPoint = awayPoints.get(awayPoints.size()-1);
        System.out.println(lastIndAwayPoint);
        double outputIndex = outputMultipler * lastIndAwayPoint;
        for(int i = lastIndAwayPoint; i < results.size()-outputIndex; i++){
            List<Double> values = getValuesOfAwayPoints(awayPoints, i);
            Sequence sequence = convertValuesToSequence(values);
            double outputValue = results.get(objects.get(i + (int) outputIndex));
            Integer partition = HMMUtils.getPartition(outputValue, HMMUtils.getRanges(values, numberOfClassifications));
            updateHmmData(sequence, ""+partition.intValue(), i);
        }
        return hmmDataMap.values().stream().collect(Collectors.toList());
    }

    private List<Integer> getAwayPoints() {
        List<Integer> awayPoints = new ArrayList<>();
        awayPoints.add(0);
        double distance = firstDistanceInputPoints*inputMultipler;
        double point = distance;
        for(int i = 0; i < sequenceLength; i++){
            awayPoints.add((int)point);
            distance = distance*inputMultipler;
            point = distance + point;
        }
        return awayPoints;
    }

    private List<Double> getValuesOfAwayPoints(List<Integer> awayPoints, int i) {
        List<Double> values = new ArrayList<>();
        for(int j = 0; j < awayPoints.size(); j++){
            int index = i - awayPoints.get(j);
            values.add(results.get(objects.get(index)));
        }
        return values;
    }

    private void updateHmmData(Sequence sequence, String key, int i) {
        if(!hmmDataMap.containsKey(key)){
            HmmData hmmData = new HmmData(new LearningData(new ArrayList<Sequence>(), key), new HmmTests(new ArrayList<Sequence>(), key), key);
            hmmDataMap.put(key, hmmData);
        }
        HmmData hmmData = hmmDataMap.get(key);
        if(i < 130000) {
            hmmData.getLearningData().addToSequences(sequence);
        } else {
            hmmData.getHmmTests().addToSequences(sequence);
        }
    }

    private Sequence convertValuesToSequence(List<Double> values) {
        List<Double> ranges = HMMUtils.getRanges(values, numberOfSymbols);
        return new Sequence(
                values.stream()
                        .map(value -> HMMUtils.getPartition(value, ranges))
                        .collect(Collectors.toList())
        );
    }

}
