package michael.com.tasksjavakotlin.java.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import michael.com.tasksjavakotlin.java.data.DataManager;
import michael.com.tasksjavakotlin.java.network.TaskApi;
import michael.com.tasksjavakotlin.java.util.Constants;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mborisovskiy on 6/26/17.
 */

@Module
public class DataModule {

    private Application app;

    public DataModule(Application app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return app;
    }

    @Provides
    @Singleton
    Retrofit networkCall() {

        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    TaskApi provideApiService(Retrofit retrofit) {
        return networkCall().create(TaskApi.class);
    }

    @Provides
    @Singleton
    DataManager provideDataManager(Application app) {
        return DataManager.provideData(app);
    }
}
