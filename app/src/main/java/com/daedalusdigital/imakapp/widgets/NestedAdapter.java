package com.daedalusdigital.imakapp.widgets;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;

import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder;

import java.util.List;


public class NestedAdapter extends RendererRecyclerViewAdapter {

	private static final String TAG = NestedAdapter.class.getSimpleName();

	@Override
	public ViewHolder onCreateViewHolder(final ViewGroup parent, final int typeIndex) {
		final ViewHolder viewHolder = super.onCreateViewHolder(parent, typeIndex);
		Log.d(TAG, "onCreateViewHolder: " + viewHolder.getClass().getSimpleName());
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, final int position, @Nullable final List payloads) {
		Log.d(TAG, "onBindViewHolder: " + holder.getClass().getSimpleName());
		super.onBindViewHolder(holder, position, payloads);
	}

	@Override
	public void onViewRecycled(final ViewHolder holder) {
		Log.d(TAG, "onViewRecycled: " + holder.getClass().getSimpleName());
		super.onViewRecycled(holder);
	}
}