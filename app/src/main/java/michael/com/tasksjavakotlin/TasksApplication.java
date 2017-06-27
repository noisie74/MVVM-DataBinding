package michael.com.tasksjavakotlin;

import android.app.Application;

import michael.com.tasksjavakotlin.java.di.AppComponent;
import michael.com.tasksjavakotlin.java.di.ApplicationModule;
import michael.com.tasksjavakotlin.java.di.DaggerAppComponent;

/**
 * Created by mborisovskiy on 6/26/17.
 */

public class TasksApplication extends Application {

    private AppComponent mComponent;
    private static TasksApplication app;

//    private ApplicationModule appModule;
//    private DataModule dataModule;
//    private AppComponent appComponent;



    @Override
    public void onCreate() {
        super.onCreate();

        initInjector();
    }

    public AppComponent getAppComponent() {
        return mComponent;
    }

    private void initInjector() {
        mComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule((getApplicationContext())))
                .build();
    }

    public static TasksApplication getApplication() {
        return new TasksApplication();
    }

    public AppComponent getComponent() {
        return mComponent;
    }
//
////    public ApplicationModule getAppModule() {
////        if (appModule == null) {
////            appModule = new ApplicationModule(this);
////        }
////        return appModule;
////    }
//
//    public DataModule getDataModule() {
//        if (dataModule == null) {
//            dataModule = new DataModule(this);
//        }
//        return dataModule;
//    }
}


