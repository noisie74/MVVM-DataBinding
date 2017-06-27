package michael.com.tasksjavakotlin.java.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import michael.com.tasksjavakotlin.MainActivity;
import michael.com.tasksjavakotlin.java.data.DataManager;
import michael.com.tasksjavakotlin.java.network.TaskApi;
import michael.com.tasksjavakotlin.java.ui.TaskFragment;
import michael.com.tasksjavakotlin.java.ui.TaskViewModel;

/**
 * Created by mborisovskiy on 6/26/17.
 */

@Singleton
@Component(modules = {DataModule.class, ApplicationModule.class, NetworkModule.class})
public interface AppComponent {

    TaskApi provideApi();

    DataManager getDataManager();

    Context getContext();

    void inject(DataManager dataManager);

    void inject(TaskViewModel viewModel);

    void inject(TaskFragment fragment);

    void inject(MainActivity mainActivity);

}
