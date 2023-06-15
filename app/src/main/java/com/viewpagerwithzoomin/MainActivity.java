package com.viewpagerwithzoomin;

import android.app.Activity;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends Activity {

    public static final Set<String> exts = new HashSet<>(Arrays.asList(".bmp",".gif",".jpg",".png"));
    public static final FilenameFilter imgTypeFilter = (dir, name) -> name.length() >= 4 && exts.contains(name.substring(name.length()-4));

//    private final int[] imageList = {R.mipmap.bg1, R.mipmap.bg2, R.mipmap.bg3, R.mipmap.bg1, R.mipmap.bg2, R.mipmap.bg3};
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

        File parentDir = new File(Environment.getExternalStorageDirectory(), "DCIM/Camera");
        parentDir.mkdirs();
        File[] imageList = parentDir.listFiles(imgTypeFilter);
        if(imageList != null && imageList.length > 0) {
            adapterEventImages = new AdapterEventImages(this, parentDir, imageList, onTouchListener, isEnabledPagePadding);
            viewpagerImages.setAdapter(adapterEventImages);
            adapterEventImages.notifyDataSetChanged();
            return;
        }
        else if(imageList == null) Toast.makeText(this, "Please manually grant storage access permissions", Toast.LENGTH_LONG).show();
        else /*if(imageList.length()==0)*/ Toast.makeText(this, "No images found in "+parentDir.getAbsolutePath(), Toast.LENGTH_LONG).show();
        finishAffinity();
    }

    AdapterEventImages.OnTouchListener onTouchListener = this::setViewpagerSetting;

    private void setViewpagerSetting(boolean isZoomed) {
        int rlPadding = isZoomed ? 0 : padding;
        rlParentMain.setPadding(rlPadding,0,rlPadding,0);
    }

}
