package io.bej.android.unisysfinances.ui;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.bej.android.unisysfinances.R;
import io.bej.android.unisysfinances.databinding.ListFragmentBinding;
import io.bej.android.unisysfinances.db.entity.WorkWeekEntity;
import io.bej.android.unisysfinances.viewmodel.WeekListViewModel;

public class WeekListFragment extends Fragment {

    public static final String TAG = WeekListFragment.class.getSimpleName();

    private WeekAdapter mWeekAdapter;

    private ListFragmentBinding mBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false);

        mWeekAdapter = new WeekAdapter(mWeekClickCallback);
        mBinding.rvWeeksList.setAdapter(mWeekAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final WeekListViewModel viewModel =
                ViewModelProviders.of(this).get(WeekListViewModel.class);

        subscribeUI(viewModel.getWeeks());
    }

    private void subscribeUI(LiveData<List<WorkWeekEntity>> liveData) {
        liveData.observe(this, weekList -> {
            if (weekList != null) {
                mBinding.setIsLoading(false);
                mWeekAdapter.setWeekList(weekList);
            } else {
                mBinding.setIsLoading(true);
            }
            mBinding.executePendingBindings();
        });
    }

    private final WeekAdapter.WeekClickCallback mWeekClickCallback = weekItem -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((MainActivity) getActivity()).show(weekItem);
        }
    };
}
