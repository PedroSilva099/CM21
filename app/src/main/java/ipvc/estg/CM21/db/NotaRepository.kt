package ipvc.estg.CM21.db

import android.provider.SyncStateContract.Helpers.update
import androidx.lifecycle.LiveData
import ipvc.estg.CM21.dao.NotaDao
import ipvc.estg.CM21.entities.Nota



class NotaRepository(private val notaDao: NotaDao) {

    val allNotas: LiveData<List<Nota>> = notaDao.getAlphabetizedWords()


    suspend fun insert(nota: Nota) {
        notaDao.insert(nota)
    }

    suspend fun delete(id:Int){
        notaDao.delete(id)
    }
    suspend fun update(id:Int, titulo :String, descricao: String){
        notaDao.update(id, titulo, descricao)
    }
}