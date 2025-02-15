package domain.usecase

import domain.models.partidas.Partida
import domain.repository.PartidaInterface

class GetPartidasByNombre(val repository: PartidaInterface) {
    val nombre: String? = null

    suspend operator fun invoke(): Partida? {
        if (nombre?.isNullOrBlank() == true){
            return null
        }else{
            return repository.getPartidasByNombre(nombre)
        }
    }
}