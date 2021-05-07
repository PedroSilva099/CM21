package ipvc.estg.CM21

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ipvc.estg.CM21.api.EndPoints
import ipvc.estg.CM21.api.Iflogin
import ipvc.estg.CM21.api.ServiceBuilder
import ipvc.estg.CM21.api.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import retrofit2.Response.error

class MainLogin : AppCompatActivity() {

    private lateinit var user: EditText
    private lateinit var pass: EditText
    private lateinit var check: CheckBox
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val button = findViewById<Button>(R.id.btn_sign_up)
        button.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



        val button2 = findViewById<Button>(R.id.btn_log_in)
        button2.setOnClickListener{
            login()



        }

        val button3 = findViewById<Button>(R.id.btn_light)
        button3.setOnClickListener{
            val intent = Intent(this, SensorLuz::class.java)
            startActivity(intent)
        }

        val sharedPreferences = getSharedPreferences(getString(R.string.spf), Context.MODE_PRIVATE)
        val checkLogin = sharedPreferences.getBoolean(getString(R.string.remember),false)

        if (checkLogin) {
            val intent = Intent(this, MapsActivity::class.java)
            Toast.makeText(this@MainLogin, getString(R.string.sucess), Toast.LENGTH_LONG).show()
            startActivity(intent)
            finish()

        }

        user = findViewById(R.id.username)
        pass = findViewById(R.id.password)
        check = findViewById(R.id.checkBoxRememberMe)


    }

    fun login() {
        val username =  user.text.toString()
        val password = pass.text.toString()
        val checkbox = check

        if(username.isNotEmpty() && password.isNotEmpty()){



            val request = ServiceBuilder.buildService(EndPoints::class.java)
            val call = request.login(username, password)
            call.enqueue(object :Callback<Iflogin> {
                override fun onResponse(call: Call<Iflogin>, response: Response<Iflogin>) {
                    if (response.isSuccessful) {

                        val safe : Iflogin=response.body()!!
                        Toast.makeText(this@MainLogin,safe.MSG,Toast.LENGTH_SHORT).show()

                        if (safe.status == true ) {
                            val sharedPreferences = getSharedPreferences(getString(R.string.spf), Context.MODE_PRIVATE)
                            with(sharedPreferences.edit()) {
                                putString(R.string.utilizador.toString(), username)
                                putInt(R.string.userid.toString(),response.body()!!.id)
                                putBoolean(getString(R.string.remember),checkbox.isChecked)
                                putInt(R.string.id_sh.toString(), safe.id)
                                commit()


                            }
                            val intent = Intent(this@MainLogin, MapsActivity::class.java)
                            intent.putExtra("id", safe.id)
                            startActivity(intent)
                            finish()
                        }

                    }

                }

                override fun onFailure(call: Call<Iflogin>, t: Throwable) {
                    Toast.makeText(this@MainLogin,"${t.message}",Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}