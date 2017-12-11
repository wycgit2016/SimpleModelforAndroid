package com.module.wyc.mvpmodelforandroid.presenter;

import com.module.wyc.mvpmodelforandroid.view.UserRepoBaseView;


public interface UserRepoPresenter extends BasePresenter<UserRepoBaseView>{

    void loadGitHubUserRepo(String userName);
}
