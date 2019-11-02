package app.foodin.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.nio.charset.Charset
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter





class JwtCreator {

    // Sample method to construct a JWT
    companion object {

        // The secret key. This should be in a property file NOT under source
// control and not hard coded in real life. We're putting it here for
// simplicity.

        fun createJWT(id: String, issuer: String, audience: String, keyId: String, subject: String, ttlMillis: Long): String {

            val SECRET_KEY = JwtCreator::class.java.getResource("/client_key.txt").readText(Charset.forName("UTF-8"));
//            val SECRET_KEY = "MIGTAgEA49AwEHBHkwdwIBAQQgQUwc+DG9Q7pDILxY" +
//                    "o784B53QXHTK1xehRANCAARot6TCmCoPXUCH" +
//                    "ASXvLNyQ0y7iJb5PQRzN2kg/+zWNYdRt0HT17n" +
//                    "aVGy95us"
            // The JWT signature algorithm we will be using to sign the token
            val signatureAlgorithm = SignatureAlgorithm.ES256

            val nowMillis = System.currentTimeMillis()
            val now = Date(nowMillis)

            // We will sign our JWT with our ApiKey secret
            val apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY)
            val privateKey = keyToValue(apiKeySecretBytes)
            val signingKey = SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.jcaName)

            // Let's set the JWT Claims
            val builder = Jwts.builder().setId(id)
                    .setIssuedAt(now)
                    .setAudience(audience)
                    .setSubject(subject)
                    .setIssuer(issuer)
                    .signWith(privateKey,SignatureAlgorithm.ES256)
                    .setHeaderParam("kid", keyId)

            // if it has been specified, let's add the expiration
            if (ttlMillis >= 0) {
                val expMillis = nowMillis + ttlMillis
                val exp = Date(expMillis)
                builder.setExpiration(exp)
            }

            // Builds the JWT and serializes it to a compact, URL-safe string
            return builder.compact()
        }

        fun decodeJWT(jwt: String): Claims {

            // This line will throw an exception if it is not a signed JWS (as expected)
            return Jwts.parser()
//                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJwt(jwt).getBody()
        }

        fun  keyToValue( pkcs8key : ByteArray) : PrivateKey
        {

            val spec = PKCS8EncodedKeySpec(pkcs8key);
            val factory = KeyFactory.getInstance("EC");
            val privateKey = factory.generatePrivate(spec);
            return privateKey;
        }
    }
}