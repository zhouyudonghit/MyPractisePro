package com.example.localuser.retrofittest.MVPTest.mvp1.base;

import android.app.Activity;

public abstract class BaseXActivity<P extends IBaseXPresenter> extends Activity implements IBaseXView{
    protected P mPresenter;

    @Override
    public Activity getSelfActivity() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 在生命周期结束时，将 presenter 与 view 之间的联系断开，防止出现内存泄露
         */
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
