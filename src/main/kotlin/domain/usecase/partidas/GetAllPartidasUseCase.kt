package domain.usecase.partidas

import domain.models.partidas.Partida
import domain.repository.PartidaInterface

class GetAllPartidasUseCase(val repository: PartidaInterface) {

    suspend operator fun invoke(): List<Partida> = repository.getAllPartidas()
}