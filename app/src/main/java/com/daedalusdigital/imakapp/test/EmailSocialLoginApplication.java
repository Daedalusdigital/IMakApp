package com.daedalusdigital.imakapp.test;

import android.app.Application;
import android.graphics.Color;

import com.daedalusdigital.imakapp.R;
import com.daedalusdigital.imakapp.activities.MainActivity;

import com.daedalusdigital.imakapp.Argus;
import com.daedalusdigital.imakapp.veiw.ArgusTheme;
import com.daedalusdigital.imakapp.nextscreenproviders.SimpleNextScreenProvider;
import com.daedalusdigital.imakapp.provider.BaseProvider;
import com.daedalusdigital.imakapp.storage.DefaultArgusStorage;
import com.daedalusdigital.imakapp.validations.LengthValidation;

import java.util.ArrayList;
import java.util.List;

public class EmailSocialLoginApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        ArgusTheme.Builder themeBuilder = new ArgusTheme.Builder();
        themeBuilder.logo(R.mipmap.ic_launcher);

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