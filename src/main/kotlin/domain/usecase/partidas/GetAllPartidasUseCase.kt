package domain.usecase.partidas

import domain.models.partidas.Partida
import domain.models.partidas.PartidaSinDni
import domain.repository.PartidaInterface

class GetAllPartidasUseCase(val repository: PartidaInterface) {

    suspend operator fun invoke(dniUsuario: String): List<PartidaSinDni> = repository.getAllPartidas(dniUsuario)
}