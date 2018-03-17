package com.daedalusdigital.imakapp.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.materialdrawer.Drawer;
import com.daedalusdigital.imakapp.R;



/**
 * A simple {@link Fragment} subclass.
 * This is just a demo fragment with a long scrollable view of editTexts. Don't see this as a reference for anything
 */
public class PendingrFragment extends Fragment {
    private static final String KEY_TITLE = "title";

    private Drawer result;

    public PendingrFragment() {
        // Required empty public constructor
    }

    public static PendingrFragment newInstance(String title) {
        PendingrFragment f = new PendingrFragment();

        Bundle args = new Bundle();

        args.putString(KEY_TITLE, title);
        f.setArguments(args);

        return (f);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // don't look at this layout it's just a listView to show how to handle the keyboard
        View view = inflater.inflate(R.layout.fragment_simple_sample, container, false);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        super.onSaveInstanceState(outState);
    }
}
