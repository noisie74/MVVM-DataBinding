package michael.com.tasksjavakotlin;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import michael.com.tasksjavakotlin.java.data.DataManager;
import michael.com.tasksjavakotlin.java.ui.TaskFragment;
import michael.com.tasksjavakotlin.java.ui.TaskViewModel;

public class MainActivity extends AppCompatActivity {

    TaskViewModel viewModel;
    TaskFragment taskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            taskFragment = createFragment();
        }
        viewModel = new TaskViewModel(getApplicationContext(), DataManager.provideData(getApplicationContext()));
        taskFragment.setViewModel(viewModel);

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
}