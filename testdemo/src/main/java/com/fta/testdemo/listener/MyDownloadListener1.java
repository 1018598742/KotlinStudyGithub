package com.fta.testdemo.listener;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fta.testdemo.bean.ItemInfo;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;

public class MyDownloadListener1 extends DownloadListener1 {

    private static final String TAG = "bqt";

    private ItemInfo itemInfo;
    private long totalLength;
    private String readableTotalLength;
    private ProgressBar progressBar;//谨防内存泄漏
    private Context context;//谨防内存泄漏

    public MyDownloadListener1(ItemInfo itemInfo, ProgressBar progressBar) {
        this.itemInfo = itemInfo;
        this.progressBar = progressBar;
        context = progressBar.getContext();
    }

    @Override
    public void taskStart(@NonNull DownloadTask task, @NonNull Listener1Assist.Listener1Model model) {
        Log.i(TAG, "MyDownloadListener1-taskStart: ");
    }

    @Override
    public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {
        Log.i(TAG, "MyDownloadListener1-retry: ");
    }

    @Override
    public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset, long totalLength) {
        Log.i(TAG, "MyDownloadListener1-connected: ");
    }

    @Override
    public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
        Log.i(TAG, "MyDownloadListener1-progress: ");
    }

    @Override
    public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull Listener1Assist.Listener1Model model) {
        Log.i(TAG, "MyDownloadListener1-taskEnd: ");
    }
}
