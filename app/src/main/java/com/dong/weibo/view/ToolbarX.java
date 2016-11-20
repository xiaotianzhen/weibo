package com.dong.weibo.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.dong.weibo.R;


/**
 * Created by 川东 on 2016/11/15.
 */

public class ToolbarX  {
    private Toolbar mToolbar;
    private AppCompatActivity mActivity;
    private ActionBar mActionbar;
    private RelativeLayout rlMenu;

    public ToolbarX(Toolbar mToolbar, final AppCompatActivity mActivity) {
        this.mToolbar = mToolbar;
        rlMenu= (RelativeLayout) mToolbar.findViewById(R.id.rlMenu);
        this.mActivity = mActivity;
        mActivity.setSupportActionBar(mToolbar);
        mActionbar=mActivity.getSupportActionBar();
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
    }
    public ToolbarX setTitle(String title){
        mActionbar.setTitle(title);
        return this;
    }
    public ToolbarX setSubtitle(String subtitle){
        mActionbar.setSubtitle(subtitle);
        return this;
    }
    public ToolbarX setTitle(int resId){
        mActionbar.setTitle(resId);
        return this;
    }
    public ToolbarX setSubtitle(int resId){
        mActionbar.setSubtitle(resId);
        return this;
    }
    public ToolbarX setDisplayHomeAsUpEnabled(boolean showHomeAsUp ){
        mActionbar.setDisplayHomeAsUpEnabled(showHomeAsUp);
        return this;
    }
    public ToolbarX setLogo(int resId){
        mActionbar.setLogo(resId);
        return this;
    }
    public ToolbarX setNavigationIcon(int resId){
        mToolbar.setNavigationIcon(resId);
        return this;
    }
    public ToolbarX setNavigationOnClickListener(View.OnClickListener listener){
        mToolbar.setNavigationOnClickListener(listener);
        return this;
    }
     public ToolbarX setCustomView(View view){
         rlMenu.removeAllViews();
         rlMenu.addView(view);
         return this;
     }
    public ToolbarX  hide(){
        mActionbar.hide();
        return this;
    }

}
