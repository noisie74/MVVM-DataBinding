package michael.com.tasksjavakotlin.java.ui;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.util.Log;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import michael.com.tasksjavakotlin.BR;
import michael.com.tasksjavakotlin.java.data.DataManager;
import michael.com.tasksjavakotlin.java.model.ResponseObject;
import michael.com.tasksjavakotlin.java.model.Task;
import michael.com.tasksjavakotlin.java.network.TaskService;
import retrofit2.Response;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Mikhail on 6/17/17.
 */

public class TaskViewModel extends BaseObservable {

    private CompositeSubscription mSubscription;
//    private DataManager mDataManager;
    private Context mContext;
    @Inject DataManager dataManager;


    private int progress;
    private final ObservableField<Task> mTaskObservable = new ObservableField<>();
    public final ObservableList<Task> items = new ObservableArrayList<>();
    public final ObservableField<String> header = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> snackBar = new ObservableField<>();

    public TaskViewModel(Context context, DataManager manager) {
//        getAppComponent().getAppComponent().inject(this);
        mContext = context.getApplicationContext();
        dataManager = manager;
        mSubscription = new CompositeSubscription();
    }

//    private TasksApplication getAppComponent() {
//        return ((TasksApplication) getAppComponent());
//    }

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
                    }

                    @Override
                    public void onError(Throwable error) {
                        snackBar.set("Unable to save new Task");
                    }
                }));
    }


    public void taskClicked(Task task) {
        changeTaskStatus(task);
        updateTask(task);
    }

    private void updateTask(Task task) {
        mSubscription.add(TaskService.networkCall().updateTask(task.getId(), task)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<Response<ResponseObject>>() {
                    @Override
                    public void onSuccess(Response<ResponseObject> updatedTask) {
                    }

                    @Override
                    public void onError(Throwable error) {
                        snackBar.set("Unable to update task");
                    }
                })
        );
    }

    private void changeTaskStatus(Task task) {
        checkNotNull(task);
        if (task.isCompleted()) {
            task.setIsCompleted(false);
            snackBar.set(task.getTaskTitle() + " todo!");
        } else {
            task.setIsCompleted(true);
            snackBar.set(task.getTaskTitle() + " done!");
        }
    }

    public String getSnackbarText() {
        return snackBar.get();
    }

    private void setHeaderText(String text) {
        header.set(text);
    }
}
