package symbols;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Symbol {
    private List<List<Integer>> coordinates = null;

    public List<List<Integer>> getSequences() {
        return sequences;
    }

    private List<List<Integer>> sequences = null;
    private ArrayList<ArrayList<Integer>> testingCoordinates = new ArrayList<ArrayList<Integer>>();
    private String id = "";
    private int goodRecognation = 0;
    private int badRecognation = 0;
    private int numberOfStates;
    private int numberOfSymbols;
    private int numberOfSections1;
    private int numberOfSections2;
    private int numberOfParts;
    private int numberOfCoordinates;
    private int partsAll;
    private int rgbCount;
    private int averaging;
    private String katalog = "";
    private String program = "";

    Symbol(String id, List<List<Integer>> coordinates, int numberOfStates, int numberOfSections1, int numberOfSections2, int numberOfParts, int numberOfCoordinates, int partsAll, int rgbCount, int averaging) throws IOException {
        this.id = id;
        this.coordinates = coordinates;
        setHMM(numberOfStates, numberOfSections1, numberOfSections2, numberOfParts, numberOfCoordinates, partsAll, rgbCount, averaging);
    }

    public int getNumberOfSections1(){
        return this.numberOfSections1;
    }

    public int getAveraging(){
        return this.averaging;
    }

    public int getNumerAllParts(){
        return this.partsAll;
    }

    public int getRgbCount(){
        return this.rgbCount;
    }

    public void setProgram(String kat, String pr){
        this.katalog = kat;
        this.program = pr;
    }

    public String getProgram(){
        return program;
    }

    public String getKatalog(){
        return katalog;
    }

    public int getNumberOfSections2(){
        return this.numberOfSections2;
    }

    public int getNumberOfParts(){
        return this.numberOfParts;
    }

    public int getNumberOfCoordinates(){
        return this.numberOfCoordinates;
    }

    public void setHMM(int numberOfStates, int numberOfSections1, int numberOfSections2, int numberOfParts, int numberOfCoordinates, int partsAll, int rgbCount, int averaging) throws IOException{
        if(numberOfParts == 0)
            numberOfSymbols = numberOfSections1*numberOfSections2;
        else
            numberOfSymbols =  numberOfSections1*numberOfSections2*numberOfParts;
        if(rgbCount != 0)
            numberOfSymbols = rgbCount;
        sequences = new ArrayList<List<Integer>>();
        int countSymbols = coordinates.size();
        for(int i = 0; i < countSymbols; i++){
            ArrayList<Integer> seq = Tools.toSequenceVector(coordinates.get(i), numberOfSections1, numberOfSections2, numberOfParts, numberOfCoordinates, partsAll, rgbCount, averaging);
            sequences.add(seq);
        }
        this.numberOfSections1 = numberOfSections1;
        this.numberOfSections2 = numberOfSections2;
        this.numberOfParts = numberOfParts;
        this.numberOfStates = numberOfStates;
        this.numberOfCoordinates = numberOfCoordinates;
        this.partsAll = partsAll;
        this.rgbCount = rgbCount;
        this.averaging = averaging;
    }

    public String getId(){
        return id;
    }

    public void goodRecognation(){
        goodRecognation++;
    }

    public void badRecognation(){
        badRecognation++;
    }

    public void setTests(ArrayList<ArrayList<Integer>> a){
        this.testingCoordinates = a;
    }

    public ArrayList<ArrayList<Integer>> getTests(){
        return this.testingCoordinates;
    }

    public List<List<Integer>> getCoordinates(){
        return this.coordinates;
    }

    public int getNumberOfTests(){
        return this.testingCoordinates.size();
    }

    public void addTest(ArrayList<Integer> x){
        this.testingCoordinates.add(x);
    }

    public String getStatistics(){
        HashMap<String, Integer> stats = new  HashMap<String, Integer>();
        int procent = 0;
        try{
            procent = (int)(100*((double)goodRecognation/(badRecognation+goodRecognation)));
        } catch(Exception e){
            procent = 0;
        }
        return "Rozpoznano "+goodRecognation+"/"+(goodRecognation+badRecognation)+"     Skuteczność "+procent+"%";
    }

    public String toFile(){
        String corRet = "";
        int size = coordinates.size();
        for(List<Integer> cor: coordinates){
            for(Integer number: cor)
                corRet = corRet.concat(""+number+" ");
            corRet = corRet.concat("\n");
        }
        return getId()+"\n"+size+"\n"+corRet;
    }

    public String testToFile(){
        String corRet = "";
        if(testingCoordinates==null)
            return corRet;
        int size = testingCoordinates.size();
        for(ArrayList<Integer> cor: testingCoordinates){
            for(Integer number: cor)
                corRet = corRet.concat(""+number+" ");
            corRet = corRet.concat("\n");
        }
        return getId()+"\n"+size+"\n"+corRet;
    }

    public int getNumberOfSymbols() {
        return numberOfSymbols;
    }

}
