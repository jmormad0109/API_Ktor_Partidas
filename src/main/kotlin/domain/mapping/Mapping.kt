package domain.mapping

import data.persistence.partidas.PartidaDao
import data.persistence.usuarios.UsuarioDao
import domain.models.partidas.Partida
import domain.models.partidas.Resultado
import domain.models.usuarios.UpdateUsuario
import domain.models.usuarios.Usuario

fun PartidaDaoToPartida (partidaDao: PartidaDao) : Partida {
    val partida = Partida(
        partidaDao.nombrePartida,
        partidaDao.resultado.toResultado(),
        partidaDao.estadistica,
        partidaDao.fecha
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

fun UsuarioDaoToUsuario(usuarioDao: UsuarioDao): Usuario{
    val usuario = Usuario(
        usuarioDao.name,
        usuarioDao.dni,
        usuarioDao.email,
        usuarioDao.password,
        usuarioDao.token
    )
    return usuario
}

fun Usuario.toUpdateUsuario(): UpdateUsuario {
    return UpdateUsuario(
        dni = dni,
        name = name,
        email = email,
        password = password,
        token = token
    )
}

fun UpdateUsuario.toUsuario(): Usuario {
    return Usuario(
        dni = dni!!,
        name = name!!,
        email = email!!,
        password = password!!,
        token = token!!
    )
}