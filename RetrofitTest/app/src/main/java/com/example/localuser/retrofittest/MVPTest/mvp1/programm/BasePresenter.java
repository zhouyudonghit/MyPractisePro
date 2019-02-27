package com.example.localuser.retrofittest.MVPTest.mvp1.programm;

import android.support.annotation.NonNull;

import com.example.localuser.retrofittest.MVPTest.mvp1.base.BaseXPresenter;

public abstract class BasePresenter<V extends IBaseView> extends BaseXPresenter<V> implements IBasePresenter<V> {
    public BasePresenter(@NonNull V view) {

    }

    @Override
    public void cancel(@NonNull Object tag) {

    }

    @Override
    public void cancelAll() {

    }
}
