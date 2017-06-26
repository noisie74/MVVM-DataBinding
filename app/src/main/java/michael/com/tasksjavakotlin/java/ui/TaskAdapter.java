package michael.com.tasksjavakotlin.java.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(Task task);
    }

    public TaskAdapter(List<Task> tasks,
                       DataManager dataManager,
                       TaskViewModel taskViewModel,
                       OnItemClickListener listener) {
        mDataManager = dataManager;
        mTasksViewModel = taskViewModel;
        mListener = listener;
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
        final Task mTask = mTasks.get(position);
        bindTask(taskItemBinding, mTask);
        setTaskClickListener(taskItemBinding, mTask);
        taskItemBinding.executePendingBindings();
    }

    private void setTaskClickListener(TaskItemBinding taskItemBinding, final Task task) {
        taskItemBinding.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(task);
                Log.d("Adapter", task.getTaskTitle() + " Clicked");
            }
        });
    }

    private void bindTask(TaskItemBinding taskItemBinding, Task task) {
        String title = task.getTaskTitle();
        boolean taskCompleted = task.isCompleted();
        taskItemBinding.taskTitle.setText(title);

        if (taskCompleted) {
            taskItemBinding.complete.setChecked(true);
        } else {
            taskItemBinding.complete.setChecked(false);
        }
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
