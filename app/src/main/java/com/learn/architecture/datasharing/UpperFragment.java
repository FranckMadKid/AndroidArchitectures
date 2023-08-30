package com.learn.architecture.datasharing;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learn.architecture.R;
import com.learn.architecture.databinding.FragmentCountBinding;
import com.learn.architecture.generated.callback.OnClickListener;

public class UpperFragment extends Fragment {
    private FragmentCountBinding binding;

    private SharedViewModel viewmodel;

    public UpperFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCountBinding.inflate(inflater, container, false);
        //viewmodel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        viewmodel = new ViewModelProvider(this).get(SharedViewModel.class);

        binding.getRoot().setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light, getActivity().getTheme()));
        binding.incrButton.setText("INCREMENT FROM UPPER FRAGMENT");

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