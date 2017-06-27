package michael.com.tasksjavakotlin;

import android.app.Application;

import michael.com.tasksjavakotlin.java.data.DataManager;
import michael.com.tasksjavakotlin.java.di.ApplicationModule;
import michael.com.tasksjavakotlin.java.di.DataComponent;
import michael.com.tasksjavakotlin.java.di.DataModule;

/**
 * Created by mborisovskiy on 6/26/17.
 */

public class TasksApplication extends Application {

    private DataComponent mDataComponent;

//    private ApplicationModule appModule;
//    private DataModule dataModule;
//    private DataComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mDataComponent = DaggerDataComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();

    }

    public DataComponent getDataManagerComponent() {
        return mDataComponent;
    }

    public static TasksApplication getApplication() {
        return new TasksApplication();
    }

//    public DataComponent getComponent() {
//        return appComponent;
//    }
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


