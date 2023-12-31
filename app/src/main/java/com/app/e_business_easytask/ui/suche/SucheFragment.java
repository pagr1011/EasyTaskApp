package com.app.e_business_easytask.ui.suche;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.e_business_easytask.databinding.FragmentSucheBinding;

public class SucheFragment extends Fragment {

    private FragmentSucheBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SucheViewModel sucheViewModel =
                new ViewModelProvider(this).get(SucheViewModel.class);

        binding = FragmentSucheBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}