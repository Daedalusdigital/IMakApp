package com.daedalusdigital.imakapp.activities;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.daedalusdigital.imakapp.R;
import com.microsoft.azure.mobile.MobileCenter;
import com.microsoft.azure.mobile.analytics.Analytics;
import com.microsoft.azure.mobile.crashes.Crashes;
import net.hockeyapp.android.CrashManager;

import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;

import android.view.animation.OvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;


public class main extends AppCompatActivity {
    public enum Category {
        ALL(1000),
        FEATURED(1001),
        LOVED(1002),
        BUILDINGS(1),
        FOOD(2),
        NATURE(4),
        PEOPLE(8),
        TECHNOLOGY(16),
        OBJECTS(32);

        public final int id;

        private Category(int id) {
            this.id = id;
        }
    }

    public Drawer result;
    private IProfile profile;
    private static final int PROFILE_SETTING = 1;
    private AccountHeader headerResult = null;
    private FragmentManager fragmentManager;
    private OnFilterChangedListener onFilterChangedListener;

    public void setOnFilterChangedListener(OnFilterChangedListener onFilterChangedListener) {
        this.onFilterChangedListener = onFilterChangedListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);
        MobileCenter.start(getApplication(), "3086d8da-da5a-4288-98bc-b809037c6abe",
                Analytics.class, Crashes.class);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
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
                        new PrimaryDrawerItem().withName(R.string.category_location).withIdentifier(Category.ALL.id).withIcon(GoogleMaterial.Icon.gmd_my_location),
                        new SectionDrawerItem().withName(R.string.category_section_menu),
                        new PrimaryDrawerItem().withName(R.string.category_salon).withIdentifier(Category.BUILDINGS.id).withIcon(GoogleMaterial.Icon.gmd_location_city),
                        new PrimaryDrawerItem().withName(R.string.category_style).withIdentifier(Category.FOOD.id).withIcon(GoogleMaterial.Icon.gmd_local_activity),
                        new PrimaryDrawerItem().withName(R.string.category_book).withIdentifier(Category.FEATURED.id).withIcon(GoogleMaterial.Icon.gmd_book),
                        new PrimaryDrawerItem().withName(R.string.category_fav).withIdentifier(Category.NATURE.id).withIcon(GoogleMaterial.Icon.gmd_favorite),
                        new PrimaryDrawerItem().withName(R.string.category_invite).withIdentifier(Category.OBJECTS.id).withIcon(GoogleMaterial.Icon.gmd_share),
                        new PrimaryDrawerItem().withName(R.string.category_customer).withIdentifier(Category.PEOPLE.id).withIcon(GoogleMaterial.Icon.gmd_help),
                        new PrimaryDrawerItem().withName(R.string.category_set).withIdentifier(Category.TECHNOLOGY.id).withIcon(GoogleMaterial.Icon.gmd_settings)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {

                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            if (drawerItem instanceof Nameable) {
                                toolbar.setTitle(((Nameable) drawerItem).getName().getText(main.this));
                            }
                            if (onFilterChangedListener != null) {
                                onFilterChangedListener.onFilterChanged(drawerItem.getIdentifier());
                            }
                        }

                        return false;
                    }
                })
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
                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == PROFILE_SETTING) {
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
        menu.findItem(R.id.action_shuffle).setIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_search).paddingDp(1).color(Color.WHITE).actionBar());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_open_source) {
            new LibsBuilder()
                    .withFields(R.string.class.getFields())
                    .withActivityTitle(getString(R.string.action_open_source))
                    .withActivityTheme(R.style.MaterialDrawerTheme)
                    .withLibraries("rxJava", "rxAndroid")
                    .start(this);

            return true;
        }
        if (id == R.id.action_shuffle) {


            return true;
        }
        return false; //super.onOptionsItemSelected(item);
    }
    public void move()
    {

    }
    private void checkForCrashes() {
        CrashManager.register(this);
    }
    public void onResume() {
        super.onResume();
        // ... your own onResume implementation
        checkForCrashes();
    }
    public interface OnFilterChangedListener {
        public void onFilterChanged(long filter);
    }
}
