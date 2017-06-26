package michael.com.tasksjavakotlin;

import android.app.Application;

import michael.com.tasksjavakotlin.java.di.AppComponent;
import michael.com.tasksjavakotlin.java.di.ApplicationModule;
import michael.com.tasksjavakotlin.java.di.DataModule;

/**
 * Created by mborisovskiy on 6/26/17.
 */

public class TasksApplication extends Application {

    private ApplicationModule appModule;
    private DataModule dataModule;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerComponent.builder()
//                .appModule(new ApplicationModule(this))
                .dataModule(new DataModule(this))
                .build();

    }

    public static TasksApplication getApplication() {
        return new TasksApplication();
    }

    public AppComponent getComponent() {
        return appComponent;
    }

//    public ApplicationModule getAppModule() {
//        if (appModule == null) {
//            appModule = new ApplicationModule(this);
//        }
//        return appModule;
//    }

    public DataModule getDataModule() {
        if (dataModule == null) {
            dataModule = new DataModule(this);
        }
        return dataModule;
    }
}


