package com.viewpagerwithzoomin;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

    private final int[] imageList = {R.mipmap.bg1, R.mipmap.bg2, R.mipmap.bg3, R.mipmap.bg1, R.mipmap.bg2, R.mipmap.bg3};
    private ViewPager viewpagerImages;
    AdapterEventImages adapterEventImages;
    private boolean isEnabledPagePadding;
    private int padding = 20;
    private RelativeLayout rlParentMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewpagerImages = (ViewPager) findViewById(R.id.viewpager_event_images);
        rlParentMain = (RelativeLayout) findViewById(R.id.rl_parent_main);
        isEnabledPagePadding = true;
        setViewpagerSetting(!isEnabledPagePadding);
        adapterEventImages = new AdapterEventImages(this, imageList, onTouchListener, isEnabledPagePadding);
        viewpagerImages.setAdapter(adapterEventImages);
        adapterEventImages.notifyDataSetChanged();
    }

    AdapterEventImages.OnTouchListener onTouchListener = new AdapterEventImages.OnTouchListener() {
        @Override
        public void onTouch(boolean isZoom) {
            setViewpagerSetting(isZoom);
        }
    };

    private void setViewpagerSetting(boolean isZoomed) {
        int rlPadding = isZoomed ? 0 : padding;
        rlParentMain.setPadding(rlPadding,0,rlPadding,0);
    }

}
