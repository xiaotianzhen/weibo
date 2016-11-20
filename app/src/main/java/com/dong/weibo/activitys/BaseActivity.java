package com.dong.weibo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;


import com.dong.weibo.R;
import com.dong.weibo.view.ToolbarX;

/**
 * Created by 川东 on 2016/11/14.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private RelativeLayout rlContent;
    private Toolbar toolbar;
    private ToolbarX toolbarX;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baselayout);
        initView();
        View view = getLayoutInflater().inflate(getLayoutId(), rlContent, false);//IOC 控制反转 在父类中，调用子类的实现
        rlContent.addView(view);
        toolbarX = new ToolbarX(toolbar, this);
    }

    public abstract int getLayoutId();

    private void initView() {
        rlContent = (RelativeLayout) this.findViewById(R.id.rlContent);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);

        overridePendingTransition(R.anim.anim_in_right_left, R.anim.anim_out_right_left);

    }

    @Override
    public void finish() {
        overridePendingTransition(R.anim.anim_in_left_right, R.anim.anim_out_left_right);
        super.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        overridePendingTransition(R.anim.anim_in_right_left, R.anim.anim_out_right_left);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public ToolbarX getToolbar() {
        if (toolbarX == null) {
            toolbarX = new ToolbarX(toolbar, this);
        }
        return toolbarX;
    }
}
