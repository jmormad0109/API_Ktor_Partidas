package domain.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.jwt.*
import kotlin.math.log

object JwtConfig {
    private const val secret = "Secreto_123"
    private const val issuer = "domain.com"
    private const val audience = "ktor_audience"
    private const val realm = "ktor_realm"
    private val algorithm = Algorithm.HMAC256(secret)

    fun generateToken(userId: Int): String {
        return JWT.create()
            .withIssuer(issuer)
            .withAudience(audience)
            .withSubject("Authentication")
            .withClaim("userId", userId)
            .withClaim("time", System.currentTimeMillis())
            .sign(algorithm)
    }

    fun configureAuthentication(config: JWTAuthenticationProvider.Config) {
        config.realm = realm
        config.verifier(
            JWT.require(algorithm)
                .withIssuer(issuer)
                .withAudience(audience)
                .build()
        )
        config.validate { credential ->
            if (credential.payload.getClaim("userId").asInt() != null) {
                JWTPrincipal(credential.payload)
            } else null
        }
    }

    fun getUserIdFromToken(token: String): Int?{
        return try{
            JWT.decode(token).getClaim("userId").asInt()
        }catch(e: Exception){
            null
        }
    }
}
