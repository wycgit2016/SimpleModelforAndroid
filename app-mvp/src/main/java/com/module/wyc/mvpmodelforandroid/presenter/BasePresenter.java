package com.module.wyc.mvpmodelforandroid.presenter;

import com.module.wyc.mvpmodelforandroid.view.BaseView;


public interface BasePresenter<T> {
    void attachView(T view);
    void detachView();
}
