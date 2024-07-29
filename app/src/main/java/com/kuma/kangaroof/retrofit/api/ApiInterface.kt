package com.kuma.kangaroof.retrofit.api

import com.kuma.kangaroof.model.User
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import rx.Observable

//import rx.Observable

interface ApiInterface {


    @FormUrlEncoded
    @POST("/login")
//    suspend
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<User>
    /*
    what does the suspend keyword mean?
     */
}