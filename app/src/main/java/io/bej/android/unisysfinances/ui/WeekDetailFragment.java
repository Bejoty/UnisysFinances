package io.bej.android.unisysfinances.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import io.bej.android.unisysfinances.R;
import io.bej.android.unisysfinances.databinding.WeekFragmentBinding;
import io.bej.android.unisysfinances.db.entity.WorkWeekEntity;
import io.bej.android.unisysfinances.viewmodel.WeekViewModel;

public class WeekDetailFragment extends Fragment {

    private static final String KEY_WEEK_ID = "week_id";

    private WeekFragmentBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.week_fragment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO - Simplify code after replacing Factory in WeekViewModel
        WeekViewModel.Factory factory = new WeekViewModel.Factory(
                getActivity().getApplication(), getArguments().getInt(KEY_WEEK_ID));

        final WeekViewModel model = ViewModelProviders.of(this, factory)
                .get(WeekViewModel.class);

        mBinding.setWeekViewModel(model);
        
        subscribeToModel(model);
    }

    private void subscribeToModel(WeekViewModel model) {
        model.getObservableWeek().observe(this, new Observer<WorkWeekEntity>() {
            @Override
            public void onChanged(WorkWeekEntity workWeekEntity) {
                model.setWeek(workWeekEntity);
            }
        });
    }

    public static WeekDetailFragment forWeek(int weekId) {
        WeekDetailFragment fragment = new WeekDetailFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_WEEK_ID, weekId);
        fragment.setArguments(args);
        return fragment;
    }
}
