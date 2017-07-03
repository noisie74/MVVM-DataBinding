package michael.com.tasksjavakotlin;

import android.app.Application;

import michael.com.tasksjavakotlin.features.di.AppComponent;
import michael.com.tasksjavakotlin.features.di.ApplicationModule;
import michael.com.tasksjavakotlin.features.di.DaggerAppComponent;
import michael.com.tasksjavakotlin.features.di.NetworkModule;
import michael.com.tasksjavakotlin.features.util.Constants;

/**
 * Created by mborisovskiy on 6/26/17.
 */

public class TasksApplication extends Application {

    private AppComponent mComponent;
    private static TasksApplication app;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;

        initInjector();
    }

    public AppComponent getAppComponent() {
        return mComponent;
    }

    private void initInjector() {
        mComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule((app)))
                .networkModule(new NetworkModule(Constants.BASE_URL))
                .build();
    }

    public static TasksApplication getApplication() {
        return app;
    }

}


