package michael.com.tasksjavakotlin.java.di;

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

    @Provides
    @Singleton
    DataManager provideDataManager(Context context) {
        return new DataManager(context.getApplicationContext());
    }
}
