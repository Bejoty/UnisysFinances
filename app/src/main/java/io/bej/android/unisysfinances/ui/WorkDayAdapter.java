package io.bej.android.unisysfinances.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.bej.android.unisysfinances.R;
import io.bej.android.unisysfinances.model.WorkDay;

public class WorkDayAdapter extends RecyclerView.Adapter<WorkDayAdapter.WorkDayViewHolder> {

    List<WorkDay> mWorkDayList;

    public WorkDayAdapter(List<WorkDay> workDayEntities) {
        mWorkDayList = workDayEntities;
    }

    @NonNull
    @Override
    public WorkDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.old_work_day_list_item, parent, false);

        return new WorkDayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkDayViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mWorkDayList.size();
    }

    public class WorkDayViewHolder extends RecyclerView.ViewHolder {

        TextView dateView;
        TextView mileageView;

        public WorkDayViewHolder(@NonNull View itemView) {
            super(itemView);

            dateView = itemView.findViewById(R.id.tv_date);
            mileageView = itemView.findViewById(R.id.tv_mileage);
        }

        public void bind(int position) {
            WorkDay day = mWorkDayList.get(position);
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            dateView.setText(format.format(day.getDate()));
            mileageView.setText(String.valueOf(day.getMiles()) + " miles");
        }
    }
}
