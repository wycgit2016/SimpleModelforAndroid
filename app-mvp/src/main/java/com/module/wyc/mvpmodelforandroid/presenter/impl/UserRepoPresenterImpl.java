package com.module.wyc.mvpmodelforandroid.presenter.impl;

import android.view.View;

import com.module.wyc.mvpmodelforandroid.model.GithubService;
import com.module.wyc.mvpmodelforandroid.model.Repository;
import com.module.wyc.mvpmodelforandroid.presenter.UserRepoPresenter;
import com.module.wyc.mvpmodelforandroid.view.UserRepoBaseView;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class UserRepoPresenterImpl implements UserRepoPresenter {
    private UserRepoBaseView userRepoBaseView;
    private Subscription subscription;
    private List<Repository> repositories;

    @Override
    public void attachView(UserRepoBaseView view) {
        this.userRepoBaseView = view;
    }

    @Override
    public void detachView() {
        if (subscription != null)
            subscription.unsubscribe();
        this.userRepoBaseView = null;
    }

    @Override
    public void loadGitHubUserRepo(String userName) {
        userRepoBaseView.showProgress();
        subscription = GithubService.Factory.create().publicRepositories(userName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Repository>>() {

                    @Override
                    public void onCompleted() {
                        if (UserRepoPresenterImpl.this.repositories != null){
                            userRepoBaseView.showRecycleView(UserRepoPresenterImpl.this.repositories);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        userRepoBaseView.showErrorMessage();
                    }

                    @Override
                    public void onNext(List<Repository> repositories) {
                        UserRepoPresenterImpl.this.repositories = repositories;
                    }
                });

    }
}
