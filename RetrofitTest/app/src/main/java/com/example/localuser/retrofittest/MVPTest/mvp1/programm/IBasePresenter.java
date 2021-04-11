package com.example.localuser.retrofittest.MVPTest.mvp1.programm;

import com.example.localuser.retrofittest.MVPTest.mvp1.base.IBaseXPresenter;

public interface IBasePresenter<V extends IBaseView> extends IBaseXPresenter<V> {
    void cancel(Object tag);
    void cancelAll();
}
