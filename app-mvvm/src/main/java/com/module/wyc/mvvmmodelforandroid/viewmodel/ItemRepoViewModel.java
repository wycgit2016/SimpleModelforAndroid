package com.module.wyc.mvvmmodelforandroid.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.ObservableInt;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.module.wyc.mvvmmodelforandroid.R;
import com.module.wyc.mvvmmodelforandroid.model.Repo;
import com.module.wyc.mvvmmodelforandroid.utils.FavoReposHelper;
import com.module.wyc.mvvmmodelforandroid.view.UserRepoActivity;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author wyc
 * @describe describe
 * @email 1432248992@qq.com
 * @time 2017/5/11 0011 上午 11:17
 */

public class ItemRepoViewModel {

    private Repo repo;
    private static Context context;
    private String TAG = "ItemRepoViewModel";
    public ObservableInt favStarImage = new ObservableInt();

    public ItemRepoViewModel(Context context, Repo repo) {
        this.repo = repo;
        this.context = context;

        if (FavoReposHelper.getInstance().contains(repo)) {
            favStarImage.set(R.mipmap.ic_star_checked);
        } else {
            favStarImage.set(R.mipmap.ic_star_unchecked);
        }
    }

    @BindingAdapter({"avatarUrl"})
    public static void setAvatarImage(CircleImageView image, String url) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(context).load(url).into(image);
        }
    }

    @BindingAdapter({"favStar"})
    public static void setFavImageRes(ImageView image,int id){
        image.setImageResource(id);
    }

    public int getFavImageId(){
        return favStarImage.get();
    }

    public void onItemClick(View view) {
        openUserRepoActivity(repo.getOwner());
    }

    public void onImageClick1(View view) {
        openUserRepoActivity(repo.getContributors().get(0).getName());
    }

    public void onImageClick2(View view) {
        openUserRepoActivity(repo.getContributors().get(1).getName());
    }

    public void onImageClick3(View view) {
        openUserRepoActivity(repo.getContributors().get(2).getName());
    }

    public void onImageClick4(View view) {
        openUserRepoActivity(repo.getContributors().get(3).getName());
    }

    public void onImageClick5(View view) {
        openUserRepoActivity(repo.getContributors().get(4).getName());
    }

    public void onFavClick(View view) {
        if (FavoReposHelper.getInstance().contains(repo)){
            favStarImage.set(R.mipmap.ic_star_unchecked);
            ImageView image = (ImageView) view;
            image.setImageResource(R.mipmap.ic_star_unchecked);
            FavoReposHelper.getInstance().removeFavo(repo);
        }else {
            favStarImage.set(R.mipmap.ic_star_checked);
            ImageView image = (ImageView) view;
            image.setImageResource(R.mipmap.ic_star_checked);
            FavoReposHelper.getInstance().addFavo(repo);
        }
    }

    public String getTitle() {
        return repo.getOwner() + " / " + repo.getName();
    }

    public String getDes() {
        return repo.getDes();
    }

    public String getMeta() {
        return repo.getMeta();
    }

    public String getAvatar1() {
        if (repo.getContributors().size() > 0)
            return repo.getContributors().get(0).getAvatar();
        return null;
    }

    public String getAvatar2() {
        if (repo.getContributors().size() > 1)
            return repo.getContributors().get(1).getAvatar();
        return null;
    }

    public String getAvatar3() {
        if (repo.getContributors().size() > 2)
            return repo.getContributors().get(2).getAvatar();
        return null;
    }

    public String getAvatar4() {
        if (repo.getContributors().size() > 3)
            return repo.getContributors().get(3).getAvatar();
        return null;
    }

    public String getAvatar5() {
        if (repo.getContributors().size() > 4)
            return repo.getContributors().get(4).getAvatar();
        return null;
    }

    public void setRepo(Repo repo) {
        this.repo = repo;
    }

    public void openUserRepoActivity(String name){
        Intent intent = new Intent(context,UserRepoActivity.class);
        intent.putExtra("username",name);
        context.startActivity(intent);
    }
}
