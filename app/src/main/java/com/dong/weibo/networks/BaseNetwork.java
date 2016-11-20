package com.dong.weibo.networks;

import android.content.Context;
import android.content.pm.ProviderInfo;

import com.dong.weibo.activitys.CWConstant;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;

/**
 * Created by 川东 on 2016/11/17.
 */

public abstract class BaseNetwork {


    private AsyncWeiboRunner asyncWeiboRunner;
    private String url;

    public BaseNetwork(Context context, String url) {
        asyncWeiboRunner = new AsyncWeiboRunner(context);
        this.url = url;
    }

    private RequestListener requestListener = new RequestListener() {
        @Override
        public void onComplete(String s) {
               /*{
                "request" : "/statuses/home_timeline.json",
                    "error_code" : "20502",
                    "error" : "Need you follow uid."
            }*/

            HttpResponse response = new HttpResponse();
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(s);
            if (element.isJsonObject()) {
                JsonObject object = element.getAsJsonObject();
                if (object.has("error_code")) {
                    response.code = object.get("error_code").getAsInt();
                } else if (object.has("error")) {
                    response.response = object.get("error").getAsString();
                } else if (object.has("statuses")) {
                    response.response = object.get("statuses").toString();
                } else if (object.has("users")) {
                    response.response = object.get("users").toString();
                } else if (object.has("comments")) {
                    response.response = object.get("comments").toString();
                } else {
                    response.response = s;
                }

            }
            onfinish(response, true);
        }

        @Override
        public void onWeiboException(WeiboException e) {
            HttpResponse response = new HttpResponse();
            response.message = e.getMessage();
            onfinish(response, true);

        }
    };

    public void get() {
        asyncWeiboRunner.requestAsync(url, onPrepare(), "GET", requestListener);
    }

    public void post() {
        asyncWeiboRunner.requestAsync(url, onPrepare(), "POST", requestListener);
    }

    public void delete() {
        asyncWeiboRunner.requestAsync(url, onPrepare(), "DELETE", requestListener);
    }

    public abstract WeiboParameters onPrepare();

    public abstract void onfinish(HttpResponse response, boolean success);
}
