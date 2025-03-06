package domain.usecase.partidas

import domain.models.partidas.Partida
import domain.repository.PartidaInterface

class DeletePartidauseCase(val repository: PartidaInterface) {

    var nombre: String? = null

    suspend operator fun invoke(dniUsuario: String): Partida? {
        if (nombre == null){
            return null
        }else{
            return repository.deletePartida(nombre!!, dniUsuario)
        }
    }
}