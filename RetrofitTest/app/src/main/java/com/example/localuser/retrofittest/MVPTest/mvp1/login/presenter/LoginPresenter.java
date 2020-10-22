package com.example.localuser.retrofittest.MVPTest.mvp1.login.presenter;

import android.support.annotation.NonNull;

import com.example.localuser.retrofittest.MVPTest.mvp1.login.contact.LoginContacts;
import com.example.localuser.retrofittest.MVPTest.mvp1.programm.BasePresenter;

public class LoginPresenter extends BasePresenter<LoginContacts.LoginUI> implements LoginContacts.LogintPresenter {

    public LoginPresenter(@NonNull LoginContacts.LoginUI view) {
        super(view);
    }

    @Override
    public void login(String username, String password) {

    }

}
