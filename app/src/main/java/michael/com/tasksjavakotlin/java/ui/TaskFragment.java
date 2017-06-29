package michael.com.tasksjavakotlin.java.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;

import javax.inject.Inject;

import michael.com.tasksjavakotlin.R;
import michael.com.tasksjavakotlin.TasksApplication;
import michael.com.tasksjavakotlin.databinding.FragmentMainBinding;
import michael.com.tasksjavakotlin.java.data.DataManager;
import michael.com.tasksjavakotlin.java.model.Task;
import michael.com.tasksjavakotlin.java.util.SnackbarUtils;

/**
 * Created by Mikhail on 6/17/17.
 */

public class TaskFragment extends Fragment {

    @Inject Context context;
    @Inject DataManager dataManager;
    private Observable.OnPropertyChangedCallback mSnackbarCallBack;
    private TaskAdapter mAdapter;
    private FragmentMainBinding mBinding;
    private TaskViewModel mViewModel;

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

        TasksApplication.getApplication().getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        mBinding.setView(this);
        mBinding.setViewmodel(mViewModel);

        setHasOptionsMenu(true);

        mViewModel.setProgress(View.VISIBLE);
        mViewModel.loadTasks(true);

        setupAdapter();

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupSnackBar();
        setupFabButton();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewModel.stop();
    }

    private void setupAdapter() {

        RecyclerView recyclerView = mBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        mAdapter = new TaskAdapter(new ArrayList<Task>(0), new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                mViewModel.onTaskClicked(task);
                Log.d("Fragment", task.getTaskTitle() + " Clicked");

            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    private void setupFabButton() {
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTask = mBinding.editText.getText().toString();
                mViewModel.saveTask(newTask);
                hideKeyboard(v);
            }
        });
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
                mViewModel.setProgress(View.VISIBLE);
                mViewModel.loadTasks(true);
                break;
            case R.id.completed:
                mViewModel.setProgress(View.VISIBLE);
                mViewModel.loadCompletedTasks();
                break;
        }
        return true;
    }

    private void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

}
