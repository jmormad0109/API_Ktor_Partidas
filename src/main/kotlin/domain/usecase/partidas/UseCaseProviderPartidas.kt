package domain.usecase.partidas

import data.persistence.repository.PersistencePartidaRepository
import domain.models.partidas.Partida
import domain.models.partidas.Resultado
import domain.models.partidas.UpdatePartida

object UseCaseProviderPartidas {

    private val repository = PersistencePartidaRepository()
    //val logger: Logger = LoggerFactory.getLogger("PartidaUseCaseLogger")

    private val getAllPartidasUseCase = GetAllPartidasUseCase(repository)
    private val getPartidasByNombreUsecase = GetPartidasByNombre(repository)
    private val getPartidaByResultadoUseCase = GetPartidaByResultadoUseCase(repository)
    private val insertPartidaUseCase = InsertPartidaUseCase(repository)
    private val updatePartidaUseCae = UpdatePartidaUseCae(repository)
    private val deletePartidauseCase = DeletePartidauseCase(repository)



    suspend fun getAllPartidas() = getAllPartidasUseCase()

    suspend fun getPartidasByNombre(nombre: String): Partida?{
        if (nombre.isNullOrBlank()){
            return null
        }
        getPartidasByNombreUsecase.nombre = nombre
        val part = getPartidasByNombreUsecase()

        if (part == null){
            return null
        }else{
            return part
        }
    }

    suspend fun getPartidasByResultado(resultado: Resultado): List<Partida>{
        getPartidaByResultadoUseCase.resultado = resultado
        return getPartidaByResultadoUseCase()
    }


    suspend fun insertPartida(partida: Partida?): Boolean{
        if (partida == null){
            return false
        }
        insertPartidaUseCase.partida = partida
        val res = insertPartidaUseCase()

        if (!res){
            return false
        }else{
            return true
        }
    }

    suspend fun updatePartida(updatePartida: UpdatePartida?, nombre: String): Boolean{
        if (updatePartida == null){
            return false
        }

        updatePartidaUseCae.nuevaPartida = updatePartida
        updatePartidaUseCae.nombre = nombre
        return updatePartidaUseCae()
    }

    suspend fun deletePartida(nombre: String): Boolean{
        deletePartidauseCase.nombre = nombre
        return deletePartidauseCase()
    }

}