package com.example.localuser.retrofittest.MVPTest.mvp1.login.view;

import com.example.localuser.retrofittest.MVPTest.mvp1.login.contact.LoginContacts;
import com.example.localuser.retrofittest.MVPTest.mvp1.login.presenter.LoginPresenter;
import com.example.localuser.retrofittest.MVPTest.mvp1.programm.BaseActivity;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContacts.LoginUI {
    @Override
    public void loginSuccess() {

    }

    @Override
    public void loginFailure() {

    }
}
