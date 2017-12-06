package lr1;

public class Action {
    private int number;
    private ActionType actionType;

    public Action(ActionType actionType, int number) {
        this.number = number;
        this.actionType = actionType;
    }


    public int getNumber() {
        return number;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public String toString() {
        return actionType==ActionType.ACCEPT?"Accept":actionType.toString().substring(0,1)+number;
    }
}
