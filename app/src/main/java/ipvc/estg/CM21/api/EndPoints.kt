package ipvc.estg.CM21.api


import retrofit2.Call
import retrofit2.http.*


interface EndPoints {

    @GET("/utilizador/")
    fun getUsers(): Call<List<User>>


}