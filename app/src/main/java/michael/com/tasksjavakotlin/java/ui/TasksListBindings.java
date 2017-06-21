package michael.com.tasksjavakotlin.java.ui;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import michael.com.tasksjavakotlin.java.model.Task;

/**
 * Created by mborisovskiy on 6/20/17.
 */

public class TasksListBindings {

    @SuppressWarnings("unchecked")
    @BindingAdapter("app:items")
    public static void setItems(RecyclerView view, List<Task> items) {
        TaskAdapter adapter = (TaskAdapter) view.getAdapter();
        if (adapter != null && items != null)
        {
            adapter.replaceData(items);
        }
    }
}
