package com.module.wyc.mvvmmodelforandroid.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.module.wyc.mvvmmodelforandroid.R;
import com.module.wyc.mvvmmodelforandroid.databinding.ItemRepoBinding;
import com.module.wyc.mvvmmodelforandroid.model.Repository;
import com.module.wyc.mvvmmodelforandroid.viewmodel.ItemRepositoryViewModel;

import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {

    private Context mContext;
    private List<Repository> repositoryList;

    public RepositoryAdapter(Context context, List<Repository> repositoryList) {
        this.mContext = context;
        this.repositoryList = repositoryList;
    }


    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRepoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_repo, parent, false);
        return new RepositoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        Repository repository = repositoryList.get(position);
        holder.bindingData(repository);

    }

    @Override
    public int getItemCount() {
        return repositoryList.size();
    }

    public class RepositoryViewHolder extends RecyclerView.ViewHolder {
        private ItemRepoBinding binding;

        public RepositoryViewHolder(ItemRepoBinding binding) {
            super(binding.cardView);
            this.binding = binding;
        }

        public void bindingData(Repository repository) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ItemRepositoryViewModel(mContext, repository));
            } else {
                binding.getViewModel().setRepository(repository);
            }
        }
    }
}
