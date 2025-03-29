import androidx.compose.runtime.Composable
import com.example.passfort.navigation.Screen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.passfort.navigation.PasswordGeneratorScreen
import com.example.passfort.screen.auth.LoginScreen
import com.example.passfort.screen.auth.RegisterScreen
import com.example.passfort.screen.main.HomeScreen
import com.example.passfort.screen.passwords.AddPasswordScreen
import com.example.passfort.screen.passwords.PasswordListScreen
import com.example.passfort.screen.passwords.SettingsScreen

@Composable
fun NavigationGraph(navController: NavHostController, UserLoggedIn: Boolean) {
    val startDestination = when {
        !UserLoggedIn -> Screen.Login.route
        //Pin -> Screen.PinCode.route     Если добавим Пинкод
        else -> Screen.HomeScreen.route
    }
    NavHost(navController = navController, startDestination) {

        composable(Screen.Login.route) {
            LoginScreen()
        }

        composable(Screen.Register.route) {
            RegisterScreen()
        }

        composable(Screen.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(Screen.PasswordGenerator.route) {
            PasswordGeneratorScreen(navController)
        }
        composable(Screen.AddPassword.route) {
            AddPasswordScreen(navController)
        }
        composable(Screen.PasswordList.route) {
            PasswordListScreen(navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }
}




