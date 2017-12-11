package com.module.wyc.mvpmodelforandroid.view.impl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.module.wyc.mvpmodelforandroid.R;
import com.module.wyc.mvpmodelforandroid.adapter.RepoListAdapter;
import com.module.wyc.mvpmodelforandroid.model.Repo;
import com.module.wyc.mvpmodelforandroid.presenter.impl.MainViewPresenterImpl;
import com.module.wyc.mvpmodelforandroid.view.MainBaseView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainBaseView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_description)
    TextView textDescription;
    @BindView(R.id.layout_header)
    LinearLayout layoutHeader;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.text_info)
    TextView textInfo;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    private MainViewPresenterImpl mainViewPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        textDescription.setText("GitHub Java");

        mainViewPresenter = new MainViewPresenterImpl();
        mainViewPresenter.attachView(this);
        mainViewPresenter.loadGitHubJava();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainViewPresenter.detachView();
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
        textInfo.setVisibility(View.GONE);
        recycler.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMessage() {
        progress.setVisibility(View.GONE);
        textInfo.setVisibility(View.VISIBLE);
        recycler.setVisibility(View.GONE);
    }

    @Override
    public void showRecycleView(List<Repo> repos) {
        progress.setVisibility(View.GONE);
        textInfo.setVisibility(View.GONE);
        recycler.setVisibility(View.VISIBLE);

        RepoListAdapter adapter = new RepoListAdapter(this,repos);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

}
