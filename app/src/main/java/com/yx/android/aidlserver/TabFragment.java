package com.yx.android.aidlserver;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/3/2 0002.
 */
public class TabFragment extends Fragment{
    private static final String TITLE="title";
    private String mTitle="title";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments()!=null){
            mTitle=getArguments().getString(TITLE);
        }
        TextView textView=new TextView(getActivity());
        textView.setTextSize(20);
        textView.setText(mTitle);
        textView.setBackgroundColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
