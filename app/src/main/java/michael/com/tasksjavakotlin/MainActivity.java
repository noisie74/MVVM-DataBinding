package michael.com.tasksjavakotlin;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import michael.com.tasksjavakotlin.features.data.DataManager;
import michael.com.tasksjavakotlin.features.ui.TaskFragment;
import michael.com.tasksjavakotlin.features.ui.TaskViewModel;

public class MainActivity extends AppCompatActivity {

    @Inject DataManager dataManager;
    @Inject Context context;
    private TaskViewModel viewModel;
    private TaskFragment taskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();

        if (savedInstanceState == null) {
            taskFragment = createFragment();
        }

        TasksApplication.getApplication().getAppComponent().inject(this);

        viewModel = new TaskViewModel(context, dataManager);

        if (viewModel != null) {
            taskFragment.setViewModel(viewModel);
        }

    }

    private TaskFragment createFragment() {
        TaskFragment taskFragment = (TaskFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        if (taskFragment == null) {
            taskFragment = TaskFragment.newInstance();
            initFragment(taskFragment);
        }
        return taskFragment;
    }

    private void initFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.commit();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}