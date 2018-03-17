package com.daedalusdigital.imakapp.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.daedalusdigital.imakapp.StatusBarUtil;
import com.daedalusdigital.imakapp.activities.main;
import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;

import com.daedalusdigital.imakapp.R;
public class WelcomeFragmentsActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_fragments_layout);
        fragmentManager = getSupportFragmentManager();
        StatusBarUtil.immersive(this);
        final PaperOnboardingFragment onBoardingFragment = PaperOnboardingFragment.newInstance(getDataForOnboarding());

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, onBoardingFragment);
        fragmentTransaction.commit();

        onBoardingFragment.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {
                try{
                    goToAttract();

                }catch(Exception e){}
            }
        });
    }
    public void goToAttract()
    {
        Intent intent = new Intent( this, main.class);
        startActivity(intent);
        finish();
    }
    private ArrayList<PaperOnboardingPage> getDataForOnboarding() {
        // prepare data
        PaperOnboardingPage scr1 = new PaperOnboardingPage("Salons", "Find Avalible Salons Around You",
                Color.parseColor("#00ad79"), R.drawable.hotels, R.drawable.key);
        PaperOnboardingPage scr2 = new PaperOnboardingPage("Banks", "Find Avalible Salons Around You",
                Color.parseColor("#FFFF00"), R.drawable.banks, R.drawable.wallet);
        PaperOnboardingPage scr3 = new PaperOnboardingPage("Stores", "Find Avalible Salons Around You",
                Color.parseColor("#000000"), R.drawable.stores, R.drawable.shopping_cart);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(scr1);
        elements.add(scr2);
        elements.add(scr3);
        return elements;
    }
}
