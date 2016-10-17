package com.ymsfd.practices.ui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ymsfd.practices.R;

/**
 * Created by ymsfdDev.
 * User: ymsfd
 * Date: 4/24/15
 * Time: 14:13
 */
public class BaseFragment extends Fragment {
    protected TextView tv_name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        SpannableString spannableString = new SpannableString("ymsfd");
        try {
            Drawable drawable = ContextCompat.getDrawable(getActivity(),R.drawable.ic_schedule);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            ImageSpan imageSpan = new ImageSpan(drawable, DynamicDrawableSpan.ALIGN_BOTTOM);
            spannableString.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_name.setText(spannableString);
        return view;
    }
}
