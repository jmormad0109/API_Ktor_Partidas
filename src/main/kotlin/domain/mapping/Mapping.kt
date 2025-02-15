package domain.mapping

import data.persistence.partidas.PartidaDao
import domain.models.partidas.Partida
import domain.models.partidas.Resultado

fun PartidaDaoToPartida (partidaDao: PartidaDao) : Partida {
    val partida = Partida(
        partidaDao.nombrePartida,
        partidaDao.resultado.toResultado(),
        partidaDao.estadistica,
        partidaDao.fecha,
        partidaDao.token?: ""
    )

    return partida
}

fun String.toResultado(): Resultado {
    return try {
        Resultado.valueOf(this)
    }catch (e: IllegalArgumentException){
        Resultado.PERDIDA
    }
}