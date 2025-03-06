package domain.usecase.partidas

import domain.models.partidas.Partida
import domain.models.usuarios.Usuario
import domain.repository.PartidaInterface

class InsertPartidaUseCase(val repository: PartidaInterface) {
    var partida: Partida? = null

    suspend operator fun invoke(dniUsuario: String): Partida?{
        if (partida == null){
            return null
        }else{
            return repository.postPartida(partida!!, dniUsuario)

        }
    }
}