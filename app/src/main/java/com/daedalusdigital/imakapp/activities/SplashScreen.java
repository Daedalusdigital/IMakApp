package com.daedalusdigital.imakapp.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.daedalusdigital.imakapp.MainActivity;
import com.daedalusdigital.imakapp.R;
import com.daedalusdigital.imakapp.StatusBarUtil;


public class SplashScreen extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    StatusBarUtil.immersive(this);
    startActivity(new Intent(this, MainActivity.class));
    finish();
  }
}
