package domain.usecase.partidas

import domain.models.partidas.UpdatePartida
import domain.repository.PartidaInterface

class UpdatePartidaUseCae(val repository: PartidaInterface) {

    var nuevaPartida: UpdatePartida? = null
    var nombre: String? = null

    suspend operator fun invoke(): Boolean{
        if (nuevaPartida == null || nombre == null){
            return false
        }else{
            return repository.updatePartida(nuevaPartida!!, nombre!!)

        }
    }
}