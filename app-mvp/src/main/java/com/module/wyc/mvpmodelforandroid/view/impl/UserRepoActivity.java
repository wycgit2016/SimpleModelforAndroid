package com.module.wyc.mvpmodelforandroid.view.impl;

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


import com.module.wyc.mvpmodelforandroid.R;
import com.module.wyc.mvpmodelforandroid.adapter.RepositoryAdapter;
import com.module.wyc.mvpmodelforandroid.model.Repository;
import com.module.wyc.mvpmodelforandroid.presenter.impl.UserRepoPresenterImpl;
import com.module.wyc.mvpmodelforandroid.view.UserRepoBaseView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserRepoActivity extends AppCompatActivity implements UserRepoBaseView, View.OnClickListener {

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

    private UserRepoPresenterImpl userRepoPresenter;
    private RepositoryAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_repo);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        buttonSearch.setOnClickListener(this);

        String username = getIntent().getStringExtra("username");
        editTextUsername.setText(username);
        addTextListener();

        userRepoPresenter = new UserRepoPresenterImpl();
        userRepoPresenter.attachView(this);
        userRepoPresenter.loadGitHubUserRepo(username);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userRepoPresenter.detachView();
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

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTextUsername.getWindowToken(), 0);
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
        textInfo.setVisibility(View.GONE);
        reposRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMessage() {
        progress.setVisibility(View.GONE);
        textInfo.setVisibility(View.VISIBLE);
        reposRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showRecycleView(List<Repository> repositories) {
        progress.setVisibility(View.GONE);
        textInfo.setVisibility(View.GONE);
        reposRecyclerView.setVisibility(View.VISIBLE);

        adapter = new RepositoryAdapter(this, repositories);
        reposRecyclerView.setAdapter(adapter);
        reposRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        hideSoftKeyboard();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_search:
                userRepoPresenter.loadGitHubUserRepo(editTextUsername.getText().toString());
                break;
        }
    }
}
