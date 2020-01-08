package com.fta.testdemo.listener;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.fta.testdemo.bean.ItemInfo;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener3;

public class MyDownloadListener3 extends DownloadListener3 {

    private static final String TAG = "bqt";

    private ItemInfo itemInfo;
    private long totalLength;
    private String readableTotalLength;
    private ProgressBar progressBar;//谨防内存泄漏
    private Context context;//谨防内存泄漏

    public MyDownloadListener3(ItemInfo itemInfo, ProgressBar progressBar) {
        this.itemInfo = itemInfo;
        this.progressBar = progressBar;
        context = progressBar.getContext();
    }

    @Override
    protected void started(@NonNull DownloadTask task) {
        Log.i(TAG, "MyDownloadListener3-started: ");
    }

    @Override
    protected void completed(@NonNull DownloadTask task) {
        Log.i(TAG, "MyDownloadListener3-completed: ");
    }

    @Override
    protected void canceled(@NonNull DownloadTask task) {
        Log.i(TAG, "MyDownloadListener3-canceled: ");
    }

    @Override
    protected void error(@NonNull DownloadTask task, @NonNull Exception e) {
        Log.i(TAG, "MyDownloadListener3-error: ");
    }

    @Override
    protected void warn(@NonNull DownloadTask task) {
        Log.i(TAG, "MyDownloadListener3-warn: ");
    }

    @Override
    public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {
        Log.i(TAG, "MyDownloadListener3-retry: ");
    }

    @Override
    public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset, long totalLength) {
        Log.i(TAG, "MyDownloadListener3-connected: ");
    }

    @Override
    public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
        Log.i(TAG, "MyDownloadListener3-progress: ");
    }
}
