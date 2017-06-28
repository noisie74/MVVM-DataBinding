package michael.com.tasksjavakotlin.java.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import michael.com.tasksjavakotlin.TasksApplication;
import michael.com.tasksjavakotlin.java.model.ResponseObject;
import michael.com.tasksjavakotlin.java.model.Task;
import michael.com.tasksjavakotlin.java.network.TaskApi;
import retrofit2.Response;
import rx.Observable;
import rx.Single;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Mikhail on 6/18/17.
 */
public class DataManager {

    Context mContext;
    @Inject TaskApi taskApi;

    @Inject
    public DataManager(Context context) {
        mContext = context;

        TasksApplication.getApplication().getAppComponent().inject(this);
    }

    public Observable<List<Task>> getTasks() {

        final List<Task> tasks = new ArrayList<>();

        return taskApi.getTasks()
                .map(new Func1<Response<ResponseObject>, List<Task>>() {
                    @Override
                    public List<Task> call(Response<ResponseObject> responseObjectResponse) {
                        List<Task> response = responseObjectResponse.body().getTasksResponse();
                        tasks.addAll(response);
                        return tasks;
                    }
                }).takeUntil(new Func1<List<Task>, Boolean>() {
                    @Override
                    public Boolean call(List<Task> tasks) {
                        return tasks == null;
                    }
                });
    }


    public Observable<List<Task>> getCompletedTasks() {
        return taskApi.getTasks()
                .flatMap(new Func1<Response<ResponseObject>, Observable<Task>>() {
                    @Override
                    public Observable<Task> call(Response<ResponseObject> responseObject) {
                        return Observable.from(responseObject.body().getTasksResponse());
                    }
                })
                .filter(new Func1<Task, Boolean>() {
                    @Override
                    public Boolean call(Task task) {
                        return task.isCompleted();
                    }
                })
                .toList();
    }


    public Single<Task> saveTask(Task task) {
        return taskApi
                .saveTask(task)
                .map(new Func1<Response<ResponseObject>, Task>() {
                    @Override
                    public Task call(Response<ResponseObject> newTask) {

                        String taskId = newTask.body().getNewTaskId();
                        String taskTitle = newTask.body().getNewTaskTitle();
                        boolean taskStatus = newTask.body().getNewTaskStatus();

                        Task task = new Task(taskId, taskTitle, taskStatus);

                        return task;
                    }
                });
    }

    public Single<Task> deleteTask(final Task task) {
        String id = task.getId();

        taskApi.deleteTask(id, task)
                .doOnSuccess(new Action1<Response<ResponseObject>>() {
                    @Override
                    public void call(Response<ResponseObject> responseObjectResponse) {

                    }
                });
//                .map(new Func1<Response<ResponseObject>, Task>() {
//
//                    @Override
//                    public Task call(Response<ResponseObject> responseObject) {
//
//                        return responseObject.body().getTasksResponse().
//                    }
//                });
    }

}
