package ipvc.estg.CM21.report

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ipvc.estg.CM21.MapsActivity
import ipvc.estg.CM21.api.EndPoints
import ipvc.estg.CM21.api.Report
import ipvc.estg.CM21.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportPreview : AppCompatActivity() {
/*
    private lateinit var ocorrencia: List<Report>
    private lateinit var desc: TextView
    private lateinit var tit: TextView
    //private lateinit var tipo: TextView
    //private lateinit var user: TextView
    private lateinit var foto: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_ocorrencia)

        desc = findViewById(R.id.ocodescriptionV)
        tit = findViewById(R.id.ocotitleV)
        foto = findViewById(R.id.imageView2)
        //tipo = findViewById(R.id.textView4)
        //user = findViewById(R.id.textView6)

        val buttonBack = findViewById<Button>(R.id.buttonBackVer)
        buttonBack.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
            finish()
        }

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getReports()
        call.enqueue(object : Callback<List<Report>> {
            override fun onResponse(
                    call: Call<List<Report>>,
                    response: Response<List<Report>>
            ) {
                if (response.isSuccessful) {
                    ocorrencia = response.body()!!
                    for (ocorr in ocorrencia) {
                        desc.setText(ocorr.descricao)
                        tit.setText(ocorr.titulo)
                        //tipo.setText(ocorr.tipo_id)
                        //user.setText(ocorr.users_id.toString())
                        Picasso.with(this@ReportPreview)
                                .load("https://cidintdiogo.000webhostapp.com/myslim/API/uploads/" + ocorr.foto + ".png")
                                .into(foto)
                    }
                }
            }

            override fun onFailure(call: Call<List<Report>>, t: Throwable) {
                Toast.makeText(this@ReportPreview, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
*/
}

