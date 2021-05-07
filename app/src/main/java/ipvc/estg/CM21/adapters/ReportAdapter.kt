package ipvc.estg.CM21.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.CM21.R
import ipvc.estg.CM21.api.Report

const val TUT="TITLE"
const val DES="DESCRIPTION"

class ReportAdapter(val report: List<Report>): RecyclerView.Adapter<ReportViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_recycler_report, parent, false)
        return ReportViewHolder(view)
    }

    override fun getItemCount(): Int {
        return report.size
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        return holder.bind(report[position])
    }

}

class ReportViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
    private val title: TextView = itemView.findViewById(R.id.tit)
    private val description: TextView = itemView.findViewById(R.id.desc)



    fun bind(report: Report) {
        title.text = report.titulo
        description.text = report.descricao

    }


}