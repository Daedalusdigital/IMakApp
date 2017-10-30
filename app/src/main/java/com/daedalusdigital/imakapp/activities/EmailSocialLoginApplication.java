package com.daedalusdigital.imakapp.activities;

import android.app.Application;
import android.graphics.Color;

import com.daedalusdigital.imakapp.R;
import com.daedalusdigital.imakapp.test.SimpleForgotPasswordProvider;
import com.daedalusdigital.imakapp.Argus;
import com.daedalusdigital.imakapp.veiw.ArgusTheme;
import com.daedalusdigital.imakapp.nextscreenproviders.SimpleNextScreenProvider;
import com.daedalusdigital.imakapp.storage.DefaultArgusStorage;

public class EmailSocialLoginApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();



        ArgusTheme.Builder themeBuilder = new ArgusTheme.Builder();
        themeBuilder.logo(R.drawable.argus_logo)
                .backgroundDrawable(R.drawable.bg)
                .buttonDrawable(R.drawable.button_bg)
                .welcomeText(getString(R.string.welcome))
                .welcomeTextColor(Color.WHITE)
                .welcomeTextSize(18.0f)
                .showEditTextDrawables(false);

        new Argus.Builder()
                .argusStorage(new DefaultArgusStorage(getApplicationContext()))
                .nextScreenProvider(new SimpleNextScreenProvider(MainActivity.class))
                .enableSkipLogin(true)
                .skipLoginText(getString(R.string.skip_login))
                .theme(themeBuilder.build())
                .forgotPasswordProvider(new SimpleForgotPasswordProvider())
                .build();
    }
}