package com.elstele.bill.usersDataStorage;

public class UserStateData {
    private boolean busy;
    private float progress;

    public boolean isBusy() {
        return busy;
    }

    public float getProgress() {
        return progress;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }
}
