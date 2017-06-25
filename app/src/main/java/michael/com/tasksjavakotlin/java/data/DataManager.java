package michael.com.tasksjavakotlin.java.data;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import michael.com.tasksjavakotlin.java.model.ResponseObject;
import michael.com.tasksjavakotlin.java.model.Task;
import michael.com.tasksjavakotlin.java.network.TaskService;
import retrofit2.Response;
import rx.Observable;
import rx.Single;
import rx.functions.Func1;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Mikhail on 6/18/17.
 */

public class DataManager {

    public static DataManager provideData(Context context) {
        checkNotNull(context);
        return new DataManager();
    }

    public Observable<List<Task>> getTasks() {

        final List<Task> tasks = new ArrayList<>();

        return TaskService.networkCall().getTasks()
                .map(new Func1<Response<ResponseObject>, List<Task>>() {
                    @Override
                    public List<Task> call(Response<ResponseObject> responseObjectResponse) {
                        List<Task> response = responseObjectResponse.body().getTasksResponse();
                        tasks.addAll(response);
                        return tasks;
                    }
                });
    }


    public Observable<List<Task>> getCompletedTasks() {
        return TaskService.networkCall().getTasks()
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
        return TaskService.networkCall().saveTask(task)
                .map(new Func1<Response<ResponseObject>, Task>() {
                    @Override
                    public Task call(Response<ResponseObject> responseObject) {

                        String taskId = responseObject.body().getNewTaskId();
                        String taskTitle = responseObject.body().getNewTaskTitle();
                        boolean taskStatus = responseObject.body().getNewTaskStatus();

                        Task responseTask = new Task(taskId,taskTitle,taskStatus);
                        Log.d("New Task: ", responseTask.toString());

                        return responseTask;
                    }
                });
    }
}
