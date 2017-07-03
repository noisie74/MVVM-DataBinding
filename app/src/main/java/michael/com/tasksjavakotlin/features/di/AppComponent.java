package michael.com.tasksjavakotlin.features.di;

import javax.inject.Singleton;

import dagger.Component;
import michael.com.tasksjavakotlin.MainActivity;
import michael.com.tasksjavakotlin.features.data.DataManager;
import michael.com.tasksjavakotlin.features.ui.TaskAdapter;
import michael.com.tasksjavakotlin.features.ui.TaskFragment;
import michael.com.tasksjavakotlin.features.ui.TaskViewModel;

/**
 * Created by mborisovskiy on 6/26/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface AppComponent {

    void inject(DataManager dataManager);

    void inject(TaskViewModel viewModel);

    void inject(TaskFragment fragment);

    void inject(MainActivity mainActivity);

    void inject(TaskAdapter adapter);

}
