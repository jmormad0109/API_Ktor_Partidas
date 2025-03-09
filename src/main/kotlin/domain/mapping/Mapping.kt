package domain.mapping

import data.persistence.partidas.PartidaDao
import data.persistence.usuarios.UsuarioDao
import domain.models.partidas.Partida
import domain.models.partidas.PartidaSinDni
import domain.models.partidas.Resultado
import domain.models.partidas.UpdatePartida
import domain.models.usuarios.UpdateUsuario
import domain.models.usuarios.Usuario


// TODO mapeo de partidas

fun PartidaDaoToPartida (partidaDao: PartidaDao) : Partida {
    val partida = Partida(
        partidaDao.nombrePartida,
        partidaDao.resultado.toResultado(),
        partidaDao.estadistica,
        partidaDao.fecha,
        partidaDao.dniUsuario
    )

    return partida
}

fun PartidaDaoToPartidaSinDni(partidaDao: PartidaDao): PartidaSinDni {
    val partidaSinDni = PartidaSinDni(
        partidaDao.nombrePartida,
        partidaDao.resultado.toResultado(),
        partidaDao.estadistica,
        partidaDao.fecha
    )

    return partidaSinDni
}

fun String.toResultado(): Resultado {
    return try {
        Resultado.valueOf(this)
    }catch (e: IllegalArgumentException){
        Resultado.PERDIDA
    }
}

fun Partida.toUpdatePartida(): UpdatePartida{
    return UpdatePartida(
        nombrePartida = nombrePartida,
        resultado = resultado,
        estadistica = estadistica,
        fecha = fecha,
        dniUsuario = dniUsuario
    )
}

fun Partida.toPartidaSinDni(): PartidaSinDni{
    return PartidaSinDni(
        nombrePartida = nombrePartida,
        resultado = resultado,
        estadistica = estadistica,
        fecha = fecha
    )
}

fun UpdatePartida.toPartidaSinDni(): PartidaSinDni{
    return PartidaSinDni(
        nombrePartida = nombrePartida!!,
        resultado = resultado!!,
        estadistica = estadistica!!,
        fecha = fecha!!
    )
}

// TODO mapeo de usuarios


fun Usuario.toUpdateUsuario(): UpdateUsuario {
    return UpdateUsuario(
        dni = dni,
        name = name,
        email = email,
        password = password,
        urlImg = urlImg,
        token = token
    )
}

fun UpdateUsuario.toUsuario(): Usuario {
    return Usuario(
        name = name!!,
        dni = dni!!,
        email = email!!,
        password = password!!,
        urlImg = urlImg!!,
        token = token!!
    )
}

fun UsuarioDao.toUsuario(): Usuario{
    val usuario = Usuario(
        this.name,
        this.dni,
        this.email,
        this.password,
        this.urlImg?: "null",
        this.token?: "null"
    )
    return usuario
}