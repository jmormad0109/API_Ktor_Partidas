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



    suspend fun getAllPartidas(dniUsuario: String) = getAllPartidasUseCase(dniUsuario)

    suspend fun getPartidasByNombre(nombre: String, dniUsuario: String): Partida?{
        if (nombre.isNullOrBlank()){
            return null
        }
        getPartidasByNombreUsecase.nombre = nombre
        val part = getPartidasByNombreUsecase(dniUsuario)

        if (part == null){
            return null
        }else{
            return part
        }
    }

    suspend fun getPartidasByResultado(resultado: Resultado, dniUsuario: String): List<Partida>{
        getPartidaByResultadoUseCase.resultado = resultado
        return getPartidaByResultadoUseCase(dniUsuario)
    }


    suspend fun insertPartida(partida: Partida?, dniUsuario: String): Partida?{
        if (partida == null){
            return null
        }
        insertPartidaUseCase.partida = partida
        val res = insertPartidaUseCase(dniUsuario)

        if (res != null){
            return getPartidasByNombre(res.nombrePartida, dniUsuario)
        }else{
            return null
        }
    }

    suspend fun updatePartida(updatePartida: UpdatePartida?, nombre: String, dniUsuario: String): Partida?{
        if (updatePartida == null){
            return null
        }

        updatePartidaUseCae.nuevaPartida = updatePartida
        updatePartidaUseCae.nombre = nombre
        return updatePartidaUseCae(dniUsuario)
    }

    suspend fun deletePartida(nombre: String, dniUsuario: String): Partida?{
        deletePartidauseCase.nombre = nombre
        return deletePartidauseCase(dniUsuario)
    }

}