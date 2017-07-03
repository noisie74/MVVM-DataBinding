package michael.com.tasksjavakotlin.features.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import michael.com.tasksjavakotlin.features.network.TaskApi;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mborisovskiy on 6/26/17.
 */

@Module
public class NetworkModule {

    private String url;

    public NetworkModule(String url) {
        this.url = url;
    }

    @Provides
    @Singleton
    TaskApi provideApiService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(TaskApi.class);
    }
}
