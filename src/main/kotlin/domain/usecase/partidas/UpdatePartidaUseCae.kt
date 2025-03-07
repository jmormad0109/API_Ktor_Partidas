package domain.usecase.partidas

import domain.mapping.toPartidaSinDni
import domain.models.partidas.Partida
import domain.models.partidas.PartidaSinDni
import domain.models.partidas.UpdatePartida
import domain.models.usuarios.Usuario
import domain.repository.PartidaInterface

class UpdatePartidaUseCae(val repository: PartidaInterface) {

    var nuevaPartida: UpdatePartida? = null
    var nombre: String? = null

    suspend operator fun invoke(dniUsuario: String): Partida? {
        if (nuevaPartida == null || nombre == null){
            return null
        }else{
            return repository.updatePartida(nuevaPartida!!, nombre!!, dniUsuario)

        }
    }
}