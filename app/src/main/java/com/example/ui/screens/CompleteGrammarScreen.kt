package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.GrammarCatalogData
import com.example.model.GrammarTopic
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.LightAmber
import com.example.ui.theme.PureWhite
import com.example.ui.theme.RoyalBlue
import com.example.ui.theme.RoyalBlueLight
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarmAmber
import com.example.ui.viewmodel.LearningViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompleteGrammarScreen(
    viewModel: LearningViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedTopic by viewModel.selectedGrammarTopic.collectAsState()
    val searchQuery by viewModel.grammarSearchQuery.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }

    val filteredTopics = GrammarCatalogData.ALL_TOPICS.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.hindiTitle.contains(searchQuery, ignoreCase = true) ||
                it.letter.toString().equals(searchQuery.trim(), ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "A to Z Complete Grammar",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                        Text(
                            text = "26 प्रमुख विषय • नियम, फॉर्मूला, उदाहरण व टेस्ट",
                            style = MaterialTheme.typography.labelSmall,
                            color = LightAmber
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = PureWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = RoyalBlue)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {
            // Search Box
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setGrammarSearchQuery(it) },
                placeholder = { Text("विषय खोजें (e.g. Tenses, Articles, Modals)...") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = RoyalBlue)
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.setGrammarSearchQuery("") }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Clear")
                        }
                    }
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .testTag("grammar_search_input")
            )

            // Horizontal Alphabet Bar (A to Z)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                items(GrammarCatalogData.ALL_TOPICS) { topic ->
                    val isSelected = topic.letter == selectedTopic.letter
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) RoyalBlue else PureWhite)
                            .clickable { viewModel.selectGrammarTopic(topic) }
                    ) {
                        Text(
                            text = topic.letter.toString(),
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) PureWhite else RoyalBlue
                        )
                    }
                }
            }

            // Topic Selector Header Card
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(SaffronOrange)
                    ) {
                        Text(
                            text = selectedTopic.letter.toString(),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = selectedTopic.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = RoyalBlue
                        )
                        Text(
                            text = selectedTopic.hindiTitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Tabs (1. Rules & Formula, 2. Examples, 3. Mistakes, 4. Speaking Practice, 5. Quiz)
            val tabs = listOf("📖 नियम व फॉर्मूला", "💡 उदाहरण", "❌ गलतियाँ", "🎤 बोलकर अभ्यास", "📝 टेस्ट")
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                edgePadding = 16.dp,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = RoyalBlue
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                color = if (selectedTab == index) RoyalBlue else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                }
            }

            // Tab Content
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                }

                when (selectedTab) {
                    0 -> item {
                        GrammarRulesTab(topic = selectedTopic)
                    }
                    1 -> item {
                        GrammarExamplesTab(
                            topic = selectedTopic,
                            onListen = { viewModel.speakEnglish(it) }
                        )
                    }
                    2 -> item {
                        GrammarMistakesTab(topic = selectedTopic)
                    }
                    3 -> item {
                        GrammarSpeakingPracticeTab(
                            topic = selectedTopic,
                            onListen = { viewModel.speakEnglish(it) }
                        )
                    }
                    4 -> item {
                        GrammarMiniQuizTab(
                            topic = selectedTopic,
                            onSpeak = { viewModel.speakEnglish(it) }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
    }
}

@Composable
fun GrammarRulesTab(topic: GrammarTopic) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "🎯 मुख्य नियम (Detailed Rules in Hindi):",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = RoyalBlue
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = topic.detailedRuleHindi,
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 22.sp
                )
            }
        }

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = LightAmber),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "📐 फॉर्मूला और वाक्य संरचना (Formulas):",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = SaffronOrange
                )
                Spacer(modifier = Modifier.height(8.dp))
                topic.formulas.forEach { formula ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = PureWhite,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 6.dp)
                    ) {
                        Text(
                            text = "• $formula",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = RoyalBlue,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
        }

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "वाक्य प्रकार (Structures):",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "✅ Positive: ${topic.positiveStructure}", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "❌ Negative: ${topic.negativeStructure}", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "❓ Question: ${topic.questionStructure}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun GrammarExamplesTab(topic: GrammarTopic, onListen: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        topic.examples.forEachIndexed { index, example ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${index + 1}. \"${example.english}\"",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = RoyalBlue,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { onListen(example.english) }) {
                            Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Listen", tint = RoyalBlue)
                        }
                    }

                    Text(
                        text = example.hindi,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = LightAmber
                    ) {
                        Text(
                            text = "💡 ${example.explanation}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF78350F),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GrammarMistakesTab(topic: GrammarTopic) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        topic.commonMistakes.forEach { (wrong, correct) ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Wrong", tint = ErrorRed, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "गलत: $wrong",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = ErrorRed
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Correct", tint = SuccessGreen, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "सही: $correct",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = SuccessGreen
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GrammarSpeakingPracticeTab(topic: GrammarTopic, onListen: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = "🎤 बोलकर 3 बार दोहराएं (Speaking Drills):",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = RoyalBlue
        )

        topic.speakingPracticeSentences.forEachIndexed { index, sentence ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${index + 1}. $sentence",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    IconButton(onClick = { onListen(sentence) }) {
                        Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Listen", tint = SaffronOrange)
                    }
                }
            }
        }
    }
}

@Composable
fun GrammarMiniQuizTab(topic: GrammarTopic, onSpeak: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        topic.miniTestQuestions.forEachIndexed { qIndex, question ->
            var selectedAnswer by remember { mutableIntStateOf(-1) }
            var isAnswerSubmitted by remember { androidx.compose.runtime.mutableStateOf(false) }

            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Q${qIndex + 1}: ${question.question}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = RoyalBlue
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    question.options.forEachIndexed { optIndex, optionText ->
                        val isOptionSelected = selectedAnswer == optIndex
                        val isOptionCorrect = optIndex == question.correctIndex

                        val bgColor = when {
                            !isAnswerSubmitted && isOptionSelected -> RoyalBlue.copy(alpha = 0.12f)
                            isAnswerSubmitted && isOptionCorrect -> SuccessGreen.copy(alpha = 0.15f)
                            isAnswerSubmitted && isOptionSelected && !isOptionCorrect -> ErrorRed.copy(alpha = 0.15f)
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = bgColor,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 6.dp)
                                .clickable {
                                    if (!isAnswerSubmitted) {
                                        selectedAnswer = optIndex
                                        isAnswerSubmitted = true
                                    }
                                }
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${('A' + optIndex)}. ",
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = optionText,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = if (isOptionSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }

                    if (isAnswerSubmitted) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = LightAmber,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "💡 व्याख्या: ${question.hindiExplanation}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF78350F),
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
