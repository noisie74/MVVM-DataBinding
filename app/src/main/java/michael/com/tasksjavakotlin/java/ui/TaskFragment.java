package michael.com.tasksjavakotlin.java.ui;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import michael.com.tasksjavakotlin.R;
import michael.com.tasksjavakotlin.databinding.FragmentMainBinding;
import michael.com.tasksjavakotlin.java.data.DataManager;
import michael.com.tasksjavakotlin.java.model.Task;
import michael.com.tasksjavakotlin.java.util.SnackbarUtils;

/**
 * Created by Mikhail on 6/17/17.
 */

public class TaskFragment extends Fragment {

    private Observable.OnPropertyChangedCallback mSnackbarCallBack;
    private TaskAdapter mAdapter;
    private FragmentMainBinding binding;
    private TaskViewModel mViewModel;
    private Toolbar mToolbar;
    private FloatingActionButton mButtonSave;
    private DataManager mdataManger;


    public TaskFragment() {
    }

    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    public void setViewModel(TaskViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mAdapter = new TaskAdapter(new ArrayList<Task>());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);

        binding.setView(this);
        binding.setViewmodel(mViewModel);

        mViewModel.loadTasks(true);
        setupAdapter();

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupSnackBar();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.start();
    }


    private void setupAdapter() {

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));

        mAdapter = new TaskAdapter(new ArrayList<Task>(0),
                DataManager.provideData(getContext().getApplicationContext()), mViewModel);
        recyclerView.setAdapter(mAdapter);
    }


    private void setupSnackBar() {
        mSnackbarCallBack = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                SnackbarUtils.showSnackBar(getView(), mViewModel.getSnackbarText());
            }
        };
        mViewModel.snackBar.addOnPropertyChangedCallback(mSnackbarCallBack);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.all:
                //TODO
                break;
            case R.id.completed:
                //TODO
                break;
        }
        return true;
    }

}
