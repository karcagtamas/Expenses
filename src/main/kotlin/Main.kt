import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowSize
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.koin.core.context.startKoin
import screen.home.HomeScreen
import ui.theme.AppTheme

@Composable
@Preview
fun App() {
    initKoin()

    AppTheme {
        Navigator(HomeScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Expenses",
        state = WindowState(width = 1200.dp, height = 800.dp)
    ) {
        App()
    }
}

fun initKoin() {
    startKoin {
        modules()
    }
}


