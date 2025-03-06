package domain.usecase.partidas

import domain.models.partidas.Partida
import domain.models.partidas.Resultado
import domain.repository.PartidaInterface

class GetPartidaByResultadoUseCase(val repository: PartidaInterface) {
    var resultado: Resultado? = null

    suspend operator fun invoke(dniUsuario: String): List<Partida>{
        return resultado?.let {
            repository.getPartidasByResultado(it, dniUsuario)
        }?:run {
            emptyList()
        }
    }
}