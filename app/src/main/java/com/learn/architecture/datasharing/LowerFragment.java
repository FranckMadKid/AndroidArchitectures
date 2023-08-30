package com.learn.architecture.datasharing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.learn.architecture.R;
import com.learn.architecture.databinding.FragmentCountBinding;

public class LowerFragment extends Fragment {

    private FragmentCountBinding binding;

    private SharedViewModel viewmodel;

    public LowerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCountBinding.inflate(inflater, container, false);
        viewmodel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        binding.getRoot().setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark, getActivity().getTheme()));
        binding.incrButton.setText("INCREMENT FROM LOWER FRAGMENT");

        addListeners();
        observeViewModel();

        return binding.getRoot();
    }


    private void addListeners() {
        binding.incrButton.setOnClickListener(view -> viewmodel.incrementCounter());
    }

    private void observeViewModel() {
        viewmodel.getCounter().observe(getViewLifecycleOwner(),
                counter -> binding.counterTV.setText(counter.toString()));
    }
}