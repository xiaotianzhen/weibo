package com.dong.weibo.fragments;

import android.support.v4.app.Fragment;
import android.content.Intent;

import com.dong.weibo.R;

/**
 * Created by 川东 on 2016/11/14.
 */

public class BaseFragment extends Fragment {

    @Override
    public void startActivity(Intent intent) {

        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.anim_in_right_left, R.anim.anim_out_right_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {

        getActivity().startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(R.anim.anim_in_right_left, R.anim.anim_out_right_left);
    }
}
