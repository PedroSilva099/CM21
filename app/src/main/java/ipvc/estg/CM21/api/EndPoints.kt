package ipvc.estg.CM21.api


import retrofit2.Call
import retrofit2.http.*


interface EndPoints {

    @GET("utilizador")
    fun getUsers(): Call<List<User>>

    @GET("report")
    fun getReports(): Call<List<Report>>

    @FormUrlEncoded
    @POST("login")
    fun login(
            @Field("username") username: String,
            @Field("password") password: String
    ): Call<Iflogin>






}