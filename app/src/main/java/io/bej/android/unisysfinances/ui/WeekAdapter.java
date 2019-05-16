package io.bej.android.unisysfinances.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import io.bej.android.unisysfinances.R;
import io.bej.android.unisysfinances.databinding.WeekItemBinding;
import io.bej.android.unisysfinances.model.WorkWeek;

public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.WeekViewHolder> {

    private static final String TAG = WeekAdapter.class.getSimpleName();

    List<? extends WorkWeek> mWeekList;

    @Nullable
    private final WeekClickCallback mWeekClickCallback;

    public interface WeekClickCallback {
        void onClick(WorkWeek week);
    }

    public WeekAdapter(@Nullable WeekClickCallback clickCallback) {
        mWeekClickCallback = clickCallback;
    }

    public void setWeekList(final List<? extends WorkWeek> weekList) {
        if (mWeekList == null) {
            mWeekList = weekList;
            notifyItemRangeInserted(0, weekList.size());
        } else {
            // TODO
        }
    }

    @NonNull
    @Override
    public WeekViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WeekItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.week_item, parent, false);
        binding.setCallback(mWeekClickCallback);
        return new WeekViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekViewHolder holder, int position) {
        holder.binding.setWeek(mWeekList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mWeekList == null ? 0 : mWeekList.size();
    }

    public class WeekViewHolder extends RecyclerView.ViewHolder {
        final WeekItemBinding binding;
        public WeekViewHolder(WeekItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
