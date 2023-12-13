package com.app.e_business_easytask;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private static final List<Task> taskList = new ArrayList<>();

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void setTaskList(List<Task> tasks) {
        taskList.clear();
        taskList.addAll(tasks);
        notifyDataSetChanged();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView textViewServiceType;
        private final TextView textViewJobDetails;
        private final TextView textViewFormattedDate;
        private final TextView textViewFormattedTime;
        private final TextView textViewStreet;
        private final TextView textViewHouseNumber;
        private final TextView textViewZipCode;
        private final TextView textViewDuration;
        private final TextView textViewBudget;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize your Views for each element
            textViewServiceType = itemView.findViewById(R.id.textViewServiceType);
            textViewJobDetails = itemView.findViewById(R.id.textViewJobDetails);
            textViewFormattedDate = itemView.findViewById(R.id.textViewFormattedDate);
            textViewFormattedTime = itemView.findViewById(R.id.textViewFormattedTime);
            textViewStreet = itemView.findViewById(R.id.textViewStreet);
            textViewHouseNumber = itemView.findViewById(R.id.textViewHouseNumber);
            textViewZipCode = itemView.findViewById(R.id.textViewZipCode);
            textViewDuration = itemView.findViewById(R.id.textViewDuration);
            textViewBudget = itemView.findViewById(R.id.textViewBudget);

            // Set the click listener
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && itemClickListener != null) {
                Task clickedTask = taskList.get(position);
                itemClickListener.onItemClick(clickedTask);
            }
        }


        public void bind(Task task) {
            // Set the Task data in the Views
            textViewServiceType.setText(itemView.getContext().getString(R.string.service_type, task.getServiceType()));
            textViewJobDetails.setText(itemView.getContext().getString(R.string.job_details, task.getJobDetails()));
            textViewFormattedDate.setText(itemView.getContext().getString(R.string.date, task.getFormattedDate()));
            textViewFormattedTime.setText(itemView.getContext().getString(R.string.time, task.getFormattedTime()));
            textViewStreet.setText(itemView.getContext().getString(R.string.street, task.getStreet()));
            textViewHouseNumber.setText(itemView.getContext().getString(R.string.house_number, task.getHouseNumber()));
            textViewZipCode.setText(itemView.getContext().getString(R.string.zip_code, task.getZipCode()));
            textViewDuration.setText(itemView.getContext().getString(R.string.duration, task.getDuration(), task.getDurationUnit()));
            textViewBudget.setText(itemView.getContext().getString(R.string.budget, task.getBudget()));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Task task);
    }

    private static OnItemClickListener itemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }
}
