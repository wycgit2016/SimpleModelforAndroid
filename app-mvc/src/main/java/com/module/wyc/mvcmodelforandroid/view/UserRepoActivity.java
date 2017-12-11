package com.module.wyc.mvcmodelforandroid.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.module.wyc.mvcmodelforandroid.R;
import com.module.wyc.mvcmodelforandroid.adapter.RepositoryAdapter;
import com.module.wyc.mvcmodelforandroid.model.GithubService;
import com.module.wyc.mvcmodelforandroid.model.Repository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserRepoActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.button_search)
    ImageButton buttonSearch;
    @BindView(R.id.edit_text_username)
    EditText editTextUsername;
    @BindView(R.id.layout_search)
    RelativeLayout layoutSearch;
    @BindView(R.id.layout_header)
    LinearLayout layoutHeader;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.text_info)
    TextView textInfo;
    @BindView(R.id.repos_recycler_view)
    RecyclerView reposRecyclerView;
    private RepositoryAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_repo);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        addTextListener();
        buttonSearch.setOnClickListener(this);

        String username = getIntent().getStringExtra("username");
        editTextUsername.setText(username);
        loadGithubRepos(username);


    }


    private void addTextListener() {
        editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buttonSearch.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    private void loadGithubRepos(String username) {
        GithubService.Factory.create().publicRepositories(username)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Repository>>() {

                    @Override
                    public void onCompleted() {
                        textInfo.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        progress.setVisibility(View.GONE);
                        textInfo.setVisibility(View.VISIBLE);
                        reposRecyclerView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(List<Repository> repositories) {
                        progress.setVisibility(View.GONE);
                        if (repositories != null) {
                            reposRecyclerView.setVisibility(View.VISIBLE);
                            setupRecyclerView(reposRecyclerView, repositories);
                        }
                    }
                });

    }

    private void setupRecyclerView(RecyclerView recyclerView, List<Repository> repos) {
        adapter = new RepositoryAdapter(this, repos);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_search:
                hideSoftKeyboard();
                textInfo.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                reposRecyclerView.setVisibility(View.GONE);
                loadGithubRepos(editTextUsername.getText().toString());
                break;
        }
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTextUsername.getWindowToken(), 0);
    }
}
