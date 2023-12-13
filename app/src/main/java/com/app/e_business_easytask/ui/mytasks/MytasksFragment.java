package com.app.e_business_easytask.ui.mytasks;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.e_business_easytask.R;
import com.app.e_business_easytask.Task;
import com.app.e_business_easytask.TaskAdapter;
import com.app.e_business_easytask.database.TaskDataSource;

import java.util.List;

public class MytasksFragment extends Fragment {


    private TaskAdapter createdTasksAdapter;
    private TaskDataSource taskDataSource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mytasks, container, false);

        // Initialize RecyclerView and adapters
        RecyclerView createdTasksRecyclerView = view.findViewById(R.id.recycler_created_tasks);
        createdTasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        createdTasksAdapter = new TaskAdapter();
        createdTasksRecyclerView.setAdapter(createdTasksAdapter);

        // Set the icon click listener in the adapter
        createdTasksAdapter.setOnItemClickListener(this::showTaskDetailsPopup);
        createdTasksRecyclerView.setAdapter(createdTasksAdapter);
        setupItemClick();

        // Initialize data source
        taskDataSource = new TaskDataSource(getContext());
        taskDataSource.open();

        taskDataSource.logAllTasks();

        // Load data from the database and set it in the adapter
        List<Task> createdTasks = taskDataSource.getAllTasks();
        Log.d("MytasksFragment", "Number of tasks loaded from database: " + createdTasks.size());
        createdTasksAdapter.setTaskList(createdTasks);

        // Set the icon click listener in the adapter
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Close the data source when the fragment is destroyed
        taskDataSource.close();
    }

    // Add this method to handle item clicks
    private void setupItemClick() {
        createdTasksAdapter.setOnItemClickListener(this::showTaskDetailsPopup);
    }
    private void showTaskDetailsPopup(Task task) {
        // Open the TaskDetailsDialogFragment
        TaskDetailsDialogFragment dialogFragment = new TaskDetailsDialogFragment(task);
        dialogFragment.show(getChildFragmentManager(), "task_details");
    }
}