package com.fta.testdemo.view

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bennyhuo.mvp.impl.BaseFragment
import com.blankj.utilcode.util.AppUtils
import com.fta.testdemo.R
import com.fta.testdemo.network.MyInterceptor
import com.fta.testdemo.network.download.DownLoadObserver
import com.fta.testdemo.network.download.DownloadInfo
import com.fta.testdemo.network.download.DownloadManager
import com.fta.testdemo.network.entities.Person
import com.fta.testdemo.network.services.TestService
import com.fta.testdemo.presenter.MyPresenter
import com.fta.testdemo.utils.SSLSocketFactoryUtils
import com.fta.testdemo.utils.toStringMap
import com.liulishuo.okdownload.DownloadListener
import com.liulishuo.okdownload.DownloadMonitor
import com.liulishuo.okdownload.DownloadTask
import com.liulishuo.okdownload.OkDownload
import com.liulishuo.okdownload.core.Util
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo
import com.liulishuo.okdownload.core.cause.EndCause
import com.liulishuo.okdownload.core.cause.ResumeFailedCause
import com.liulishuo.okdownload.core.connection.DownloadOkHttp3Connection
import com.liulishuo.okdownload.core.dispatcher.CallbackDispatcher
import kotlinx.android.synthetic.main.fragment_demo.*
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.concurrent.thread

class MyFragment : BaseFragment<MyPresenter>(), View.OnClickListener {

    companion object {
        val TAG = MyFragment::class.java.simpleName
    }

    private lateinit var okHttpClient: OkHttpClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_demo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aboutLogin.setOnClickListener {

            TestService.testData(toStringMap(Person()))
                .subscribe({
                    Log.i(TAG, "MyFragment-onViewCreated: person resulet=${it}");
                }, {
                    Log.e(TAG, "MyFragment-onViewCreated: error=${it.printStackTrace()}");
                })

        }

        val uuidName = UUID.randomUUID().toString()
        val uuidSpilt = uuidName.substring(0, 8)
        Log.i(TAG, "MyFragment-onViewCreated: ${uuidName};${uuidSpilt}");

        aboutDownload.setOnClickListener(this)

        okhttpDownload.setOnClickListener(this)

        httpConDownload.setOnClickListener(this)

        aboutThirdDownload.setOnClickListener(this)

        readFile.setOnClickListener(this)

        uninstallSelf.setOnClickListener {
            AppUtils.uninstallApp(context?.packageName)
        }

        downloadAc.setOnClickListener {
            startActivity(Intent(this.context, DownloadActivity::class.java))
        }

        okHttpClient = OkHttpClient.Builder()
//            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .sslSocketFactory(
                SSLSocketFactoryUtils.createSSLSocketFactory(),
                SSLSocketFactoryUtils.createTrustAllManager()
            )
            .hostnameVerifier(SSLSocketFactoryUtils.trustAllHostnameVerifier())
            .build()


