package michael.com.tasksjavakotlin.java.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mborisovskiy on 6/26/17.
 */

@Module
public class ApplicationModule {

    private Application app;

    public ApplicationModule(Application app) {
        this.app = app;
    }

//    @Provides
//    @Singleton
//    TasksApplication provideApplication() {
//        return (TasksApplication) app;
//    }

    @Provides
    @Singleton
    Context provideContext() {
        return app;
    }
}
