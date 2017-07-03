package michael.com.tasksjavakotlin.features.ui;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import michael.com.tasksjavakotlin.features.model.Task;

/**
 * Created by mborisovskiy on 6/20/17.
 */

public class TasksListBindings {

    @SuppressWarnings("unchecked")
    @BindingAdapter("app:items")
    public static void setItems(RecyclerView view, List<Task> items) {
        TaskAdapter adapter = (TaskAdapter) view.getAdapter();
        if (adapter != null)
        {
            adapter.replaceData(items);
        }
    }
}
