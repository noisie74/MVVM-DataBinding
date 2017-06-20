package michael.com.tasksjavakotlin.java.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import michael.com.tasksjavakotlin.BR;
import michael.com.tasksjavakotlin.java.data.DataManager;
import michael.com.tasksjavakotlin.java.model.Task;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Mikhail on 6/17/17.
 */

public class TaskViewModel extends BaseObservable {

    private LiveData<List<Task>> mtasks;
    private CompositeSubscription mSubscription;
    private DataManager mDataManager;
    private Context mContext;

    private int progress;
    public final ObservableList<Task> items = new ObservableArrayList<>();
    public final ObservableField<String> header = new ObservableField<>();


    public TaskViewModel(Context context, DataManager dataManager) {
        mContext = context.getApplicationContext();
        mDataManager = dataManager;
        mSubscription = new CompositeSubscription();
    }

    @Bindable
    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        notifyPropertyChanged(BR.progress);
    }

    public void start() {
        loadTasks(false);
    }

    public void loadTasks(boolean isLoading) {

        if (isLoading) {

            mSubscription.add(mDataManager.getTasks()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<Task>>() {
                        @Override
                        public void onCompleted() {
                            setProgress(View.INVISIBLE);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("ViewModel", e.toString());
                        }

                        @Override
                        public void onNext(List<Task> tasks) {
                            items.addAll(tasks);
                            Log.d("ViewModel", tasks.get(0).getTaskTitle());
                        }
                    })
            );
        }

    }

}
