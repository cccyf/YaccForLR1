package yacc;

import javafx.util.Pair;

public class PFTuple {
    int pNumber;// the number of production
    int pointPosition; // the position of point in the production
    char follow; // the follow char
    String production; // the production

    public PFTuple(int pNumber,int pointPosition,char follow,String production){
        this.pNumber = pNumber;
        this.pointPosition = pointPosition;
        this.follow = follow;
        this.production = production;
    }

    public PFTuple movePoint(char c){
        PFTuple tuple=null;
        if (pointPosition<=production.length()-1&&production.charAt(pointPosition)==c){
            tuple= new PFTuple(pNumber,pointPosition+1,follow,production);
        }
        return tuple;
    }

    public boolean isSameTo(PFTuple t){
        return pNumber == t.pNumber && pointPosition == t.pointPosition && follow==(t.follow);
    }

    public boolean end(){
        if (this.pointPosition == this.production.length()){
            return true;
        }
        return false;
    }

    public Pair<Character,Character> nextIfUnterminal(){
        if (this.pointPosition>=this.production.length()){
            return null;
        }
        char thisChar = this.production.charAt(this.pointPosition);
        if (thisChar<='Z'&&thisChar>='A'){
            if (this.pointPosition+1<this.production.length()) {
                return new Pair<>(thisChar, this.production.charAt(pointPosition + 1));
            }
            else {
                return new Pair<>(thisChar, this.follow);
            }
        }
        return null;
    }
}
