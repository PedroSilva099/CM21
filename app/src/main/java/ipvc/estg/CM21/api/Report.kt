package ipvc.estg.CM21.api

data class Report (
        val id: Int?,
        val titulo: String,
        val descricao: String,
        val image: String,
        val latitude: String,
        val longitude: String,
        val user_id: Int?,
        val type_id: Int?

)
