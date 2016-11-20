package com.dong.weibo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.dong.weibo.R;
import com.dong.weibo.view.ToolbarX;

/**
 * Created by 川东 on 2016/11/14.
 */

public class FirstActivity extends BaseActivity {

    private ToolbarX toolbarX;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view= LayoutInflater.from(this).inflate(R.layout.custom_menu,null);
        toolbarX=this.getToolbar();
        toolbarX.setTitle("nihao").setDisplayHomeAsUpEnabled(true).setSubtitle("hi").setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstActivity.this.finish();
            }
        });

        toolbarX.setCustomView(view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.ac_firstlayout;
    }

    public void button(View view){
        Intent intent=new Intent();
        intent.setClass(FirstActivity.this,SecondActivity.class);
        startActivity(intent);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_first,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       switch (item.getItemId()){
           case R.id.search:
               Toast.makeText(this,"你点击了搜索",Toast.LENGTH_SHORT).show();
               break;
           case R.id.share:
               Toast.makeText(this,"你点击了分享",Toast.LENGTH_SHORT).show();
               break;
       }
        return super.onOptionsItemSelected(item);
    }
}
