package michael.com.tasksjavakotlin.java.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mborisovskiy on 6/26/17.
 */

@Module
public class ApplicationModule {

    private final Context mContext;
//    private final TasksApplication mApplication;

//    public ApplicationModule(Context context) {
//        mContext = context;
//    }

    public ApplicationModule(Context context) {
        mContext = context;
    }

//    public ApplicationModule(TasksApplication application) {
//        mApplication = application;
//    }

    @Provides
    Context provideContext() {
        return mContext.getApplicationContext();
    }
}
