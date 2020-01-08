package com.fta.testdemo.network.download;

import android.content.Context;

import java.io.File;

import okhttp3.OkHttpClient;

/**
 * 下载文件类的使用
 */
public class DownloadUser {

    private void mySelf(Context context, OkHttpClient okHttpClient, String url) {
        String apk = context.getFilesDir().getPath().concat(File.separator).concat("apk");
        File file = new File(apk);
        if (!file.exists()) {
            file.mkdir();
        }
        DownloadManager.getInstance(okHttpClient).download(url, file.getPath(), new DownLoadObserver() {

            @Override
            public void onComplete() {

            }

            @Override
            public void onNext(DownloadInfo downloadInfo) {
                super.onNext(downloadInfo);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }
}
