
import LexParser.Analyser;
import LexParser.FileHelper;
import LexParser.Token;
import lr1.LR1Analyser;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        String inputFile = "example.txt";
        char[] input = FileHelper.readFile(inputFile);
        Analyser analyser = new Analyser(input);
        List<Token> tokens = analyser.analyse();

        LR1Analyser lr1Analyser = new LR1Analyser(tokens);
        List<String> reductions = lr1Analyser.Analyse();
        for (int i = 0; i < reductions.size(); i++) {
            System.out.println(reductions.get(i));
        }

    }
}
