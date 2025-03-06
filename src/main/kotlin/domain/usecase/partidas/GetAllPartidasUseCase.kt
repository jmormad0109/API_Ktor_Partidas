package domain.usecase.partidas

import domain.models.partidas.Partida
import domain.repository.PartidaInterface

class GetAllPartidasUseCase(val repository: PartidaInterface) {

    suspend operator fun invoke(userId: Int): List<Partida> = repository.getAllPartidas(userId)
}