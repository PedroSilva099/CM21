package ipvc.estg.CM21

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import androidx.appcompat.app.AppCompatActivity
import ipvc.estg.CM21.api.EndPoints
import ipvc.estg.CM21.api.Report
import ipvc.estg.CM21.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImagePreview : AppCompatActivity() {
    private lateinit var report: List<Report>
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var image: ImageView
    private lateinit var backMap: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        title = findViewById(R.id.titulo)
        description = findViewById(R.id.descricao)
        image = findViewById(R.id.image)

        backMap = findViewById(R.id.back)
        backMap.setOnClickListener {
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
                    report = response.body()!!
                    for (rep in report) {
                        description.setText(rep.descricao)
                        title.setText(rep.titulo)
                        Picasso.with(this@ImagePreview)
                                .load("\"https://citizencity.000webhostapp.com/myslim/api/uploads/" + rep.image + ".png")
                                .into(image)
                    }
                }
            }

            override fun onFailure(call: Call<List<Report>>, t: Throwable) {
                Toast.makeText(this@ImagePreview, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}