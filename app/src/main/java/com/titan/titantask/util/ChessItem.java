package com.titan.titantask.util;

public class ChessItem {
    public int val;

    public ChessItem(int val, boolean flagRunning) {
        this.val = val;
        this.flagRunning = flagRunning;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public boolean isFlagRunning() {
        return flagRunning;
    }

    public void setFlagRunning(boolean flagRunning) {
        this.flagRunning = flagRunning;
    }

    public boolean flagRunning;

    public ChessItem(){

    }
}
