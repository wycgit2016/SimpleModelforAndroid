package com.module.wyc.mvvmmodelforandroid.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.module.wyc.mvvmmodelforandroid.R;
import com.module.wyc.mvvmmodelforandroid.adapter.RepoListAdapter;
import com.module.wyc.mvvmmodelforandroid.databinding.ActivityMainBinding;
import com.module.wyc.mvvmmodelforandroid.model.Repo;
import com.module.wyc.mvvmmodelforandroid.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainViewModel.DataListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainViewModel viewModel = new MainViewModel(this);
        binding.setViewModel(viewModel);

        setSupportActionBar(binding.toolbar);
        binding.textDescription.setText(getResources().getString(R.string.github_java));
    }

    @Override
    public void repoDataChange(List<Repo> repos) {
        RepoListAdapter adapter = new RepoListAdapter(this, repos);
        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
    }
}
