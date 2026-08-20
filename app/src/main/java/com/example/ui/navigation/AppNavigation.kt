package com.example.ui.navigation

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.CompleteGrammarScreen
import com.example.ui.screens.DailyPracticeScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LessonInteractiveScreen
import com.example.ui.screens.LevelJourneyScreen
import com.example.ui.screens.MistakesReviewScreen
import com.example.ui.screens.ProfileDashboardScreen
import com.example.ui.screens.SpeakingExamScreen
import com.example.ui.screens.TalkWithAiScreen
import com.example.ui.screens.VocabularyVaultScreen
import com.example.ui.viewmodel.LearningViewModel

object AppRoutes {
    const val HOME = "home"
    const val LEVELS = "levels"
    const val LESSON = "lesson"
    const val TALK_AI = "talk_ai"
    const val GRAMMAR = "grammar"
    const val DAILY_PRACTICE = "daily_practice"
    const val MISTAKES = "mistakes"
    const val EXAM = "exam"
    const val VOCAB = "vocab"
    const val PROFILE = "profile"
}

@Composable
fun AppNavigation(
    viewModel: LearningViewModel,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Request Audio Permission for Microphone
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        // Handled
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    NavHost(
        navController = navController,
        startDestination = AppRoutes.HOME,
        modifier = modifier.fillMaxSize(),
        enterTransition = { fadeIn(animationSpec = tween(250)) },
        exitTransition = { fadeOut(animationSpec = tween(200)) }
    ) {
        composable(AppRoutes.HOME) {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToLevels = { navController.navigate(AppRoutes.LEVELS) },
                onNavigateToLesson = { navController.navigate(AppRoutes.LESSON) },
                onNavigateToTalkAi = { navController.navigate(AppRoutes.TALK_AI) },
                onNavigateToGrammar = { navController.navigate(AppRoutes.GRAMMAR) },
                onNavigateToDailyPractice = { navController.navigate(AppRoutes.DAILY_PRACTICE) },
                onNavigateToMistakes = { navController.navigate(AppRoutes.MISTAKES) },
                onNavigateToExam = { navController.navigate(AppRoutes.EXAM) },
                onNavigateToVocab = { navController.navigate(AppRoutes.VOCAB) },
                onNavigateToProfile = { navController.navigate(AppRoutes.PROFILE) }
            )
        }

        composable(AppRoutes.LEVELS) {
            LevelJourneyScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onSelectLevel = { navController.navigate(AppRoutes.LESSON) }
            )
        }

        composable(AppRoutes.LESSON) {
            LessonInteractiveScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.TALK_AI) {
            TalkWithAiScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.GRAMMAR) {
            CompleteGrammarScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.DAILY_PRACTICE) {
            DailyPracticeScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNavigateToTalkAi = { navController.navigate(AppRoutes.TALK_AI) }
            )
        }

        composable(AppRoutes.MISTAKES) {
            MistakesReviewScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNavigateToLesson = { navController.navigate(AppRoutes.LESSON) }
            )
        }

        composable(AppRoutes.EXAM) {
            SpeakingExamScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.VOCAB) {
            VocabularyVaultScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.PROFILE) {
            ProfileDashboardScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
