package com.viewpagerwithzoomin;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.io.File;

public class AdapterEventImages extends PagerAdapter {

    private Context mContext;
    private OnTouchListener onTouchListener;
    private boolean isEnabledPagePadding;

    private File parentDir;
    private File[] imageList;

    protected AdapterEventImages(Context mContext, File parentDir, File[] imageList, OnTouchListener onTouchListener, boolean isEnabledPagePadding) {
        this.mContext = mContext;
        this.parentDir = parentDir;
        this.imageList = imageList;
        this.onTouchListener = onTouchListener;
        this.isEnabledPagePadding =isEnabledPagePadding;
    }

    @Override
    public int getCount() {
        return imageList.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.event_image_pager, container, false);

        TouchImageView image = (TouchImageView)itemView.findViewById(R.id.iv_event_pager);
        image.setImageURI(Uri.fromFile(imageList[position]));
        container.addView(itemView);
        if(isEnabledPagePadding) {
            image.setOnTouchListener((v, event) -> {
                onTouchListener.onTouch(image.getCurrentZoom() > 1.0);
                return false;
            });
        }
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    interface  OnTouchListener{
        void onTouch(boolean isZoom);
    }
}
