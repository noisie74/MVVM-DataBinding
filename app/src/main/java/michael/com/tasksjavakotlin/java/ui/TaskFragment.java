package michael.com.tasksjavakotlin.java.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.function.ToDoubleBiFunction;

import michael.com.tasksjavakotlin.R;

/**
 * Created by Mikhail on 6/17/17.
 */

public class TaskFragment extends Fragment {

    Toolbar mToolbar;
    FloatingActionButton mButtonSave;


    public TaskFragment() {

    }

    public static TaskFragment newInstance() {
        return new TaskFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        initView(rootView);
        setToolBar();

        return rootView;
    }

    private void initView(View view) {
        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mButtonSave = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);

    }

    private void setToolBar() {
        mToolbar.inflateMenu(R.menu.menu_main);
        setHasOptionsMenu(true);
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.all) {
                    //TODO
                }
                if (id == R.id.completed) {
                    //TODO
                }
                return true;
            }
        });
    }

}
