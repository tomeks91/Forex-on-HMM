package forex;

import hmm.HMMUtils;
import hmm.HmmData;
import hmm.Sequence;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Builder
public class CurrencyToDataHmm {
    private final int sequenceLength;
    private final double multipler;
    private final int numberOfSymbols;
    private final int numberOfClassifications;
    private final Currency currency;

    public List<HmmData> convert(){
        TreeMap<Date, Double> results = (TreeMap)currency.getResults();
        List<Date> objects = new ArrayList(results.keySet());
        List<Integer> awayPoints = new ArrayList<>();
        awayPoints.add(0);
        double point = multipler;
        for(int i = 0; i < sequenceLength; i++){
            awayPoints.add((int)point);
            point = point*multipler;
        }
        int lastIndAwayPoint = awayPoints.get(awayPoints.size()-1);
        for(int i = lastIndAwayPoint; i < results.size(); i++){
            List<Double> values = new ArrayList<>();
            for(int j = 0; j < awayPoints.size(); j++){
                int index = i - awayPoints.get(j);
                double value = results.get(objects.get(index));
                values.add(value);
            }
            Sequence sequence = convertValuesToSequence(values);
        }
        return null;
    }

    private Sequence convertValuesToSequence(List<Double> values) {
        List<Double> ranges = HMMUtils.getRanges(values, numberOfSymbols);
        List<Integer> sequence = values.stream()
                .map(value -> HMMUtils.getPartition(value, ranges))
                .collect(Collectors.toList());
        return new Sequence(sequence);
    }

}
