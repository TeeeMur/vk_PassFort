import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.passfort.repository.NetworkChecker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NetworkCheckerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : NetworkChecker {
    override fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}
