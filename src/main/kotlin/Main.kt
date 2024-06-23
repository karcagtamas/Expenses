import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.koin.core.context.startKoin
import screen.home.HomeScreen

@Composable
@Preview
fun App() {
    initKoin()

    MaterialTheme {
        Navigator(HomeScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Expenses") {
        App()
    }
}

fun initKoin() {
    startKoin {
        modules()
    }
}


