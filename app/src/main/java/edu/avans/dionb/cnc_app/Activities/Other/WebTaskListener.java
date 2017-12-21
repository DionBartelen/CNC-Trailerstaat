package edu.avans.dionb.cnc_app.Activities.Other;

/**
 * Created by dionb on 5-11-2017.
 */

public interface WebTaskListener {

    public void TaskDone(String message);
    public void TaskError(String message);
}
