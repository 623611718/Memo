package com.example.lz.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lz.Activity.R;

/**
 * Created by Administrator on 2019/7/23.
 */

public class SlidingMenu_Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        return layoutInflater.inflate(R.layout.fragment_sliding_menu_,container,false);
    }
}

