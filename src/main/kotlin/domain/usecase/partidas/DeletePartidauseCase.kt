package domain.usecase.partidas

import domain.repository.PartidaInterface

class DeletePartidauseCase(val repository: PartidaInterface) {

    var nombre: String? = null

    suspend operator fun invoke(): Boolean {
        if (nombre == null){
            return false
        }else{
            return repository.deletePartida(nombre!!)

        }
    }
}