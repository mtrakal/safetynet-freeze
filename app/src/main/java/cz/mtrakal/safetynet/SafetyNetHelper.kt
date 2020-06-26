package cz.mtrakal.safetynet

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.safetynet.SafetyNet
import kotlinx.coroutines.suspendCancellableCoroutine
import java.nio.charset.Charset
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.coroutines.resume

object SafetyNetHelper {

    private const val ENCRYPTION_ALGORITHM = "SHA-256"
    private const val SAFETY_NET_API_KEY = "TODO_PROVIDE_KEY"

    suspend fun checkSafetyNet(context: Context, nonce: String) = suspendCancellableCoroutine<Boolean> { continuation ->
        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS) {
            val encryptedNonce = nonce.encrypt(ENCRYPTION_ALGORITHM)

            SafetyNet.getClient(context).attest(encryptedNonce, SAFETY_NET_API_KEY)
                .addOnSuccessListener { continuation.resume(true) }
                .addOnFailureListener { continuation.resume(true) }
        } else {
            continuation.resume(true)
        }
    }
}

@Throws(NoSuchAlgorithmException::class)
fun String.encrypt(algorithm: String, charset: Charset = Charsets.UTF_8) = toByteArray(charset).encrypt(algorithm)

@Throws(NoSuchAlgorithmException::class)
fun ByteArray.encrypt(algorithm: String) = MessageDigest.getInstance(algorithm).let {
    it.update(this)
    it.digest()
}