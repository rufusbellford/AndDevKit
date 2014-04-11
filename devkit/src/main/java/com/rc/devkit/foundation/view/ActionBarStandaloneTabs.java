package com.rc.devkit.foundation.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rc.devkit.utilities.SDK;

import java.util.ArrayList;
import java.util.List;

public class ActionBarStandaloneTabs extends LinearLayout
{
    private List<Tab> groupOfTabs = new ArrayList<Tab>();
    private ActionBarAdapter adapter;
    private OnTabSelectListener onTabSelectListener;
    private Tab currentSelectedTab;

    public ActionBarStandaloneTabs(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void setAdapter(ActionBarAdapter adapter)
    {
        this.adapter = adapter;
        populateTabs();
        selectFirstTab();
    }

    private void selectFirstTab()
    {
        showTabAtPositionSelected(0, true);
        currentSelectedTab = groupOfTabs.get(0);
    }

    private void populateTabs()
    {
//        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics());
//        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 160, getResources().getDisplayMetrics());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;

        for (int i = 0; i < adapter.getItemCount(); i++)
        {
            Tab tab = new Tab(i);
            View customTabView = adapter.getCustomViewAtPosition(i);

            if (customTabView == null)
            {
                String tabTitle = adapter.getItemTitle(i);
                TabView tabView = new TabView(getContext());
                tabView.setTitle(tabTitle);
                tab.setCustomView(tabView);
            }
            else
            {
                tab.setCustomView(customTabView);
            }

            tab.getView().setOnClickListener(onClickListener);
            groupOfTabs.add(tab);
            this.addView(tab.getView(), layoutParams);
        }
    }

    public void setOnTabSelectListener(OnTabSelectListener onTabSelectListener)
    {
        this.onTabSelectListener = onTabSelectListener;
    }

    public static class Tab
    {
        private View tabView;
        private int position;

        public Tab(int position)
        {
            this.position = position;
        }

        public View getView()
        {
            return tabView;
        }

        public void setCustomView(View tabView)
        {
            this.tabView = tabView;
            this.tabView.setTag(getPosition());
        }

        public int getPosition()
        {
            return position;
        }

        public void setPosition(int position)
        {
            this.position = position;
        }
    }

    private OnClickListener onClickListener = new OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            Integer position = (Integer) view.getTag();
            Tab selectedTab = groupOfTabs.get(position);
            onTabSelected(selectedTab);
        }
    };

    public void selectTabAtPosition(int position)
    {
        Tab tab = groupOfTabs.get(position);
        if (tab != null)
        {
            onTabSelected(tab);
        }
    }

    private void showTabAtPositionSelected(int position, boolean isSelected)
    {
        Tab tab = groupOfTabs.get(position);
        if (tab != null)
        {
            tab.getView().setSelected(isSelected);
        }
    }

    private void onTabSelected(Tab selectedTab)
    {
        boolean hasListener = (onTabSelectListener != null);

        if (selectedTab != currentSelectedTab)
        {
            if (currentSelectedTab != null)
            {
                currentSelectedTab.getView().setSelected(false);
                if (hasListener)
                {
                    onTabSelectListener.onTabUnselected(currentSelectedTab);
                }
            }

            if (hasListener)
            {
                onTabSelectListener.onTabSelected(selectedTab);
            }

            selectedTab.getView().setSelected(true);

            currentSelectedTab = selectedTab;
        }
        else
        {
            if (hasListener)
            {
                onTabSelectListener.onTabReselected(selectedTab);
            }
        }
    }

    public static class TabView extends FrameLayout
    {
        private TextView title;

        public TabView(Context context)
        {
            super(context);
            setup();
        }

        private void setup()
        {
            title = new TextView(getContext());
            title.setGravity(Gravity.CENTER);
            this.addView(title, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        public void setTitle(String text)
        {
            title.setText(text.toUpperCase());
        }

        private void setBKG(Drawable drawable)
        {
            if (SDK.isSDKEquealOrAbove(16))
            {
                this.setBackground(drawable);
            }
            else
            {
                this.setBackgroundDrawable(drawable);
            }
        }
    }

    public static interface OnTabSelectListener
    {
        public void onTabSelected(Tab tab);

        public void onTabUnselected(Tab tab);

        public void onTabReselected(Tab tab);
    }

    public static abstract class ActionBarAdapter
    {
        public abstract int getItemCount();

        public abstract String getItemTitle(int position);

        public abstract View getCustomViewAtPosition(int position);
    }
}
