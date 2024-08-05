package uz.otamurod.mytaxi.presentation.ui.home

import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import org.koin.androidx.compose.koinViewModel
import uz.otamurod.mytaxi.presentation.ui.home.HomeScreenReducer.HomeEffect
import uz.otamurod.mytaxi.presentation.util.compose.rememberFlowWithLifecycle

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel: HomeViewModel = koinViewModel()

        val state = viewModel.state.collectAsStateWithLifecycle()
        val effect = rememberFlowWithLifecycle(viewModel.effect)
        val context = LocalContext.current

        LaunchedEffect(effect) {
            effect.collect {
                when (it) {
                    is HomeEffect.ShowToast -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }

                    is HomeEffect.ShowSnackBar -> {
                        // TODO: show Snackbar
                    }
                }
            }
        }

        Text(
            text = "Current Location at ${state.value.userLiveLocation?.timestamp} = ${state.value.userLiveLocation?.latitude}, ${state.value.userLiveLocation?.longitude}"
        )

        state.value.userLiveLocation?.let { location ->
            viewModel.saveLocation(location)
        }
    }
}