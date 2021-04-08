 package ipvc.estg.CM21.viewModel

import android.app.Application
import androidx.lifecycle.*
import ipvc.estg.CM21.db.NotaRepository
import ipvc.estg.CM21.db.NotaRoomDatabase
import ipvc.estg.CM21.entities.Nota
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotaViewModel(application: Application): AndroidViewModel(application) {
    private val repository: NotaRepository

    val allNotas: LiveData<List<Nota>>

    init {
        val notasDao = NotaRoomDatabase.getDatabase(application, viewModelScope).NotaDao()
        repository = NotaRepository(notasDao)
        allNotas = repository.allNotas
    }


    fun insert(nota: Nota) = viewModelScope.launch {
        repository.insert(nota)
    }



    fun delete(id:Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(id)
    }

    fun update(id:Int,titulo:String, descricao:String) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(id,titulo, descricao)
    }

}

