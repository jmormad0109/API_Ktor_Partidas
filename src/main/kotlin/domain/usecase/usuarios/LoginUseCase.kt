package domain.usecase.usuarios

import data.persistence.repository.PersistenceUsuarioRepository

class LoginUseCase(val repository: PersistenceUsuarioRepository) {

    suspend operator fun invoke(dni: String?, password: String?): Boolean{
        if (dni.isNullOrBlank() || password.isNullOrBlank())
            return false
        else{
            try {
                val res = repository.login(dni, password)
                return res
            }catch (e: Exception){
                println("Error en el login: ${e.localizedMessage}")
                return false
            }
        }
    }
}