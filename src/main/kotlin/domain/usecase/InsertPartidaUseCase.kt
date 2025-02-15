package domain.usecase

import domain.models.partidas.Partida
import domain.repository.PartidaInterface

class InsertPartidaUseCase(val repository: PartidaInterface) {
    var partida: Partida? = null

    suspend operator fun invoke(): Boolean{
        if (partida == null){
            return false
        }else{
            repository.postPartida(partida!!)
            return true
        }
    }
}