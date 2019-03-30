/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package symbols;

import hmm.HmmTests;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class WczytajTesty {
    public static Map<String, HmmTests> wczytaj() throws IOException {
        File file = new File("baza_touchpad_testy.txt");
        Scanner in = null;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException ex) {

        }
        if(in != null){
            List<HmmTests> hmmTests = new ArrayList<>();
            while(in.hasNextLine()){
                List<List<Integer>> coordinates = new ArrayList<List<Integer>>();
                String id = in.nextLine();
                int ilosc = Integer.parseInt(in.nextLine());
                for(int i = 0; i < ilosc; i++){
                    StringTokenizer st = new StringTokenizer(in.nextLine());
                    ArrayList<Integer> coordinate = new ArrayList<Integer>();
                    while(st.hasMoreTokens()){
                        coordinate.add(Integer.parseInt(st.nextToken()));
                    }
                    coordinates.add(Tools.toSequenceVector(coordinate, 1, 3, 8, 6, 0, 0, 1));
                }
                hmmTests.add(HmmTests.createFromSequncesIntegers(coordinates, id));
            }
            return hmmTests.stream().collect(Collectors.toMap(x -> x.getId(), x -> x));
        }

        return null;
    }
}
