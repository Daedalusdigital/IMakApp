package com.daedalusdigital.imakapp.activities;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.daedalusdigital.imakapp.Fragment.DashboardFragment;
import com.daedalusdigital.imakapp.Fragment.PayloadFragment;
import com.daedalusdigital.imakapp.Fragment.PendingrFragment;
import com.daedalusdigital.imakapp.R;
import com.microsoft.azure.mobile.MobileCenter;
import com.microsoft.azure.mobile.analytics.Analytics;
import com.microsoft.azure.mobile.crashes.Crashes;

import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import android.widget.Toast;

import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;




public class main extends AppCompatActivity {


    public Drawer result;
    private IProfile profile;
    private static final int PROFILE_SETTING = 1;
    private AccountHeader headerResult = null;
    private OnFilterChangedListener onFilterChangedListener;
    Fragment frag_dashboard,frag_report,frag_stylist;
    public void setOnFilterChangedListener(OnFilterChangedListener onFilterChangedListener) {
        this.onFilterChangedListener = onFilterChangedListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);

        MobileCenter.start(getApplication(), "3086d8da-da5a-4288-98bc-b809037c6abe",
                Analytics.class, Crashes.class);
        final Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null)
        {


        }

        // Create a few sample profile
        profile = new ProfileDrawerItem().withName("Welcome King").withEmail("Welcome@outlook.com").withIcon(getResources().getDrawable(com.daedalusdigital.imakapp.R.drawable.profile));
        // Create the AccountHeader
        buildHeader(false, savedInstanceState);

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.category_location).withBadge("15").withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)).withIconColor(0).withIcon(GoogleMaterial.Icon.gmd_my_location).withIdentifier(1),
                        new SectionDrawerItem().withName(R.string.category_section_menu).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.category_salon).withIcon(GoogleMaterial.Icon.gmd_location_city).withIdentifier(3).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.category_style).withIcon(GoogleMaterial.Icon.gmd_local_activity).withIdentifier(4).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.category_book).withIcon(GoogleMaterial.Icon.gmd_book).withIdentifier(5).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.category_fav).withIcon(GoogleMaterial.Icon.gmd_favorite).withIdentifier(6).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.category_invite).withIcon(GoogleMaterial.Icon.gmd_share).withIdentifier(7).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.category_customer).withIcon(GoogleMaterial.Icon.gmd_help).withIdentifier(8).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.category_set).withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(9).withSelectable(false)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener()
                {

                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem )
                    {

                        if(drawerItem.getIdentifier() == 1)
                        {
                            frag_dashboard=new DashboardFragment();
                            try{
                                    Fragment f = new  DashboardFragment();
                                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
                                    return true;
                                }
                                catch(Exception e)
                                {
                                    Toast.makeText(main.this, ((Nameable) drawerItem).getName().getText(main.this), Toast.LENGTH_SHORT).show();
                                }
                        }
                        if(drawerItem.equals(3))
                        {
                            frag_stylist=new PayloadFragment();
                            try{
                                Fragment f = new  PayloadFragment();
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
                                return true;
                            }
                            catch(Exception e)
                            {}
                        }
                        if(drawerItem.equals(7))
                        {
                            frag_report =new report();
                            try{
                                Fragment f = new  report();
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
                                return true;
                            }
                            catch(Exception e)
                            {}
                        }
                        if(drawerItem.equals(6))
                        {

                        }try{
                            Fragment f = new PendingrFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
                        }
                        catch(Exception e)
                        {}
                        return false;
                    }
                }).withSavedInstance(savedInstanceState)
                .build();

        result.getRecyclerView().setVerticalScrollBarEnabled(false);
    }


    private void buildHeader(boolean compact, Bundle savedInstanceState)
    {
        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(com.daedalusdigital.imakapp.R.drawable.header)
                .withCompactStyle(compact)
                .addProfiles(
                        profile,
                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        new ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new Account").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add)),
                        new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
                        if (profile instanceof IDrawerItem && profile.getIdentifier() == PROFILE_SETTING) {
                            IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Batman").withEmail("batman@gmail.com").withIcon(getResources().getDrawable(com.daedalusdigital.imakapp.R.drawable.profile));
                            if (headerResult.getProfiles() != null) {
                                //we know that there are 2 setting elements. set the new profile above them ;)
                                headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
                            } else {
                                headerResult.addProfiles(newProfile);
                            }
                        }

                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        menu.findItem(R.id.action_open_source).setIcon(new IconicsDrawable(this, FontAwesome.Icon.faw_cart_arrow_down).color(Color.WHITE).actionBar());
        menu.findItem(R.id.action_refresh).setIcon(new IconicsDrawable(this, FontAwesome.Icon.faw_refresh).paddingDp(1).color(Color.WHITE).actionBar());
        menu.findItem(R.id.action_shuffle).setIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_search).paddingDp(1).color(Color.WHITE).actionBar());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_open_source) {
            frag_report =new report();
            try {
                Fragment f = new report();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
            }catch(Exception e){}
            return true;
        }
        if (id == R.id.action_shuffle) {
            frag_stylist=new PayloadFragment();
            try{
                Fragment f = new  PayloadFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
            }catch(Exception e){}
            return true;
        }
        if (id == R.id.action_refresh) {

            frag_dashboard=new DashboardFragment();
            try{
                Fragment f = new  DashboardFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
            }catch(Exception e){}
            return true;
        }
        return false; //super.onOptionsItemSelected(item);
    }
    public void move()
    {

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = result.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    public void onResume() {
        super.onResume();
        // ... your own onResume implementation

    }
    public interface OnFilterChangedListener {
        void onFilterChanged(long filter);
    }
    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
