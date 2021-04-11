package com.example.localuser.retrofittest.MVPTest.mvp1.base;

import java.lang.ref.SoftReference;

public abstract class BaseXPresenter<V extends IBaseXView> implements IBaseXPresenter<V>{
    private SoftReference<V> mViewRef;

    @Override
    public void attachView(V v) {
        mViewRef = new SoftReference<>(v);
    }

    @Override
    public V getView() {
        if(mViewRef != null)
        {
            return mViewRef.get();
        }
        return null;
    }

    @Override
    public boolean isViewAttach() {
        return mViewRef != null && mViewRef.get() != null;
    }

    @Override
    public void detachView() {
        if(mViewRef != null)
        {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
