package michael.com.tasksjavakotlin.features.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Mikhail on 6/21/17.
 */

public class SnackbarUtils {

    public static void showSnackBar(View v, String message) {
        if (v == null || message == null) {
            return;
        }
        Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show();
    }
}
