package com.example.model

data class LevelStage(
    val stageNumber: Int,
    val range: String, // e.g. "Level 0 - 10"
    val titleHindi: String,
    val titleEnglish: String,
    val description: String,
    val hindiSupportLevel: String, // "High (अधिकतम हिंदी)", "Medium", "Low (Mostly English)"
    val milestoneTestName: String,
    val startLevel: Int,
    val endLevel: Int
)

data class LevelItem(
    val levelNumber: Int,
    val title: String,
    val hindiTitle: String,
    val stageNumber: Int,
    val summary: String,
    val targetSentenceExamples: List<String>,
    val targetGrammarConcept: String,
    val targetSpeakingGoal: String,
    val xpReward: Int = 100,
    val isCheckpoint: Boolean = false,
    val checkpointTestTitle: String? = null
)

object LevelDataRepository {
    val STAGES = listOf(
        LevelStage(
            stageNumber = 1,
            range = "Level 0 – 10",
            titleHindi = "शुरुआती स्तर (Absolute Beginner)",
            titleEnglish = "Absolute Beginner",
            description = "Alphabet, sounds, greetings, I/You/He/She, This/That, very simple sentences.",
            hindiSupportLevel = "Maximum Hindi Support (80% हिंदी)",
            milestoneTestName = "Level 10: Basic Speaking Test",
            startLevel = 0,
            endLevel = 10
        ),
        LevelStage(
            stageNumber = 2,
            range = "Level 11 – 20",
            titleHindi = "बुनियादी बोलचाल (Basic Spoken English)",
            titleEnglish = "Basic Spoken English",
            description = "Daily-use vocab, basic verbs, Simple Present, questions, negative sentences, daily routine.",
            hindiSupportLevel = "High Hindi Support (70% हिंदी)",
            milestoneTestName = "Level 20: Daily English Test",
            startLevel = 11,
            endLevel = 20
        ),
        LevelStage(
            stageNumber = 3,
            range = "Level 21 – 30",
            titleHindi = "रोजमर्रा की बातचीत (Daily Conversation)",
            titleEnglish = "Daily Conversation",
            description = "Home, family, food, shopping, mobile, travel, asking simple things.",
            hindiSupportLevel = "Medium-High Support (60% हिंदी)",
            milestoneTestName = "Level 30: Question/Answer Test",
            startLevel = 21,
            endLevel = 30
        ),
        LevelStage(
            stageNumber = 4,
            range = "Level 31 – 40",
            titleHindi = "सवाल और जवाब (Questions & Answers - WH Words)",
            titleEnglish = "WH-Questions Mastery",
            description = "What, Why, Where, When, Who, Whom, Whose, Which, How, How much/many, How far/often.",
            hindiSupportLevel = "Balanced (50% English / 50% Hindi)",
            milestoneTestName = "Level 40: Grammar Speaking Test",
            startLevel = 31,
            endLevel = 40
        ),
        LevelStage(
            stageNumber = 5,
            range = "Level 41 – 50",
            titleHindi = "बोलने के लिए ग्रामर (Grammar for Speaking)",
            titleEnglish = "Grammar for Speaking",
            description = "12 Tenses, Articles, Prepositions, Modals (Can/Could/Should), Determiners, Subject-Verb agreement.",
            hindiSupportLevel = "English-Focused with Hindi explanations (40% Hindi)",
            milestoneTestName = "Level 50: Conversation Test",
            startLevel = 41,
            endLevel = 50
        ),
        LevelStage(
            stageNumber = 6,
            range = "Level 51 – 60",
            titleHindi = "रियल बातचीत (Real Conversations)",
            titleEnglish = "Interactive Conversations",
            description = "Meeting friends, ordering food, shopping, asking directions, phone calls, introducing oneself.",
            hindiSupportLevel = "Conversational English (30% Hindi)",
            milestoneTestName = "Level 60: Real-Life Speaking Test",
            startLevel = 51,
            endLevel = 60
        ),
        LevelStage(
            stageNumber = 7,
            range = "Level 61 – 70",
            titleHindi = "व्यावहारिक परिस्थितियाँ (Real-Life Situations)",
            titleEnglish = "Real-Life Situational English",
            description = "Railway station, airport, hotel check-in, bank, office talks, customer service, job interview basics.",
            hindiSupportLevel = "Low Hindi Support (20% Hindi)",
            milestoneTestName = "Level 70: Fluency Test",
            startLevel = 61,
            endLevel = 70
        ),
        LevelStage(
            stageNumber = 8,
            range = "Level 71 – 80",
            titleHindi = "तेजी और आत्मविश्वास (Fluency Building)",
            titleEnglish = "Fluency & Fast Sentence Formation",
            description = "Speaking without translating every word, natural responses, storytelling, confidence building.",
            hindiSupportLevel = "Minimal Hindi (10% Hindi)",
            milestoneTestName = "Level 80: Advanced Conversation Test",
            startLevel = 71,
            endLevel = 80
        ),
        LevelStage(
            stageNumber = 9,
            range = "Level 81 – 90",
            titleHindi = "उच्च स्तरीय बोलचाल (Advanced Speaking)",
            titleEnglish = "Advanced Speaking & Discussions",
            description = "Long conversations, giving opinions, explaining concepts, presentations, professional meetings.",
            hindiSupportLevel = "Pure English with Hindi on-demand",
            milestoneTestName = "Level 90: Professional Speaking Test",
            startLevel = 81,
            endLevel = 90
        ),
        LevelStage(
            stageNumber = 10,
            range = "Level 91 – 100",
            titleHindi = "धाराप्रवाह महारत (Fluent English & Mastery)",
            titleEnglish = "Fluent English Mastery",
            description = "Job interviews, public speaking, debates, complex talks, impromptu speaking, idiom usage.",
            hindiSupportLevel = "Advanced English",
            milestoneTestName = "Level 100: Final English Speaking Assessment",
            startLevel = 91,
            endLevel = 100
        )
    )

