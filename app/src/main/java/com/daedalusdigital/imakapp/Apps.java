package com.daedalusdigital.imakapp;

import android.app.Application;

public class Apps extends Application
{
	@Override
	public void onCreate() {
		FontsOverride.setDefaultFont(this, "SERIF","fonts/apercu_regular.otf");
		super.onCreate();
	}
}
