package ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveScaffold
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveWidget
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import kotlinx.coroutines.launch
import missilegame.composeapp.generated.resources.Res
import missilegame.composeapp.generated.resources.intro_welcome_message
import missilegame.composeapp.generated.resources.intro_welcome_title
import missilegame.composeapp.generated.resources.next
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import platform.platformIntroPages

@OptIn(
    ExperimentalAdaptiveApi::class,
    ExperimentalResourceApi::class,
    ExperimentalFoundationApi::class
)
class IntroScreen : Screen {
    private val commonPagesCount = 1

    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        val pagerState = rememberPagerState { commonPagesCount + platformIntroPages.size }

        AdaptiveScaffold(
            floatingActionButton = {
                AdaptiveWidget(
                    material = {
                        FloatingActionButton(
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                                contentDescription = stringResource(Res.string.next)
                            )
                        }
                    },
                    cupertino = {}
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues)
            ) {
                HorizontalPager(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    state = pagerState
                ) { page ->
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        when {
                            // First page, welcome
                            page == 0 -> WelcomePage()
                            // All the platform-specific intro pages
                            page - commonPagesCount < platformIntroPages.size -> with(
                                platformIntroPages[page - commonPagesCount]
                            ) {
                                Content()
                            }
                            // Last page, final
                            else -> FinalPage()
                        }
                    }
                }
                AdaptiveWidget(
                    material = {},
                    cupertino = { /* TODO: Draw bottom bar with next button */ }
                )
            }
        }
    }

    @Suppress("UnusedReceiverParameter")
    @Composable
    fun ColumnScope.WelcomePage() {
        Text(
            text = stringResource(Res.string.intro_welcome_title),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(top = 64.dp),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(Res.string.intro_welcome_message),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(top = 12.dp),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }

    @Suppress("UnusedReceiverParameter")
    @Composable
    fun ColumnScope.FinalPage() {

    }
}
