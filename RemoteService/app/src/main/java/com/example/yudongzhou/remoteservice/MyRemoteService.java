package com.example.yudongzhou.remoteservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.yudongzhou.remoteservice.bean.Book;

import java.util.ArrayList;
import java.util.List;

public class MyRemoteService extends Service {
    private String TAG = getClass().getSimpleName();
    private List<Book> mBoookList = new ArrayList<>();

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public String getServiceDescriptor()
    {
        return "hello,client,this is service";
    }

    @Override
    public void onCreate() {
        Log.d(TAG,"onCreate()");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    public class MyBinder extends IBookManager.Stub
    {

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBoookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBoookList.add(book);
            Log.d(TAG,mBoookList.toString());
        }
    }
}
