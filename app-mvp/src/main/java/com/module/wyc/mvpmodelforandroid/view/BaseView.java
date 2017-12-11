package com.module.wyc.mvpmodelforandroid.view;

import com.module.wyc.mvpmodelforandroid.model.Repo;

import java.util.List;

public interface BaseView<T> {
    void showProgress();
    void showErrorMessage();
    void showRecycleView(T t);
}
