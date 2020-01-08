package com.fta.testdemo.network.download;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadManager {
    private static final String TAG = "DownloadManager";

    private static final AtomicReference<DownloadManager> INSTANCE = new AtomicReference<>();

    private HashMap<String, Call> downCalls;//用来存放各个下载的请求

    private OkHttpClient mClient;//OKHttpClient;


    private DownloadManager(OkHttpClient okHttpClient) {
        downCalls = new HashMap<>();
        mClient = okHttpClient;
    }

    public static DownloadManager getInstance(OkHttpClient okHttpClient) {
        for (; ; ) {
            DownloadManager current = INSTANCE.get();
            if (current != null) {
                return current;
            }
            current = new DownloadManager(okHttpClient);
            if (INSTANCE.compareAndSet(null, current)) {
                return current;
            }
        }
    }

    /**
     * 开始下载
     *
     * @param url              下载请求的网址
     * @param downLoadObserver 用来回调的接口
     */
    public void download(String url, String filePath, DownLoadObserver downLoadObserver) {
//        Observable.just(url)
//                .filter(s -> !downCalls.containsKey(s))//call的map已经有了,就证明正在下载,则这次不下载
//                .flatMap(s -> Observable.just(createDownInfo(s, filePath)))
//                .map(this::getRealFileName)//检测本地文件夹,获取文件长度
//                .flatMap(downloadInfo -> Observable.create(new DownloadSubscribe(downloadInfo)))//下载
//                .subscribeOn(Schedulers.io())//在子线程执行
//                .observeOn(AndroidSchedulers.mainThread())//在主线程回调
////                .observeOn(Schedulers.newThread())//在子线程回调
//                .subscribe(downLoadObserver);//添加观察者


        Observable.just(url)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        boolean b = downCalls.containsKey(s);
                        Log.i(TAG, "DownloadManager-test: 包含下载=" + b);
                        return !b;
                    }
                })
                .flatMap(s -> Observable.just(createDownInfo(s, filePath)))
                .map(this::getRealFileName)//检测本地文件夹,获取文件长度
                .flatMap(downloadInfo -> Observable.create(new DownloadSubscribe(downloadInfo)))//下载
                .subscribeOn(Schedulers.io())//在子线程执行
                .observeOn(Schedulers.newThread())//在子线程回调
                .subscribe(downLoadObserver);//添加观察者
    }

    public void cancel(String url) {
        Call call = downCalls.get(url);
        if (call != null) {
            call.cancel();//取消
        }
        downCalls.remove(url);
    }

    public boolean isDownloading(String url) {
        return downCalls != null && downCalls.containsKey(url);
    }


    /**
     * 创建DownInfo
     *
     * @param url 请求网址
     * @return DownInfo
     */
    private DownloadInfo createDownInfo(String url, String filePath) {
        DownloadInfo downloadInfo = new DownloadInfo(url);
//        long contentLength = getContentLength(url);//获得文件大小
        long contentLength = 0;//获得文件大小
        downloadInfo.setTotal(contentLength);
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        downloadInfo.setFileName(fileName);
        downloadInfo.setFilePath(filePath);
        Log.i(TAG, "DownloadManager-createDownInfo: 创建下载=" + downloadInfo.toString());
        return downloadInfo;
    }

    private DownloadInfo getRealFileName(DownloadInfo downloadInfo) {
        Log.i(TAG, "DownloadManager-getRealFileName: " + downloadInfo.toString());
        String fileName = downloadInfo.getFileName();
        long downloadLength = 0, contentLength = downloadInfo.getTotal();
        File file = new File(downloadInfo.getFilePath(), fileName);
        if (file.exists()) {
            //找到了文件,代表已经下载过,则获取其长度
            downloadLength = file.length();
        }
        //之前下载过,需要重新来一个文件
        int i = 1;
        Log.i(TAG, "DownloadManager-getRealFileName: downloadLength=" + downloadLength + "  =contentLength=" + contentLength);
        while (contentLength > 0 && downloadLength >= contentLength) {
            Log.i(TAG, "DownloadManager-getRealFileName: while");
            int dotIndex = fileName.lastIndexOf(".");
            String fileNameOther;
            if (dotIndex == -1) {
                fileNameOther = fileName + "(" + i + ")";
            } else {
                fileNameOther = fileName.substring(0, dotIndex)
                        + "(" + i + ")" + fileName.substring(dotIndex);
            }
            File newFile = new File(downloadInfo.getFilePath(), fileNameOther);
            file = newFile;
            downloadLength = newFile.length();
            i++;
        }
        //设置改变过的文件名/大小
        downloadInfo.setProgress(downloadLength);
        downloadInfo.setFileName(file.getName());
        Log.i(TAG, "DownloadManager-getRealFileName: " + downloadInfo.toString());
        return downloadInfo;
    }

    private class DownloadSubscribe implements ObservableOnSubscribe<DownloadInfo> {
        private DownloadInfo downloadInfo;

        public DownloadSubscribe(DownloadInfo downloadInfo) {
            this.downloadInfo = downloadInfo;
        }

        @Override
        public void subscribe(ObservableEmitter<DownloadInfo> e) throws Exception {
            Log.i(TAG, "subscribe: 下载=" + downloadInfo.toString());
            String url = downloadInfo.getUrl();
            long downloadLength = downloadInfo.getProgress();//已经下载好的长度
            long contentLength = downloadInfo.getTotal();//文件的总长度
            //初始进度信息
            e.onNext(downloadInfo);

            Request request = new Request.Builder()
                    //确定下载的范围,添加此头,则服务器就可以跳过已经下载好的部分
//                    .addHeader("RANGE", "bytes=" + downloadLength + "-" + contentLength)
                    .url(url)
                    .build();
            Call call = mClient.newCall(request);
            downCalls.put(url, call);//把这个添加到call里,方便取消
            Response response = call.execute();
            Log.i(TAG, "DownloadSubscribe-subscribe: response");
            File file = new File(downloadInfo.getFilePath(), downloadInfo.getFileName());
            InputStream is = null;
            FileOutputStream fileOutputStream = null;
            try {
                String totalLength = response.header("Content-Length");
                if (totalLength != null){
                    contentLength = Long.parseLong(totalLength);
                    downloadInfo.setTotal(contentLength);
                }
                is = response.body().byteStream();
                Log.i(TAG, "DownloadSubscribe-subscribe: response.body().byteStream()");
                fileOutputStream = new FileOutputStream(file, true);
//                byte[] buffer = new byte[1024 * 4];//缓冲数组2kB
                byte[] buffer = new byte[1024 * 1024 * 2];//缓冲数组2M
                int len;
                while ((len = is.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, len);
                    downloadLength += len;
                    downloadInfo.setProgress((int) (((float) downloadLength / contentLength) * 100));
                    e.onNext(downloadInfo);
                }
                fileOutputStream.flush();

            } finally {
                downCalls.remove(url);
                //关闭IO流
                IOUtil.closeAll(is, fileOutputStream);
            }
            e.onComplete();//完成
        }
    }

    /**
     * 获取下载长度
     *
     * @param downloadUrl
     * @return
     */
    private long getContentLength(String downloadUrl) {
        Log.i(TAG, "getContentLength: downloadUrl=" + downloadUrl);
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        try {
            Log.i(TAG, "DownloadManager-getContentLength: execute before");
            Response response = mClient.newCall(request).execute();
            Log.i(TAG, "DownloadManager-getContentLength: execute after");
            if (response != null && response.isSuccessful()) {
                Log.i(TAG, "DownloadManager-getContentLength: 1");
                String header = response.header("Content-Length");
                Log.i(TAG, "DownloadManager-getContentLength: Content-Length=" + header);
                long contentLength = response.body().contentLength();
                Log.i(TAG, "DownloadManager-getContentLength: contentLength=" + contentLength);
                response.close();
                Log.i(TAG, "DownloadManager-getContentLength: response is close");
                return contentLength == 0 ? DownloadInfo.TOTAL_ERROR : contentLength;
            }
        } catch (IOException e) {
            Log.i(TAG, "getContentLength: " + e.getMessage());
            e.printStackTrace();
        }
        return DownloadInfo.TOTAL_ERROR;
    }

}
