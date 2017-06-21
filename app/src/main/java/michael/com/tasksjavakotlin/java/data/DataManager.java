package michael.com.tasksjavakotlin.java.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import michael.com.tasksjavakotlin.java.model.ResponseObject;
import michael.com.tasksjavakotlin.java.model.Task;
import michael.com.tasksjavakotlin.java.network.TaskService;
import retrofit2.Response;
import rx.Observable;
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
//                .flatMap(new Func1<Response<ResponseObject>, Observable<List<Task>>>() {
//                    @Override
//                    public Observable<List<Task>> call(Response<ResponseObject> responseObjectResponse) {
//                        List<Task> response = responseObjectResponse.body().getTasksResponse();
//                        tasks.addAll(response);
//                        return Observable.just(tasks);
//                    }
//                });
                .map(new Func1<Response<ResponseObject>, List<Task>>() {
                    @Override
                    public List<Task> call(Response<ResponseObject> responseObjectResponse) {
                        List<Task> response = responseObjectResponse.body().getTasksResponse();
                        tasks.addAll(response);
                        return tasks;
                    }
                });
    }

    public void saveTask() {


    }
}
