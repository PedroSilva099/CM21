package ipvc.estg.CM21

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class AddNota : AppCompatActivity() {


    private lateinit var tituloText: EditText
    private lateinit var descricaoText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_nota)

        tituloText = findViewById(R.id.titulo)
        descricaoText = findViewById(R.id.descricao)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(tituloText.text) || TextUtils.isEmpty(descricaoText.text)  ) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                replyIntent.putExtra(EXTRA_REPLY_titulo, tituloText.text.toString())
                replyIntent.putExtra(EXTRA_REPLY_descricao, descricaoText.text.toString())
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
        val button_back=findViewById<Button>(R.id.voltar)
        button_back.setOnClickListener{
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY_titulo = "com.example.android.titulo"
        const val EXTRA_REPLY_descricao = "com.example.android.descricao"
    }
}