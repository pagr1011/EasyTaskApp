package com.app.e_business_easytask.ui.mytasks;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.app.e_business_easytask.Task;

public class TaskDetailsDialogFragment extends DialogFragment {

    private final Task selectedTask;

    public TaskDetailsDialogFragment(Task task) {
        this.selectedTask = task;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Task Details")
                .setMessage(getTaskDetailsString())
                .setPositiveButton("OK", (dialog, which) -> dismiss());

        return builder.create();
    }

    //Hier werden die Informationen beim Anklicken oder nicht anklicken beim Recycler angezeigt
    private String getTaskDetailsString() {
        return "Dienstleistungsart: " + selectedTask.getServiceType() +
                "\nAuftragsdetails: " + selectedTask.getJobDetails() +
                "\nDatum: " + selectedTask.getFormattedDate() +
                "\nUhrzeit: " + selectedTask.getFormattedTime() +
                "\nStraße: " + selectedTask.getStreet() +
                "\nHaus Nr.: " + selectedTask.getHouseNumber() +
                "\nHausnummer: " + selectedTask.getZipCode() +
                "\nBudget: $" + selectedTask.getBudget() +
                "\nDauer:" + selectedTask.getDuration() + " " + selectedTask.getDurationUnit();
    }
}
