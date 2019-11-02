package app.foodin

import app.foodin.common.utils.JwtUtils
import app.foodin.config.JwtCreator.Companion.createJWT
import app.foodin.config.JwtCreator.Companion.decodeJWT
import junit.framework.Assert.assertEquals
import org.junit.Test

class JwtCreatorTest {

    /***
     * const headers = {
    kid: process.env.KEY_ID,
    typ: undefined // is there another way to remove type?
    }
    const claims = {
    'iss': process.env.TEAM_ID,
    'aud': 'https://appleid.apple.com',
    'sub': process.env.CLIENT_ID,
    }
     */
    @Test
    fun parse() {

        val jwt = "eyJraWQiOiJBSURPUEsxIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoiYXBwLmZvb2Rpbi5jbGllbnQiLCJleHAiOjE1NzIxMDQyNDAsImlhdCI6MTU3MjEwMzY0MCwic3ViIjoiMDAxNzI4LjFmMWJkYTk4YTc1ZTRjYTFiOWNjZTgwMWYyZmIzMzY3LjE1MjAiLCJjX2hhc2giOiI5eHlYaEpRWW1Ta3I5LXJaR2kyWlhRIiwiZW1haWwiOiJmb29kaW5AZm9vZGluLmFwcCIsImVtYWlsX3ZlcmlmaWVkIjoidHJ1ZSIsImF1dGhfdGltZSI6MTU3MjEwMzY0MH0.QwFCcK6FV8iKR5Av36SdgWfg4YVliLSF-p8hSfpFelC4OHSOQi23GYvS6qP0JFzFNG0axwIvgCRlxBeOBHlr2N9ebbTKXtalJDD8Qcmmz7MCnc0BwmF84QCVyF32TODxVRBfst6xUTl-vTDT1NgSrSnZ2042AvCfMe9ZnPnHjvnXhtsigsLJIdQ0PmPACL3J7iy9cVGMcxV3U15LwB4BiHn4-ZpI0a0zXeTAUMZ-rbmoixY8o364Fyv07py2KQzr-S3kzaZvBdFDbSx9aLGGSS1ht0vzJPyngfTguZmEBnDEIS1fuzFjY5fQGtsES9y3mVfgZY3id_bTS5UqtCRopA"
        print(JwtUtils.decoded(jwt))
    }

    @Test
    fun create() {
        val jwtId = "SOMEID1234"
        val keyId = "J75U62Y2Z7"
        val jwtIssuer = "G47VD4R777" // team ID
        val jwtAud = "https://appleid.apple.com"
        val jwtSubject = "app.foodin.client" // clientId
        val jwtTimeToLive = 800000L

        val jwt = createJWT(
                jwtId, // claim = jti
                jwtIssuer, // claim = iss
                jwtAud, // claim = iss
                keyId,
                jwtSubject, // claim = sub
                jwtTimeToLive // used to calculate expiration (claim = exp)
        )

        println("jwt = \"" + jwt.toString() + "\"")

        val claims = decodeJWT(jwt)

        println("claims = " + claims.toString())

        assertEquals(jwtId, claims.getId())
        assertEquals(jwtIssuer, claims.getIssuer())
        assertEquals(jwtSubject, claims.getSubject())
    }
}