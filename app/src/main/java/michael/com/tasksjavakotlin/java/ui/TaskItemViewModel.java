package michael.com.tasksjavakotlin.java.ui;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableField;

import java.util.List;

import michael.com.tasksjavakotlin.java.data.DataManager;
import michael.com.tasksjavakotlin.java.model.Task;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mborisovskiy on 6/20/17.
 */

public class TaskItemViewModel extends BaseObservable {

    private CompositeSubscription mSubscription;
    private Context mContext;
    private DataManager mdataManager;
    private final ObservableField<Task> mTaskObservable = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<Boolean> completedCheckBox = new ObservableField<>();

    public TaskItemViewModel(Context context, DataManager dataManager) {
        mContext = context.getApplicationContext();
        mdataManager = dataManager;
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
