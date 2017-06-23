package michael.com.tasksjavakotlin.java.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import michael.com.tasksjavakotlin.R;
import michael.com.tasksjavakotlin.databinding.TaskItemBinding;
import michael.com.tasksjavakotlin.java.data.DataManager;
import michael.com.tasksjavakotlin.java.model.Task;

/**
 * Created by Mikhail on 6/19/17.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.BindingHolder> {

    private List<Task> mTasks;
    private TaskViewModel mTasksViewModel;
    private TaskItemViewModel viewModel;
    private DataManager mDataManager;
    private Context context;


    public TaskAdapter(List<Task> tasks) {
        setList(tasks);
    }

    public TaskAdapter(List<Task> tasks,
                       DataManager dataManager,
                       TaskViewModel taskViewModel) {
        mDataManager = dataManager;
        mTasksViewModel = taskViewModel;
        setList(tasks);
    }

    public void replaceData(List<Task> tasks) {
        setList(tasks);
        notifyDataSetChanged();
    }

    private void setList(List<Task> tasks) {
        mTasks = tasks;

    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        TaskItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.task_item, parent, false);

        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, final int position) {

        TaskItemBinding taskItemBinding = holder.getBinding();
//
//        TaskItemViewModel viewModel = taskItemBinding.getViewmodel();
//
//        if (viewModel == null) {
//            viewModel = new TaskItemViewModel(context, DataManager.provideData(context.getApplicationContext()));
//            taskItemBinding.setViewmodel(viewModel);
//        }

        Task data = mTasks.get(position);

        String title = data.getTaskTitle();
        boolean taskCompleted = data.isCompleted();


        taskItemBinding.taskTitle.setText(title);

        if (taskCompleted) {
            taskItemBinding.complete.setChecked(true);
        } else {
            taskItemBinding.complete.setChecked(false);
        }

        taskItemBinding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {

        TaskItemBinding binding;

        public BindingHolder(TaskItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public TaskItemBinding getBinding() {
            return binding;
        }
    }

}
