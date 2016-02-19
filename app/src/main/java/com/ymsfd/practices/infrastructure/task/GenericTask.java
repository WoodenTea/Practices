package com.ymsfd.practices.infrastructure.task;

import java.util.Observable;
import java.util.Observer;

import android.os.AsyncTask;

public abstract class GenericTask extends
        AsyncTask<TaskParams, Object, TaskResult> implements Observer {

    private TaskListener mListener = null;
    private boolean isCancelable = true;

    public TaskListener getListener() {
        return mListener;
    }

    public void setListener(TaskListener taskListener) {
        mListener = taskListener;
    }

    public void doPublishProgress(Object... values) {
        super.publishProgress(values);
    }

    @Override
    protected TaskResult doInBackground(TaskParams... params) {
        return _doInBackground(params);
    }

    abstract protected TaskResult _doInBackground(TaskParams... params);

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (mListener != null) {
            mListener.onPreExecute(this);
        }

    }

    @Override
    protected void onPostExecute(TaskResult result) {
        super.onPostExecute(result);

        if (mListener != null) {
            mListener.onPostExecute(this, result);
        }
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);

        if (mListener != null) {
            if (values != null && values.length > 0) {
                mListener.onProgressUpdate(this, values[0]);
            }
        }

    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

        if (mListener != null) {
            mListener.onCancelled(this);
        }
    }

    public void update(Observable o, Object arg) {
        if (TaskManager.CANCEL_ALL.equals(arg) && isCancelable) {
            if (getStatus() == Status.RUNNING) {
                cancel(true);
            }
        }
    }

    public void setCancelable(boolean flag) {
        isCancelable = flag;
    }

}
