package com.dong.weibo.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dong.weibo.R;
import com.dong.weibo.utis.SPUtils;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * Created by 川东 on 2016/11/16.
 */

public class LandingActivity extends BaseActivity {

    private SsoHandler ssoHandler;
    private AuthInfo mAuthIofo;
    private SPUtils spUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().hide();
        spUtils = SPUtils.getInstance(getApplicationContext());
        mAuthIofo = new AuthInfo(getApplicationContext(), CWConstant.APP_KEY,
                CWConstant.REDIRECT_URL, CWConstant.SCOPE);
        ssoHandler = new SsoHandler(this, mAuthIofo);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkIsFirst();

            }
        }, 500);
    }

    private void checkIsFirst() {
        SharedPreferences sp = getSharedPreferences("name", MODE_PRIVATE);
        boolean isFirst = sp.getBoolean("isFirst", true);
        if (isFirst) {
            SharedPreferences.Editor ed = sp.edit();
            ed.putBoolean("isFirst",false).commit();
            Intent intent=new Intent(LandingActivity.this,GuideActivity.class);
            startActivity(intent);
            finish();
        }else {
            checkLogin();
        }

    }


    private void checkLogin() {

        if (spUtils.isLogin()) {
            startActivity(new Intent(LandingActivity.this, HomePageActivity.class));
            finish();
        } else {
            ssoHandler.authorize(new WeiboAuthListener() {
                @Override
                public void onComplete(Bundle bundle) {

                    Oauth2AccessToken accessToken = Oauth2AccessToken.parseAccessToken(bundle);
                    spUtils.saveToken(accessToken);
                    startActivity(new Intent(LandingActivity.this, HomePageActivity.class));
                    finish();

                }

                @Override
                public void onWeiboException(WeiboException e) {
                    Log.i("520it", "*******" + e);
                }

                @Override
                public void onCancel() {
                    Log.i("520it", "*******取消");
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.ac_landinglayout;
    }

}