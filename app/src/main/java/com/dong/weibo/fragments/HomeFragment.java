package com.dong.weibo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dong.weibo.R;
import com.dong.weibo.activitys.CWConstant;
import com.dong.weibo.adapters.HomeFragmentAdapter;
import com.dong.weibo.entities.StatusEntity;
import com.dong.weibo.entities.UsersEntity;
import com.dong.weibo.networks.BaseNetwork;
import com.dong.weibo.networks.CWUrls;
import com.dong.weibo.networks.HttpResponse;
import com.dong.weibo.utis.SPUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.constant.WBPageConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.utils.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 川东 on 2016/11/15.
 */

public class HomeFragment extends BaseFragment implements HomeFragmentAdapter.OnItemOnClickListener {
    private AsyncWeiboRunner asyncWeiboRunner;
    private WeiboParameters parameters;
    private String url = "https://api.weibo.com/2/statuses/public_timeline.json";
    private SPUtils spUtils;
    private RecyclerView rv_homefragment;
    private List<StatusEntity> list = new ArrayList<StatusEntity>();
    private HomeFragmentAdapter adapter;

    private void initView(View view) {
        rv_homefragment = (RecyclerView) view.findViewById(R.id.rv_homefragment);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_homefragment.setLayoutManager(layoutManager);
        rv_homefragment.setItemAnimator(new DefaultItemAnimator());


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        asyncWeiboRunner = new AsyncWeiboRunner(getActivity());
        parameters = new WeiboParameters(CWConstant.APP_KEY);
        spUtils = SPUtils.getInstance(getActivity());
        Log.i("520itonCreate", "**********");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("520itononCreateView", "**********");
        View view = inflater.inflate(R.layout.home_fragment_layout, container, false);
       /* asyncWeiboRunner.requestAsync(url, parameters, "GET", new RequestListener() {
            @Override
            public void onComplete(String s) {
                Log.i("onComplete", "******************" + s);
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(s).getAsJsonObject();
                JsonArray array = jsonObject.get("statuses").getAsJsonArray();
                List<StatusEntity> list = new Gson().fromJson(array,new TypeToken<ArrayList<StatusEntity>>(){}.getType());
                Log.i("520it","**********"+list.size());


            }

            @Override
            public void onWeiboException(WeiboException e) {

            }
        });*/
        new BaseNetwork(getActivity(), CWUrls.public_timeline) {
            @Override
            public WeiboParameters onPrepare() {
                parameters.put(WBConstants.AUTH_ACCESS_TOKEN, spUtils.getToken().getToken());
                parameters.put(WBPageConstants.ParamKey.COUNT,100);
                parameters.put(WBPageConstants.ParamKey.PAGE,1);
                return parameters;
            }

            @Override
            public void onfinish(HttpResponse response, boolean success) {
                if (success) {
                    Log.i("520it", "**********" + response.response);
                    list = new Gson().fromJson(response.response, new TypeToken<ArrayList<StatusEntity>>() {
                    }.getType());
                    Log.i("520it", "**********" + list.size());
                    adapter.notifyDataSetChanged();

                } else {
                    Log.i("520it", "************" + response.message);
                }
            }
        }.get();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("520itonViewCreated", "**********");
        initView(view);
        Log.i("520itonViewCreated", "**********" + list.size());
        adapter = new HomeFragmentAdapter(list,getActivity());
        adapter.setOnItemOnClickListener(this);
        rv_homefragment.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), "您点击了" + position + "位置", Toast.LENGTH_SHORT).show();
    }
}
