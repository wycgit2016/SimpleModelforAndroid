package com.module.wyc.mvcmodelforandroid.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.module.wyc.mvcmodelforandroid.R;
import com.module.wyc.mvcmodelforandroid.adapter.RepoListAdapter;
import com.module.wyc.mvcmodelforandroid.model.GithubService;
import com.module.wyc.mvcmodelforandroid.model.Repo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

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
    private RepoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        textDescription.setText("GitHub Java");
        loadGitHubRepo();
    }

    private void loadGitHubRepo() {
        String url = "http://github.laowch.com/json/java_daily";
        GithubService.Factory.create().javaRepositories(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Repo>>() {
                    @Override
                    public void onCompleted() {
                        textInfo.setVisibility(View.GONE);
                        Log.d("onCompleted", "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        progress.setVisibility(View.GONE);
                        textInfo.setVisibility(View.VISIBLE);
                        recycler.setVisibility(View.GONE);
                        Log.d("onError", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(List<Repo> repos) {
                        Log.d("onNext", "onNext: ");
                        progress.setVisibility(View.GONE);
                        if (repos != null)
                            setupRecyclerView(recycler, repos);
                    }
                });
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<Repo> repos) {
        adapter = new RepoListAdapter(this, repos);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));
    }

}
