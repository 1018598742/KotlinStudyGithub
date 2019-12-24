package com.fta.testdemo.network.services

import com.fta.testdemo.network.retrofit
import com.mdm.online.model.bean.SzgaAllocateCheckBean
import retrofit2.Call
import retrofit2.http.*
import rx.Observable

interface TestApi {

    @POST("/sync/msgBack/device/mobDeviceManage/deviceCheck")
    fun deviceCheck(@Body szgaAllocateCheckBean: SzgaAllocateCheckBean): Observable<Result<Any>>

    //json 格式
    @POST("/sync/msgBack/device/mobDeviceManage/deviceCheck")
    fun deviceCheck2(@Body szgaAllocateCheckBean: SzgaAllocateCheckBean): Call<Result<Any>>

    @POST("/sync/msgBack/test/data")
    @FormUrlEncoded
    fun testData(@FieldMap map: Map<String, String>): Observable<Result<Any>>

}

object TestService : TestApi by retrofit.create(TestApi::class.java)