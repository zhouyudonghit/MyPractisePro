package com.example.localuser.retrofittest.MVPTest.mvp1.programm;

import android.app.ProgressDialog;

import com.example.localuser.retrofittest.MVPTest.mvp1.base.BaseXActivity;
import com.example.localuser.retrofittest.MVPTest.mvp1.base.IBaseXPresenter;

public abstract class BaseActivity<P extends IBaseXPresenter> extends BaseXActivity<P> implements IBaseView {
    // 加载进度框
    private ProgressDialog mProgressDialog;

    @Override
    public void showLoading(){
    }

    @Override
    public void hideLoading(){
    }

    @Override
    public void showToast(String msg){
    }

    @Override
    protected void onDestroy() {
        hideLoading();
        super.onDestroy();
    }
}
