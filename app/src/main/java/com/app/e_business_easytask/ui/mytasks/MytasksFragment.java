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
        // Die Methode, die die Ansicht für dieses Fragment erstellt
        View view = inflater.inflate(R.layout.fragment_mytasks, container, false);

        // Initialisiert die RecyclerView und die Adapter
        RecyclerView createdTasksRecyclerView = view.findViewById(R.id.recycler_created_tasks);
        createdTasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        createdTasksAdapter = new TaskAdapter();
        createdTasksRecyclerView.setAdapter(createdTasksAdapter);

        createdTasksAdapter.setOnItemClickListener(this::showTaskDetailsPopup);
        createdTasksRecyclerView.setAdapter(createdTasksAdapter);
        setupItemClick();

        // Initialisiert die Datenquelle
        taskDataSource = new TaskDataSource(getContext());
        taskDataSource.open();

        // Protokolliert alle Aufgaben aus der Datenbank
        taskDataSource.logAllTasks();

        // Lädt Daten aus der Datenbank und setzt sie im Adapter
        List<Task> createdTasks = taskDataSource.getAllTasks();
        Log.d("MytasksFragment", "Number of tasks loaded from database: " + createdTasks.size());
        createdTasksAdapter.setTaskList(createdTasks);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Schließt die Datenquelle, wenn das Fragment zerstört wird
        taskDataSource.close();
    }

    // Methode zum Behandeln von Klicks auf Listenelemente
    private void setupItemClick() {
        createdTasksAdapter.setOnItemClickListener(this::showTaskDetailsPopup);
    }

    // Methode zum Anzeigen des Popups mit den Aufgabendetails
    private void showTaskDetailsPopup(Task task) {
        // Öffnet das TaskDetailsDialogFragment
        TaskDetailsDialogFragment dialogFragment = new TaskDetailsDialogFragment(task);
        dialogFragment.show(getChildFragmentManager(), "task_details");
    }
}