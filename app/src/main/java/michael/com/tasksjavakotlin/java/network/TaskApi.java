package michael.com.tasksjavakotlin.java.network;


import michael.com.tasksjavakotlin.java.model.ResponseObject;
import michael.com.tasksjavakotlin.java.model.Task;
import retrofit2.Response;
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
    Observable<Response<ResponseObject>> getTasks();

    @POST("/tasks")
    Single<Response<ResponseObject>> saveTask(@Body Task task);

    @PATCH("/tasks/{id}")
    Single<Response<ResponseObject>> updateTask(@Path("id") String id, @Body Task task);

    @DELETE("/tasks/{id}")
    Single<ResponseObject> deleteTask(@Path("id") String id);

}
