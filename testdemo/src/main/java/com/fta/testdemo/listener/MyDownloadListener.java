package com.fta.testdemo.listener;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fta.testdemo.bean.ItemInfo;
import com.liulishuo.okdownload.DownloadListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;

import java.util.List;
import java.util.Map;

public class MyDownloadListener implements DownloadListener {

    private static final String TAG = "bqt";
    
    private ItemInfo itemInfo;
    private long totalLength;
    private String readableTotalLength;
    private ProgressBar progressBar;//谨防内存泄漏
    private Context context;//谨防内存泄漏

    public MyDownloadListener(ItemInfo itemInfo, ProgressBar progressBar) {
        this.itemInfo = itemInfo;
        this.progressBar = progressBar;
        context = progressBar.getContext();
    }

    @Override
    public void taskStart(@NonNull DownloadTask task) {
        Log.i(TAG, "MyDownloadListener-taskStart: ");
    }

    @Override
    public void connectTrialStart(@NonNull DownloadTask task, @NonNull Map<String, List<String>> requestHeaderFields) {
        Log.i(TAG, "MyDownloadListener-connectTrialStart: ");
    }

    @Override
    public void connectTrialEnd(@NonNull DownloadTask task, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {
        Log.i(TAG, "MyDownloadListener-connectTrialEnd: ");
    }

    @Override
    public void downloadFromBeginning(@NonNull DownloadTask task, @NonNull BreakpointInfo info, @NonNull ResumeFailedCause cause) {
        Log.i(TAG, "MyDownloadListener-downloadFromBeginning: ");
    }

    @Override
    public void downloadFromBreakpoint(@NonNull DownloadTask task, @NonNull BreakpointInfo info) {
        Log.i(TAG, "MyDownloadListener-downloadFromBreakpoint: ");
    }

    @Override
    public void connectStart(@NonNull DownloadTask task, int blockIndex, @NonNull Map<String, List<String>> requestHeaderFields) {
        Log.i(TAG, "MyDownloadListener-connectStart: ");
    }

    @Override
    public void connectEnd(@NonNull DownloadTask task, int blockIndex, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {
        Log.i(TAG, "MyDownloadListener-connectEnd: ");
    }

    @Override
    public void fetchStart(@NonNull DownloadTask task, int blockIndex, long contentLength) {
        Log.i(TAG, "MyDownloadListener-fetchStart: ");
    }

    @Override
    public void fetchProgress(@NonNull DownloadTask task, int blockIndex, long increaseBytes) {
        Log.i(TAG, "MyDownloadListener-fetchProgress: blockIndex="+blockIndex);
    }

    @Override
    public void fetchEnd(@NonNull DownloadTask task, int blockIndex, long contentLength) {
        Log.i(TAG, "MyDownloadListener-fetchEnd: ");
    }

    @Override
    public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause) {
        Log.i(TAG, "MyDownloadListener-taskEnd: ");
    }
}
