package com.module.wyc.mvvmmodelforandroid.viewmodel;

import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import com.module.wyc.mvvmmodelforandroid.model.GithubService;
import com.module.wyc.mvvmmodelforandroid.model.Repo;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author wyc
 * @describe describe
 * @email 1432248992@qq.com
 * @time 2017/5/9 0009 下午 10:37
 */

public class MainViewModel {
    public ObservableInt progressVisibility = new ObservableInt(View.GONE);
    public ObservableInt errorMessageVisibility = new ObservableInt(View.GONE);
    public ObservableInt recycleViewVisibility = new ObservableInt(View.GONE);

    private List<Repo> repos;
    private DataListener listener;

    public MainViewModel(DataListener listener) {
        this.listener = listener;
        loadGitHubJava();
    }

    private void loadGitHubJava() {
        progressVisibility.set(View.VISIBLE);
        String url = "http://github.laowch.com/json/java_daily";
        GithubService.Factory.create().javaRepositories(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Repo>>() {
                    @Override
                    public void onCompleted() {
                        progressVisibility.set(View.GONE);
                        errorMessageVisibility.set(View.GONE);
                        recycleViewVisibility.set(View.VISIBLE);

                        if (repos != null && listener != null) {
                            listener.repoDataChange(repos);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        progressVisibility.set(View.GONE);
                        errorMessageVisibility.set(View.VISIBLE);
                        recycleViewVisibility.set(View.GONE);
                    }

                    @Override
                    public void onNext(List<Repo> repos) {
                        MainViewModel.this.repos = repos;
                    }
                });
    }

    public interface DataListener {
        void repoDataChange(List<Repo> repos);
    }
}
