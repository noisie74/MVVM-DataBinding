package michael.com.tasksjavakotlin.java.ui;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;

/**
 * Created by mborisovskiy on 6/20/17.
 */

public class TaskItemViewModel extends BaseObservable {

    private Context mContext;
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<Boolean> completed = new ObservableField<>();

    public TaskItemViewModel(Context context) {
        mContext = context.getApplicationContext();
    }


}
