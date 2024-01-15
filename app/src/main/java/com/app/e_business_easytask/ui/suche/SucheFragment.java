package com.app.e_business_easytask.ui.suche;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.e_business_easytask.R;
import com.app.e_business_easytask.Task;
import com.app.e_business_easytask.TaskAdapter;
import com.app.e_business_easytask.database.TaskDataSource;
import com.app.e_business_easytask.ui.mytasks.TaskDetailsDialogFragment;

import java.util.List;

public class SucheFragment extends Fragment {

    private TaskAdapter tasksAdapter;
    private TaskDataSource taskDataSource;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suche, container, false);

        RecyclerView existingTasksRecyclerView = view.findViewById(R.id.recycler_existing_tasks);
        existingTasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        RecyclerView searchedTasksRecyclerView = view.findViewById(R.id.recycler_searched_tasks);
        searchedTasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        SearchView searchView = view.findViewById(R.id.search_bar);

        tasksAdapter = new TaskAdapter();
        existingTasksRecyclerView.setAdapter(tasksAdapter);
        searchedTasksRecyclerView.setAdapter(tasksAdapter);

        // Set the icon click listener in the adapter
        tasksAdapter.setOnItemClickListener(this::showTaskDetailsPopup);
        existingTasksRecyclerView.setAdapter(tasksAdapter);
        searchedTasksRecyclerView.setAdapter(tasksAdapter);
        setupItemClick();

        taskDataSource = new TaskDataSource(getContext());
        taskDataSource.open();

        List<Task> existingTasks = taskDataSource.getAllTasks();
        tasksAdapter.setTaskList(existingTasks);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                existingTasksRecyclerView.setVisibility(View.GONE);
                searchedTasksRecyclerView.setVisibility(View.VISIBLE);

                List<Task> searchedTasks = taskDataSource.getSearchedTasks(query);
                tasksAdapter.setTaskList(searchedTasks);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        taskDataSource.close();
    }

    private void setupItemClick() {
        tasksAdapter.setOnItemClickListener(this::showTaskDetailsPopup);
    }
    private void showTaskDetailsPopup(Task task) {
        // Open the TaskDetailsDialogFragment
        TaskDetailsDialogFragment dialogFragment = new TaskDetailsDialogFragment(task);
        dialogFragment.show(getChildFragmentManager(), "task_details");
    }
}