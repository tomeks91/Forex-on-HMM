package hmm;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.Map;

@AllArgsConstructor
@Getter
public class Currency {
    private final String code;
    private final Map<Date, Double> results;

    public void addToResults(Date date, Double currency){
        if(this.results.containsKey(date)){
            throw new RuntimeException("Istnieje taka data "+date);
        }
        this.results.put(date, currency);
    };
}
