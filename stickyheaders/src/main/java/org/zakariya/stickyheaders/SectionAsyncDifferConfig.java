package org.zakariya.stickyheaders;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.util.DiffUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SectionAsyncDifferConfig<T> {

    @NonNull
    private final Executor mMainThreadExecutor;
    @NonNull
    private final Executor mBackgroundThreadExecutor;
    @NonNull
    private final SectionItemCallback<T> mDiffCallback;

    SectionAsyncDifferConfig(@NonNull Executor mainThreadExecutor, @NonNull Executor backgroundThreadExecutor, @NonNull SectionItemCallback<T> diffCallback) {
        this.mMainThreadExecutor = mainThreadExecutor;
        this.mBackgroundThreadExecutor = backgroundThreadExecutor;
        this.mDiffCallback = diffCallback;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @Nullable
    public Executor getMainThreadExecutor() {
        return this.mMainThreadExecutor;
    }

    @NonNull
    public Executor getBackgroundThreadExecutor() {
        return this.mBackgroundThreadExecutor;
    }

    @NonNull
    public SectionItemCallback<T> getDiffCallback() {
        return this.mDiffCallback;
    }

    public static final class Builder<T> {
        private Executor mMainThreadExecutor;
        private Executor mBackgroundThreadExecutor;
        private final SectionItemCallback<T> mDiffCallback;
        private static final Object sExecutorLock = new Object();
        private static Executor sDiffExecutor = null;

        public Builder(@NonNull SectionItemCallback<T> diffCallback) {
            this.mDiffCallback = diffCallback;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        @NonNull
        public SectionAsyncDifferConfig.Builder<T> setMainThreadExecutor(Executor executor) {
            this.mMainThreadExecutor = executor;
            return this;
        }

        @NonNull
        public SectionAsyncDifferConfig.Builder<T> setBackgroundThreadExecutor(Executor executor) {
            this.mBackgroundThreadExecutor = executor;
            return this;
        }

        @NonNull
        public SectionAsyncDifferConfig<T> build() {
            if (this.mBackgroundThreadExecutor == null) {
                Object var1 = sExecutorLock;
                synchronized(sExecutorLock) {
                    if (sDiffExecutor == null) {
                        sDiffExecutor = Executors.newFixedThreadPool(2);
                    }
                }

                this.mBackgroundThreadExecutor = sDiffExecutor;
            }

            return new SectionAsyncDifferConfig<>(this.mMainThreadExecutor, this.mBackgroundThreadExecutor, this.mDiffCallback);
        }
    }

}
