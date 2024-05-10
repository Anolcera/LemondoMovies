package anolcera.myapplication

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import anolcera.myapplication.navigation.AppNavHost


@Composable
internal fun App(navController: NavHostController = rememberNavController()) {
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) {
        AppNavHost(
            modifier = Modifier
                .consumeWindowInsets(it)
                .fillMaxSize(),
            navController = navController,
        )
    }
}