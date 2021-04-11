package com.example.localuser.retrofittest.MVPTest.mvp1.login.contact;

import com.example.localuser.retrofittest.MVPTest.mvp1.programm.IBasePresenter;
import com.example.localuser.retrofittest.MVPTest.mvp1.programm.IBaseView;

public final class LoginContacts {
    public interface LoginUI extends IBaseView{
        /**
         * 登录成功
         */
        void loginSuccess();

        /**
         * 登录失败
         */
        void loginFailure();
    }

    public interface LogintPresenter extends IBasePresenter<LoginUI>{
        void login(String username, String password);
    }
}
