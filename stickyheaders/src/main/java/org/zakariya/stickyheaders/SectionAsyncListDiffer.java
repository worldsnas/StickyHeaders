package org.zakariya.stickyheaders;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

public class SectionAsyncListDiffer<T> {

    private final SectionListUpdateCallback listUpdateCallback;
    final SectionAsyncDifferConfig<T> config;
    final Executor mainThreadExecutor;
    private static final Executor sMainThreadExecutor = new SectionAsyncListDiffer.MainThreadExecutor();
    @Nullable
    private List<T> list;
    @NonNull
    private List<T> readOnlyList;
    int maxScheduledGeneration;

    public SectionAsyncListDiffer(@NonNull SectioningAdapter adapter, @NonNull SectionItemCallback<T> callback) {
        this(new AdapterListUpdateCallback(adapter), new SectionAsyncDifferConfig.Builder<T>(callback).build());
    }

    public SectionAsyncListDiffer(@NonNull SectionListUpdateCallback listUpdateCallback, @NonNull SectionAsyncDifferConfig<T> config) {
        this.readOnlyList = Collections.emptyList();
        this.listUpdateCallback = listUpdateCallback;
        this.config = config;
        if (config.getMainThreadExecutor() != null) {
            this.mainThreadExecutor = config.getMainThreadExecutor();
        } else {
            this.mainThreadExecutor = sMainThreadExecutor;
        }
    }

    private static class MainThreadExecutor implements Executor {
        final Handler mHandler = new Handler(Looper.getMainLooper());

        MainThreadExecutor() {
        }

        public void execute(@NonNull Runnable command) {
            this.mHandler.post(command);
        }
    }

    @NonNull
    public List<T> getCurrentList() {
        return this.readOnlyList;
    }


    void latchList(@NonNull List<T> newList, @NonNull DiffUtil.DiffResult diffResult) {
        this.list = newList;
        this.readOnlyList = Collections.unmodifiableList(newList);
        diffResult.dispatchUpdatesTo(this.listUpdateCallback);
    }
}
