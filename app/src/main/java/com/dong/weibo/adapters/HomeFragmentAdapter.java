package com.dong.weibo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.LoginFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dong.weibo.R;
import com.dong.weibo.entities.PicUrlsEntity;
import com.dong.weibo.entities.StatusEntity;

import java.util.List;


/**
 * Created by 川东 on 2016/11/18.
 */

public class HomeFragmentAdapter extends RecyclerView.Adapter {

    private List<StatusEntity> list;
    private OnItemOnClickListener mOnItemOnClickListener;
    private Context context;

    public HomeFragmentAdapter(List<StatusEntity> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homefragment_rv_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewHolder viewHoder = (ViewHolder) holder;
        viewHoder.position = position;
        StatusEntity entity = list.get(position);
        Glide.with(context).load(entity.user.profile_image_url).into(viewHoder.im_weibouser);
        viewHoder.created_at.setText(entity.created_at);
        viewHoder.screen_name.setText(entity.user.screen_name);
        viewHoder.source.setText(Html.fromHtml(entity.source));

       List<PicUrlsEntity> pics=entity.pic_urls;
        if (pics!=null&&pics.size()>0){
            Log.i(" 520List<PicUrlsEntity>","**************"+pics.size());
            PicUrlsEntity pic=pics.get(0);
            viewHoder.im_content.setVisibility(View.VISIBLE);
            Glide.with(context).load(pic.thumbnail_pic).into(viewHoder.im_content);
        }else {
            viewHoder.im_content.setVisibility(View.GONE);
        }
        StatusEntity reStatus=entity.retweeted_status;
        if (reStatus!= null) {
            viewHoder.llayout.setVisibility(View.VISIBLE);
            viewHoder.retweeted_status.setText(reStatus.text);

            List<PicUrlsEntity> picss=entity.pic_urls;
            if (picss!=null&&picss.size()>0){
                PicUrlsEntity pic=picss.get(0);
                viewHoder.im_recontent.setVisibility(View.VISIBLE);
                Glide.with(context).load(pic.thumbnail_pic).into(viewHoder.im_recontent);
            }else {
                viewHoder.im_recontent.setVisibility(View.GONE);
            }
        } else {
            viewHoder.llayout.setVisibility(View.GONE);
        }

        viewHoder.text.setText(Html.fromHtml(entity.text));
        Log.i("520it","***********************");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView created_at;
        private TextView screen_name;
        private TextView text;
        private TextView source;
        private TextView retweeted_status;
        private int position;
        private ImageView im_weibouser;
        private ImageView im_content;
        private ImageView im_recontent;
        private LinearLayout llayout;


        public ViewHolder(final View itemView) {
            super(itemView);
            created_at = (TextView) itemView.findViewById(R.id.weibotime);
            screen_name = (TextView) itemView.findViewById(R.id.screen_name);
            text = (TextView) itemView.findViewById(R.id.weibocontent);
            source = (TextView) itemView.findViewById(R.id.comefrom);
            retweeted_status = (TextView) itemView.findViewById(R.id.retweeted_status);
            im_weibouser = (ImageView) itemView.findViewById(R.id.im_weibouser);
            im_content= (ImageView) itemView.findViewById(R.id.im_content);
            im_recontent= (ImageView) itemView.findViewById(R.id.im_reContent);
            llayout= (LinearLayout) itemView.findViewById(R.id.llayout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemOnClickListener != null) {
                        mOnItemOnClickListener.onItemClick(itemView, position);
                    }
                }
            });
        }
    }

    public interface OnItemOnClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemOnClickListener(OnItemOnClickListener listener) {
        this.mOnItemOnClickListener = listener;
    }
}
