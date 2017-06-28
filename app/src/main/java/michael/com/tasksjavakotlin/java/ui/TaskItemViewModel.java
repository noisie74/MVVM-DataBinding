package michael.com.tasksjavakotlin.java.ui;

import android.content.Context;
import android.databinding.Observable;
import android.databinding.ObservableField;

import michael.com.tasksjavakotlin.java.data.DataManager;
import michael.com.tasksjavakotlin.java.model.Task;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mborisovskiy on 6/20/17.
 */

public class TaskItemViewModel extends TaskViewModel {

    private CompositeSubscription mSubscription;
    private Context mContext;
    private DataManager mdataManager;
    private final ObservableField<Task> mTaskObservable = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<Boolean> completedCheckBox = new ObservableField<>();

    public TaskItemViewModel(Context context, DataManager dataManager) {
        super(context,dataManager);
        mSubscription = new CompositeSubscription();

        mTaskObservable.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                Task task = mTaskObservable.get();
                if (task != null) {
                    title.set(task.getTaskTitle());
                    if (task.isCompleted()) {
                        completedCheckBox.set(true);
                    } else {
                        completedCheckBox.set(false);
                    }
                }
            }
        });
    }

}
