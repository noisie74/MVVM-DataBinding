package michael.com.tasksjavakotlin.java.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.BaseObservable;

import java.util.List;

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
    Context mContext;


    public TaskViewModel(Context context, DataManager dataManager) {

        mContext = context.getApplicationContext();
        mDataManager = dataManager;
        mSubscription = new CompositeSubscription();

    }


    public void start() {
        loadTasks(true);
    }

    public void loadTasks(boolean isLoading) {

        if (isLoading) {

            mSubscription.add(mDataManager.getTasks()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<Task>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(List<Task> tasks) {

                        }
                    })
            );
        }

    }

    private void tasksListResponse() {

    }
}
