package michael.com.tasksjavakotlin.java.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import michael.com.tasksjavakotlin.java.data.DataManager;

/**
 * Created by mborisovskiy on 6/26/17.
 */

@Module
public class DataModule {

    private Application app;
//    private ApplicationModule applicationModule;

//    public DataModule(Application app) {
//        this.app = app;
//    }
//
//    @Provides
//    @Singleton
//    Context provideContext() {
//        return app;
//    }
//
//    @Provides
//    @Singleton
//    Retrofit networkCall() {
//
//        return new Retrofit.Builder()
//                .baseUrl(Constants.BASE_URL)
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }
//
//    @Provides
//    @Singleton
//    TaskApi provideApiService(Retrofit retrofit) {
//        return networkCall().create(TaskApi.class);
//    }

    @Provides
    @Singleton
    DataManager provideDataManager(Context context) {
        return DataManager.provideData(context.getApplicationContext());
    }
}
