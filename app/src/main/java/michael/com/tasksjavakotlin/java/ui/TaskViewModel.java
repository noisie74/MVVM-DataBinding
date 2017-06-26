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

/**
 * Created by Mikhail on 6/17/17.
 */

public class TaskViewModel extends BaseObservable {

    private CompositeSubscription mSubscription;
    private DataManager mDataManager;
    private Context mContext;

    private int progress;
    public final ObservableList<Task> items = new ObservableArrayList<>();
    public final ObservableField<String> header = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> snackBar = new ObservableField<>();

    private final ObservableField<Task> mTaskObservable = new ObservableField<>();


    public TaskViewModel(Context context, DataManager dataManager) {
        mContext = context.getApplicationContext();
        mDataManager = dataManager;
        mSubscription = new CompositeSubscription();
    }

    @Bindable
    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        notifyPropertyChanged(michael.com.tasksjavakotlin.BR.progress);
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
        mSubscription.add(mDataManager.getTasks()
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
        mSubscription.add(mDataManager.getCompletedTasks()
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

    public void saveTask(String text) {

        Task task = new Task(text);

        mSubscription.add(TaskService.networkCall().saveTask(task)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<Response<ResponseObject>>() {
                    @Override
                    public void onSuccess(Response<ResponseObject> value) {
                        String taskId = value.body().getNewTaskId();
                        String taskTitle = value.body().getNewTaskTitle();
                        boolean taskStatus = value.body().getNewTaskStatus();

                        Task responseTask = new Task(taskId, taskTitle, taskStatus);
                        items.add(responseTask);
                        Log.d("SaveTask ViewModel: ", value.toString());
                        snackBar.set(title + " saved");
                        title.set("");
                    }

                    @Override
                    public void onError(Throwable error) {
                        snackBar.set("Unable to save new Task");
                    }
                }));
//                .subscribe(new Action1<Response<ResponseObject>>() {
//                    @Override
//                    public void call(Response<ResponseObject> responseObject) {
//                        String taskId = responseObject.body().getNewTaskId();
//                        String taskTitle = responseObject.body().getNewTaskTitle();
//                        boolean taskStatus = responseObject.body().getNewTaskStatus();
//
//                        Task responseTask = new Task(taskId, taskTitle, taskStatus);
//                        items.add(responseTask);
//                        Log.d("SaveTask ViewModel: ", responseObject.toString());
//                        snackBar.set(title + " saved");
//                        title.set("");
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        snackBar.set("Unable to save new Task");
//                    }
//                }));


//                .subscribe(new Action1<Task>() {
//                    @Override
//                    public void call(Task task) {
//                        Log.d("SaveTask ViewModel: ", task.toString());
//                        items.add(task);
//                        snackBar.set(title + " saved");
//                        title.set("");
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        snackBar.set("Unable to save new Task");
//                    }
//                }));


//        if (!task.isEmpty()) {
//            mSubscription.add(mDataManager.saveTask(task)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Action1<Task>() {
//                        @Override
//                        public void call(Task task) {
//                            Log.d("SaveTask ViewModel: ", task.toString());
//                            items.add(task);
//                            snackBar.set(title + " saved");
//                            title.set("");
//                        }
//                    }, new Action1<Throwable>() {
//                        @Override
//                        public void call(Throwable throwable) {
//                            snackBar.set("Unable to save new Task");
//                        }
//                    }));
//        }

    }


    public String getSnackbarText() {
        return snackBar.get();
    }

    private void setHeaderText(String text) {
        header.set(text);
    }
}
