package com.app.e_business_easytask.ui.suche;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    // Hier eine Liste für alle Mock-Ergebnisse erstellen
    private List<Task> allMockTasks;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suche, container, false);

        RecyclerView searchedTasksRecyclerView = view.findViewById(R.id.recycler_searched_tasks);
        searchedTasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // RecyclerView für alle Mock-Ergebnisse
        RecyclerView existingTasksRecyclerView = view.findViewById(R.id.recycler_existing_tasks);
        existingTasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SearchView searchView = view.findViewById(R.id.search_bar);

        tasksAdapter = new TaskAdapter();
        searchedTasksRecyclerView.setAdapter(tasksAdapter);

        // Adapter für alle Mock-Ergebnisse
        TaskAdapter existingTasksAdapter = new TaskAdapter();
        existingTasksRecyclerView.setAdapter(existingTasksAdapter);

        taskDataSource = new TaskDataSource(getContext());
        taskDataSource.open();

        // Alle Mock-Ergebnisse laden und in der RecyclerView anzeigen
        allMockTasks = taskDataSource.getAllMockTasks();
        existingTasksAdapter.setTaskList(allMockTasks);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Sichtbarkeitseinstellungen
                searchedTasksRecyclerView.setVisibility(View.VISIBLE);
                existingTasksRecyclerView.setVisibility(View.GONE);

                // Suche nach Mock-Daten in der Datenbank
                List<Task> searchedTasks = taskDataSource.getSearchedMockTasks(query);
                tasksAdapter.setTaskList(searchedTasks);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Echtzeit-Suche
                List<Task> realTimeSearchResults = taskDataSource.getRealTimeSearchResults(newText);
                tasksAdapter.setTaskList(realTimeSearchResults);

                // Zeige die Ergebnisse in der searchedTasksRecyclerView an
                searchedTasksRecyclerView.setVisibility(View.VISIBLE);
                existingTasksRecyclerView.setVisibility(View.GONE);

                return true;
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        taskDataSource.close();
        allMockTasks = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupItemClick();

    }

    private void setupItemClick() {
        tasksAdapter.setOnItemClickListener(this::showTaskDetailsPopup);
    }

    private void showTaskDetailsPopup(Task task) {
        if (task != null) {
            TaskDetailsDialogFragment dialogFragment = new TaskDetailsDialogFragment(task);
            dialogFragment.show(getChildFragmentManager(), "task_details");
        }
    }

}
