package anolcera.myapplication

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import anolcera.myapplication.navigation.AppNavHost


@Composable
internal fun App(
    appViewModel: AppViewModel,
    navController: NavHostController = rememberNavController()
) {

    val isOffline by appViewModel.isOffline.collectAsStateWithLifecycle()

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(key1 = isOffline) {
        if (isOffline) {
            snackBarHostState.showSnackbar(
                message = "No Internet Connection",
                duration = SnackbarDuration.Indefinite

            )
        } else {
            snackBarHostState.currentSnackbarData?.dismiss()
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier
                    .padding(bottom = 12.dp),
                hostState = snackBarHostState,
            ) {
                Snackbar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    actionOnNewLine = true,
                ) {
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = it.visuals.message,
                        )
                    }
                }
            }
        }
    ) {
        AppNavHost(
            modifier = Modifier
                .consumeWindowInsets(it)
                .fillMaxSize(),
            navController = navController,
        )
    }
}