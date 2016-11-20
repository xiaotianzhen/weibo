package com.dong.weibo.activitys;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;

import com.dong.weibo.R;
import com.dong.weibo.fragments.HomeFragment;
import com.dong.weibo.fragments.MessageFragment;
import com.dong.weibo.fragments.UserFragment;
import com.dong.weibo.view.ToolbarX;

/**
 * Created by 川东 on 2016/11/15.
 */

public class HomePageActivity extends BaseActivity {

    private FrameLayout fl_container;
    private FragmentTabHost tabHost;
    private RadioGroup rg_tab;
    private RadioButton tab_home, tab_user, tab_message;
    private Class[] fragment;
    private int menu_id=R.menu.menu_first;
    private ToolbarX toolbarX;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragment = new Class[]{HomeFragment.class, MessageFragment.class, UserFragment.class};
        initView();
        toolbarX=this.getToolbar();

    }

    private void initView() {
        fl_container = (FrameLayout) findViewById(R.id.fl_container);
        tabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        rg_tab = (RadioGroup) findViewById(R.id.rg_tab);
        tab_home = (RadioButton) findViewById(R.id.tab_home);
        tab_message = (RadioButton) findViewById(R.id.tab_message);
        tab_user = (RadioButton) findViewById(R.id.tab_user);

        tabHost.setup(getApplicationContext(), getSupportFragmentManager(), R.id.fl_container);
        for (int i = 0; i < fragment.length; i++) {
            Log.i("520it", "*************" + fragment.length);
            //indeicator 指示器，可以放tab的view
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(String.valueOf(i)).setIndicator(String.valueOf(i));
            tabHost.addTab(tabSpec, fragment[i], null);
        }
        tabHost.setCurrentTab(0);
        rg_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.tab_home:
                        tabHost.setCurrentTab(0);
                        menu_id=R.menu.menu_first;
                        break;
                    case R.id.tab_message:
                        tabHost.setCurrentTab(1);
                        menu_id=-1;
                        break;
                    case R.id.tab_user:
                        tabHost.setCurrentTab(2);
                        menu_id=-1;
                        break;
                }
                //重新加载菜单
                supportInvalidateOptionsMenu();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(menu_id==-1){
            menu.clear();
        }else {
            getMenuInflater().inflate(menu_id,menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.ac_homepagelayout;
    }
}
