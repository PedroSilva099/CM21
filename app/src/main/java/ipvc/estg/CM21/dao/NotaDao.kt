package ipvc.estg.CM21.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ipvc.estg.CM21.entities.Nota

@Dao
interface NotaDao {

    @Query("SELECT * FROM nota_table ORDER BY id ASC")
    fun getAlphabetizedWords(): LiveData<List<Nota>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert (nota: Nota)


    @Query("DELETE FROM nota_table where id = :id")
    suspend fun delete(id:Int)

    @Query("UPDATE  nota_table SET titulo = :titulo , descricao = :descricao where id = :id")
    suspend fun update(id:Int, titulo :String, descricao: String)

}