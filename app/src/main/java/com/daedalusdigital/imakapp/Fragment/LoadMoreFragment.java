package com.daedalusdigital.imakapp.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daedalusdigital.imakapp.utils.YourDataProvider;
import com.daedalusdigital.imakapp.activities.BaseScreenFragment;
import com.daedalusdigital.imakapp.R;
import com.daedalusdigital.imakapp.widgets.EndlessScrollListener;
import com.daedalusdigital.imakapp.widgets.ItemOffsetDecoration;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.binder.LoadMoreViewBinder;
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder;
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewProvider;

import java.lang.reflect.Type;

import static java.security.AccessController.getContext;


public class LoadMoreFragment extends BaseScreenFragment {

	public static final int COLUMNS_COUNT = 4;
	private final YourDataProvider mYourDataProvider = new YourDataProvider();
	private RendererRecyclerViewAdapter mAdapter;
	private RecyclerView mRecyclerView;
	private GridLayoutManager mLayoutManager;

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_list, container, false);

		mAdapter = new RendererRecyclerViewAdapter();
		mAdapter.enableDiffUtil();


//		mAdapter.setLoadMoreModel(new YourLoadMoreModel()); /* you can change the LoadMoreModel if needed */
		mAdapter.registerRenderer(new LoadMoreViewBinder(R.layout.item_load_more, getContext()));
		mAdapter.registerRenderer(new ViewBinder<>(R.layout.item_simple_square, SimpleViewModel.class, getContext(),
				(model, finder, payloads) -> finder.find(R.id.text, (ViewProvider<TextView>) textView -> textView.setText(model.getText()))
		));
//		adapter.registerRenderer(...);
//		adapter.registerRenderer(...);

		mAdapter.setItems(mYourDataProvider.getLoadMoreItems());

		mLayoutManager = new GridLayoutManager(getContext(), COLUMNS_COUNT);
		mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
			@Override
			public int getSpanSize(final int position) {
				final Type type = mAdapter.getType(position);
				if (type.equals(SimpleViewModel.class)) {
					return 1;
				}
				return COLUMNS_COUNT;
			}
		});

		mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.addItemDecoration(new ItemOffsetDecoration(10));
		mRecyclerView.addOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(final int page, final int totalItemsCount) {
				Log.d("#####", "onLoadMore " + page);
				mAdapter.showLoadMore();
//				mAdapter.hideLoadMore(); /* if you need force hide progress or call setItems() */
				mYourDataProvider.getLoadMoreItems(list -> getActivity().runOnUiThread(() -> mAdapter.setItems(list)));
			}
		});

		return view;
	}

	public static class SimpleViewModel implements ViewModel {

		private final String mText;

		public SimpleViewModel(final String text) {
			mText = text;
		}

		public String getText() {
			return mText;
		}
	}
}
