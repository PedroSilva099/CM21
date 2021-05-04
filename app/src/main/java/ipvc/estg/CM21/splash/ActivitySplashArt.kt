package ipvc.estg.CM21.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ipvc.estg.CM21.R
import ipvc.estg.CM21.MainActivity
import ipvc.estg.CM21.MainLogin
import ipvc.estg.CM21.MapsActivity
import java.lang.Exception

class ActivitysplashArt : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val background = object : Thread() {
            override fun run(){
                try {
                    Thread.sleep(3000)

                    val intent= Intent(baseContext, MainLogin::class.java)
                    startActivity(intent)

                }catch (e:Exception){
                    e.printStackTrace()
                }
            }

        }

        background.start()
    }
}