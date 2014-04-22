package com.rc.devkit.debug;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.rc.devkit.foundation.LoadingDialogShowable;
import com.rc.devkit.utilities.Activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * TODO: Naprawic indexowanie rejestracji ! Tak �eby pasowa�o do p��niejszych uruchomien
 * TODO: Work on naming !!!
 */
public abstract class DebugActivity extends Activity implements AdapterView.OnItemClickListener, LoadingDialogShowable
{
    // Installation:
    // Add this to AndroidManifest.xml
    //  <activity android:name="YOUR_ACTIVITY_THAT_EXTENDS_THIS_CLASS" >
    // And give this activity a start priviliges (this part into activity tags):
    //    <intent-filter>
    //        <action android:name="android.intent.action.MAIN" />
    //        <category android:name="android.intent.category.LAUNCHER" />
    //    </intent-filter>
    //
    // Add this for fragment classes support
    //  <activity android:name=".devkit.com.rc.devkit.debug.DebugFragmentActivity" />

	public final int DISPLAY_TYPE_DEBUG_LIST = -1; 
	
    private final int POST_DELAY_TIME = 600;
    private final String DEBUG_GROUP = "DEBUG:GROUP";
    private Map<DebugRunOption, String> groupMap;
    private List<DebugRunOption> groupOfRunOptions = null;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init()
    {
        groupOfRunOptions = new ArrayList<DebugRunOption>();
        groupMap = new HashMap<DebugRunOption, String>();
        initRunOptions();

        int indexToRun = runOnStartOptionOnIndex();

        if (indexToRun < 0) {
            initViewWithRunOptions();
        }
        else
        {
            if (indexToRun >= groupOfRunOptions.size()) {
                throw new RuntimeException("Index exceeds bounds of passed DebugRunOptions");
            }

            DebugRunOption runOption = groupOfRunOptions.get(indexToRun);
            startRunOptionWithExit(runOption);
        }
    }

    private void initViewWithRunOptions()
    {
        FrameLayout frameLayout = new FrameLayout(this);
        ListView listView = new ListView(this);
        frameLayout.addView(listView);
        listView.setAdapter(new RunOptionAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1));
        listView.setOnItemClickListener(this);
        setContentView(frameLayout);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        DebugRunOption runOption = (DebugRunOption) adapterView.getAdapter().getItem(i);
        startRunOption(runOption, false);
    }

    public void register(DebugRunOption runOption)
    {
        register(runOption, DEBUG_GROUP);
    }

    public void register(DebugRunOption runOption, String group)
    {
        groupOfRunOptions.add(runOption);
        groupMap.put(runOption, group);
    }

    private void startRunOptionWithExit(DebugRunOption runOption)
    {
        startRunOption(runOption, true);
    }

    public abstract void onPreStartRunOption(DebugRunOption option);

    private void startRunOption(final DebugRunOption runOption, final boolean shouldExitOnComplete)
    {
    	// PRE START
        onPreStartRunOption(runOption);
        
        // START RUN OPTION
        DebugProceed proceed = new DebugProceed() {
			@Override
			public void proceed() {
				hideProgressBar();
		        @SuppressWarnings("rawtypes")
				Class runClass = runOption.getRunningClass();
		        Bundle extras = runOption.getExtras();

		        if (Activity.class.isAssignableFrom(runClass))
		        {
		            deployActivity(runClass, extras);
		        }
		        else if (Fragment.class.isAssignableFrom(runClass))
		        {
		            // TODO: Passing extras not implemented
		            // TODO: Check how to pass intent to fragment!!!!
		            Bundle fragmentBundle = new Bundle();
		            if (extras != null) {
		                fragmentBundle.putAll(extras);
		            }

		            fragmentBundle.putSerializable(DebugFragmentActivity.FRAGMENT_CLASS_TAG, runClass);
		            Activities.startActivity(DebugActivity.this, DebugFragmentActivity.class, fragmentBundle);
		        }

		        // POST START
		        new Handler().postDelayed(new Runnable()
		        {
		            @Override
		            public void run()
		            {
		                delayedPostStartRunOption(runOption);
		            }
		        }, POST_DELAY_TIME);

                executeExitOnComplete(shouldExitOnComplete);
			}

			@Override
			public void failed() {
				hideProgressBar();
                executeExitOnComplete(shouldExitOnComplete);
			}
		};
		
		if (shouldStartManually(runOption)) {
			showProgressBarWithMessage("Waiting to start manually!");
			startManually(runOption, proceed);
		}
		else {
			proceed.proceed();
		}
    }

    private void executeExitOnComplete(boolean shouldExitOnComplete)
    {
        if (shouldExitOnComplete)
        {
            this.finish();
        }
    }

    private void deployActivity(@SuppressWarnings("rawtypes") Class runClass, Bundle extras)
    {
        if (extras == null) {
            Activities.startActivity(this, runClass);
        }
        else {
            Activities.startActivity(this, runClass, extras);
        }
    }

    public abstract boolean shouldStartManually(DebugRunOption option);
    public abstract void startManually(DebugRunOption option, DebugProceed proceed);
    public abstract void delayedPostStartRunOption(DebugRunOption option);

    /**
     *  Use register methods to init these screens
     */
    public abstract void initRunOptions();
    // TODO: Refactor to registerRunOptions();

    /**
     * Return index of Option to run.
     * Index is specified in order which was added in initRunOptions() method.
     * Pass value below 0 (< 0) to start DebugActivity with options displayed on screen
     *
     * @return
     */
    public abstract int runOnStartOptionOnIndex();

    private class RunOptionAdapter extends ArrayAdapter<DebugRunOption>
    {
        public RunOptionAdapter(Context context, int resource, int textViewResourceId)
        {
            super(context, resource, textViewResourceId);
        }

        @Override
        public int getCount()
        {
            return groupOfRunOptions.size();
        }

        @Override
        public DebugRunOption getItem(int position)
        {
            return groupOfRunOptions.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            DebugRunOption runOption = getItem(position);

            if (convertView == null)
            {
                LayoutInflater mInflater = LayoutInflater.from(getContext());
                convertView = mInflater.inflate(android.R.layout.simple_list_item_1, null);
            }

            TextView textView = (TextView) convertView.findViewById(android.R.id.text1);

            String title = runOption.getTitle();

            if (title == null || "".equalsIgnoreCase(title)) {
                title = runOption.getRunningClass().getSimpleName();
            }

            textView.setText(title);
            return convertView;
        }
    }

    protected boolean belongsToGroup(DebugRunOption debugRunOption, String group)
    {
        if (groupMap.containsKey(debugRunOption)) {
            return groupMap.get(debugRunOption).equalsIgnoreCase(group);
        }

        return false;
    }

    protected boolean belongsToEveryGroup(DebugRunOption debugRunOption, String ... listOfGroups)
    {
        for (int i = 0 ; i < listOfGroups.length ; i++)
        {
            if (!belongsToGroup(debugRunOption, listOfGroups[i])) {
                return false;
            }
        }

        return true;
    }

    protected boolean belongsToAnyGroup(DebugRunOption debugRunOption, String ... listOfGroups)
    {
        for (int i = 0 ; i < listOfGroups.length ; i++)
        {
            if (belongsToGroup(debugRunOption, listOfGroups[i])) {
                return true;
            }
        }

        return false;
    }
    
    @Override
    public void showProgressBarWithMessage(String message)
    {
        if (mProgressDialog == null)
        {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(message);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }
    }

    @Override
    public void hideProgressBar()
    {
        if (mProgressDialog != null)
        {
            mProgressDialog.cancel();
            mProgressDialog = null;
        }
    }
}
