package domain.usecase.partidas

import domain.models.partidas.Partida
import domain.repository.PartidaInterface

class InsertPartidaUseCase(val repository: PartidaInterface) {
    var partida: Partida? = null

    suspend operator fun invoke(userId: Int): Boolean{
        partida?.let {
            it.usuarioId = userId
            return repository.postPartida(it)
        } ?: return false
    }
}