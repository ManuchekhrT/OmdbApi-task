package tj.test.omdbapi.extensions

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable

const val OMDB_API_KEY = "f6e9dfe5"

fun View.show() {
    this.isVisible = true
}

fun View.hide() {
    this.isVisible = false
}

fun View.showSnackBar(message: String? = null, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, message ?: "", duration).show()
}

fun Context.showToast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}
