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
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
}
