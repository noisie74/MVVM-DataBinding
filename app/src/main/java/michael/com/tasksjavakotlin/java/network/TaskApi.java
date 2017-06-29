package michael.com.tasksjavakotlin.java.network;


import michael.com.tasksjavakotlin.java.model.Response;
import michael.com.tasksjavakotlin.java.model.Task;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;
import rx.Single;

/**
 * Created by Mikhail on 5/31/17.
 */

public interface TaskApi {

    @GET("/tasks")
    Observable<Response> getTasks();

    @POST("/tasks")
    Single<Response> saveTask(@Body Task task);

    @PATCH("/tasks/{id}")
    Single<Response> updateTask(@Path("id") String id, @Body Task task);

    @DELETE("/tasks/{id}")
    Single<Response> deleteTask(@Path("id") String id);

}
