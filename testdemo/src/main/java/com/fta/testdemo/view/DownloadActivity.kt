package com.fta.testdemo.view

import android.app.ListActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import com.fta.testdemo.bean.ItemInfo
import com.fta.testdemo.listener.*
import com.fta.testdemo.utils.Utils
import com.liulishuo.okdownload.DownloadListener
import com.liulishuo.okdownload.DownloadTask
import com.liulishuo.okdownload.StatusUtil
import org.jetbrains.anko.toast
import java.io.File


class DownloadActivity : ListActivity() {
    companion object {
        const val URL1 =
            "http://192.168.0.92:80/downloadFile/AMap.apk"
        const val URL2 =
            "https://cdn.llscdn.com/yy/files/xs8qmxn8-lls-LLS-5.8-800-20171207-111607.apk"
        const val URL3 =
            "https://downapp.baidu.com/appsearch/AndroidPhone/1.0.78.155/1/1012271b/20190404124002/appsearch_AndroidPhone_1-0-78-155_1012271b.apk"
    }

    private lateinit var list: List<ItemInfo>

    private lateinit var progressBar: ProgressBar

    private val map = HashMap<String, DownloadTask?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val array = arrayOf<String>(
            "使用DownloadListener4WithSpeed",
            "使用DownloadListener3",
            "使用DownloadListener2",
            "使用DownloadListener3",
            "使用DownloadListener",
            "=====删除下载的文件，并重新启动Activity=====",
            "查看任务1的状态",
            "查看任务2的状态",
            "查看任务3的状态",
            "查看任务4的状态",
            "查看任务5的状态"
        )

        listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, array)
        list = arrayListOf(
            ItemInfo(URL1, "AMap.apk"),
            ItemInfo(URL1, "AMap.apk"),
            ItemInfo(URL2, "英语流利说.apk"),
            ItemInfo(URL2, "百度手机助手.apk"),
            ItemInfo(URL3, "哎哎哎.apk")
        )

        progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal)
        progressBar.setIndeterminate(false)
        listView.addFooterView(progressBar)
        File(Utils.PARENT_PATH).mkdirs()
    }

    override fun onDestroy() {
        super.onDestroy()

        map.forEach {
            it.value?.cancel()
        }
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        when (position) {
            0, 1, 2, 3, 4 -> download(position)
            5 -> {
                Utils.deleateFiles(File(Utils.PARENT_PATH), null, false)
                recreate()
            }
            else -> {
                val itemInfo = list.get(position - 6)
                val task = map.get(itemInfo.pkgName)
                task?.let {
                    toast("状态为：${StatusUtil.getStatus(task).name}")
                }

                val info =
                    StatusUtil.getCurrentInfo(itemInfo.url, Utils.PARENT_PATH, itemInfo.pkgName)
                info?.let {
                    val percent: Float = (it.totalOffset / it.totalLength.toFloat() * 100)
                    Log.i("bqt", "【当前进度】${percent}%")
                    progressBar.max = it.totalLength.toInt()
                    progressBar.progress = it.totalOffset.toInt()
                }?: run {
                    Log.i("bqt", "【任务不存在】")
                }
            }
        }
    }

    private fun download(position: Int) {
        val itemInfo = list.get(position)
        var task = map.get(itemInfo.pkgName)
        // 0：没有下载  1：下载中  2：暂停  3：完成
        when (itemInfo.status) {
            0 -> {
                if (task == null){
                    task = createDownloadTask(itemInfo)
                    map.put(itemInfo.pkgName, task)
                }
                task.enqueue(createDownloadListener(position))
                itemInfo.status = 1;//更改状态
                toast("开始下载")
            }
            1 -> {//下载中
                task?.cancel()
                itemInfo.status = 2
                toast("暂停下载")
            }
            2 -> {
                task?.enqueue(createDownloadListener(position))
                itemInfo.status = 1
                toast("继续下载")
            }
            3 -> {
//                Utils.launchOrInstallApp(this, itemInfo.pkgName)
                toast("下载完成")
            }
        }

    }

    private fun createDownloadTask(itemInfo: ItemInfo): DownloadTask {
        return DownloadTask.Builder(itemInfo.url, File(Utils.PARENT_PATH)) //设置下载地址和下载目录，这两个是必须的参数
            .setFilename(itemInfo.pkgName) //设置下载文件名，没提供的话先看 response header ，再看 url path(即启用下面那项配置)
            .setFilenameFromResponse(false) //是否使用 response header or url path 作为文件名，此时会忽略指定的文件名，默认false
            .setPassIfAlreadyCompleted(true) //如果文件已经下载完成，再次下载时，是否忽略下载，默认为true(忽略)，设为false会从头下载
            .setConnectionCount(2) //需要用几个线程来下载文件，默认根据文件大小确定；如果文件已经 split block，则设置后无效
            .setPreAllocateLength(false) //在获取资源长度后，设置是否需要为文件预分配长度，默认false
            .setMinIntervalMillisCallbackProcess(100) //通知调用者的频率，避免anr，默认3000
            .setWifiRequired(false) //是否只允许wifi下载，默认为false
            .setAutoCallbackToUIThread(true) //是否在主线程通知调用者，默认为true
//.setHeaderMapFields(new HashMap<String, List<String>>())//设置请求头
//.addHeader(String key, String value)//追加请求头
            .setPriority(0) //设置优先级，默认值是0，值越大下载优先级越高
            .setReadBufferSize(4096) //设置读取缓存区大小，默认4096
            .setFlushBufferSize(16384) //设置写入缓存区大小，默认16384
            .setSyncBufferSize(65536) //写入到文件的缓冲区大小，默认65536
            .setSyncBufferIntervalMillis(2000) //写入文件的最小时间间隔，默认2000
            .build()
    }

    private fun createDownloadListener(position: Int): DownloadListener {
        return when (position) {
            0 -> MyDownloadListener4WithSpeed(list.get(position), progressBar)
            1 -> MyDownloadListener3(list.get(position), progressBar)
            2 -> MyDownloadListener2(list.get(position), progressBar)
            3 -> MyDownloadListener1(list.get(position), progressBar)
            else -> MyDownloadListener(list.get(position), progressBar)
        }
    }
}