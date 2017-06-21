package michael.com.tasksjavakotlin.java.ui;

import android.databinding.DataBindingUtil;
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
import java.util.List;

import michael.com.tasksjavakotlin.R;
import michael.com.tasksjavakotlin.databinding.FragmentMainBinding;
import michael.com.tasksjavakotlin.java.model.Task;

/**
 * Created by Mikhail on 6/17/17.
 */

public class TaskFragment extends Fragment {

    private TaskAdapter mAdapter;
    private FragmentMainBinding binding;
    private TaskViewModel mViewModel;
    private Toolbar mToolbar;
    private FloatingActionButton mButtonSave;
    RecyclerView recyclerView;
    List<Task> list;



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

        mAdapter = new TaskAdapter(new ArrayList<Task>());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(mAdapter);
//        mAdapter.notifyDataSetChanged();
//        initView(rootView);
//        setToolBar();
        list = mViewModel.getTaskList();

        return binding.getRoot();
    }


//    @Override
//    public void onResume() {
//        super.onResume();
//        mViewModel.start();
//    }

//    private void initView(View view) {
//        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        mButtonSave = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
//
//    }


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

//    private void setToolBar() {
//        mToolbar.inflateMenu(R.menu.menu_main);
//        setHasOptionsMenu(true);
//        mToolbar.setTitle(R.string.app_name);
//        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                int id = item.getItemId();
//
//                if (id == R.id.all) {
//                    //TODO
//                }
//                if (id == R.id.completed) {
//                    //TODO
//                }
//                return true;
//            }
//        });
//    }

}
