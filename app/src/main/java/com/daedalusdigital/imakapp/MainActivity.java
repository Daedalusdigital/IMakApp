package com.daedalusdigital.imakapp;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.daasuu.ei.Ease;
import com.daasuu.ei.EasingInterpolator;
import com.daedalusdigital.imakapp.Fragment.DashboardFragment;
import com.daedalusdigital.imakapp.Fragment.LoginFragment;
import com.daedalusdigital.imakapp.activities.main;


public class MainActivity extends AppCompatActivity 
{
	Fragment frag_login, frag_dashboard;
	ProgressBar pbar;
	View button_login, button_label,button_icon,ic_menu1,ic_menu2;
	private DisplayMetrics dm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		pbar=(ProgressBar) findViewById(R.id.mainProgressBar1);
		button_icon=findViewById(R.id.button_icon);
		button_label=findViewById(R.id.button_label);


		dm=getResources().getDisplayMetrics();
		button_login=findViewById(R.id.button_login);
		button_login.setTag(0);
		pbar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
		StatusBarUtil.immersive(this);

		frag_login=new LoginFragment();
		frag_dashboard=new DashboardFragment();
		getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, frag_login).commit();
		final ValueAnimator va=new ValueAnimator();
		va.setDuration(1000);
		va.setInterpolator(new DecelerateInterpolator());
		va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
				@Override
				public void onAnimationUpdate(ValueAnimator p1) {
					RelativeLayout.LayoutParams button_login_lp= (RelativeLayout.LayoutParams) button_login.getLayoutParams();
					button_login_lp.width=Math.round((Float) p1.getAnimatedValue());
					button_login.setLayoutParams(button_login_lp);
				}
			});
		button_login.animate().translationX(dm.widthPixels+button_login.getMeasuredWidth()).setDuration(0).setStartDelay(0).start();
		button_login.animate().translationX(0).setStartDelay(6500).setDuration(1500).setInterpolator(new OvershootInterpolator()).start();
		button_login.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1) {
					if((int)button_login.getTag()==1){
						return;
					}else if((int)button_login.getTag()==2){
						button_login.animate().x(dm.widthPixels/2).y(dm.heightPixels/2).setInterpolator(new EasingInterpolator(Ease.CUBIC_IN)).setListener(null).setDuration(1000).setStartDelay(0).start();
						button_login.animate().setStartDelay(600).setDuration(1000).scaleX(40).scaleY(40).setInterpolator(new EasingInterpolator(Ease.CUBIC_IN_OUT)).start();
						button_icon.animate().alpha(0).rotation(90).setStartDelay(0).setDuration(800).start();
						return;
					}
					button_login.setTag(1);
					va.setFloatValues(button_login.getMeasuredWidth(), button_login.getMeasuredHeight());
					va.start();
					pbar.animate().setStartDelay(300).setDuration(1000).alpha(1).start();
					button_label.animate().setStartDelay(100).setDuration(500).alpha(0).start();
					button_login.animate().setInterpolator(new FastOutSlowInInterpolator()).setStartDelay(4000).setDuration(1000).setListener(new Animator.AnimatorListener(){
							@Override
							public void onAnimationStart(Animator p1) {


							}

							@Override
							public void onAnimationEnd(Animator p1)
							{
								try{
									goToAttract();

								}catch(Exception e){}
							}

							@Override
							public void onAnimationCancel(Animator p1) {
								// TODO: Implement this method
							}

							@Override
							public void onAnimationRepeat(Animator p1) {
								// TODO: Implement this method
							}
						}).start();

					
				}
			});
    }
	public void goToAttract()
	{
		Intent intent = new Intent( this, main.class);
		startActivity(intent);
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		
	}

}
