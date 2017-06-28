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
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(Context context) {
        return new DataManager(context);
    }
}
