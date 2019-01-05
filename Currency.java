import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by tomek on 01.01.19.
 */
public class Currency {
    private String code = "";
    private Map<Date, Double> results = new TreeMap<>();

    public Currency(String code) {
        this.code = code;
    }

    public Map<Date, Double> getResults() {
        return results;
    }

    public void setResults(Map<Date, Double> results) {
        this.results = results;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void addToResults(Date date, Double currency){
        if(this.results.containsKey(date)){
            throw new RuntimeException("Istnieje taka data "+date);
        }
        this.results.put(date, currency);
    };
}
