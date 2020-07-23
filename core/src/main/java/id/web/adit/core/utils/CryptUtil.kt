package id.web.adit.core.utils

import android.util.Base64
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object CryptUtil {
    private const val ALGORITHM = "Blowfish"
    private const val MODE = "Blowfish/CBC/PKCS5Padding"
    private const val IV = "abcdefgh"
    private const val KEY = "ADITYA"
    @Throws(
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        InvalidAlgorithmParameterException::class,
        InvalidKeyException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class
    )
    fun encrypt(value: String): String {
        val secretKeySpec = SecretKeySpec(
            KEY.toByteArray(),
            ALGORITHM
        )
        val cipher: Cipher = Cipher.getInstance(MODE)
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, IvParameterSpec(IV.toByteArray()))
        val values: ByteArray = cipher.doFinal(value.toByteArray())
        return Base64.encodeToString(values, Base64.DEFAULT)
    }

    @Throws(
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        InvalidAlgorithmParameterException::class,
        InvalidKeyException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class
    )
    fun decrypt(value: String): String {
        val values: ByteArray = Base64.decode(value, Base64.DEFAULT)
        val secretKeySpec = SecretKeySpec(
            KEY.toByteArray(),
            ALGORITHM
        )
        val cipher: Cipher = Cipher.getInstance(MODE)
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, IvParameterSpec(IV.toByteArray()))
        return String(cipher.doFinal(values))
    }
}
