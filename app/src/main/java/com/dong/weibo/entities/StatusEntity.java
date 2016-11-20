package com.dong.weibo.entities;

import java.util.List;

/**
 * Created by 川东 on 2016/11/17.
 */

public class StatusEntity {

       /* {"created_at": "Thu Nov 17 17:23:01 +0800 2016",
            "id": 4042852374320744,
            "mid": "4042852374320744",
            "idstr": "4042852374320744",
            "text": "#田柾国##田柾国BEGIN# <饭拍高清> 161116 Asia Artist Award \ncr：JK'S FOREST\n＝禁二改商用，转载请注明出处＝",
            "textLength": 105,
            "source_allowclick": 0,
            "source_type": 2,
            "source": "<a href=\"http://weibo.com/\" rel=\"nofollow\">果果多多哒iPhone 6</a>",
            "favorited": false,
            "truncated": false,
            "in_reply_to_status_id": "",
            "in_reply_to_user_id": "",
            "in_reply_to_screen_name": ""}*/
    public String created_at;
    public long id;
    public String mid;
    public String idstr;
    public String text;
    public  int source_allowclick;
    public int source_type;
    public String source ;
    public boolean favorited;
    public boolean truncated;
    public String in_reply_to_status_id;
    public String in_reply_to_user_id;
    public String in_reply_to_screen_name;

    public String thumbnail_pic;
    public String bmiddle_pic ;
    public  String original_pic;
    public Object geo;
    public UsersEntity user;
    public StatusEntity retweeted_status;
    public  int reposts_count;
    public int comments_count;
    public int attitudes_count;
    public int mlevel;
    public Object visible;
    public Object pic_ids;
    public List<PicUrlsEntity> pic_urls;



}
