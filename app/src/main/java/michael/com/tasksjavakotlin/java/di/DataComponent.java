package michael.com.tasksjavakotlin.java.di;

import javax.inject.Singleton;

import dagger.Component;
import michael.com.tasksjavakotlin.java.data.DataManager;
import michael.com.tasksjavakotlin.java.network.TaskApi;
import michael.com.tasksjavakotlin.java.ui.TaskFragment;
import michael.com.tasksjavakotlin.java.ui.TaskViewModel;
import retrofit2.Retrofit;

/**
 * Created by mborisovskiy on 6/26/17.
 */

@Singleton
@Component(modules = {DataModule.class, ApplicationModule.class, NetworkModule.class})
public interface DataComponent {

    Retrofit getRetrofit();

    TaskApi provideApi();

    DataManager getDataManager();

    void inject(DataManager dataManager);

    void inject(TaskViewModel viewModel);

    void inject(TaskFragment fragment);

}
