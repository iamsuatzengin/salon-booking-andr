package com.zapplications.salonbooking.core

import android.graphics.Bitmap
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.serialization.json.Json

const val WIDTH = 512
const val HEIGHT = 512

/**
 * Generates a QR code bitmap representing the given object.
 *
 * This function uses Kotlin Serialization to encode the input object [T] into a JSON string.
 * It then uses the zxing library (via `BarcodeEncoder`) to generate a QR code bitmap from the JSON string.
 *
 * ```
 * @Serializable
 * data class MyData(val name: String, val age: Int)
 *
 * val data = MyData("John Doe", 30)
 * val qrCodeBitmap: Bitmap? = data.qrCode()
 *
 * if (qrCodeBitmap != null) {
 *     // Use the QR code bitmap
 *     imageView.setImageBitmap(qrCodeBitmap)
 * } else {
 *     // Handle the error
 *     println("Failed to generate QR code")
 * }
 * ```
 *  @receiver The object to be encoded into a QR code. This object must be serializable by kotlinx.serialization.
 *  @return A [Bitmap] representing the QR code, or `null` if an error occurs during the process.
 *  @throws Exception Any exception thrown during the JSON encoding or QR code generation process will be caught and printed to the stack trace.
 *  @param T The type of the object to be encoded. Must be reified for use with `Json.encodeToString<T>`.
 */
inline fun <reified T> T.qrCode(): Bitmap? = runCatching {
    val json = Json.encodeToString<T>(this)
    val encoder = BarcodeEncoder()
    encoder.encodeBitmap(
        json,
        BarcodeFormat.QR_CODE,
        WIDTH,
        HEIGHT
    )
}.getOrElse {
    it.printStackTrace()
    Firebase.crashlytics.recordException(it)
    null
}
