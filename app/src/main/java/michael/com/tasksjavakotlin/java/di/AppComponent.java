package michael.com.tasksjavakotlin.java.di;

import javax.inject.Singleton;

import dagger.Component;
import michael.com.tasksjavakotlin.java.data.DataManager;
import michael.com.tasksjavakotlin.java.ui.TaskViewModel;

/**
 * Created by mborisovskiy on 6/26/17.
 */

@Singleton
@Component(modules = {DataModule.class})
public interface AppComponent {

    void inject(DataManager dataManager);
    void inject(TaskViewModel model);
}
