package forex;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Currency implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String code;
    private final Map<Date, Double> results;

    public void addToResults(Date date, Double currency){
        if(this.results.containsKey(date)){
            throw new RuntimeException("Istnieje taka data "+date);
        }
        this.results.put(date, currency);
    };
}
