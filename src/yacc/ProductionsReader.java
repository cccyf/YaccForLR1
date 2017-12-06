package yacc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductionsReader {
    private static final String in = "i.txt";
    public static List<String> readFromFile(){
        BufferedReader reader;
        List<String> productions = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(new File(in)));
            String line ;
            String first ;
            boolean firstInwhile = true;

            while ((line=reader.readLine())!=null){
                if (firstInwhile){
                    String[] parts = line.split("->");
                    first = parts[0].trim();
                    productions.add("#->"+first);
                    firstInwhile=false;
                }
                productions.add(line);
            }
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("cannot find i.txt!");
        }
        return productions;
    }
}
