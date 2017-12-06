package yacc;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    private static final int deviationPosition = 3;

    private static List<I> iList = new ArrayList<>();

    public static void main(String[] args) {
        List<Character> uts = new ArrayList<>();
        List<Character> ts = new ArrayList<>();
        List<String> productions = ProductionsReader.readFromFile();
        Character startChar = productions.get(0).charAt(0);

        for (String s : productions) {
            for (Character c : s.toCharArray()) {
                if (c >= 'A' && c <= 'Z') {
                    if (!uts.contains(c))
                        uts.add(c);
                } else if (c != '#' && c != '-' && c != '>' && (!ts.contains(c))) {
                    ts.add(c);
                }
            }
        }
        ts.add('$');

        ChangeTable changeTable = new ChangeTable(ts, uts);


        I I0 = new I();
        I0.setNumber(0);

        for (int i = 0; i < productions.size(); i++) {
            String pro = productions.get(i);
            if (pro.charAt(0) == startChar) {
                I0.addTuple(new PFTuple(i, deviationPosition + 0, '$', pro));
            }
        }

        I0 = extendInside(I0, productions);

        Queue<I> iQueue = new LinkedBlockingQueue<>();
        iQueue.offer(I0);
        iList.add(I0);

        int nextNumber = 1;

        while (true) {
            if (iQueue.isEmpty()) {
                break;
            }
            I currentI = iQueue.poll();

            int thisNumber = currentI.getNumber();

            for (Character c : ts) {
                I nextI = currentI.movePoint(c);
                nextI = extendInside(nextI, productions);
                if (nextI.notEmpty()) {
                    I same = containsI(nextI);
                    if (same == null) {
                        nextI.setNumber(nextNumber);
                        iQueue.offer(nextI);
                        iList.add(nextI);
                        changeTable.setShiftString(thisNumber, nextNumber, c);
                        nextNumber++;
                    } else {
                        changeTable.setShiftString(thisNumber, same.getNumber(), c);
                    }
                }
            }

            for (int i = 0; i < uts.size(); i++) {
                char c = uts.get(i);
                I nextI = currentI.movePoint(c);
                nextI = extendInside(nextI, productions);
                if (nextI.notEmpty()) {
                    I same = containsI(nextI);
                    if (same == null) {
                        nextI.setNumber(nextNumber);
                        iQueue.offer(nextI);
                        iList.add(nextI);
                        changeTable.setGotoTable(thisNumber, i, nextNumber);
                        nextNumber++;
                    } else {
                        changeTable.setGotoTable(thisNumber, i, same.getNumber());
                    }
                }
            }

            Map<Character, Integer> reductions = currentI.getReductions();
            if (reductions != null && reductions.size() != 0) {
                changeTable.setReduceTable(thisNumber, reductions);
            }
        }

        changeTable.printTable();

        String productionsToWrite = getProductionsToWrite(productions);
        String actionTable = changeTable.getTableString();
        String gotoTable = changeTable.getGOTOString();
        String gotoSequence = changeTable.getGotoSequence();
        String tokenMap = changeTable.getUtsString();

        FileWriter.writeAllFiles(productionsToWrite,actionTable,gotoTable,tokenMap,gotoSequence);

    }


    private static String getProductionsToWrite(List<String> productions) {
        String result = "";
        for (int i = 0; i < productions.size(); i++) {
            result+="\""+productions.get(i)+"\",";
        }
        result = result.substring(0,result.length()-1);
        return result;
    }


    private static I containsI(I currentI) {
        for (int j = 0; j < iList.size(); j++) {
            I ij = iList.get(j);
            if (ij.isSameI(currentI)) {
                return ij;
            }
        }
        return null;
    }

    private static I extendInside(I i, List<String> productions) {
        boolean addNew = true;
        while (addNew) {
            addNew = false;
            Map<Character, String> unterminals = i.getUnTerminals();
            for (int j = 0; j < productions.size(); j++) {
                String s = productions.get(j);
                if (unterminals.keySet().contains(s.charAt(0))) {
                    String temp = unterminals.get(s.charAt(0));
                    for (int k = 0; k < temp.length(); k++) {
                        char c = temp.charAt(k);
                        if (c >= 'A' && c <= 'Z') {
                            String firsts = getFirst(c, productions);
                            if (firsts.length() == 0) {
                                continue;
                            }
                            for (char each : firsts.toCharArray()) {
                                PFTuple tuple = new PFTuple(j, 0 + deviationPosition, each, s);
                                if (!i.hasTuple(tuple)) {
                                    i.addTuple(tuple);
                                    addNew = true;
                                }
                            }
                        } else {
                            PFTuple tuple = new PFTuple(j, 0 + deviationPosition, temp.charAt(k), s);
                            if (!i.hasTuple(tuple)) {
                                i.addTuple(tuple);
                                addNew = true;
                            }
                        }
                    }
                }
            }
        }
        return i;
    }

    private static String getFirst(char c, List<String> productions) {
        String first = "";
        for (String s : productions) {
            if (s.charAt(0) == c) {
                char f = s.charAt(3);
                if (!(f >= 'A' && f <= 'Z')) {
                    first += s.charAt(3);
                }
            }
        }
        return first;
    }
}
