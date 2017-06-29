package michael.com.tasksjavakotlin.java.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import michael.com.tasksjavakotlin.R;
import michael.com.tasksjavakotlin.TasksApplication;
import michael.com.tasksjavakotlin.databinding.TaskItemBinding;
import michael.com.tasksjavakotlin.java.data.DataManager;
import michael.com.tasksjavakotlin.java.model.Task;
import michael.com.tasksjavakotlin.java.util.SnackbarUtils;

/**
 * Created by Mikhail on 6/19/17.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.BindingHolder> {

    private List<Task> mTasks;
    private Context context;
    private OnItemClickListener mListener;
    private TaskViewModel viewModel;
    @Inject DataManager dataManager;
    @Inject Context mContext;

    public interface OnItemClickListener {
        void onItemClick(Task task);
    }

    public TaskAdapter(List<Task> tasks, OnItemClickListener listener) {
        mListener = listener;
        setList(tasks);

        TasksApplication.getApplication().getAppComponent().inject(this);
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

        setUpViewModel(taskItemBinding, mTask);
        setTaskClickListener(taskItemBinding, mTask);
        onLongClickTaskDelete(taskItemBinding, position);
        taskItemBinding.executePendingBindings();

    }

    private void setUpViewModel(TaskItemBinding taskItemBinding, Task task) {
        viewModel = new TaskViewModel(mContext, dataManager);
        taskItemBinding.setViewmodel(viewModel);
        viewModel.setTask(task);
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

    private void onLongClickTaskDelete(TaskItemBinding taskItemBinding, final int taskPosition) {

        final String taskTitle = mTasks.get(taskPosition).getTaskTitle();

        taskItemBinding.taskItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                delete(taskPosition);
                SnackbarUtils.showSnackBar(v, taskTitle + " deleted");
                return false;
            }
        });
    }

    private void delete(int position) {
        String taskId = mTasks.get(position).getId();
        mTasks.remove(position);
        viewModel.removeTask(taskId);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public class BindingHolder extends RecyclerView.ViewHolder {

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
