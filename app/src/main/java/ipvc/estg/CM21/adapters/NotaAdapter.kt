package ipvc.estg.CM21.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.CM21.R
import ipvc.estg.CM21.entities.Nota

class NotaAdapter  internal constructor(
        context: Context, var clickListener: OnNotaClickListener
) : RecyclerView.Adapter<NotaAdapter.NotaViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notas = emptyList<Nota>()

    class NotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notaItemView: TextView = itemView.findViewById(R.id.titulo)
        val descricaoItemView: TextView = itemView.findViewById(R.id.descricao)

        fun initialize(nota: Nota, action: OnNotaClickListener){
            notaItemView.text=nota.titulo
            descricaoItemView.text=nota.descricao

            itemView.setOnClickListener{
                action.onItemClick(nota, adapterPosition)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val itemView = inflater.inflate(R.layout.recycler_item, parent, false)
        return NotaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val current = notas[position]
        holder.notaItemView.text =  current.titulo
        holder.descricaoItemView.text= current.descricao

        holder.initialize(notas.get(position),clickListener)

    }

    internal fun setNotas( notas : List<Nota>) {
        this.notas = notas
        notifyDataSetChanged()
    }

    override fun getItemCount() =notas.size

    interface OnNotaClickListener{
        fun onItemClick(nota: Nota, position: Int)
    }

}