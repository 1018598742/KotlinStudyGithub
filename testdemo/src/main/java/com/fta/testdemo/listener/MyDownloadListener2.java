package com.fta.testdemo.listener;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fta.testdemo.bean.ItemInfo;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.listener.DownloadListener2;

public class MyDownloadListener2 extends DownloadListener2 {

    private static final String TAG = "bqt";

    private ItemInfo itemInfo;
    private long totalLength;
    private String readableTotalLength;
    private ProgressBar progressBar;//谨防内存泄漏
    private Context context;//谨防内存泄漏

    public MyDownloadListener2(ItemInfo itemInfo, ProgressBar progressBar) {
        this.itemInfo = itemInfo;
        this.progressBar = progressBar;
        context = progressBar.getContext();
    }

    @Override
    public void taskStart(@NonNull DownloadTask task) {
        Log.i(TAG, "MyDownloadListener2-taskStart: ");
    }

    @Override
    public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause) {
        Log.i(TAG, "MyDownloadListener2-taskEnd: ");
    }
}
