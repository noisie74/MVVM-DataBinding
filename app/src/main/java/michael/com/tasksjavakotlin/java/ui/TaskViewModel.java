package michael.com.tasksjavakotlin.java.ui;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;
import java.util.List;

import michael.com.tasksjavakotlin.java.data.DataManager;
import michael.com.tasksjavakotlin.java.model.Task;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Mikhail on 6/17/17.
 */

public class TaskViewModel extends BaseObservable {

    private CompositeSubscription mSubscription;
    private DataManager mDataManager;
    private Context mContext;

    private int progress;
    public final ObservableList<Task> items = new ObservableArrayList<>();
    public final ObservableField<String> header = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();
    private final ObservableField<Task> mTaskObservable = new ObservableField<>();
    final List<Task> cache = new ArrayList<>();


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

            getTaskList();
        }
    }

    public List<Task> getTaskList() {


        mSubscription.add(mDataManager.getTasks()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<List<Task>>() {
            @Override
            public void call(List<Task> tasks) {

               cache.addAll(tasks);

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        }));

//        mSubscription = new CompositeSubscription();

//        mSubscription.add(mDataManager.getTasks()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<List<Task>>() {
//                    @Override
//                    public void onCompleted() {
//                        setProgress(View.INVISIBLE);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("ViewModel", e.toString());
//                    }
//
//                    @Override
//                    public void onNext(List<Task> tasks) {
//
//                        List<Task> results = tasks;
//
////                        items.addAll(tasks);
//                        cache.addAll(results);
//                        for (int i = 0; i <= 3; i++) {
//
//                            Log.d("ViewModel", cache.get(i).getTaskTitle());
//                        }
//                    }
//
//                })
//        );
//
//        return cache;
//
        return cache;
   }

}
