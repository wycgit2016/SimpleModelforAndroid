package com.module.wyc.mvpmodelforandroid.presenter.impl;

import android.util.Log;
import android.view.View;

import com.module.wyc.mvpmodelforandroid.model.GithubService;
import com.module.wyc.mvpmodelforandroid.model.Repo;
import com.module.wyc.mvpmodelforandroid.presenter.MainViewPresenter;
import com.module.wyc.mvpmodelforandroid.view.BaseView;
import com.module.wyc.mvpmodelforandroid.view.MainBaseView;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainViewPresenterImpl implements MainViewPresenter {

    private MainBaseView mainBaseView;
    private List<Repo> repos;
    private Subscription subscription;

    @Override
    public void loadGitHubJava() {
        mainBaseView.showProgress();
        String url = "http://github.laowch.com/json/java_daily";
        subscription = GithubService.Factory.create().javaRepositories(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Repo>>() {
                    @Override
                    public void onCompleted() {
                        if (MainViewPresenterImpl.this.repos != null) {
                            mainBaseView.showRecycleView(repos);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("MainViewPresenterImpl", "onError: " + e.getMessage());
                        mainBaseView.showErrorMessage();
                    }

                    @Override
                    public void onNext(List<Repo> repos) {
                        MainViewPresenterImpl.this.repos = repos;
                    }
                });
    }

    @Override
    public void attachView(MainBaseView view) {
        this.mainBaseView = view;
    }

    @Override
    public void detachView() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
        this.mainBaseView = null;
    }
}
