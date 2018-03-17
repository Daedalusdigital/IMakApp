package com.daedalusdigital.imakapp.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daedalusdigital.imakapp.activities.BaseScreenFragment;
import com.daedalusdigital.imakapp.R;
import com.daedalusdigital.imakapp.widgets.ItemOffsetDecoration;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultDiffCallback;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder;
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewProvider;



public class DiffUtilFragment extends BaseScreenFragment {

	private YourDataProvider mYourDataProvider = new YourDataProvider();
	private RendererRecyclerViewAdapter mAdapter;
	private RecyclerView mRecyclerView;

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_list, container, false);

		mAdapter = new RendererRecyclerViewAdapter();

		mAdapter.setDiffCallback(new DiffCallback());
//		adapter.enableDiffUtil(); /* Or just call it to enable DiffUtil with DefaultDiffCallback */

		mAdapter.registerRenderer(new ViewBinder<>(R.layout.item_simple_square, DiffViewModel.class, getContext(),
				(model, finder, payloads) -> finder
						.find(R.id.text, (ViewProvider<TextView>) textView -> textView.setText(model.getText()))
						.setOnClickListener(R.id.text, v -> {
							reloadItems(model);
						})
		));
//		adapter.registerRenderer(...);
//		adapter.registerRenderer(...);

		mAdapter.setItems(mYourDataProvider.getDiffItems());

		mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
		mRecyclerView.addItemDecoration(new ItemOffsetDecoration(10));

		return view;
	}

	private void reloadItems(@NonNull final DiffViewModel model) {
		mAdapter.setItems(mYourDataProvider.getUpdatedDiffItems(model));
	}

	private class DiffCallback extends DefaultDiffCallback<DiffViewModel> {

		@Override
		public boolean areItemsTheSame(@NonNull final DiffViewModel oldItem, @NonNull final DiffViewModel newItem) {
			return oldItem.getID() == newItem.getID();
		}
	}

	public static class DiffViewModel implements ViewModel {

		private final int mID;
		private final String mText;

		public DiffViewModel(final int ID, final String text) {
			mID = ID;
			mText = text;
		}

		public String getText() {
			return mText;
		}

		public int getID() {
			return mID;
		}

		@Override
		public boolean equals(final Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}

			final DiffViewModel that = (DiffViewModel) o;

			if (mID != that.mID) {
				return false;
			}
			return mText != null ? mText.equals(that.mText) : that.mText == null;
		}

		@Override
		public int hashCode() {
			int result = mID;
			result = 31 * result + (mText != null ? mText.hashCode() : 0);
			return result;
		}
	}
}