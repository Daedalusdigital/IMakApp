package com.daedalusdigital.imakapp.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.daedalusdigital.imakapp.R;
import com.daedalusdigital.imakapp.utils.LogUtil;


public class SplashActivity extends AppCompatActivity {

    private Class<?> mMainActivity;
    private AsyncTask<Void, Void, Boolean> mPrepareApp;

    @Deprecated
    public void initSplashActivity(@Nullable Bundle savedInstanceState, @NonNull Class<?> mainActivity, int duration) {
        initSplashActivity(savedInstanceState, mainActivity);
    }

    public void initSplashActivity(@Nullable Bundle savedInstanceState, @NonNull Class<?> mainActivity) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mMainActivity = mainActivity;
        prepareApp();
    }



    @Override
    protected void onDestroy() {
        if (mPrepareApp != null) mPrepareApp.cancel(true);
        super.onDestroy();
    }

    private void prepareApp() {
        mPrepareApp = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                while (!isCancelled()) {
                    try {
                        Thread.sleep(500);
                        return true;
                    } catch (Exception e) {
                        LogUtil.e(Log.getStackTraceString(e));
                        return false;
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                mPrepareApp = null;
                if (aBoolean) {
                    startActivity(new Intent(SplashActivity.this, mMainActivity));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
