package michael.com.tasksjavakotlin.features.ui;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.util.Log;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import michael.com.tasksjavakotlin.BR;
import michael.com.tasksjavakotlin.TasksApplication;
import michael.com.tasksjavakotlin.features.data.DataManager;
import michael.com.tasksjavakotlin.features.model.Response;
import michael.com.tasksjavakotlin.features.model.Task;
import michael.com.tasksjavakotlin.features.network.TaskApi;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Mikhail on 6/17/17.
 */

public class TaskViewModel extends BaseObservable {

    @Inject DataManager dataManager;
    @Inject TaskApi taskApi;
    private CompositeSubscription mSubscription;
    private Context mContext;
    private int progress;
    public final ObservableList<Task> items = new ObservableArrayList<>();
    public final ObservableField<String> header = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> snackBar = new ObservableField<>();
    public final ObservableField<String> taskTitle = new ObservableField<>();
    public final ObservableField<Boolean> completedCheckBox = new ObservableField<>();
    private final ObservableField<Task> mTaskObservable = new ObservableField<>();

    public TaskViewModel(Context context, DataManager manager) {
        mContext = context.getApplicationContext();
        dataManager = manager;
        mSubscription = new CompositeSubscription();

        TasksApplication.getApplication().getAppComponent().inject(this);

        mTaskObservable.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                Task task = mTaskObservable.get();
                if (task != null) {
                    taskTitle.set(task.getTaskTitle());
                    if (task.isCompleted()) {
                        completedCheckBox.set(true);
                    } else {
                        completedCheckBox.set(false);
                    }
                }
            }
        });
    }

    public void setTask(Task task) {
        mTaskObservable.set(task);
    }

    @Bindable
    public boolean getCompleted() {
        return mTaskObservable.get().isCompleted();
    }

    public void setCompleted(boolean completed) {
        Task task = mTaskObservable.get();
        task.setCompleted(completed);
    }

    @Bindable
    public String getTitleForList() {
        if (mTaskObservable.get() == null) {
            return "No data";
        }
        return mTaskObservable.get().getTaskTitle();
    }

    @Bindable
    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        notifyPropertyChanged(BR.progress);
    }

    public void start() {
        loadTasks(false);
    }

    public void stop() {
        mSubscription.clear();
    }

    public void loadTasks(boolean isLoading) {

        if (isLoading) {
            getTaskList();
        }
    }

    private void getTaskList() {
        mSubscription.add(dataManager.getTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Task>>() {
                    @Override
                    public void call(List<Task> tasks) {
                        items.clear();
                        items.addAll(tasks);
                        setHeaderText("All tasks");
                        setProgress(View.INVISIBLE);
                        Log.d("Viewmodel: ", items.get(0).getTaskTitle());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        snackBar.set("Unable to load Tasks");
                    }
                })
        );
    }

    public void loadCompletedTasks() {
        mSubscription.add(dataManager.getCompletedTasks()
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Task>>() {
                    @Override
                    public void call(List<Task> tasks) {
                        items.clear();
                        items.addAll(tasks);
                        setHeaderText("Completed tasks");
                        setProgress(View.INVISIBLE);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        snackBar.set("Unable to load Tasks");
                    }
                })
        );
    }

    public void saveTask(String taskTitle) {

        Task task = new Task(taskTitle);

        if (!task.isEmpty()) {
            createTask(task);
        } else {
            snackBar.set("Task cannot be empty!");
        }
    }

    private void createTask(Task task) {
        mSubscription.add(dataManager.saveTask(task)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<Task>() {
                    @Override
                    public void onSuccess(Task newTask) {
                        items.add(newTask);
                        snackBar.set(newTask.getTaskTitle() + " saved");
                        title.set("");
                    }

                    @Override
                    public void onError(Throwable error) {
                        snackBar.set("Unable to save new Task");
                    }
                }));
    }


    public void onTaskClicked(Task task) {
        changeTaskStatus(task);
        updateTask(task);
    }

    private void updateTask(Task task) {
        mSubscription.add(taskApi.updateTask(task.getId(), task)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<Response>() {
                    @Override
                    public void onSuccess(Response updatedTask) {
                        notifyChange();
                    }

                    @Override
                    public void onError(Throwable error) {
                        snackBar.set("Unable to update task");
                    }
                })
        );
    }

    private void changeTaskStatus(Task task) {
        if (task.isCompleted()) {
            snackBar.set(task.getTaskTitle() + " - done!");
        } else {
            snackBar.set(task.getTaskTitle() + " - todo!");
        }
    }

    public void removeTask(String taskID) {
        mSubscription.add(taskApi.deleteTask(taskID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Response>() {
                    @Override
                    public void call(Response response) {

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        snackBar.set("unable to delete task");
                    }
                })
        );

    }

    public String getSnackbarText() {
        return snackBar.get();
    }

    private void setHeaderText(String text) {
        header.set(text);
    }
}
