package com.daedalusdigital.imakapp.utils;

import com.daedalusdigital.imakapp.Fragment.LoadMoreFragment;
import com.daedalusdigital.imakapp.utils.DiffUtilFragment.DiffViewModel;
import com.daedalusdigital.imakapp.Fragment.PayloadFragment.PayloadViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultCompositeViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.daedalusdigital.imakapp.utils.ViewRendererFragment.RectViewModel;
import static com.daedalusdigital.imakapp.utils.ViewStateFragment.StateViewModel;



public class YourDataProvider {

	final ArrayList<PayloadViewModel> mPayloadItems = new ArrayList<>();

	private ArrayList<LoadMoreFragment.SimpleViewModel> mLoadMoreItems = new ArrayList<>();
	private ThreadPoolExecutor mExecutor = new ThreadPoolExecutor(2, 2, 2, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
	private ArrayList<ViewModel> mDiffItems = new ArrayList<>();

	public List<ViewModel> getDiffItems() {
		for (int i = 0; i < 50; i++) {
			mDiffItems.add(new DiffViewModel(i, String.valueOf(i)));
		}
		return mDiffItems;
	}

	public List<ViewModel> getUpdatedDiffItems(final DiffViewModel model) {
		final int clickedIndex = mDiffItems.indexOf(model);
		final ViewModel clickedModel = mDiffItems.remove(clickedIndex);
		final ViewModel remove = mDiffItems.remove(0);
		Collections.shuffle(mDiffItems);

		/* https://stackoverflow.com/a/43461324/4894238
		 * use RendererRecyclerViewAdapter.setUpdateCallback(ListUpdateCallback) if you want
		 * */
		mDiffItems.add(0, remove);
		mDiffItems.add(clickedIndex, clickedModel);
		return mDiffItems;
	}

	public List<ViewModel> getSquareItems() {
		final ArrayList<ViewModel> items = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			items.add(new RectViewModel(String.valueOf(i)));
		}
		return items;
	}

	public List<ViewModel> getCompositeSimpleItems() {
		final ArrayList<ViewModel> items = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			items.add(new DefaultCompositeViewModel(getDiffItems()));
		}
		return items;
	}

	public List<ViewModel> getStateItems() {
		final ArrayList<ViewModel> items = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			items.add(new StateViewModel(i, getDiffItems()));
		}
		return items;
	}

	public List<PayloadViewModel> getPayloadItems() {
		if (mPayloadItems.isEmpty()) {
			for (int i = 0; i < 50; i++) {
				mPayloadItems.add(new PayloadViewModel(i, String.valueOf(i), "model ID: " + i));
			}
		}
		return mPayloadItems;
	}

	public List<PayloadViewModel> getChangedPayloadItems(final PayloadViewModel model) {
		/* Just for example */
		final ArrayList<PayloadViewModel> newList = new ArrayList<>();

		for (final PayloadViewModel viewModel : mPayloadItems) {
			if (viewModel.getID() == model.getID()) {
				newList.add(new PayloadViewModel(
						model.getID(),
						String.valueOf(Integer.valueOf(model.getText()) + 1),
						"model ID: " + model.getID()
				));
			} else {
				newList.add(viewModel);
			}
		}

		mPayloadItems.clear();
		mPayloadItems.addAll(newList);

		return mPayloadItems;
	}

	public List<? extends ViewModel> getLoadMoreItems() {
		final int size = mLoadMoreItems.size();
		for (int i = size; i < size + 30; i++) {
			mLoadMoreItems.add(new LoadMoreFragment.SimpleViewModel(String.valueOf(i)));
		}
		return mLoadMoreItems;
	}

	public void getLoadMoreItems(final Listener listener) {
		mExecutor.execute(() -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			final int size = mLoadMoreItems.size();
			for (int i = size; i < size + 30; i++) {
				mLoadMoreItems.add(new LoadMoreFragment.SimpleViewModel(String.valueOf(i)));
			}
			listener.onChanged(new ArrayList<>(mLoadMoreItems));
		});
	}

	public interface Listener {

		void onChanged(List<? extends ViewModel> list);
	}
}