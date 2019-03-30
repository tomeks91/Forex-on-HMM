package symbols;

import hmm.LearningData;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Wczytaj {
    public static int numberOfSymbols = 0;

    public static List<LearningData> wczytaj() throws IOException {
        File file = new File("baza_touchpad.txt");
        Scanner in = null;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException ex) {

        }
        if (in != null) {
            ArrayList<Symbol> noweGesty = new ArrayList<Symbol>();
            while (in.hasNextLine()) {
                List<List<Integer>> coordinates = new ArrayList<List<Integer>>();
                String id = in.nextLine();
                int ilosc = Integer.parseInt(in.nextLine());
                for (int i = 0; i < ilosc; i++) {
                    StringTokenizer st = new StringTokenizer(in.nextLine());
                    ArrayList<Integer> coordinate = new ArrayList<Integer>();
                    while (st.hasMoreTokens()) {
                        coordinate.add(Integer.parseInt(st.nextToken()));
                    }
                    coordinates.add(coordinate);
                }
                Symbol nowyGest = new Symbol(id, coordinates, 2, 1, 3, 8, 6, 0, 0, 1);
                numberOfSymbols = nowyGest.getNumberOfSymbols();
                noweGesty.add(nowyGest);
            }

            return noweGesty.stream().map(gest -> LearningData.createFromSequncesIntegers(gest.getSequences(), gest.getId()))
                    .collect(Collectors.toList());

        }
        return null;
    }
}