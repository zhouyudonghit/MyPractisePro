package com.example.localuser.retrofittest.AsyncTaskTest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class AsyncTaskTestActivity extends AppCompatActivity {
    public static String TAG_PREFIX = "AsyncTaskTest--";
    private String TAG = TAG_PREFIX+getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AsyncTask<Void,Integer,String> task = new MyAsyncTask();
        task.execute();
        try {
            Thread.sleep(2000);
            task.execute();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class MyAsyncTask extends AsyncTask<Void,Integer,String>
    {
        public MyAsyncTask()
        {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //publishProgress(100);
            return "hello";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(TAG,"result = "+result);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
