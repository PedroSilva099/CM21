package ipvc.estg.CM21.api


import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface EndPoints {

    @GET("utilizador")
    fun getUsers(): Call<List<User>>

    @GET("allReports")
    fun getReports(): Call<List<Report>>

    @FormUrlEncoded
    @POST("login")
    fun login(
            @Field("username") username: String,
            @Field("password") password: String
    ): Call<Iflogin>

    @Multipart
    @POST("addReport")
    fun addRep(
            @Part("titulo") titulo: RequestBody,
            @Part("descricao") descricao: RequestBody,
            @Part("latitude") latitude: RequestBody,
            @Part("longitude") longitude: RequestBody,
            @Part image: MultipartBody.Part,
            @Part("user_id") user_id: Int?,
            @Part("type_id") type_id: Int?
    ): Call<ReportMarker>




}