package yacc;

import java.util.*;

public class ChangeTable {
    Map<Integer, String[]> changeTable = new TreeMap<>();//the action table
    Map<Integer, int[]> gotoTable = new TreeMap<>();// the goto table

    List<Character> ts = new ArrayList<>(); // the Vt
    List<Character> uts = new ArrayList<>(); // the Vn

    public ChangeTable(List<Character> ts, List<Character> uts) {
        this.ts = ts;
        this.uts = uts;
    }

    public void setShiftString(int i1, int i2, char c) {
        int pos = this.ts.indexOf(c);

        String[] str = changeTable.get(i1);
        if (str == null) {
            str = new String[ts.size()];
        }
        str[pos] = "s" + i2;
        this.changeTable.put(i1, str);
    }

    public void setGotoTable(int i1, int pos, int to) {
        int[] ints = this.gotoTable.get(i1);
        if (ints == null) {
            ints = new int[uts.size()];
            for (int i = 0; i < ints.length; i++) {
                ints[i] = -1;
            }
        }
        ints[pos] = to;
        this.gotoTable.put(i1, ints);
    }

    public void setReduceTable(int p, Map<Character, Integer> map) {
        String[] str = this.changeTable.get(p);
        if (str == null) {
            str = new String[ts.size()];
        }
        for (Character c : map.keySet()) {
            if (map.get(c) == 0) {
                str[ts.indexOf(c)] = "a";
            } else {
                str[ts.indexOf(c)] = "r" + map.get(c);
            }
        }
        this.changeTable.put(p, str);
    }

    public void printTable() {
        System.out.println("yacc.ChangeTable");
        changeTable.forEach((i, s) -> {
            System.out.print("{");
            for (int j = 0; j < s.length - 1; j++) {
                if (s[j] != null) {
                    System.out.print("\"" + s[j] + "\",");
                } else {
                    System.out.print("null,");
                }
            }
            if (s[s.length - 1] != null) {
                System.out.print("\"" + s[s.length - 1] + "\"}");
            } else {
                System.out.print("null}");
            }
            int[] gotoInts = gotoTable.get(i);
            if (gotoInts != null)
                for (int in : gotoInts) {
                    System.out.print(in + "\t");
                }
            System.out.println();
        });
    }

    public String getTableString() {
        String result = "";
        for (Integer i : changeTable.keySet()) {
            result += "{";
            String[] strs = changeTable.get(i);
            for (int j = 0; j < strs.length - 1; j++) {
                if (strs[j] == null) {
                    result += "null,";
                } else {
                    result += "\"" + strs[j] + "\",";
                }
            }
            result += strs[strs.length - 1] == null ? strs[strs.length - 1] + "}," : "\"" + strs[strs.length - 1] + "\"},";
            ;
        }
        result = result.substring(0, result.length() - 1);
        return result;
    }

    public String getGOTOString() {
        String result = "";
        for (int i = 0; i < changeTable.size(); i++) {
            if (gotoTable.get(i) != null) {
                if (gotoTable.get(i).length == 1) {
                    result += gotoTable.get(i)[0] + ",";
                } else {
                    result += "{";
                    for (int j = 0; j < gotoTable.get(i).length; j++) {
                        result += gotoTable.get(i)[j] + ",";
                    }
                    result = result.substring(0, result.length() - 1);
                    result += "},";
                }
            } else {
                result += "-1,";
            }
        }
        result = result.substring(0, result.length() - 1);
        return result;
    }

    public String getUtsString() {
        String result = "\nput(TokenType.IF, 0);\n" +
                "            put(TokenType.ELSE, 1);\n" +
                "            put(TokenType.SEMICOLON, 2);\n" +
                "            put(TokenType.A, 3);\n" +
                "            put(TokenType.B, 4);\n" +
                "            put(TokenType.DOLLAR, 5);";
        return result;
    }

    public String getGotoSequence() {
        String result = "";
        for (char s : uts) {
            result += s;
        }
        return result;
    }
}
