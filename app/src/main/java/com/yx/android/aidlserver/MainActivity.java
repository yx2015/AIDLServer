package com.yx.android.aidlserver;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private List<Fragment> list = new ArrayList<>();
    private String[] mTitles = new String[]{
            "TAB1", "TAB2", "TAB3", "TAB4",
    };
    FragmentPagerAdapter adapter;

    private ChangeColorWithText tab1, tab2, tab3, tab4;
    private List<ChangeColorWithText> mTabIndicators = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setOverflowButtonAlways();
        getActionBar().setDisplayShowHomeEnabled(false);

        initView();
        initDatas();
        setClickListener();

        viewPager.setAdapter(adapter);
    }

    private void setClickListener() {
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab4.setOnClickListener(this);
        viewPager.setOnPageChangeListener(this);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.vp_viewpager);
        tab1 = (ChangeColorWithText) findViewById(R.id.tab1);
        tab2 = (ChangeColorWithText) findViewById(R.id.tab2);
        tab3 = (ChangeColorWithText) findViewById(R.id.tab3);
        tab4 = (ChangeColorWithText) findViewById(R.id.tab4);

    }

    /**
     * 填充数据
     */
    private void initDatas() {
        for (String title : mTitles) {
            TabFragment tabFragment = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            tabFragment.setArguments(bundle);
            list.add(tabFragment);
        }

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }
        };
        mTabIndicators.add(tab1);
        mTabIndicators.add(tab2);
        mTabIndicators.add(tab3);
        mTabIndicators.add(tab4);
        tab1.setIconAlpha(1.0f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    private void setOverflowButtonAlways() {
        ViewConfiguration configuration = ViewConfiguration.get(this);
        try {
            Field menuKey = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKey.setAccessible(true);
            menuKey.setBoolean(configuration, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        resetOtherTabs();

        switch (v.getId())
        {
            case R.id.tab1:
                mTabIndicators.get(0).setIconAlpha(1.0f);
                viewPager.setCurrentItem(0, false);
                break;
            case R.id.tab2:
                mTabIndicators.get(1).setIconAlpha(1.0f);
                viewPager.setCurrentItem(1, false);
                break;
            case R.id.tab3:
                mTabIndicators.get(2).setIconAlpha(1.0f);
                viewPager.setCurrentItem(2, false);
                break;
            case R.id.tab4:
                mTabIndicators.get(3).setIconAlpha(1.0f);
                viewPager.setCurrentItem(3, false);
                break;
        }


    }

    /**
     * 重置其他的TabIndicator的颜色
     */
    private void resetOtherTabs()
    {
        for (int i = 0; i < mTabIndicators.size(); i++)
        {
            mTabIndicators.get(i).setIconAlpha(0);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0)
        {
            ChangeColorWithText left = mTabIndicators.get(position);
            ChangeColorWithText right = mTabIndicators.get(position + 1);
            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
