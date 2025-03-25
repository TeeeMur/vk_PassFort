import androidx.compose.runtime.Composable
import com.example.passfort.navigation.Screen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.passfort.navigation.PasswordGeneratorScreen
import com.example.passfort.screen.auth.LoginScreen
import com.example.passfort.screen.auth.RegisterScreen
import com.example.passfort.screen.main.HomeScreen

@Composable
fun NavigationGraph(navController: NavHostController, UserLoggedIn: Boolean) {
    // Если добавим Пинкод
    //NavHost(navController = navController, startDestination = if (UserLoggedIn) Screen.PinCode.route else Screen.Login.route) {

    NavHost(navController = navController, startDestination = if (UserLoggedIn) Screen.HomeScreen.route else Screen.Login.route) {

        composable(Screen.Login.route) {
            LoginScreen(
                //lonLoginSuccess = { navController.navigate(Screen.HomeScreen.route) }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                //onRegisterSuccess = { navController.popBackStack() }
            )
        }

        composable(Screen.HomeScreen.route) {
            //HomeScreen(navController)
        }

        composable(Screen.PasswordGenerator.route) {
            PasswordGeneratorScreen()
        }
    }
}


