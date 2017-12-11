package com.module.wyc.mvvmmodelforandroid.viewmodel;

import android.content.Context;

import com.module.wyc.mvvmmodelforandroid.R;
import com.module.wyc.mvvmmodelforandroid.model.Repository;

/**
 * @author wyc
 * @describe describe
 * @email 1432248992@qq.com
 * @time 2017/5/11 0011 下午 4:31
 */

public class ItemRepositoryViewModel {
    private Repository repository;
    private Context context;

    public ItemRepositoryViewModel(Context context, Repository repository){
        this.context = context ;
        this.repository = repository ;
    }

    public String getName() {
        return repository.getName();
    }

    public String getDescription() {
        return repository.getDescription();
    }

    public String getWatchers() {
        return context.getResources().getString(R.string.text_watchers, repository.getWatchers());
    }

    public String getStars() {
        return context.getResources().getString(R.string.text_stars, repository.getStargazers_count());
    }

    public String getForks() {
        return context.getResources().getString(R.string.text_forks, repository.getForks());
    }

    public void setRepository(Repository repository) {
        this.repository = repository ;
    }
}
