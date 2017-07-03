package michael.com.tasksjavakotlin.java.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import michael.com.tasksjavakotlin.TasksApplication;
import michael.com.tasksjavakotlin.java.model.Response;
import michael.com.tasksjavakotlin.java.model.Task;
import michael.com.tasksjavakotlin.java.network.TaskApi;
import rx.Observable;
import rx.Single;
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
                .map(new Func1<Response, List<Task>>() {
                    @Override
                    public List<Task> call(Response responseObject) {
                        List<Task> response = responseObject.getTasksResponse();
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
                .flatMap(new Func1<Response, Observable<Task>>() {
                    @Override
                    public Observable<Task> call(Response responseObject) {
                        return Observable.from(responseObject.getTasksResponse());
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
                .map(new Func1<Response, Task>() {
                    @Override
                    public Task call(Response newTask) {

                        String taskId = newTask.getNewTaskId();
                        String taskTitle = newTask.getNewTaskTitle();
                        boolean taskStatus = newTask.getNewTaskStatus();

                        return new Task(taskId, taskTitle, taskStatus);
                    }
                });
    }

}
