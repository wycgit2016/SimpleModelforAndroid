package com.module.wyc.mvvmmodelforandroid.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.inputmethod.InputMethodManager;

import com.module.wyc.mvvmmodelforandroid.R;
import com.module.wyc.mvvmmodelforandroid.adapter.RepositoryAdapter;
import com.module.wyc.mvvmmodelforandroid.databinding.ActivityUserRepoBinding;
import com.module.wyc.mvvmmodelforandroid.model.Repository;
import com.module.wyc.mvvmmodelforandroid.viewmodel.UserRepoViewModel;

import java.util.List;

public class UserRepoActivity extends AppCompatActivity implements UserRepoViewModel.DataListener {

    private ActivityUserRepoBinding binding ;
    private UserRepoViewModel viewModel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_repo) ;
        viewModel = new UserRepoViewModel(this,this) ;
        binding.setViewModel(viewModel);
        setSupportActionBar(binding.toolbar);

        String username = getIntent().getStringExtra("username");
        viewModel.loadGithubRepos(username) ;
        binding.editTextUsername.setText(username);
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.editTextUsername.getWindowToken(),0) ;
    }

    @Override
    public void repoDataChange(List<Repository> repositories) {
        RepositoryAdapter adapter = new RepositoryAdapter(this,repositories) ;
        binding.reposRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.reposRecyclerView.setAdapter(adapter);
        hideSoftKeyboard();
    }
}
