package com.example.model

data class GrammarExample(
    val english: String,
    val hindi: String,
    val explanation: String
)

data class GrammarPracticeQuestion(
    val id: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val hindiExplanation: String,
    val type: QuestionType = QuestionType.MCQ
)

enum class QuestionType {
    MCQ,
    FILL_IN_THE_BLANK,
    TRANSLATE_HINDI_TO_ENGLISH,
    SENTENCE_CORRECTION,
    REARRANGE_WORDS,
    SPEAKING_PROMPT
}

data class GrammarTopic(
    val letter: Char, // 'A' .. 'Z'
    val key: String,
    val title: String,
    val hindiTitle: String,
    val shortSummary: String,
    val detailedRuleHindi: String,
    val formulas: List<String>,
    val positiveStructure: String,
    val negativeStructure: String,
    val questionStructure: String,
    val examples: List<GrammarExample>,
    val commonMistakes: List<Pair<String, String>>, // Pair(Wrong, Correct)
    val speakingPracticeSentences: List<String>,
    val miniTestQuestions: List<GrammarPracticeQuestion>
)

object GrammarCatalogData {
    val ALL_TOPICS: List<GrammarTopic> = listOf(
        GrammarTopic(
            letter = 'A',
            key = "articles",
            title = "Articles (A, An, The & Zero Article)",
            hindiTitle = "आर्टिकल्स (A, An, The के नियम)",
            shortSummary = "Learn when to use A, An, The, and when no article is needed.",
            detailedRuleHindi = """
                1. 'A' का प्रयोग Consonant Sound (व्यंजन ध्वनि जैसे क, ख, ग...) से शुरू होने वाले एकवचन शब्दों से पहले होता है।
                   उदाहरण: A boy, A university (य ध्वनि), A European.
                2. 'An' का प्रयोग Vowel Sound (स्वर ध्वनि जैसे अ, आ, इ, ई, ए, ऐ, ओ, औ...) से शुरू होने वाले शब्दों से पहले होता है।
                   उदाहरण: An apple, An honest man (ऑ ध्वनि), An hour, An MLA.
                3. 'The' का प्रयोग निश्चित (Specific), अद्वितीय (Unique like Sun, Moon) या प्रसिद्ध चीजों से पहले होता है।
                4. Zero Article: भाषाओं, खेलों और सामान्य भोजन के नामों से पहले Article नहीं लगता (e.g., I speak English, not The English).
            """.trimIndent(),
            formulas = listOf(
                "A + Consonant Sound (क, ख, ग...)",
                "An + Vowel Sound (अ, आ, इ, ई, ए, ओ...)",
                "The + Specific / Unique Noun"
            ),
            positiveStructure = "This is a book. / He is an honest man. / The sun is shining.",
            negativeStructure = "This is not a pen. / He is not an officer.",
            questionStructure = "Is this an apple? / Where is the nearest bank?",
            examples = listOf(
                GrammarExample("He is an honest person.", "वह एक ईमानदार व्यक्ति है।", "Honest में 'h' silent है, 'ऑ' स्वर ध्वनि से शुरू होता है इसलिए An लगेगा।"),
                GrammarExample("She studies in a university.", "वह एक विश्वविद्यालय में पढ़ती है।", "University 'य' व्यंजन ध्वनि से शुरू होता है इसलिए A लगेगा।"),
                GrammarExample("The Taj Mahal is in Agra.", "ताजमहल आगरा में है।", "प्रसिद्ध ऐतिहासिक इमारतों के आगे The लगता है।")
            ),
            commonMistakes = listOf(
                "He is a honest man." to "He is an honest man.",
                "I saw an European." to "I saw a European.",
                "The English is a good language." to "English is a good language."
            ),
            speakingPracticeSentences = listOf(
                "I bought a new phone yesterday.",
                "She is an intelligent student.",
                "Can you please pass the water bottle?",
                "The sun rises in the east."
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "a1",
                    question = "He will return in ___ hour.",
                    options = listOf("a", "an", "the", "no article"),
                    correctIndex = 1,
                    hindiExplanation = "'Hour' में 'h' silent है और उच्चारण स्वर 'आवर' से शुरू होता है, इसलिए 'an' आएगा।"
                ),
                GrammarPracticeQuestion(
                    id = "a2",
                    question = "My brother is ___ university student.",
                    options = listOf("a", "an", "the", "none"),
                    correctIndex = 0,
                    hindiExplanation = "'University' का उच्चारण 'य' व्यंजन ध्वनि से होता है, इसलिए 'a' आएगा।"
                )
            )
        ),
        GrammarTopic(
            letter = 'B',
            key = "sentence_structure",
            title = "Basic Sentence Structure",
            hindiTitle = "वाक्य की बुनियादी संरचना (S + V + O)",
            shortSummary = "Subject + Verb + Object formula and phrase/clause fundamentals.",
            detailedRuleHindi = """
                अंग्रेजी वाक्य की मूल संरचना:
                Subject (कर्ता) + Verb (क्रिया) + Object (कर्म) + Other words.
                हिंदी में कर्ता + कर्म + क्रिया होती है (मैं सेब खाता हूँ), जबकि अंग्रेजी में Subject + Verb + Object (I eat an apple).
            """.trimIndent(),
            formulas = listOf("Subject + Verb + Object", "Subject + Helping Verb + Main Verb + Object"),
            positiveStructure = "I (Subject) write (Verb) a letter (Object).",
            negativeStructure = "I do not write a letter.",
            questionStructure = "Do you write a letter?",
            examples = listOf(
                GrammarExample("Rahul plays football.", "राहुल फुटबॉल खेलता है।", "Rahul (S) + plays (V) + football (O)"),
                GrammarExample("They are watching a movie.", "वे फिल्म देख रहे हैं।", "They (S) + are watching (V) + a movie (O)")
            ),
            commonMistakes = listOf(
                "I football play." to "I play football.",
                "She English speaks." to "She speaks English."
            ),
            speakingPracticeSentences = listOf(
                "I love learning new things.",
                "My brother works at a company.",
                "We read books every Sunday."
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "b1",
                    question = "Choose the correct sentence order:",
                    options = listOf("Tea I drink every morning.", "I drink tea every morning.", "I tea drink every morning.", "Every morning tea I drink."),
                    correctIndex = 1,
                    hindiExplanation = "अंग्रेजी में पहले Subject (I), फिर Verb (drink), फिर Object (tea) आता है।"
                )
            )
        ),
        GrammarTopic(
            letter = 'C',
            key = "conditionals",
            title = "Clauses & Conditionals (If... Then)",
            hindiTitle = "शर्त वाले वाक्य (Conditionals 0, 1, 2, 3)",
            shortSummary = "Zero, First, Second, Third, and Mixed conditionals for spoken fluency.",
            detailedRuleHindi = """
                1. Zero Conditional (Scientific truth): If + Simple Present, Simple Present. (If you heat water, it boils.)
                2. First Conditional (Real future possibility): If + Simple Present, Will + V1. (If it rains, I will stay home.)
                3. Second Conditional (Imaginary present): If + Simple Past (were/had), Would + V1. (If I were rich, I would help everyone.)
                4. Third Conditional (Past regret): If + Past Perfect (had + V3), Would have + V3. (If you had studied, you would have passed.)
            """.trimIndent(),
            formulas = listOf(
                "If + Simple Present, ... Simple Present (0)",
                "If + Simple Present, ... Will + V1 (1st)",
                "If + Simple Past, ... Would + V1 (2nd)",
                "If + Had + V3, ... Would have + V3 (3rd)"
            ),
            positiveStructure = "If you work hard, you will succeed.",
            negativeStructure = "If you don't hurry, you will miss the train.",
            questionStructure = "What would you do if you won a lottery?",
            examples = listOf(
                GrammarExample("If you call him, he will come.", "अगर तुम उसे बुलाओगे, तो वह आएगा।", "First conditional: भविष्य की वास्तविक संभावना।"),
                GrammarExample("If I had money, I would buy a car.", "अगर मेरे पास पैसे होते, तो मैं कार खरीद लेता।", "Second conditional: वर्तमान की काल्पनिक बात।")
            ),
            commonMistakes = listOf(
                "If it will rain, I will stay home." to "If it rains, I will stay home.",
                "If I was you, I would go." to "If I were you, I would go."
            ),
            speakingPracticeSentences = listOf(
                "If you practice daily, you will speak English fluently.",
                "What would you do if you were the Prime Minister?",
                "If I have free time, I will call you."
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "c1",
                    question = "If it ___ tomorrow, we will cancel the picnic.",
                    options = listOf("will rain", "rains", "rained", "raining"),
                    correctIndex = 1,
                    hindiExplanation = "'If' वाले भाग में कभी 'will' नहीं आता, Simple Present (rains) का प्रयोग होता है।"
                )
            )
        ),
        GrammarTopic(
            letter = 'D',
            key = "determiners",
            title = "Determiners & Quantifiers",
            hindiTitle = "निर्धारक शब्द (Much, Many, Some, Any, Few, Little)",
            shortSummary = "Some vs Any, Much vs Many, Few vs A Few, Little vs A Little.",
            detailedRuleHindi = """
                - Many / Few / A Few: Countable (गिने जाने वाले) संज्ञाओं के साथ। (Many books, A few friends)
                - Much / Little / A Little: Uncountable (न गिने जाने वाले जैसे पानी, समय, दूध) के साथ। (Much water, A little milk)
                - Few = न के बराबर (Negative)। A Few = कुछ/थोड़े (Positive)।
                - Little = न के बराबर (Negative)। A Little = थोड़ा सा (Positive)।
                - Some = Positive वाक्यों में। Any = Negative और Questions में।
            """.trimIndent(),
            formulas = listOf(
                "Many / (A) Few + Countable Plural Noun",
                "Much / (A) Little + Uncountable Noun",
                "Some (Affirmative) vs Any (Negative/Questions)"
            ),
            positiveStructure = "I have some friends. / I have a little water.",
            negativeStructure = "I don't have any money. / There isn't much time.",
            questionStructure = "Do you have any questions? / How many books do you have?",
            examples = listOf(
                GrammarExample("I don't have any money.", "मेरे पास बिल्कुल पैसे नहीं हैं।", "Negative वाक्य में Any का प्रयोग होता है।"),
                GrammarExample("She has a few close friends.", "उसके कुछ पक्के दोस्त हैं।", "A few का अर्थ सकारात्मक 'कुछ' है।")
            ),
            commonMistakes = listOf(
                "I don't have some money." to "I don't have any money.",
                "How much people came?" to "How many people came?",
                "I drank many water." to "I drank much/a lot of water."
            ),
            speakingPracticeSentences = listOf(
                "Do you have any extra pens?",
                "There is only a little sugar left in the jar.",
                "How many hours do you sleep at night?"
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "d1",
                    question = "How ___ water do you drink every day?",
                    options = listOf("many", "much", "few", "any"),
                    correctIndex = 1,
                    hindiExplanation = "Water अनकाउंटेबल नाउन है, इसलिए इसके साथ 'much' का प्रयोग होता है।"
                )
            )
        ),
        GrammarTopic(
            letter = 'E',
            key = "verbs_forms",
            title = "Verbs & Verb Forms (V1, V2, V3, V-ing)",
            hindiTitle = "क्रिया और उसके रूप (V1, V2, V3, V-ing)",
            shortSummary = "Action vs Stative verbs, Regular vs Irregular, Transitive vs Intransitive.",
            detailedRuleHindi = """
                क्रिया के मुख्य रूप:
                - V1 (Base / Present): Go, Eat, Write, Speak
                - V2 (Simple Past): Went, Ate, Wrote, Spoke
                - V3 (Past Participle - Perfect tenses): Gone, Eaten, Written, Spoken
                - V-ing (Continuous / Gerund): Going, Eating, Writing, Speaking
                - Stative Verbs (जैसे Love, Know, Understand, Believe, Want) का उपयोग सामान्यतः -ing में नहीं होता (I know you, NOT I am knowing you).
            """.trimIndent(),
            formulas = listOf("V1: Present", "V2: Past (Yesterday)", "V3: Perfect (Have/Has/Had + V3)", "V4: Continuous (is/am/are + V-ing)"),
            positiveStructure = "I write (V1). / I wrote (V2). / I have written (V3).",
            negativeStructure = "I do not write. / I did not write.",
            questionStructure = "Did you write? / Have you written?",
            examples = listOf(
                GrammarExample("I understand your problem.", "मैं आपकी समस्या समझता हूँ।", "Understand stative verb है, 'I am understanding' गलत है।"),
                GrammarExample("He has broken the glass.", "उसने गिलास तोड़ दिया है।", "Has के साथ V3 (broken) का प्रयोग हुआ।")
            ),
            commonMistakes = listOf(
                "I am knowing him very well." to "I know him very well.",
                "He did not went there." to "He did not go there. (Did के बाद हमेशा V1 आता है)"
            ),
            speakingPracticeSentences = listOf(
                "I speak English every day.",
                "She went to the market yesterday.",
                "Have you eaten your lunch yet?"
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "e1",
                    question = "Yesterday, he ___ a very interesting book.",
                    options = listOf("read (pronounced red)", "reads", "is reading", "has read"),
                    correctIndex = 0,
                    hindiExplanation = "Yesterday बीते समय का संकेत है, इसलिए V2 रूप 'read' (उच्चारण 'रेड') का प्रयोग होगा।"
                )
            )
        ),
        GrammarTopic(
            letter = 'F',
            key = "finite_non_finite",
            title = "Finite & Non-Finite Verbs",
            hindiTitle = "सीमित और असीमित क्रियाएं (Infinitives, Gerunds, Participles)",
            shortSummary = "To-infinitive, Bare infinitive, Gerunds and Participles.",
            detailedRuleHindi = """
                - Finite Verbs: जो Tense और Subject के अनुसार बदलती हैं (I go, He goes, We went).
                - Non-Finite Verbs: जो कभी Tense या Subject बदलने पर नहीं बदलती:
                  1. Infinitive (To + V1): I want to learn. He wants to learn.
                  2. Bare Infinitive (Without 'to'): Let me go. You must speak.
                  3. Gerund (V-ing as Noun): Swimming is good exercise.
                  4. Participle (V-ing / V3 as Adjective): A smiling baby. A broken chair.
            """.trimIndent(),
            formulas = listOf("To + V1 (Infinitive)", "V-ing as Subject/Object (Gerund)", "V3/V-ing as Adjective (Participle)"),
            positiveStructure = "I want to improve my English speaking.",
            negativeStructure = "Try not to make mistakes.",
            questionStructure = "Do you like to travel?",
            examples = listOf(
                GrammarExample("Learning English is fun.", "अंग्रेजी सीखना मजेदार है।", "Learning यहाँ Gerund (Noun) के रूप में प्रयुक्त है।"),
                GrammarExample("I decided to wake up early.", "मैंने जल्दी उठने का फैसला किया।", "To wake up Infinitive है।")
            ),
            commonMistakes = listOf(
                "Let me to go." to "Let me go. (Let के बाद bare infinitive आता है)",
                "I want learning." to "I want to learn."
            ),
            speakingPracticeSentences = listOf(
                "I want to speak fluent English.",
                "Reading books helps improve vocabulary.",
                "She helped me solve the problem."
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "f1",
                    question = "Please let me ___ first.",
                    options = listOf("speak", "to speak", "speaking", "spoke"),
                    correctIndex = 0,
                    hindiExplanation = "'Let' के बाद Bare Infinitive (बिना 'to' के V1) आता है।"
                )
            )
        ),
        GrammarTopic(
            letter = 'G',
            key = "gerunds",
            title = "Gerunds (Verb-ing as Noun)",
            hindiTitle = "जेरंड (संज्ञा के रूप में -ing क्रिया)",
            shortSummary = "Gerund identification, gerunds after prepositions, gerund vs infinitive.",
            detailedRuleHindi = """
                जब किसी क्रिया में '-ing' जोड़कर उसे वाक्य का कर्ता (Subject) या कर्म (Object) बनाया जाता है, तो उसे Gerund कहते हैं।
                नियम: किसी भी Preposition (in, on, at, about, for, without) के बाद हमेशा Gerund (V-ing) आता है।
                उदाहरण: Thank you for coming. / Good at speaking English.
            """.trimIndent(),
            formulas = listOf("Preposition + V-ing (Gerund)", "Subject = V-ing + Verb"),
            positiveStructure = "Swimming keeps you healthy. / I enjoy cooking.",
            negativeStructure = "I don't mind waiting a few minutes.",
            questionStructure = "Are you good at speaking English?",
            examples = listOf(
                GrammarExample("He is good at playing guitar.", "वह गिटार बजाने में अच्छा है।", "Preposition 'at' के बाद 'playing' (Gerund) आया।"),
                GrammarExample("Thank you for helping me.", "मेरी मदद करने के लिए धन्यवाद।", "For के बाद helping आया।")
            ),
            commonMistakes = listOf(
                "Thank you for help me." to "Thank you for helping me.",
                "I am interested in learn English." to "I am interested in learning English."
            ),
            speakingPracticeSentences = listOf(
                "I love speaking in English.",
                "Thank you for inviting me.",
                "Smoking is injurious to health."
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "g1",
                    question = "She is fond of ___ stories.",
                    options = listOf("read", "reading", "to read", "reads"),
                    correctIndex = 1,
                    hindiExplanation = "Preposition 'of' के बाद हमेशा Gerund (-ing रूप) आता है।"
                )
            )
        ),
        GrammarTopic(
            letter = 'H',
            key = "helping_verbs",
            title = "Helping Verbs (Is, Am, Are, Was, Were, Has, Have, Had, Do, Does, Did)",
            hindiTitle = "सहायक क्रियाएं (Helping / Auxiliary Verbs)",
            shortSummary = "Mastering all primary auxiliary verbs for questions and negatives.",
            detailedRuleHindi = """
                - Present: Is (He/She/It/Singular), Am (I), Are (You/We/They/Plural).
                - Past: Was (I/He/She/It/Singular), Were (You/We/They/Plural).
                - Possession/Perfect: Has (Singular), Have (Plural/I), Had (All in Past).
                - Action questions/negatives: Do (I/You/We/They), Does (He/She/It), Did (All in Past).
            """.trimIndent(),
            formulas = listOf(
                "Subject + is/am/are + Adjective/Noun/V-ing",
                "Subject + was/were + V-ing/Noun",
                "Subject + has/have/had + V3"
            ),
            positiveStructure = "She is a teacher. / They were playing.",
            negativeStructure = "He does not know. / We did not go.",
            questionStructure = "Are you ready? / Did you finish your homework?",
            examples = listOf(
                GrammarExample("Does he work here?", "क्या वह यहाँ काम करता है?", "He के साथ Does का प्रयोग होता है।"),
                GrammarExample("They have finished the project.", "उन्होंने प्रोजेक्ट पूरा कर लिया है।", "They के साथ have + V3 आता है।")
            ),
            commonMistakes = listOf(
                "He do not know." to "He does not know.",
                "You was there." to "You were there.",
                "She has went to market." to "She has gone to market (Has + V3)."
            ),
            speakingPracticeSentences = listOf(
                "I am very excited about today.",
                "Where were you yesterday evening?",
                "Have you ever visited Delhi?"
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "h1",
                    question = "___ your friend speak English?",
                    options = listOf("Do", "Does", "Is", "Are"),
                    correctIndex = 1,
                    hindiExplanation = "'Your friend' एकवचन (Singular) है, इसलिए 'Does' का प्रयोग होगा।"
                )
            )
        ),
        GrammarTopic(
            letter = 'I',
            key = "interjections",
            title = "Interjections & Emotional Expressions",
            hindiTitle = "विस्मयादिबोधक शब्द (Oh, Wow, Ouch, Alas, Hey)",
            shortSummary = "Expressing surprise, joy, sorrow, pain, and greeting naturally.",
            detailedRuleHindi = """
                Interjections अचानक मन में उठने वाले भावों (हर्ष, शोक, आश्चर्य, दर्द) को व्यक्त करते हैं।
                - Joy: Wow! Hurrah! Awesome!
                - Sorrow: Alas! Oh no!
                - Pain: Ouch!
                - Greeting / Attention: Hey! Hello! Listen!
                - Relief: Phew!
            """.trimIndent(),
            formulas = listOf("Interjection! + Sentence"),
            positiveStructure = "Wow! You speak English so well!",
            negativeStructure = "Oh no! I lost my keys.",
            questionStructure = "Hey, are you coming with us?",
            examples = listOf(
                GrammarExample("Wow! That is amazing news!", "वाह! यह तो बहुत अच्छी खबर है!", "खुशी और आश्चर्य प्रकट करने के लिए Wow का प्रयोग।"),
                GrammarExample("Ouch! That hurt.", "आउच! चोट लगी।", "अचानक दर्द के लिए Ouch का प्रयोग।")
            ),
            commonMistakes = listOf(
                "Using wrong emotion with exclamation words." to "Match the feeling to the word."
            ),
            speakingPracticeSentences = listOf(
                "Wow! Look at that beautiful sunset!",
                "Oh no! I forgot my wallet at home.",
                "Hey! It's great to see you again."
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "i1",
                    question = "___! I stepped on a sharp pin.",
                    options = listOf("Hurrah", "Ouch", "Alas", "Wow"),
                    correctIndex = 1,
                    hindiExplanation = "अचानक दर्द होने पर 'Ouch!' का प्रयोग किया जाता है।"
                )
            )
        ),
        GrammarTopic(
            letter = 'J',
            key = "conjunctions",
            title = "Conjunctions (And, But, Because, Although, Unless)",
            hindiTitle = "संयोजक शब्द (जोड़ने वाले शब्द)",
            shortSummary = "Coordinating, subordinating, and correlative connectors for fluent sentence expansion.",
            detailedRuleHindi = """
                Conjunctions दो शब्दों या वाक्यों को जोड़ते हैं:
                - And (और): I like tea and coffee.
                - But (लेकिन/परंतु): He worked hard, but he failed.
                - Because (क्योंकि): I stayed home because it was raining.
                - Although / Even though (यद्यपि / हालांकि): Although he is poor, he is honest.
                - Unless (जब तक कि नहीं): You will not pass unless you study.
                - Therefore / So (इसलिए): He was ill, so he didn't come.
            """.trimIndent(),
            formulas = listOf("Clause 1 + Conjunction + Clause 2", "Although + Clause 1, Clause 2", "Unless + Present Tense, Future Tense"),
            positiveStructure = "I woke up early because I had an interview.",
            negativeStructure = "He is rich, but he is not happy.",
            questionStructure = "Do you want tea or coffee?",
            examples = listOf(
                GrammarExample("I want to learn English because it is important.", "मैं अंग्रेजी सीखना चाहता हूँ क्योंकि यह महत्वपूर्ण है।", "Because कारण बताने के लिए प्रयुक्त हुआ।"),
                GrammarExample("Although it was raining, we went out.", "हालांकि बारिश हो रही थी, फिर भी हम बाहर गए।", "Although विरोधाभास दर्शाता है।")
            ),
            commonMistakes = listOf(
                "Although he is poor, but he is honest." to "Although he is poor, he is honest. (Although के साथ but नहीं लगाते)",
                "Because he was sick, so he didn't come." to "Because he was sick, he didn't come. (Because के साथ so नहीं लगाते)"
            ),
            speakingPracticeSentences = listOf(
                "I was tired, but I finished my work.",
                "You cannot succeed unless you work hard.",
                "I like him because he is very helpful."
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "j1",
                    question = "You cannot enter the hall ___ you have a ticket.",
                    options = listOf("if", "unless", "because", "although"),
                    correctIndex = 1,
                    hindiExplanation = "'Unless' का अर्थ होता है 'जब तक कि नहीं' (Unless you have a ticket = जब तक आपके पास टिकट न हो)।"
                )
            )
        ),
        GrammarTopic(
            letter = 'K',
            key = "question_words",
            title = "Question Words (WH Family Mastery)",
            hindiTitle = "प्रश्नवाचक शब्द (WH Family)",
            shortSummary = "What, Why, Where, When, Who, Whom, Whose, Which, How, How much/many/far/often.",
            detailedRuleHindi = """
                - What (क्या): जानकारी के लिए (What is your name?)
                - Why (क्यों): कारण के लिए (Why are you late?)
                - Where (कहाँ): स्थान के लिए (Where do you live?)
                - When (कब): समय के लिए (When will you call?)
                - Who (कौन): व्यक्ति के लिए (Who is calling?)
                - Whom (किसे/किसको): Object के लिए (Whom did you meet?)
                - Whose (किसका): स्वामित्व के लिए (Whose bag is this?)
                - Which (कौन सा): विकल्प के लिए (Which color do you like?)
                - How (कैसे): तरीके के लिए (How did you do this?)
                - How many (कितने - Countable) vs How much (कितना - Uncountable)
                - How often (कितनी बार) & How far (कितनी दूर)
            """.trimIndent(),
            formulas = listOf("WH Word + Helping Verb + Subject + Main Verb + Object?"),
            positiveStructure = "WH questions require inversion of helping verb before subject.",
            negativeStructure = "Why don't you understand? / Why isn't he coming?",
            questionStructure = "Where do you want to go today?",
            examples = listOf(
                GrammarExample("How often do you exercise?", "आप कितनी बार व्यायाम करते हैं?", "आवृत्ति (Frequency) पूछने के लिए How often का प्रयोग।"),
                GrammarExample("Why are you not listening to me?", "आप मेरी बात क्यों नहीं सुन रहे हैं?", "Negative WH question संरचना।")
            ),
            commonMistakes = listOf(
                "Where you are going?" to "Where are you going? (Helping verb Subject से पहले आती है)",
                "Why he is crying?" to "Why is he crying?"
            ),
            speakingPracticeSentences = listOf(
                "What do you do in your free time?",
                "Where can I find a good restaurant nearby?",
                "How long will it take to reach the airport?"
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "k1",
                    question = "Correct the question: 'Where you live?'",
                    options = listOf("Where do you live?", "Where you do live?", "Where are you live?", "Where live you?"),
                    correctIndex = 0,
                    hindiExplanation = "Present Simple WH सवाल में 'WH word + do/does + subject + V1' आता है।"
                )
            )
        ),
        GrammarTopic(
            letter = 'L',
            key = "modals",
            title = "Modal Verbs (Can, Could, May, Might, Should, Must, Would)",
            hindiTitle = "मॉडल क्रियाएं (Can, Could, Should, Must, Would के उपयोग)",
            shortSummary = "Ability, permission, probability, advice, obligation, polite requests.",
            detailedRuleHindi = """
                - Can: वर्तमान क्षमता (I can speak English) या अनौपचारिक अनुमति (Can I sit here?).
                - Could: भूतकाल की क्षमता (I could run fast) या अति-विनम्र निवेदन (Could you please help me?).
                - Should: सलाह या कर्तव्य (You should sleep early).
                - Must: पक्की अनिवार्यता या कड़ा नियम (You must wear a helmet).
                - May / Might: संभावना (It may rain today - 50%, It might rain - 20%).
                - Would: विनम्र इच्छा या काल्पनिक स्थिति (Would you like some tea? / I would love to come).
                - Ought to: नैतिक कर्तव्य (We ought to respect our elders).
                नियम: किसी भी Modal Verb के तुरंत बाद हमेशा क्रिया का पहला रूप (V1) आता है।
            """.trimIndent(),
            formulas = listOf("Subject + Modal Verb + V1 (Base Form) + Object"),
            positiveStructure = "You should practice speaking every single day.",
            negativeStructure = "You must not touch this wire.",
            questionStructure = "Could you please repeat what you just said?",
            examples = listOf(
                GrammarExample("Could you please lend me your pen?", "क्या आप मुझे अपना पेन दे सकते हैं?", "अति विनम्र निवेदन के लिए Could का प्रयोग।"),
                GrammarExample("You should consult a doctor immediately.", "आपको तुरंत डॉक्टर से सलाह लेनी चाहिए।", "सलाह देने के लिए Should का प्रयोग।")
            ),
            commonMistakes = listOf(
                "He can speaks English." to "He can speak English. (Modal के बाद s/es नहीं लगता)",
                "I should to go." to "I should go. (Should के बाद 'to' नहीं आता)"
            ),
            speakingPracticeSentences = listOf(
                "Can you speak a little louder, please?",
                "You should not give up on your dreams.",
                "Would you like to join us for dinner tonight?"
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "l1",
                    question = "You look very sick. You ___ see a doctor.",
                    options = listOf("should", "can", "might", "would"),
                    correctIndex = 0,
                    hindiExplanation = "स्वास्थ्य के लिए सलाह देने के लिए 'should' सबसे उपयुक्त मॉडल है।"
                )
            )
        ),
        GrammarTopic(
            letter = 'M',
            key = "nouns",
            title = "Nouns & Types (Countable, Uncountable, Plural)",
            hindiTitle = "संज्ञा और उसके प्रकार (एकवचन, बहुवचन, गणनीय, अगणनीय)",
            shortSummary = "Proper, Common, Collective, Abstract, Material, Countable/Uncountable.",
            detailedRuleHindi = """
                - Countable Nouns: जिन्हें गिना जा सके (Pen, Book, Apple, City) -> इनका बहुवचन बनता है (Books, Pens)।
                - Uncountable Nouns: जिन्हें गिना नहीं जा सकता, केवल नापा/तौला जा सकता है (Water, Milk, Money, Sugar, Information, Advice, Furniture, Luggage, Hair)।
                नियम: Uncountable Nouns के साथ 'a/an' या बहुवचन 's/es' कभी नहीं लगता (Advices गलत है, Advice सही है)।
            """.trimIndent(),
            formulas = listOf("Countable: Singular (a book) / Plural (books)", "Uncountable: No 's/es' (Information, Furniture, Advice)"),
            positiveStructure = "He gave me some good advice.",
            negativeStructure = "There is not much milk left.",
            questionStructure = "Do you have any information about the train?",
            examples = listOf(
                GrammarExample("My luggage is very heavy.", "मेरा सामान बहुत भारी है।", "Luggage हमेशा एकवचन रहता है, 'luggages' नहीं होता।"),
                GrammarExample("She gave me useful advice.", "उसने मुझे उपयोगी सलाह दी।", "Advice अनकाउंटेबल है, 'an advice' या 'advices' नहीं कहते।")
            ),
            commonMistakes = listOf(
                "He gave me many advices." to "He gave me a lot of advice / pieces of advice.",
                "I bought new furnitures." to "I bought new furniture."
            ),
            speakingPracticeSentences = listOf(
                "I need some advice regarding my career.",
                "All the furniture in the room is brand new.",
                "Can I have two cups of tea, please?"
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "m1",
                    question = "Which of the following is correct?",
                    options = listOf("He gave me an advice.", "He gave me some advice.", "He gave me many advices.", "He gave me an advices."),
                    correctIndex = 1,
                    hindiExplanation = "'Advice' अगणनीय (Uncountable) संज्ञा है, इसलिए 'some advice' सही है।"
                )
            )
        ),
        GrammarTopic(
            letter = 'N',
            key = "negative_sentences",
            title = "Negative Sentences in All Tenses & Modals",
            hindiTitle = "नकारात्मक वाक्य निर्माण (सभी कालों में Not के नियम)",
            shortSummary = "Present, Past, Future, Continuous, Perfect, and Modal negative structures.",
            detailedRuleHindi = """
                अंग्रेजी में नकारात्मक वाक्य बनाने के नियम:
                - Simple Present: Do not (Don't) / Does not (Doesn't) + V1.
                - Simple Past: Did not (Didn't) + V1. (Did के बाद हमेशा V1)
                - Continuous: is/am/are/was/were + not + V-ing.
                - Perfect: has/have/had + not + V3.
                - Modals: Cannot, Should not, Must not + V1.
                नियम: Double Negative (जैसे: I don't know nothing) गलत है -> I don't know anything.
            """.trimIndent(),
            formulas = listOf("Subject + Helping Verb + NOT + Main Verb + Object"),
            positiveStructure = "I play cricket. / I went there.",
            negativeStructure = "I do not play cricket. / I did not go there.",
            questionStructure = "Why did you not inform me?",
            examples = listOf(
                GrammarExample("I didn't recognize you at first.", "मैंने पहले आपको नहीं पहचाना।", "Didn't के बाद V1 (recognize) का प्रयोग हुआ।"),
                GrammarExample("She doesn't like tea.", "उसे चाय पसंद नहीं है।", "Doesn't के बाद like में s/es हट जाता है।")
            ),
            commonMistakes = listOf(
                "He doesn't likes coffee." to "He doesn't like coffee.",
                "I didn't went there." to "I didn't go there.",
                "I don't have no money." to "I don't have any money."
            ),
            speakingPracticeSentences = listOf(
                "I don't agree with your opinion.",
                "He didn't receive your message yesterday.",
                "We haven't met for a very long time."
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "n1",
                    question = "Choose the correct negative sentence:",
                    options = listOf("She didn't wrote the letter.", "She didn't write the letter.", "She not wrote the letter.", "She doesn't wrote the letter."),
                    correctIndex = 1,
                    hindiExplanation = "'Didn't' के बाद हमेशा क्रिया का पहला रूप (V1 - write) आता है।"
                )
            )
        ),
        GrammarTopic(
            letter = 'O',
            key = "objects",
            title = "Direct & Indirect Objects",
            hindiTitle = "प्रत्यक्ष और अप्रत्यक्ष कर्म (Direct & Indirect Objects)",
            shortSummary = "Direct object (What?) vs Indirect object (To whom?) and pronouns.",
            detailedRuleHindi = """
                - Direct Object: जो क्रिया का सीधा प्रभाव झेलता है (सवाल: क्या? / किसे?).
                - Indirect Object: जिसे या जिसके लिए काम किया जाता है (सवाल: किसको? / किसके लिए?).
                संरचना 1: Subject + Verb + Indirect Object + Direct Object (I gave him a book).
                संरचना 2: Subject + Verb + Direct Object + to/for + Indirect Object (I gave a book to him).
            """.trimIndent(),
            formulas = listOf("S + V + IO + DO", "S + V + DO + to/for + IO"),
            positiveStructure = "She told me a secret. / She told a secret to me.",
            negativeStructure = "He did not send me the email.",
            questionStructure = "Did you give him the keys?",
            examples = listOf(
                GrammarExample("My father bought me a laptop.", "मेरे पिताजी ने मुझे एक लैपटॉप खरीद कर दिया।", "Me = Indirect Object, A laptop = Direct Object."),
                GrammarExample("Can you pass me the salt?", "क्या आप मुझे नमक पकड़ा सकते हैं?", "Me = Indirect Object, The salt = Direct Object.")
            ),
            commonMistakes = listOf(
                "I gave to him a pen." to "I gave him a pen. / I gave a pen to him."
            ),
            speakingPracticeSentences = listOf(
                "Can you show me the way to the station?",
                "I sent her a birthday greeting yesterday.",
                "Please bring me a glass of water."
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "o1",
                    question = "Identify the direct object in: 'Rahul gave Priya a flower.'",
                    options = listOf("Rahul", "gave", "Priya", "a flower"),
                    correctIndex = 3,
                    hindiExplanation = "क्या दिया? 'A flower' - यह Direct Object है। किसको दिया? 'Priya' - यह Indirect Object है।"
                )
            )
        ),
        GrammarTopic(
            letter = 'P',
            key = "pronouns",
            title = "Pronouns (Personal, Possessive, Reflexive, Relative)",
            hindiTitle = "सर्वनाम (I, You, He, She, My, Mine, Myself, Who, Which)",
            shortSummary = "Subject vs Object pronouns, Possessive adjectives vs pronouns, Reflexive pronouns.",
            detailedRuleHindi = """
                - Subject Pronouns: I, You, He, She, It, We, They (काम करने वाले).
                - Object Pronouns: Me, You, Him, Her, It, Us, Them (क्रिया का प्रभाव झेलने वाले).
                - Possessive Adjective: My, Your, His, Her, Our, Their (+ Noun, e.g., My car).
                - Possessive Pronoun: Mine, Yours, His, Hers, Ours, Theirs (No Noun, e.g., This car is mine).
                - Reflexive: Myself, Yourself, Himself, Herself, Ourselves, Themselves (खुद/स्वयं).
            """.trimIndent(),
            formulas = listOf("Subject + Verb + Object Pronoun", "Possessive Pronoun = Noun + is mine/yours"),
            positiveStructure = "He told me that this book is his.",
            negativeStructure = "They did not invite us to the party.",
            questionStructure = "Is this jacket yours or mine?",
            examples = listOf(
                GrammarExample("This bag is mine.", "यह बैग मेरा है।", "Mine possessive pronoun है।"),
                GrammarExample("I prepared the entire dinner myself.", "मैंने पूरा खाना खुद बनाया।", "Myself reflexive pronoun है।")
            ),
            commonMistakes = listOf(
                "Him and me are friends." to "He and I are friends.",
                "This is mine book." to "This is my book. / This book is mine."
            ),
            speakingPracticeSentences = listOf(
                "She introduced herself to everyone in the room.",
                "Is that umbrella yours, or did someone leave it here?",
                "My brother and I are planning a trip."
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "p1",
                    question = "Between you and ___, this project is going to succeed.",
                    options = listOf("I", "me", "myself", "mine"),
                    correctIndex = 1,
                    hindiExplanation = "Preposition (Between) के बाद हमेशा Object Pronoun (me) आता है।"
                )
            )
        ),
        GrammarTopic(
            letter = 'Q',
            key = "question_formation",
            title = "Question Formation (Yes/No, WH, Tag Questions)",
            hindiTitle = "प्रश्न निर्माण कला (Yes/No, WH और Question Tags)",
            shortSummary = "Inversion rules, Tag questions, Indirect questions.",
            detailedRuleHindi = """
                1. Yes/No Questions: Helping Verb से शुरू होते हैं (Do you know him? / Is she coming?).
                2. WH Questions: WH word + Helping Verb + Subject + Main Verb (Where do you live?).
                3. Question Tags: वाक्य के अंत में पुष्टि करने के लिए:
                   - Positive sentence -> Negative tag (You speak English, don't you?).
                   - Negative sentence -> Positive tag (He isn't coming, is he?).
                4. Indirect Questions: विनम्र सवाल (Can you tell me where the station is? - No inversion in second part).
            """.trimIndent(),
            formulas = listOf(
                "Helping Verb + Subject + Main Verb + Object? (Yes/No)",
                "WH Word + Helping Verb + Subject + Main Verb? (WH)",
                "Positive Sentence, negative tag? / Negative Sentence, positive tag?"
            ),
            positiveStructure = "You are ready, aren't you?",
            negativeStructure = "You didn't see him, did you?",
            questionStructure = "Could you tell me what time the train arrives?",
            examples = listOf(
                GrammarExample("You are coming with us, aren't you?", "तुम हमारे साथ आ रहे हो, है ना?", "Positive sentence के साथ Negative tag 'aren't you' लगा।"),
                GrammarExample("Do you know where he lives?", "क्या आप जानते हैं कि वह कहाँ रहता है?", "Indirect question में 'where he lives' सीधा क्रम रहता है।")
            ),
            commonMistakes = listOf(
                "You are coming, no?" to "You are coming, aren't you?",
                "Can you tell me where does he live?" to "Can you tell me where he lives?"
            ),
            speakingPracticeSentences = listOf(
                "It's a beautiful day today, isn't it?",
                "Could you tell me how to reach the nearest metro station?",
                "Do you have a few minutes to talk right now?"
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "q1",
                    question = "He works in a software company, ___?",
                    options = listOf("isn't he", "doesn't he", "don't he", "does he"),
                    correctIndex = 1,
                    hindiExplanation = "Present Simple (works) का positive वाक्य है, इसलिए negative tag 'doesn't he?' होगा।"
                )
            )
        ),
        GrammarTopic(
            letter = 'R',
            key = "reported_speech",
            title = "Reported Speech (Direct & Indirect)",
            hindiTitle = "प्रत्यक्ष और अप्रत्यक्ष कथन (Direct & Indirect Speech)",
            shortSummary = "Converting quotes to indirect statements, questions, commands with tense and pronoun changes.",
            detailedRuleHindi = """
                जब किसी की कही हुई बात को अपने शब्दों में बताया जाता है:
                - Reporting Verb Past में हो (said):
                  1. Simple Present -> Simple Past (He said, 'I work' -> He said that he worked).
                  2. Present Continuous -> Past Continuous (is working -> was working).
                  3. Present Perfect -> Past Perfect (has worked -> had worked).
                  4. Can -> Could, Will -> Would, May -> Might.
                  5. Now -> Then, Today -> That day, Tomorrow -> The next day.
            """.trimIndent(),
            formulas = listOf("Direct: He said, 'I am busy.' -> Indirect: He said that he was busy."),
            positiveStructure = "He told me that he would call me later.",
            negativeStructure = "She said that she had not received the parcel.",
            questionStructure = "He asked me if I was ready.",
            examples = listOf(
                GrammarExample("Rahul said, 'I am tired.' -> Rahul said that he was tired.", "राहुल ने कहा, 'मैं थका हूँ।' -> राहुल ने कहा कि वह थका हुआ था।", "Present Continuous Past में बदल गया।"),
                GrammarExample("She asked me, 'Where do you live?' -> She asked me where I lived.", "उसने मुझसे पूछा कि मैं कहाँ रहता था।", "सवाल Indirect में साधारण क्रम में बदल गया।")
            ),
            commonMistakes = listOf(
                "He told that he is busy." to "He said that he was busy. / He told me that...",
                "She asked me where did I live." to "She asked me where I lived."
            ),
            speakingPracticeSentences = listOf(
                "My manager told me that the meeting was postponed.",
                "She asked me if I could help her with the presentation.",
                "He promised that he would arrive on time."
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "r1",
                    question = "Convert to Indirect: Amit said, 'I can speak English.'",
                    options = listOf("Amit said that he can speak English.", "Amit said that he could speak English.", "Amit told that he spoke English.", "Amit said that he is speaking English."),
                    correctIndex = 1,
                    hindiExplanation = "Reporting verb 'said' past में है, इसलिए modal 'can' का 'could' हो जाएगा।"
                )
            )
        ),
        GrammarTopic(
            letter = 'S',
            key = "subject_verb_agreement",
            title = "Subject-Verb Agreement",
            hindiTitle = "कर्ता और क्रिया का तालमेल (Subject-Verb Agreement)",
            shortSummary = "Singular vs plural subjects, each/every, either/neither, collective nouns.",
            detailedRuleHindi = """
                नियम 1: Singular Subject के साथ Singular Verb (He goes, The dog barks).
                नियम 2: Plural Subject के साथ Plural Verb (They go, The dogs bark).
                नियम 3: Each, Every, Everyone, Someone, Nobody के साथ हमेशा Singular Verb आती है (Everyone is here, NOT are).
                नियम 4: Either... Or / Neither... Nor में Verb पास वाले Subject के अनुसार लगती है (Neither Rahul nor his friends are coming).
                नियम 5: 'There is' (एकवचन के लिए) vs 'There are' (बहुवचन के लिए).
            """.trimIndent(),
            formulas = listOf("Singular Subject + Singular Verb (V1 + s/es or is/was/has)", "Plural Subject + Plural Verb (V1 base or are/were/have)"),
            positiveStructure = "Each student has received a certificate.",
            negativeStructure = "Neither of the answers is correct.",
            questionStructure = "Is everyone ready to begin?",
            examples = listOf(
                GrammarExample("Everyone wants to succeed in life.", "हर कोई जीवन में सफल होना चाहता है।", "Everyone एकवचन माना जाता है, इसलिए 'wants' आया।"),
                GrammarExample("There are many cars in the parking lot.", "पार्किंग में बहुत सारी गाड़ियाँ हैं।", "Cars बहुवचन है इसलिए 'There are' आया।")
            ),
            commonMistakes = listOf(
                "Everyone are happy." to "Everyone is happy.",
                "One of my friend is a doctor." to "One of my friends is a doctor.",
                "The quality of these apples are good." to "The quality of these apples is good. (Subject 'quality' है)"
            ),
            speakingPracticeSentences = listOf(
                "One of my best friends lives in London.",
                "Neither my brother nor I am interested in this.",
                "There is a lot of traffic on the road today."
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "s1",
                    question = "One of my brothers ___ in a software company.",
                    options = listOf("work", "works", "are working", "were working"),
                    correctIndex = 1,
                    hindiExplanation = "'One of my brothers' का अर्थ है 'मेरे भाइयों में से एक' (एकवचन), इसलिए 'works' आएगा।"
                )
            )
        ),
        GrammarTopic(
            letter = 'T',
            key = "all_12_tenses",
            title = "ALL 12 TENSES (The Complete Master Guide)",
            hindiTitle = "सभी 12 काल (Tenses का पूरा निचोड़)",
            shortSummary = "Simple, Continuous, Perfect, Perfect Continuous in Present, Past, and Future with formulas.",
            detailedRuleHindi = """
                ★ 1. PRESENT TENSES:
                - Simple Present: S + V1 (s/es) + O (ता है, ती है, ते हैं) -> He goes to office daily.
                - Present Continuous: S + is/am/are + V-ing + O (रहा है, रही है) -> He is learning English.
                - Present Perfect: S + has/have + V3 + O (चुका है, लिया है) -> He has finished his work.
                - Present Perfect Continuous: S + has/have been + V-ing + since/for (से कर रहा है) -> He has been studying for 2 hours.

                ★ 2. PAST TENSES:
                - Simple Past: S + V2 + O (आया, गया, खाया / ता था) -> I met him yesterday.
                - Past Continuous: S + was/were + V-ing + O (रहा था, रही थी) -> I was sleeping when you called.
                - Past Perfect: S + had + V3 + O (चुका था, दिया था) -> The train had left before I reached.
                - Past Perfect Continuous: S + had been + V-ing + since/for (से कर रहा था) -> He had been waiting since morning.

                ★ 3. FUTURE TENSES:
                - Simple Future: S + will + V1 + O (गा, गी, गे) -> I will call you tomorrow.
                - Future Continuous: S + will be + V-ing + O (रहा होगा) -> I will be waiting for you.
                - Future Perfect: S + will have + V3 + O (चुका होगा) -> I will have completed the project by Monday.
                - Future Perfect Continuous: S + will have been + V-ing + for (से कर रहा होगा) -> I will have been working here for 5 years.
            """.trimIndent(),
            formulas = listOf(
                "Simple Present: S + V1(s/es) | Past: S + V2 | Future: S + will + V1",
                "Continuous: S + (is/am/are/was/were/will be) + V-ing",
                "Perfect: S + (has/have/had/will have) + V3",
                "Perfect Continuous: S + (has/have/had/will have) been + V-ing + since/for"
            ),
            positiveStructure = "I have been practicing English speaking for three months.",
            negativeStructure = "He has not visited Delhi yet.",
            questionStructure = "What were you doing yesterday at 8 PM?",
            examples = listOf(
                GrammarExample("I have been learning English for two months.", "मैं दो महीने से अंग्रेजी सीख रहा हूँ।", "Present Perfect Continuous: समय अवधि (for two months) के साथ।"),
                GrammarExample("When I reached the station, the train had already left.", "जब मैं स्टेशन पहुँचा, ट्रेन पहले ही जा चुकी थी।", "Past Perfect: जो काम पहले हुआ उसमें had + V3 लगा।")
            ),
            commonMistakes = listOf(
                "I am living in Delhi since 2020." to "I have been living in Delhi since 2020.",
                "Yesterday I have seen a movie." to "Yesterday I saw a movie. (Past time के साथ Simple Past लगता है)"
            ),
            speakingPracticeSentences = listOf(
                "I have been living in this city for five years.",
                "By this time next year, I will have mastered spoken English.",
                "What were you doing when the power went out?"
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "t1",
                    question = "I ___ in this apartment for three years.",
                    options = listOf("am living", "have been living", "was living", "live"),
                    correctIndex = 1,
                    hindiExplanation = "'For three years' समय अवधि है, इसलिए Present Perfect Continuous (have been living) सही है।"
                ),
                GrammarPracticeQuestion(
                    id = "t2",
                    question = "Yesterday, I ___ a new mobile phone.",
                    options = listOf("have bought", "bought", "buy", "had bought"),
                    correctIndex = 1,
                    hindiExplanation = "Yesterday के साथ केवल Simple Past (V2 - bought) का प्रयोग होता है।"
                )
            )
        ),
        GrammarTopic(
            letter = 'U',
            key = "prepositions",
            title = "Prepositions (In, On, At, By, With, For, Since, Between, Into)",
            hindiTitle = "सम्बन्धबोधक अव्यय (In, On, At, Between, Among, Into)",
            shortSummary = "Prepositions of place, time, movement, and common confusion pairings.",
            detailedRuleHindi = """
                - In, On, At for Time:
                  - At: निश्चित समय (At 5 PM, At night, At noon).
                  - On: दिन और तारीख (On Monday, On 15th August).
                  - In: महीने, साल, मौसम, दशक (In July, In 2025, In summer).
                - In, On, At for Place:
                  - At: छोटा स्थान या बिंदु (At the door, At the bus stop).
                  - In: बंद या बड़ा क्षेत्र/शहर/देश (In the room, In India).
                  - On: सतह के ऊपर (On the table, On the wall).
                - Between (दो के बीच) vs Among (दो से अधिक के बीच).
                - In (स्थिर अंदर) vs Into (गति के साथ अंदर कूदना/जाना - He jumped into the river).
            """.trimIndent(),
            formulas = listOf(
                "Time: At (Exact time) -> On (Days/Dates) -> In (Months/Years)",
                "Place: At (Point) -> On (Surface) -> In (Enclosed area)",
                "Between (2 people/things) vs Among (3+)"
            ),
            positiveStructure = "I will meet you at 4 PM on Friday.",
            negativeStructure = "He is not in the office right now.",
            questionStructure = "Are you sitting on the chair or on the bed?",
            examples = listOf(
                GrammarExample("The meeting is scheduled at 10 AM on Monday.", "मीटिंग सोमवार को सुबह 10 बजे तय है।", "10 AM के साथ at, Monday के साथ on लगा।"),
                GrammarExample("Divide these sweets between Rahul and Amit.", "इन मिठाइयों को राहुल और अमित के बीच बाँट दो।", "दो लोगों के लिए Between का प्रयोग हुआ।")
            ),
            commonMistakes = listOf(
                "I was born in 15th August." to "I was born on 15th August.",
                "Distribute the apples between all students." to "Distribute the apples among all students.",
                "He jumped in the pool." to "He jumped into the pool."
            ),
            speakingPracticeSentences = listOf(
                "I will call you at six in the evening.",
                "Let's discuss this matter between ourselves.",
                "He put his phone into his pocket."
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "u1",
                    question = "My birthday is ___ October 24th.",
                    options = listOf("in", "on", "at", "by"),
                    correctIndex = 1,
                    hindiExplanation = "किसी निश्चित तारीख (Date) से पहले 'on' का प्रयोग होता है।"
                )
            )
        ),
        GrammarTopic(
            letter = 'V',
            key = "active_passive",
            title = "Active & Passive Voice",
            hindiTitle = "कर्तृवाच्य और कर्मवाच्य (Active vs Passive Voice)",
            shortSummary = "Focus on the action or object when the doer is unknown or unimportant.",
            detailedRuleHindi = """
                - Active Voice: कर्ता (Doer) मुख्य होता है (Rahul wrote a letter).
                - Passive Voice: कर्म (Object/Action) मुख्य होता है (A letter was written by Rahul).
                नियम: Passive Voice में हमेशा क्रिया का तीसरा रूप (V3 - Past Participle) आता है।
                - Simple Present: Object + is/am/are + V3 (English is spoken worldwide).
                - Simple Past: Object + was/were + V3 (The car was repaired yesterday).
                - Present Perfect: Object + has/have been + V3 (The work has been completed).
            """.trimIndent(),
            formulas = listOf("Active: Subject + Verb + Object", "Passive: Object + Helping Verb + V3 + (by Subject)"),
            positiveStructure = "English is spoken all over the world.",
            negativeStructure = "The project has not been approved yet.",
            questionStructure = "Was the email sent yesterday?",
            examples = listOf(
                GrammarExample("The thief was arrested by the police.", "चोर को पुलिस द्वारा गिरफ्तार कर लिया गया।", "Passive voice: was arrested (V3)."),
                GrammarExample("Coffee is grown in Karnataka.", "कर्नाटक में कॉफी उगाई जाती है।", "जब Doer महत्वपूर्ण न हो तो Passive का प्रयोग होता है।")
            ),
            commonMistakes = listOf(
                "The car was repair yesterday." to "The car was repaired yesterday (Passive requires V3).",
                "English spoken here." to "English is spoken here."
            ),
            speakingPracticeSentences = listOf(
                "The decision was announced this morning.",
                "All flights have been cancelled due to bad weather.",
                "This bridge was constructed in 1995."
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "v1",
                    question = "Change to Passive: 'They built this house in 2010.'",
                    options = listOf("This house is built in 2010.", "This house was built in 2010.", "This house has built in 2010.", "This house built in 2010."),
                    correctIndex = 1,
                    hindiExplanation = "Simple Past का Passive 'was/were + V3' (was built) होता है।"
                )
            )
        ),
        GrammarTopic(
            letter = 'W',
            key = "relative_clauses",
            title = "Relative Clauses (Who, Whom, Whose, Which, That, Where)",
            hindiTitle = "सम्बन्धवाचक उपवाक्य (जो, जिसे, जिसका, जहाँ)",
            shortSummary = "Combining sentences smoothly using defining and non-defining relative pronouns.",
            detailedRuleHindi = """
                - Who: लोगों के लिए (जो - The man who helped me).
                - Whom: लोगों के लिए कर्म रूप में (जिसे - The girl whom I met).
                - Whose: स्वामित्व के लिए (जिसका - The student whose project won).
                - Which: वस्तुओं और जानवरों के लिए (जो/जिसका - The car which broke down).
                - That: लोगों और वस्तुओं दोनों के लिए (defining clauses में).
                - Where: स्थान के लिए (जहाँ - The city where I grew up).
            """.trimIndent(),
            formulas = listOf("Noun + Who/Which/That/Where + Clause"),
            positiveStructure = "The man who is standing there is my teacher.",
            negativeStructure = "I don't know the person who called you.",
            questionStructure = "Do you know anyone who can speak French?",
            examples = listOf(
                GrammarExample("I met a friend who works at Google.", "मैं एक दोस्त से मिला जो गूगल में काम करता है।", "Who व्यक्ति के लिए प्रयुक्त हुआ।"),
                GrammarExample("This is the house where I was born.", "यह वही घर है जहाँ मेरा जन्म हुआ था।", "Where स्थान के लिए प्रयुक्त हुआ।")
            ),
            commonMistakes = listOf(
                "The book who is on the table." to "The book which/that is on the table.",
                "The boy which won the race." to "The boy who won the race."
            ),
            speakingPracticeSentences = listOf(
                "She is the person who inspired me to learn English.",
                "This is the smartphone that I purchased yesterday.",
                "Do you remember the place where we first met?"
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "w1",
                    question = "The woman ___ lives next door is a doctor.",
                    options = listOf("who", "which", "whose", "whom"),
                    correctIndex = 0,
                    hindiExplanation = "व्यक्ति (The woman) के लिए Subject रूप में 'who' का प्रयोग होता है।"
                )
            )
        ),
        GrammarTopic(
            letter = 'X',
            key = "exclamatory_sentences",
            title = "Exclamatory Sentences (What a... / How...)",
            hindiTitle = "विस्मयादिबोधक वाक्य (कितना सुंदर!, क्या बात है!)",
            shortSummary = "Constructing expressive high-emotion statements using 'What a' and 'How'.",
            detailedRuleHindi = """
                - What a / an + Adjective + Singular Noun! (e.g., What a beautiful car! / What an amazing idea!)
                - What + Adjective + Plural/Uncountable Noun! (e.g., What delicious food! / What beautiful flowers!)
                - How + Adjective/Adverb + Subject + Verb! (e.g., How fast he runs! / How beautiful she is!)
            """.trimIndent(),
            formulas = listOf("What a/an + (Adj) + Noun!", "How + Adj/Adv + (S + V)!"),
            positiveStructure = "What a pleasant surprise to see you here!",
            negativeStructure = "How careless could you be!",
            questionStructure = "Isn't it amazing what he achieved?",
            examples = listOf(
                GrammarExample("What a wonderful performance!", "कितना शानदार प्रदर्शन था!", "What a + Adjective + Noun की संरचना।"),
                GrammarExample("How smartly he solved the puzzle!", "उसने कितनी समझदारी से पहेली हल की!", "How + Adverb की संरचना।")
            ),
            commonMistakes = listOf(
                "How a beautiful car!" to "What a beautiful car! / How beautiful the car is!"
            ),
            speakingPracticeSentences = listOf(
                "What a fantastic movie that was!",
                "How kind of you to help me!",
                "What an incredible journey it has been!"
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "x1",
                    question = "___ beautiful sunset!",
                    options = listOf("What a", "How a", "What", "How"),
                    correctIndex = 0,
                    hindiExplanation = "एकवचन संज्ञा (sunset) के साथ 'What a' का प्रयोग होता है।"
                )
            )
        ),
        GrammarTopic(
            letter = 'Y',
            key = "yes_no_questions",
            title = "Yes/No Question Mastery",
            hindiTitle = "हाँ/ना वाले प्रश्न (Do, Does, Did, Is, Are, Have, Can)",
            shortSummary = "Asking and answering Yes/No questions swiftly with correct auxiliary verb alignment.",
            detailedRuleHindi = """
                Yes/No सवाल बनाने के लिए सहायक क्रिया को Subject से पहले रखा जाता है:
                - Do you...? (I/You/We/They के सामान्य वर्तमान के लिए)
                - Does he/she/it...? (एकवचन के सामान्य वर्तमान के लिए)
                - Did you...? (भूतकाल के सवाल के लिए)
                - Is he...? / Are you...? (वर्तमान स्थिति/कार्य के लिए)
                - Have you...? (पूर्ण कार्य के लिए)
                - Can you...? (क्षमता/अनुमति के लिए)
                - Will you...? (भविष्य के लिए)
            """.trimIndent(),
            formulas = listOf("Auxiliary Verb + Subject + Main Verb + Object?"),
            positiveStructure = "Yes, I do. / Yes, I am. / Yes, I have.",
            negativeStructure = "No, I don't. / No, I'm not. / No, I haven't.",
            questionStructure = "Did you understand the lesson clearly?",
            examples = listOf(
                GrammarExample("Are you ready for the speaking test?", "क्या आप बोलने की परीक्षा के लिए तैयार हैं?", "Are + you + ready."),
                GrammarExample("Did you finish your project on time?", "क्या आपने अपना प्रोजेक्ट समय पर पूरा किया?", "Did + you + finish (V1).")
            ),
            commonMistakes = listOf(
                "You are ready?" to "Are you ready?",
                "You did went there?" to "Did you go there?"
            ),
            speakingPracticeSentences = listOf(
                "Do you understand what I am saying?",
                "Have you ever traveled by airplane?",
                "Can you help me with this problem?"
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "y1",
                    question = "___ you see the match last night?",
                    options = listOf("Do", "Did", "Have", "Were"),
                    correctIndex = 1,
                    hindiExplanation = "'Last night' बीते समय का संकेत है, इसलिए 'Did' से प्रश्न शुरू होगा।"
                )
            )
        ),
        GrammarTopic(
            letter = 'Z',
            key = "advanced_grammar",
            title = "Advanced Grammar (Inversion, Cleft Sentences, Subjunctive)",
            hindiTitle = "उन्नत ग्रामर (Inversion, Emphasis & Formal Structures)",
            shortSummary = "Inversion for emphasis, cleft sentences, advanced modal structures, formal vs informal nuances.",
            detailedRuleHindi = """
                - Negative Inversion: जब वाक्य Hardly, Seldom, Rarely, Never, Scarcely से शुरू हो तो Helping verb Subject से पहले आ जाती है:
                  (Hardly had I arrived when the phone rang.)
                - Cleft Sentences (जोर देने के लिए): It was Rahul who told me the truth. / What I need is more practice.
                - Advanced Modals: Must have + V3 (पक्का ऐसा हुआ होगा - He must have reached by now), Should have + V3 (चाहिए था - You should have told me).
            """.trimIndent(),
            formulas = listOf(
                "Hardly/Scarcely + had + Subject + V3 + when...",
                "It is/was + (Focus Noun) + who/that...",
                "Subject + should have / must have / could have + V3"
            ),
            positiveStructure = "Never in my life have I seen such dedication.",
            negativeStructure = "You shouldn't have spoken to him like that.",
            questionStructure = "What would you have done differently?",
            examples = listOf(
                GrammarExample("You should have informed me earlier.", "आपको मुझे पहले सूचित करना चाहिए था।", "Should have + V3 (Past regret / obligation)."),
                GrammarExample("Hardly had we started our journey when it began to rain.", "हमने अपनी यात्रा शुरू ही की थी कि बारिश शुरू हो गई।", "Negative inversion संरचना।")
            ),
            commonMistakes = listOf(
                "You should have inform me." to "You should have informed me (have + V3).",
                "Hardly I had reached..." to "Hardly had I reached..."
            ),
            speakingPracticeSentences = listOf(
                "You should have told me about the delay.",
                "He must have forgotten about our meeting.",
                "What I really love about English is how global it is."
            ),
            miniTestQuestions = listOf(
                GrammarPracticeQuestion(
                    id = "z1",
                    question = "You missed the flight! You ___ left home earlier.",
                    options = listOf("should have", "must", "can have", "would"),
                    correctIndex = 0,
                    hindiExplanation = "भूतकाल में कोई काम 'करना चाहिए था' कहने के लिए 'should have + V3' (should have left) का प्रयोग होता है।"
                )
            )
        )
    )
}
