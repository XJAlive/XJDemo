package com.xj.demo.network

import com.xj.demo.BannerItem
import com.xj.demo.BizResult
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface NetWorkServiceApi {

    @GET("banner/json")
    fun getBannerAsync(): Deferred<BizResult<List<BannerItem>>>

    @GET("banner/json")
    suspend fun getBannerSuspend(): BizResult<List<BannerItem>>

}