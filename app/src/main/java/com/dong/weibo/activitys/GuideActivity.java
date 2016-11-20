package com.dong.weibo.activitys;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.dong.weibo.R;
import com.dong.weibo.utis.SPUtils;
import com.dong.weibo.view.ToolbarX;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseActivity {
    private ViewPager mViewPager;
    private Button bt_guide;
    private int[] imagesId = new int[]{R.drawable.guide1, R.drawable.guide2, R.drawable.guide3};
    private List<ImageView> list = new ArrayList<ImageView>();
    private ToolbarX mToolbarX;
    private SsoHandler ssoHandler;
    private AuthInfo mAuthIofo;
    private SPUtils spUtils;


    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        bt_guide = (Button) findViewById(R.id.bt_guide);
    }

    private void initData() {
        for (int i = 0; i < imagesId.length; i++) {
            ImageView image = new ImageView(getApplicationContext());
            image.setBackgroundResource(imagesId[i]);
            image.setScaleType(ImageView.ScaleType.CENTER);
            list.add(image);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        mToolbarX = getToolbar();
        mToolbarX.hide();
        spUtils = SPUtils.getInstance(getApplicationContext());
        mAuthIofo = new AuthInfo(getApplicationContext(), CWConstant.APP_KEY,
                CWConstant.REDIRECT_URL, CWConstant.SCOPE);
        ssoHandler = new SsoHandler(this, mAuthIofo);

        bt_guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager) container).addView(list.get(position));
                return list.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(list.get(position));
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == imagesId.length - 1) {
                    bt_guide.setVisibility(View.VISIBLE);
                    //页面跳转的动画
                    //overridePendingTransition(R.anim.anim_bt_guide_in,R.anim.anim_bt_guide_out);
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_bt_guide_in);
                    bt_guide.startAnimation(animation);
                } else {
                    bt_guide.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void checkLogin() {

        if (spUtils.isLogin()) {
            startActivity(new Intent(GuideActivity.this, HomePageActivity.class));
            finish();
        } else {
            ssoHandler.authorize(new WeiboAuthListener() {
                @Override
                public void onComplete(Bundle bundle) {

                    Oauth2AccessToken accessToken = Oauth2AccessToken.parseAccessToken(bundle);
                    spUtils.saveToken(accessToken);
                    startActivity(new Intent(GuideActivity.this, HomePageActivity.class));
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
        return R.layout.activity_guide;
    }
}
