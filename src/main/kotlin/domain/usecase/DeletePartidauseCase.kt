package domain.usecase

import domain.repository.PartidaInterface

class DeletePartidauseCase(val repository: PartidaInterface) {

    var nombre: String? = null

    suspend operator fun invoke(): Boolean {
        if (nombre == null){
            return false
        }else{
            repository.deletePartida(nombre!!)
            return true
        }
    }
}