    // Generate 101 levels (0 to 100)
    val ALL_LEVELS: List<LevelItem> = listOf(
        LevelItem(
            levelNumber = 0,
            title = "Alphabet & English Sounds",
            hindiTitle = "अंग्रेजी अक्षर और उच्चारण",
            stageNumber = 1,
            summary = "Learn A-Z sounds, vowels, and simple greetings.",
            targetSentenceExamples = listOf("Hello!", "Good morning!", "Hi there!"),
            targetGrammarConcept = "Basic Sounds & Letters",
            targetSpeakingGoal = "Greet people clearly in English"
        ),
        LevelItem(
            levelNumber = 1,
            title = "Basic Greetings & Namaste to Hello",
            hindiTitle = "अभिवादन और हाय/हेलो",
            stageNumber = 1,
            summary = "Saying Hello, Good Morning, How are you, I am fine.",
            targetSentenceExamples = listOf("Hello, how are you?", "I am fine, thank you.", "Good evening!"),
            targetGrammarConcept = "Greetings & Polite responses",
            targetSpeakingGoal = "Exchange basic pleasantries"
        ),
        LevelItem(
            levelNumber = 2,
            title = "I / You / He / She / It",
            hindiTitle = "मैं / तुम / वह / यह",
            stageNumber = 1,
            summary = "Subject pronouns with 'am', 'are', 'is'.",
            targetSentenceExamples = listOf("I am Faizan.", "You are my friend.", "He is happy.", "She is smart."),
            targetGrammarConcept = "Personal Pronouns + Helping Verbs (am, is, are)",
            targetSpeakingGoal = "State identity and describe people"
        ),
        LevelItem(
            levelNumber = 3,
            title = "This & That / These & Those",
            hindiTitle = "यह और वह / ये और वे",
            stageNumber = 1,
            summary = "Pointing to things nearby and far away.",
            targetSentenceExamples = listOf("This is a book.", "That is a car.", "These are apples.", "Those are birds."),
            targetGrammarConcept = "Demonstrative Pronouns",
            targetSpeakingGoal = "Point and name everyday objects"
        ),
        LevelItem(
            levelNumber = 4,
            title = "My / Your / His / Her",
            hindiTitle = "मेरा / आपका / उसका",
            stageNumber = 1,
            summary = "Possessive words to show belonging.",
            targetSentenceExamples = listOf("My name is Rahul.", "This is your pen.", "Her brother is kind.", "His car is new."),
            targetGrammarConcept = "Possessive Adjectives",
            targetSpeakingGoal = "Talk about personal possessions and family"
        ),
        LevelItem(
            levelNumber = 5,
            title = "Yes / No & Simple Answers",
            hindiTitle = "हाँ / ना और सरल उत्तर",
            stageNumber = 1,
            summary = "Answering questions with Yes, No, Sure, Of course.",
            targetSentenceExamples = listOf("Yes, I am ready.", "No, I am busy.", "Yes, please.", "No, thank you."),
            targetGrammarConcept = "Short Affirmative & Negative Responses",
            targetSpeakingGoal = "Respond quickly with Yes or No"
        ),
        LevelItem(
            levelNumber = 6,
            title = "Introducing Yourself",
            hindiTitle = "अपना परिचय देना",
            stageNumber = 1,
            summary = "Name, age, city, and profession.",
            targetSentenceExamples = listOf("My name is Faizan.", "I live in Delhi.", "I am a student.", "I am 22 years old."),
            targetGrammarConcept = "Self-Introduction Pattern",
            targetSpeakingGoal = "Speak a 4-line self-introduction"
        ),
        LevelItem(
            levelNumber = 7,
            title = "Feelings & States (I am happy / tired)",
            hindiTitle = "भावनाएं और स्थिति",
            stageNumber = 1,
            summary = "Expressing emotions and physical states.",
            targetSentenceExamples = listOf("I am very happy today.", "I am tired.", "He is hungry.", "Are you ready?"),
            targetGrammarConcept = "Adjectives of Emotion + 'to be'",
            targetSpeakingGoal = "Express how you feel right now"
        ),
        LevelItem(
            levelNumber = 8,
            title = "Family Members & Home",
            hindiTitle = "परिवार के सदस्य और घर",
            stageNumber = 1,
            summary = "Mother, father, brother, sister, home words.",
            targetSentenceExamples = listOf("This is my mother.", "I have one brother.", "My family is small.", "Our house is beautiful."),
            targetGrammarConcept = "Family Nouns + 'have/has'",
            targetSpeakingGoal = "Introduce your family members"
        ),
        LevelItem(
            levelNumber = 9,
            title = "Numbers, Time & Days",
            hindiTitle = "संख्याएं, समय और दिन",
            stageNumber = 1,
            summary = "Counting, telling simple time, days of week.",
            targetSentenceExamples = listOf("Today is Monday.", "It is five o'clock.", "I have two books.", "Tomorrow is a holiday."),
            targetGrammarConcept = "Time Expressions & Numerals",
            targetSpeakingGoal = "Tell current time and days"
        ),
        LevelItem(
            levelNumber = 10,
            title = "Level 10 Checkpoint: Basic Speaking Test",
            hindiTitle = "स्तर 10 टेस्ट: बुनियादी बोलचाल परीक्षा",
            stageNumber = 1,
            summary = "Comprehensive speaking & listening assessment of absolute beginner concepts.",
            targetSentenceExamples = listOf("Hello! My name is Faizan. I am a student. This is my book."),
            targetGrammarConcept = "Checkpoint Assessment",
            targetSpeakingGoal = "Pass 5 voice prompts with 70%+ score",
            isCheckpoint = true,
            checkpointTestTitle = "Stage 1 Milestone Exam"
        ),
        LevelItem(
            levelNumber = 11,
            title = "Daily Action Verbs (Eat, Drink, Sleep, Go)",
            hindiTitle = "दैनिक क्रियाएं (खाना, पीना, सोना, जाना)",
            stageNumber = 2,
            summary = "Action verbs for daily life.",
            targetSentenceExamples = listOf("I eat food.", "I drink water.", "I go to market.", "I sleep early."),
            targetGrammarConcept = "Base Verbs (V1) & Action Words",
            targetSpeakingGoal = "Speak about simple daily actions"
        ),
        LevelItem(
            levelNumber = 12,
            title = "Simple Present (I do / He does)",
            hindiTitle = "सामान्य वर्तमान काल (s/es के नियम)",
            stageNumber = 2,
            summary = "Subject-verb rules: I go vs He goes.",
            targetSentenceExamples = listOf("He goes to school every day.", "She speaks Hindi.", "They live in Mumbai.", "My father works in a bank."),
            targetGrammarConcept = "Simple Present Tense (Affirmative)",
            targetSpeakingGoal = "Use s/es correctly for He/She/It"
        ),
        LevelItem(
            levelNumber = 13,
            title = "Negative Sentences (Don't / Doesn't)",
            hindiTitle = "नकारात्मक वाक्य (Don't / Doesn't)",
            stageNumber = 2,
            summary = "Making negative statements in present tense.",
            targetSentenceExamples = listOf("I don't like tea.", "He doesn't eat spicy food.", "We don't watch TV.", "She doesn't know him."),
            targetGrammarConcept = "Do not / Does not + V1",
            targetSpeakingGoal = "Express dislikes and negative facts"
        ),
        LevelItem(
            levelNumber = 14,
            title = "Simple Questions (Do you...? / Does he...?)",
            hindiTitle = "सरल प्रश्न (क्या आप...? / क्या वह...?)",
            stageNumber = 2,
            summary = "Asking Yes/No questions in daily life.",
            targetSentenceExamples = listOf("Do you play cricket?", "Does he live here?", "Do they speak English?", "Do you understand?"),
            targetGrammarConcept = "Do / Does Question Formation",
            targetSpeakingGoal = "Ask people about their habits"
        ),
        LevelItem(
            levelNumber = 15,
            title = "My Daily Routine",
            hindiTitle = "मेरी दैनिक दिनचर्या",
            stageNumber = 2,
            summary = "Describing your entire day from morning to night.",
            targetSentenceExamples = listOf("I wake up at 6 AM.", "I brush my teeth.", "I take a bath and have breakfast.", "I study in the evening."),
            targetGrammarConcept = "Sequential Connectors (Then, After that, Finally)",
            targetSpeakingGoal = "Describe your daily routine fluently"
        ),
        LevelItem(
            levelNumber = 16,
            title = "Can & Can't (Ability & Permission)",
            hindiTitle = "सकना (Can और Can't)",
            stageNumber = 2,
            summary = "Expressing ability and asking polite permission.",
            targetSentenceExamples = listOf("I can speak English.", "Can you help me?", "He cannot swim.", "Can I come in?"),
            targetGrammarConcept = "Modal Verb 'Can'",
            targetSpeakingGoal = "State what you can do and ask permission"
        ),
        LevelItem(
            levelNumber = 17,
            title = "Present Continuous (I am doing)",
            hindiTitle = "तत्काल हो रहे काम (am/is/are + ing)",
            stageNumber = 2,
            summary = "Talking about actions happening right now.",
            targetSentenceExamples = listOf("I am learning English.", "She is cooking food.", "It is raining outside.", "They are playing."),
            targetGrammarConcept = "Present Continuous Tense",
            targetSpeakingGoal = "Describe actions happening right now"
        ),
        LevelItem(
            levelNumber = 18,
            title = "Where do you live? & Basic WH Questions",
            hindiTitle = "आप कहाँ रहते हैं? (स्थान और समय)",
            stageNumber = 2,
            summary = "Where and When basic question framing.",
            targetSentenceExamples = listOf("Where do you live?", "When do you wake up?", "Where is the railway station?", "When does the bus come?"),
            targetGrammarConcept = "Where / When + Do/Does",
            targetSpeakingGoal = "Ask where and when questions"
        ),
        LevelItem(
            levelNumber = 19,
            title = "Simple Past Basics (Did & Went)",
            hindiTitle = "बीता हुआ समय (Yesterday & Did)",
            stageNumber = 2,
            summary = "Talking about yesterday and past actions.",
            targetSentenceExamples = listOf("I went to Delhi yesterday.", "Did you call me?", "I watched a movie.", "I didn't go to work."),
            targetGrammarConcept = "Simple Past Tense (V2 & Did not)",
            targetSpeakingGoal = "Share what you did yesterday"
        ),
        LevelItem(
            levelNumber = 20,
            title = "Level 20 Checkpoint: Daily English Test",
            hindiTitle = "स्तर 20 टेस्ट: दैनिक अंग्रेजी परीक्षा",
            stageNumber = 2,
            summary = "Test your daily routine, simple present, negative, and question skills.",
            targetSentenceExamples = listOf("I wake up early. I don't drink coffee. Do you speak English? I can do it."),
            targetGrammarConcept = "Checkpoint Assessment",
            targetSpeakingGoal = "Demonstrate daily speaking mastery",
            isCheckpoint = true,
            checkpointTestTitle = "Stage 2 Milestone Exam"
        )
    ) + (21..100).map { lvl ->
        val stageNum = when (lvl) {
            in 21..30 -> 3
            in 31..40 -> 4
            in 41..50 -> 5
            in 51..60 -> 6
            in 61..70 -> 7
            in 71..80 -> 8
            in 81..90 -> 9
            else -> 10
        }
        val isCheckpoint = (lvl % 10 == 0)
        val stageObj = STAGES.first { it.stageNumber == stageNum }
        val titleText = when (lvl) {
            21 -> "Talking About Home & Rooms"
            22 -> "Shopping for Groceries & Clothes"
            23 -> "Food, Restaurant & Ordering"
            24 -> "Mobile, Internet & Social Media"
            25 -> "Travel & Taking a Bus / Train"
            26 -> "Talking to Friends & Making Plans"
            27 -> "College & School Conversations"
            28 -> "Hobbies, Sports & Movies"
            29 -> "Health & Visiting a Doctor"
            30 -> "Level 30 Checkpoint: Question/Answer Test"
            31 -> "The Power of 'What' (क्या)"
            32 -> "Mastering 'Why' & Giving Reasons (क्यों)"
            33 -> "Mastering 'Where' & Directions (कहाँ)"
            34 -> "Mastering 'When' & Time Scheduling (कब)"
            35 -> "Mastering 'Who', 'Whom' & 'Whose' (कौन/किसका)"
            36 -> "Mastering 'Which' & Choices (कौन सा)"
            37 -> "Mastering 'How' & Methods (कैसे)"
            38 -> "How Much vs How Many (कितना/कितने)"
            39 -> "How Far & How Often (कितनी दूर/कितनी बार)"
            40 -> "Level 40 Checkpoint: Grammar Speaking Test"
            41 -> "Articles: A, An, and The in Speech"
            42 -> "Prepositions of Place: In, On, At, Under"
            43 -> "Prepositions of Time: Since, For, During"
            44 -> "Modals: Should, Must & Ought to (Advice)"
            45 -> "Modals: Would & Could (Polite Requests)"
            46 -> "Present Perfect: Have/Has + V3 in Conversation"
            47 -> "Past Continuous: Was/Were doing"
            48 -> "Future: Will vs Going to"
            49 -> "Subject-Verb Agreement in Spoken Flow"
            50 -> "Level 50 Checkpoint: Conversation Test"
            51 -> "Meeting Someone New & Small Talk"
            52 -> "Ordering Food at a Restaurant"
            53 -> "Bargaining & Shopping at a Mall"
            54 -> "Asking for & Giving Directions"
            55 -> "Making Professional Phone Calls"
            56 -> "Talking to a Teacher / Professor"
            57 -> "Handling Customer Complaints"
            58 -> "Giving Compliments & Expressing Gratitude"
            59 -> "Apologizing & Making Excuses"
            60 -> "Level 60 Checkpoint: Real-Life Speaking Test"
            61 -> "At the Railway Station & Ticket Counter"
            62 -> "At the Airport: Check-in & Security"
            63 -> "Hotel Check-in & Room Service"
            64 -> "At the Bank: Opening Account & Enquiries"
            65 -> "At the Office: Daily Meetings & Updates"
            66 -> "Doctor's Appointment & Describing Symptoms"
            67 -> "Customer Support Call & Resolving Issues"
            68 -> "Job Interview: Self Introduction"
            69 -> "Job Interview: Answering Strengths & Weaknesses"
            70 -> "Level 70 Checkpoint: Fluency Test"
            71 -> "Thinking in English: Stopping Hindi Translation"
            72 -> "Fast Sentence Formation Drills"
            73 -> "Storytelling: Narration in Past Tense"
            74 -> "Expressing Agreement & Disagreement Politely"
            75 -> "Vocabulary Expansion: Smart Synonyms"
            76 -> "Using Idioms & Natural Colloquial Phrases"
            77 -> "Conversation Flow & Filler Words Mastery"
            78 -> "Speaking with Confidence & Body Language Voice"
            79 -> "Describing Imaginary Situations (Conditionals)"
            80 -> "Level 80 Checkpoint: Advanced Conversation Test"
            81 -> "Expressing Nuanced Opinions on Hot Topics"
            82 -> "Explaining Technical & Complex Ideas Simply"
            83 -> "Professional Email & Meeting Spoken Style"
            84 -> "Group Discussions (GD) & Taking the Lead"
            85 -> "Persuasive Speaking & Negotiation"
            86 -> "Giving Short Presentations with Impact"
            87 -> "Handling Difficult People & Conflict Resolution"
            88 -> "Storytelling with Emotion and Timing"
            89 -> "Telephone & Video Conference Etiquette"
            90 -> "Level 90 Checkpoint: Professional Speaking Test"
            91 -> "Advanced Job Interview: Behavioral Questions"
            92 -> "Salary Negotiation & Offer Discussions"
            93 -> "Public Speaking & Keynote Delivery"
            94 -> "Debating Skills: Rebuttal & Counterarguments"
            95 -> "Impromptu Speaking (Extempore / Table Topics)"
            96 -> "Executive Communication & Leadership Tone"
            97 -> "Sophisticated Vocabulary & Phrasal Verbs"
            98 -> "Accent Neutralization & Clear Articulation"
            99 -> "Cultural Nuances & Global English Communication"
            100 -> "Level 100 Mastery: Final English Speaking Assessment"
            else -> "Advanced English Practice $lvl"
        }

        LevelItem(
            levelNumber = lvl,
            title = titleText,
            hindiTitle = "लेवल $lvl: $titleText",
            stageNumber = stageNum,
            summary = "Master practical speaking for $titleText.",
            targetSentenceExamples = listOf(
                "I am confident in speaking English fluently.",
                "Let me explain this point step by step.",
                "In my opinion, practice makes everyone fluent."
            ),
            targetGrammarConcept = stageObj.titleEnglish,
            targetSpeakingGoal = "Speak fluently in $titleText",
            isCheckpoint = isCheckpoint,
            checkpointTestTitle = if (isCheckpoint) "Level $lvl Milestone Assessment" else null
        )
    }
}
