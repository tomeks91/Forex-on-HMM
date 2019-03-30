package symbols;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tools {
    private static int BLACK_PIXEL = -65536;

    public static ArrayList<Integer> toSequenceVector(List<Integer> wyniki, int numberOfSections1, int numberOfSections2, int numberOfParts, int numberOfCoordinates, int partsAll, int rgbCount, int averaging) throws IOException{
        List<Integer> cor = testCoordinates(wyniki);
        if(cor==null)
            return null;
        return convertCoordinates(cor, numberOfSections1, numberOfSections2, numberOfParts, numberOfCoordinates, partsAll, rgbCount, averaging);
    }

    public static List<Integer> testCoordinates(List<Integer> wyniki){
        try{
            for (int i = 0; i < wyniki.size(); i++){
                int tmp = wyniki.get(i);
                if(tmp == 0){
                    wyniki.set(i, (wyniki.get(i-2)+wyniki.get(i+2))/2);
                }
            }
        } catch (Exception e) {
            return null;
        }
        return wyniki;
    }

    private static HashMap<String, Integer> getCorners(List<Integer> x, List<Integer> y, int partsAll, int numberOfSections1, int numberOfSections2){
        int max_x = 0;
        int max_y = 0;
        int min_x = 2400;
        int min_y = 2400;
        for (int i = 0; i < x.size(); i++){
            int tmp = x.get(i);
            if(tmp > max_x)
                max_x = tmp;
            if(tmp < min_x)
                min_x = tmp;
            tmp = y.get(i);
            if(tmp > max_y)
                max_y = tmp;
            if(tmp < min_y)
                min_y = tmp;
        }
        if(partsAll == 1){
            int width = max_x - min_x+1;
            int height = max_y - min_y+1;
            if((double)width/height > (double)numberOfSections1/numberOfSections2){
                height = (int)(width*(double)numberOfSections2/numberOfSections1)+1;
                max_y = min_y + height - 1;
            } else{
                width = (int)(height*(double)numberOfSections1/numberOfSections2)+1;
                max_x = min_x + width - 1;
            }
        }
        HashMap<String, Integer> corners = new HashMap<String, Integer>();
        corners.put("min_x", min_x);
        corners.put("max_x", max_x);
        corners.put("min_y", min_y);
        corners.put("max_y", max_y);
        return corners;
    }

    private static ArrayList<Integer> convertCoordinates(List<Integer> coor, int numberOfSections1, int numberOfSections2, int numberOfParts, int numberOfCoordinates, int partsAll, int rgbCount, int averaging) throws IOException { // partsAll 0 tak jak dotychczas, 1 znaczy że skalujemy obraz i robimy tyle sekwencji ile mamy punktów, 2 znaczy, że dostosowujemy obraz i robimy tyle sekwencji ile jest podramek
        HashMap<String, ArrayList<Integer>> coordinates = averaging(coor, averaging);
        ArrayList<Integer> x = coordinates.get("x");
        ArrayList<Integer> y = coordinates.get("y");

        HashMap<String, Integer> corners = getCorners(x, y, partsAll, numberOfSections1, numberOfSections2);
        int min_x = corners.get("min_x");
        int max_x = corners.get("max_x");
        int min_y = corners.get("min_y");
        int max_y = corners.get("max_y");

        if(rgbCount != 0)
            return methodFillingFrames(x,y, numberOfSections1, numberOfSections2, rgbCount, min_x, max_x, min_y, max_y);
        return methodAwayPoints(x,y, numberOfSections1, numberOfSections2, numberOfParts, numberOfCoordinates, min_x, max_x, min_y, max_y);
    }

    private static int part(int x1, int numberOfSections, double[] x) {
        int part = 0;
        for(int j = 0; j < numberOfSections-1;j++)
            if(x1 >= x[j])
                part++;
        return part;
    }

    private static Integer convert(int x1, int y1, int x2, int y2, int numberOfParts) {
        int a = (int) (getAngle(x1,y1,x2,y2)*numberOfParts)/360;
        return a;
    }

    private static double getAngle(int x1, int y1, int x2, int y2) {
        double angle = (float) Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));
        if(angle < 0){
            angle += 360;
        }
        return angle;
    }

    private static ArrayList<Integer> methodFillingFrames(List<Integer> x, List<Integer> y, int numberOfSections1, int numberOfSections2, int rgbCount, int min_x, int max_x, int min_y, int max_y) throws IOException {
        ArrayList<Integer> ret = new ArrayList<Integer>();

        int width = max_x - min_x +10;
        int height = max_y - min_y + 10;

        if((double)width/height > (double)numberOfSections1/numberOfSections2)
            height = (int)(width*(double)numberOfSections2/numberOfSections1)+1;
        else
            width = (int)(height*(double)numberOfSections1/numberOfSections2)+1;

        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D ig2 = bi.createGraphics();
        ig2.setBackground(Color.WHITE);
        ig2.setColor(Color.RED);
        int tmpx = 0, tmpy = 0;
        for (int i = 0; i < x.size(); i++){
            tmpx = x.get(i);
            tmpy = y.get(i);
            ig2.fillRect(tmpx-min_x, tmpy-min_y, 10, 10);
        }

        ig2.dispose();

        int pos_height = numberOfSections2 * 20;
        int pos_width = numberOfSections1 * 20;

        while(true){
            if(pos_height >= height && pos_width >= width)
                break;
            pos_height += numberOfSections2 * 10;
            pos_width += numberOfSections1 * 10;
        }

        Image bi2 = bi.getScaledInstance(pos_width, pos_height, Image.SCALE_DEFAULT);

        BufferedImage buffered = new BufferedImage(pos_width, pos_height, BufferedImage.TYPE_INT_RGB);
        buffered.getGraphics().drawImage(bi2, 0, 0 , null);
        buffered.getGraphics().dispose();

        min_x = 0;
        max_x = pos_width;
        min_y = 0;
        max_y = pos_height;

        for(int i = 0; i < numberOfSections1;i++){
            int section_min_x = min_x+(i)*((int)max_x-min_x)/numberOfSections1;
            int section_max_x = min_x+(i+1)*((int)max_x-min_x)/numberOfSections1;
            for(int j = 0; j < numberOfSections2; j++){
                int section_min_y = min_y+(j)*((int)max_y-min_y)/numberOfSections2;
                int section_max_y = min_y+(j+1)*((int)max_y-min_y)/numberOfSections2;
                int black_pixels = 0, all_pixels = 0;
                for(int k = section_min_x; k < section_max_x; k++){
                    for(int l = section_min_y; l < section_max_y; l++){
                        int pixel = buffered.getRGB(k, l);
                        if(pixel == BLACK_PIXEL)
                            black_pixels += 1;
                        all_pixels +=1;
                    }
                }

                int a = (int) ((double)black_pixels/all_pixels / ((double)1/rgbCount));
                if(a == rgbCount) a = a - 1;
                ret.add(a);
            }
        }

        return ret;
    }

    private static HashMap<String, ArrayList<Integer>> averaging(List<Integer> wyniki, int averaging) {
        ArrayList<Integer> x = new ArrayList<Integer>();
        ArrayList<Integer> y = new ArrayList<Integer>();
        for (int i = 0; i < (wyniki.size()-2*(averaging-1)); i++){
            int tmp= 0;
            int c = 0;
            for(int j = i; j < i+averaging*2 && j < wyniki.size(); j = j+2){
                tmp += wyniki.get(j);
                c++;
            }
            if(c == 0)
                System.out.print(i+" "+wyniki.size()+" "+averaging+" "+(i+averaging*2));
            if(i%2 == 0)
                x.add(tmp/c);
            else
                y.add(tmp/c);
        }
        HashMap<String, ArrayList<Integer>> ret = new HashMap<String, ArrayList<Integer>>();
        ret.put("x",x);
        ret.put("y",y);
        return ret;
    }

    private static ArrayList<Integer> methodAwayPoints(ArrayList<Integer> x, ArrayList<Integer> y, int numberOfSections1, int numberOfSections2, int numberOfParts, int numberOfCoordinates, int min_x, int max_x, int min_y, int max_y) {
        ArrayList<Integer> observation_vector = new ArrayList<Integer>();
        double[] xparts = new double[numberOfSections1-1];
        double[] yparts = new double[numberOfSections2-1];
        for(int i = 0; i < numberOfSections1-1;i++)
            xparts[i] = min_x+(i+1)*((double)max_x-min_x)/numberOfSections1;
        for(int i = 0; i < numberOfSections2-1;i++)
            yparts[i] = min_y+(i+1)*((double)max_y-min_y)/numberOfSections2;
        for (int i = numberOfCoordinates; i < x.size(); i++){
            int wsk = i-numberOfCoordinates;
            int part_x = part(x.get(wsk), numberOfSections1, xparts);
            int part_y = part(y.get(wsk), numberOfSections2, yparts);
            if(numberOfParts!=0){
                int angle_observation = convert(x.get(wsk),y.get(wsk),x.get(i),y.get(i),numberOfParts);
                observation_vector.add(wsk, (part_x+numberOfSections1*part_y)*numberOfParts+angle_observation);
            } else
                observation_vector.add(wsk, part_x+numberOfSections1*part_y);
        }
        return observation_vector;
    }

}
