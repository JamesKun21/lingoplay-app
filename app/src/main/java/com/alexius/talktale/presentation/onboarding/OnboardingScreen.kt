package com.alexius.talktale.presentation.onboarding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alexius.talktale.R
import com.alexius.talktale.presentation.common.OnboardingPanelDisplay
import com.alexius.talktale.presentation.navgraph.Route
import com.alexius.talktale.presentation.navgraph_entry.NavGraphEntry
import com.alexius.talktale.presentation.sign_in.SignInScreen
import com.alexius.talktale.ui.theme.TalkTaleTheme

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onSignInButtonClick: () -> Unit,
    onSignUpButtonClick: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.OnBoarding1Screen.route
    ){
        composable(Route.OnBoarding1Screen.route){
            OnboardingPanelDisplay(
                imageDrawable = R.drawable.girl_reading,
                title = "Pilih Ceritamu, Mulai Petualangan Seru",
                description = "Ambil cerita dari rak ajaib dan belajar bahasa Inggris sambil menikmati kisah seru!",
                onClickMainButton = {navigateTo(navController, Route.OnBoarding2Screen.route)},
                mainButtonText = "Lanjut",
                underButtonContent = {
                    Text(
                        text = "Lewati",
                        modifier = modifier
                            .fillMaxWidth()
                            .clickable(onClick = {
                                navigateTo(
                                    navController,
                                    Route.OnBoarding3Screen.route
                                )
                            }),
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary),
                        textAlign = TextAlign.Center
                    )
                },
            )
        }

        composable(Route.OnBoarding2Screen.route){
            OnboardingPanelDisplay(
                imageDrawable = R.drawable.girl_pencil,
                title = "Jawab Pertanyaan Sambil Menjelajah",
                description = "Ikuti kuis seru dan tantangan menarik di setiap cerita, sambil mengasah kemampuanmu di setiap langkah!",
                onClickMainButton = {
                    navController.navigate(Route.OnBoarding3Screen.route)
                },
                mainButtonText = "Lanjut"
            )
        }

        composable(Route.OnBoarding3Screen.route){
            OnboardingPanelDisplay(
                imageDrawable = R.drawable.girl_writing,
                title = "Kenali Word Wizardmu",
                description = "Belajar memperbaiki grammar dan kosakata kamu, membuatmu lebih jago di setiap cerita!",
                onClickMainButton = {
                    onSignInButtonClick
                },
                mainButtonText = "Masuk",
                underButtonContent = {
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ){
                        Text(
                            text = "Belum punya akun?",
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = " Daftar",
                            modifier = modifier.clickable(onClick = {onSignUpButtonClick()}),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            )
        }
    }
}

private fun navigateTo(navController: NavController, route: String) {
    navController.navigate(route) {
        launchSingleTop = true
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun OnBoarding3Prev() {
    TalkTaleTheme {
        OnboardingPanelDisplay(
            imageDrawable = R.drawable.girl_writing,
            title = "Kenali Word Wizardmu",
            description = "Belajar memperbaiki grammar dan kosakata kamu, membuatmu lebih jago di setiap cerita!",
            onClickMainButton = {

            },
            mainButtonText = "Masuk",
            underButtonContent = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ){
                    Text(
                        text = "Belum punya akun?",
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = " Daftar",
                        modifier = Modifier.clickable(onClick = {}),
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary),
                        textAlign = TextAlign.Center
                    )
                }
            }
        )
    }
}