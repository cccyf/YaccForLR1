package yacc;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class I {
    private List<PFTuple> tuples = new ArrayList<>();
    private int number;

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void addTuple(PFTuple tuple) {
        tuples.add(tuple);
    }

    public boolean notEmpty() {
        if (tuples == null || tuples.size() == 0) {
            return false;
        }
        return true;
    }

    public I movePoint(char c) {
        I result = new I();
        for (PFTuple t : tuples) {
            PFTuple tuple = t.movePoint(c);
            if (tuple != null) {
                result.addTuple(tuple);
            }
        }
        return result;
    }

    public int getSize() {
        return this.tuples.size();
    }

    public boolean hasTuple(PFTuple t) {
        for (int i = 0; i < tuples.size(); i++) {
            if (tuples.get(i).isSameTo(t))
                return true;
        }
        return false;
    }

    public boolean isSameI(I i) {
        if (i == null || i.getSize() != this.tuples.size()) {
            return false;
        }

        for (PFTuple each : this.tuples) {
            if (!i.hasTuple(each)) {
                return false;
            }
        }

        return true;
    }

    public Map<Character, Integer> getReductions() {
        Map<Character, Integer> reductions = new HashMap<>();
        for (PFTuple t : this.tuples) {
            if (t.end()) {
                reductions.put(t.follow, t.pNumber);
            }
        }
        return reductions;
    }

    public void print() {
        System.out.println(this.number);
        for (int i = 0; i < this.tuples.size(); i++) {
            System.out.println(this.tuples.get(i).production.substring(0,this.tuples.get(i).pointPosition)+"."+this.tuples.get(i).production.substring(this.tuples.get(i).pointPosition)+"  "+this.tuples.get(i).follow);
        }
    }

    public Map<Character, String> getUnTerminals() {
        Map<Character, String> unterminals = new HashMap<>();
        for (PFTuple t : this.tuples) {
            Pair<Character, Character> c = t.nextIfUnterminal();
            if (c != null) {
                char key = c.getKey();
                unterminals.put(key, unterminals.containsKey(key) ? unterminals.get(key) + c.getValue() : c.getValue() + "");
            }
        }
        return unterminals;
    }
}