//        val factory = DownloadOkHttp3Connection.Factory()
//        val okhttpBuilder = OkHttpClient.Builder()
//            .addInterceptor(MyInterceptor())
//            .sslSocketFactory(
//                SSLSocketFactoryUtils.createSSLSocketFactory(),
//                SSLSocketFactoryUtils.createTrustAllManager()
//            )
//            .hostnameVerifier(SSLSocketFactoryUtils.trustAllHostnameVerifier())
//
//        factory.setBuilder(okhttpBuilder)
//        val builder = OkDownload.Builder(this@MyFragment.context!!)
//            .connectionFactory(factory)
//        OkDownload.setSingletonInstance(builder.build())
//
//        Util.enableConsoleLog()
    }

    fun getInfo() = "呵呵呵"

    override fun onClick(v: View?) {
        val apk = Environment.getExternalStorageDirectory().absolutePath
        val file = File(apk)
        if (!file.exists()) {
            file.mkdir()
        }

        val targetFile = File(apk, "test.apk")
        if (targetFile.exists()) {
            targetFile.delete()
        }

        val path = targetFile.absolutePath


        var url = "https://192.168.0.92:80/downloadFile/AMap.apk"
//        url = "http://192.168.0.92:8080/download/AMap.apk"
//        url = "https://qd.mya pp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk"
//        url =
//            "https://oss01-zb01-hz-external.test.geely.com/skr-user/strategyPhoto/IMG_20191226_163754.jpg?X-Amz-Algo" +
//                    "rithm=AWS4-HMAC-SHA256&X-Amz-Date=20191226T083807Z&X-Amz-SignedHeaders=host&X-Amz-Expires=604799&X-Amz-Credential=1W3S20BA" +
//                    "A08ICMW5M7X8%2F20191226%2F%2Fs3%2Faws4_request&X-Amz-Signature=d9d3d2cd6fd0c060181f3284ddc9d597e12f773b88c171a711d0982a2d24f54b"
//        url = "https://114.215.44.230:8001/MDMClient/apkUpgrade/client/download/719"
        Log.i(TAG, "MyFragment-onClick: click");
        when (v?.id) {
            R.id.aboutDownload -> {
                Log.i(TAG, "MyFragment-onClick: 开始下载");
                //http://192.168.0.92:80/downloadFile/AMap.apk
                //https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk
                DownloadManager.getInstance(okHttpClient)
                    .download(
                        "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk",
                        apk,
                        object :
                            DownLoadObserver() {

                            override fun onNext(downloadInfo: DownloadInfo) {
                                super.onNext(downloadInfo)
                                Log.i(TAG, "MyFragment-onNext: ${downloadInfo.progress}");
                            }

                            override fun onComplete() {
                                Log.i(TAG, "MyFragment-onComplete: 下载完成");
                            }

                            override fun onError(e: Throwable) {
                                super.onError(e)
                                Log.e(TAG, "MyFragment-onError: ${e.printStackTrace()}");
                            }

                        })

            }
            R.id.okhttpDownload -> {

                thread(start = true) {
                    Log.i(TAG, "MyFragment-onClick: okhttp start download");
                    val request = Request.Builder()
                        .url(url)
                        .header(
                            "sid",
                            "502ef3d38f16b8179c99a7d9cb821caf111dd00b1c4d52a3cca1da4e4afb480bfa74c9556f418e83046354c15e34567104e5cdd857f8a579796baa901f21dde3"
                        )
                        .build()
                    val mOkHttpClient = OkHttpClient.Builder()
//                        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

                        .sslSocketFactory(
                            SSLSocketFactoryUtils.createSSLSocketFactory(),
                            SSLSocketFactoryUtils.createTrustAllManager()
                        )
                        .hostnameVerifier(SSLSocketFactoryUtils.trustAllHostnameVerifier())
                        .build()
                    mOkHttpClient.newCall(request)
                        .enqueue(object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                                Log.i(TAG, "MyFragment-onFailure: ");
                            }

                            override fun onResponse(call: Call, response: Response) {
                                Log.i(TAG, "MyFragment-onResponse: ${response.toString()}");
                                try {
                                    if (response.isSuccessful) {
                                        val contentLength = response.body?.contentLength() ?: 0
                                        Log.i(
                                            TAG,
                                            "MyFragment-onResponse: contentLength=${contentLength}"
                                        )
                                        val byteStream = response.body?.byteStream()

                                        if (byteStream == null) {
                                            Log.i(TAG, "MyFragment-onResponse: is null");
                                        }

                                        byteStream?.let {
                                            Log.i(
                                                TAG,
                                                "MyFragment-onResponse: targetFile =${targetFile.absolutePath}"
                                            );
                                            val fileOutputStream = FileOutputStream(targetFile)
                                            val bytes = ByteArray(1024 * 1024 * 10)
                                            var len = -1
                                            var downloadLength = 0
                                            var progress = 0
                                            while ({ len = it.read(bytes);len }() != -1) {
                                                downloadLength += len
                                                progress =
                                                    (downloadLength / contentLength.toFloat() * 100).toInt()
                                                Log.i(
                                                    TAG,
                                                    "MyFragment-onResponse: write ${progress}"
                                                );
                                                fileOutputStream.write(bytes, 0, len)
                                            }
                                            fileOutputStream.flush()
                                            it.close()
                                            fileOutputStream.close()
                                            Log.i(TAG, "MyFragment-onResponse: write finish");

                                        }
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }

                            }

                        })
                }

            }
            R.id.httpConDownload -> {
                Log.i(TAG, "MyFragment-onClick: origianlDownload ");
                thread(start = true) {
                    Log.i(TAG, "MyFragment-onClick: origianl start download");
                    val openConnection = URL(url).openConnection()
                    if (openConnection is HttpURLConnection) {
                        Log.i(TAG, "MyFragment-onClick: is HttpURlCOnn");
                        val httpURLConnection = openConnection as HttpURLConnection
                        httpURLConnection.setRequestProperty(
                            "sid",
                            "158da316d45f90589f1dec48b15e74e244aa5e1c565bfdbbbe527b99686c8ddb82ebaf5bd8973aec771995f1d8838ddbe330e6a3bebadb244e89273c6fdf2be6"
                        )
                        val contentLength = httpURLConnection.contentLength
                        Log.i(
                            TAG,
                            "MyFragment-onClick: httpURLConnection-contentLength=${contentLength}"
                        );
                        val responseCode = httpURLConnection.responseCode
                        Log.i(
                            TAG,
                            "MyFragment-onClick: httpURLConnection-responseCode=${responseCode}"
                        );
                        if (responseCode == 200) {
                            val inputStream = httpURLConnection.inputStream
                            val fileOutputStream = FileOutputStream(targetFile)
                            val bytes = ByteArray(1024 * 1024 * 10)
                            var len = -1
                            var downloadLength = 0
                            var progress = 0
                            while ({ len = inputStream.read(bytes);len }() != -1) {
                                downloadLength += len
                                progress = (downloadLength / contentLength.toFloat() * 100).toInt()
                                Log.i(
                                    TAG,
                                    "MyFragment-onResponse:httpURLConnection write:${progress}"
                                );
                                fileOutputStream.write(bytes, 0, len)
                            }
                            fileOutputStream.flush()
                            inputStream.close()
                            fileOutputStream.close()
                            Log.i(TAG, "MyFragment-onResponse:httpURLConnection write finish");

                        }


                    }
                }
            }
            R.id.readFile -> {
                thread(start = true) {
                    val needFile = File(Environment.getExternalStorageDirectory(), "AMap.apk")
                    val contentLength = needFile.length();
                    val outFile = File(Environment.getExternalStorageDirectory(), "CopyAMap.apk")
                    val fileInputStream = FileInputStream(needFile)
                    val fileOutputStream = FileOutputStream(outFile)
                    val bytes = ByteArray(1024 * 1024)
                    var len = -1
                    var downloadLength = 0
                    var progress: Int = 0
                    Log.i(TAG, "MyFragment-onClick: readFile-contentLength=${contentLength}");
                    while ({ len = fileInputStream.read(bytes);len }() > 0) {
                        downloadLength += len
                        Log.i(TAG, "MyFragment-onClick: readFile-downloadLength=${downloadLength}");
                        progress = (downloadLength / contentLength.toFloat() * 100).toInt()
                        Log.i(
                            TAG,
                            "MyFragment-onResponse:readFile write:${progress}"
                        );
                        fileOutputStream.write(bytes, 0, len)
                    }
                    Log.i(TAG, "MyFragment-onClick: readFile finish");
                    fileOutputStream.flush()
                    fileInputStream.close()
                    fileOutputStream.close()
                }
            }
            R.id.aboutThirdDownload -> {

                DownloadTask.Builder(url, file)
                    .setFilename("downloadApk.apk")
                    .setFilenameFromResponse(true)
                    // the minimal interval millisecond for callback progress
                    .setMinIntervalMillisCallbackProcess(30)
                    // do re-download even if the task has already been completed in the past.
//                    .setPassIfAlreadyCompleted(false)
                    .build()
                    .enqueue(object : DownloadListener {
                        override fun connectTrialEnd(
                            task: DownloadTask,
                            responseCode: Int,
                            responseHeaderFields: MutableMap<String, MutableList<String>>
                        ) {
                            Log.i(TAG, "MyFragment-connectTrialEnd: ");
                        }

                        override fun fetchEnd(
                            task: DownloadTask,
                            blockIndex: Int,
                            contentLength: Long
                        ) {
                            Log.i(TAG, "MyFragment-fetchEnd: ");
                        }

                        override fun downloadFromBeginning(
                            task: DownloadTask,
                            info: BreakpointInfo,
                            cause: ResumeFailedCause
                        ) {
                            Log.i(TAG, "MyFragment-downloadFromBeginning: ");
                        }

                        override fun taskStart(task: DownloadTask) {
                            Log.i(TAG, "MyFragment-taskStart: ");
                        }

                        override fun taskEnd(
                            task: DownloadTask,
                            cause: EndCause,
                            realCause: Exception?
                        ) {
                            Log.i(
                                TAG,
                                "MyFragment-taskEnd: cause:${cause} ; task:${task.toString()} ; thread:${Thread.currentThread().name}"
                            );
                        }

                        override fun connectTrialStart(
                            task: DownloadTask,
                            requestHeaderFields: MutableMap<String, MutableList<String>>
                        ) {
                            Log.i(TAG, "MyFragment-connectTrialStart: ");
                        }

                        override fun downloadFromBreakpoint(
                            task: DownloadTask,
                            info: BreakpointInfo
                        ) {
                            Log.i(TAG, "MyFragment-downloadFromBreakpoint: ");
                        }

                        override fun fetchStart(
                            task: DownloadTask,
                            blockIndex: Int,
                            contentLength: Long
                        ) {
                            Log.i(
                                TAG,
                                "MyFragment-fetchStart: blockIndex:${blockIndex};contentLength:${contentLength}"
                            );
                        }

                        override fun fetchProgress(
                            task: DownloadTask,
                            blockIndex: Int,
                            increaseBytes: Long
                        ) {
                            Log.i(
                                TAG,
                                "MyFragment-fetchProgress: blockIndex:${blockIndex};increaseBytes:${increaseBytes}"
                            );
                        }

                        override fun connectEnd(
                            task: DownloadTask,
                            blockIndex: Int,
                            responseCode: Int,
                            responseHeaderFields: MutableMap<String, MutableList<String>>
                        ) {
                            Log.i(TAG, "MyFragment-connectEnd: ");
                        }

                        override fun connectStart(
                            task: DownloadTask,
                            blockIndex: Int,
                            requestHeaderFields: MutableMap<String, MutableList<String>>
                        ) {
                            Log.i(TAG, "MyFragment-connectStart: ");
                        }

                    })

            }
        }
    }
}