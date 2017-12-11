package com.module.wyc.mvvmmodelforandroid.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.module.wyc.mvvmmodelforandroid.model.GithubService;
import com.module.wyc.mvvmmodelforandroid.model.Repository;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author wyc
 * @describe describe
 * @email 1432248992@qq.com
 * @time 2017/5/11 0011 下午 3:49
 */

public class UserRepoViewModel {
    private final DataListener listener;
    private Context context;
    public ObservableInt progressVisibility = new ObservableInt(View.GONE);
    public ObservableInt errorMessageVisibility = new ObservableInt(View.GONE);
    public ObservableInt recycleViewVisibility = new ObservableInt(View.GONE);
    public ObservableInt searchButtonVisibility = new ObservableInt(View.GONE);
    private List<Repository> repositories;
    private String editTextUsernameValue;

    public UserRepoViewModel(Context context, DataListener listener) {
        this.listener = listener;
        this.context = context;
    }

    public void onClickSearch(View view) {
        loadGithubRepos(editTextUsernameValue);
    }

    public TextWatcher usernameEditTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editTextUsernameValue = s.toString() ;
                searchButtonVisibility.set(s.length() > 0 ? View.VISIBLE : View.GONE);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        } ;
    }

    public void loadGithubRepos(String userName) {
        if (TextUtils.isEmpty(userName))
            return;
        progressVisibility.set(View.VISIBLE);
        searchButtonVisibility.set(View.VISIBLE);
        errorMessageVisibility.set(View.GONE);
        recycleViewVisibility.set(View.GONE);
        GithubService.Factory.create().publicRepositories(userName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Repository>>() {
                    @Override
                    public void onCompleted() {
                        if (listener != null)
                            listener.repoDataChange(UserRepoViewModel.this.repositories);
                        errorMessageVisibility.set(View.GONE);
                        progressVisibility.set(View.GONE);
                        if (UserRepoViewModel.this.repositories != null && UserRepoViewModel.this.repositories.size() > 0) {
                            recycleViewVisibility.set(View.VISIBLE);
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        progressVisibility.set(View.GONE);
                        recycleViewVisibility.set(View.GONE);
                        errorMessageVisibility.set(View.VISIBLE);
                    }
                    @Override
                    public void onNext(List<Repository> repositories) {
                        UserRepoViewModel.this.repositories = repositories;
                    }
                });
    }


    public interface DataListener {
        void repoDataChange(List<Repository> repositories);
    }
}
