package com.example.model

data class ConversationMessage(
    val id: String,
    val sender: MessageSender,
    val textEnglish: String,
    val textHindi: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val grammarFeedback: String? = null,
    val betterAlternative: String? = null,
    val isSpeakingPlaying: Boolean = false
)

enum class MessageSender {
    AI_TEACHER,
    LEARNER
}

data class ConversationScenario(
    val id: String,
    val title: String,
    val hindiTitle: String,
    val iconName: String,
    val category: String,
    val aiRole: String, // e.g. "Waiter at Italian Cafe"
    val userRole: String, // e.g. "Customer ordering dinner"
    val initialAiMessageEnglish: String,
    val initialAiMessageHindi: String,
    val starterPrompts: List<String>,
    val targetVocabulary: List<String>,
    val difficulty: String // "Beginner (Level 0-20)", "Intermediate (21-60)", "Advanced (61-100)"
)

object ConversationDataRepository {
    val SCENARIOS: List<ConversationScenario> = listOf(
        ConversationScenario(
            id = "restaurant",
            title = "Ordering Food at a Restaurant",
            hindiTitle = "रेस्टोरेंट में खाना ऑर्डर करना",
            iconName = "restaurant",
            category = "Daily Life",
            aiRole = "Friendly Waiter (रोहित)",
            userRole = "Hungry Customer",
            initialAiMessageEnglish = "Good evening, welcome to Royal Spice! Table for how many people today?",
            initialAiMessageHindi = "शुभ संध्या! रॉयल स्पाइस में आपका स्वागत है। आज कितने लोगों के लिए टेबल चाहिए?",
            starterPrompts = listOf(
                "Table for two, please.",
                "Can I see the menu, please?",
                "What do you recommend for dinner?",
                "Could you bring a glass of water first?"
            ),
            targetVocabulary = listOf("Menu", "Recommend", "Appetizer", "Main Course", "Bill / Check", "Delicious"),
            difficulty = "Beginner (Level 0-20)"
        ),
        ConversationScenario(
            id = "railway_station",
            title = "At the Railway Ticket Counter",
            hindiTitle = "रेलवे स्टेशन पर टिकट काउंटर",
            iconName = "train",
            category = "Travel",
            aiRole = "Ticket Booking Officer",
            userRole = "Passenger traveling to Mumbai",
            initialAiMessageEnglish = "Next please! Hello, where would you like to book your train ticket to?",
            initialAiMessageHindi = "अगला व्यक्ति आइए! नमस्ते, आप कहाँ के लिए ट्रेन टिकट बुक करना चाहते हैं?",
            starterPrompts = listOf(
                "I need two tickets to Mumbai for tomorrow morning.",
                "Which train is available in the evening?",
                "How much is the AC sleeper ticket?",
                "What time will the train reach the destination?"
            ),
            targetVocabulary = listOf("Reservation", "Platform", "Departure", "Arrival", "AC Sleeper", "Fare"),
            difficulty = "Intermediate (Level 21-60)"
        ),
        ConversationScenario(
            id = "job_interview",
            title = "Job Interview: Self Introduction & Questions",
            hindiTitle = "नौकरी का इंटरव्यू (परिचय और सवाल)",
            iconName = "badge",
            category = "Career & Professional",
            aiRole = "Senior Hiring Manager (Mr. Sharma)",
            userRole = "Job Candidate",
            initialAiMessageEnglish = "Hello! Welcome to our office. Please have a seat. To start off, could you tell me a little about yourself?",
            initialAiMessageHindi = "नमस्ते! हमारे कार्यालय में आपका स्वागत है। कृपया बैठिए। शुरुआत के लिए, क्या आप मुझे अपने बारे में कुछ बता सकते हैं?",
            starterPrompts = listOf(
                "Thank you for having me. My name is...",
                "I have completed my graduation in...",
                "My key strength is quick learning and problem-solving.",
                "I am eager to contribute to your team."
            ),
            targetVocabulary = listOf("Experience", "Strengths", "Qualification", "Problem-solving", "Team player", "Career goal"),
            difficulty = "Advanced (Level 61-100)"
        ),
        ConversationScenario(
            id = "hotel_checkin",
            title = "Hotel Check-In & Room Service",
            hindiTitle = "होटल चेक-इन और रूम सर्विस",
            iconName = "hotel",
            category = "Travel & Stay",
            aiRole = "Front Desk Receptionist",
            userRole = "Hotel Guest",
            initialAiMessageEnglish = "Good afternoon! Welcome to Grand Palace Hotel. Do you have a prior reservation with us?",
            initialAiMessageHindi = "नमस्ते! ग्रैंड पैलेस होटल में आपका स्वागत है। क्या आपके पास पहले से कोई बुकिंग है?",
            starterPrompts = listOf(
                "Yes, I have a reservation under the name of Faizan.",
                "Is breakfast included in my booking?",
                "What is the Wi-Fi password for the room?",
                "Could someone please help with my luggage?"
            ),
            targetVocabulary = listOf("Reservation", "Check-in", "Luggage", "Complimentary Breakfast", "Key card", "Wi-Fi"),
            difficulty = "Intermediate (Level 21-60)"
        ),
        ConversationScenario(
            id = "doctor_visit",
            title = "Visiting the Doctor & Describing Symptoms",
            hindiTitle = "डॉक्टर से मिलना और बीमारी बताना",
            iconName = "medical",
            category = "Health",
            aiRole = "Dr. Ananya (General Physician)",
            userRole = "Patient with headache and fever",
            initialAiMessageEnglish = "Hello! Please sit down. What seems to be the problem today?",
            initialAiMessageHindi = "नमस्ते! बैठिए। आज आपको क्या तकलीफ है?",
            starterPrompts = listOf(
                "Doctor, I have a severe headache and fever since yesterday.",
                "I am feeling very weak and dizzy.",
                "Do I need to take any blood tests?",
                "How many times a day should I take this medicine?"
            ),
            targetVocabulary = listOf("Symptoms", "Fever", "Headache", "Prescription", "Dosage", "Recover"),
            difficulty = "Beginner (Level 0-20)"
        ),
        ConversationScenario(
            id = "shopping_mall",
            title = "Shopping for Clothes & Asking for Discounts",
            hindiTitle = "कपड़ों की खरीदारी और डिस्काउंट मांगना",
            iconName = "shopping",
            category = "Daily Life",
            aiRole = "Store Sales Assistant",
            userRole = "Shopper looking for a shirt",
            initialAiMessageEnglish = "Hi there! Looking for anything specific today? We have a great new collection on sale!",
            initialAiMessageHindi = "नमस्ते! आज क्या कुछ खास तलाश रहे हैं? हमारी नई कलेक्शन पर बढ़िया सेल चल रही है!",
            starterPrompts = listOf(
                "Do you have this shirt in a medium size?",
                "Can I try this on in the trial room?",
                "Is there any discount on this item?",
                "Where is the cash billing counter?"
            ),
            targetVocabulary = listOf("Trial room", "Size", "Discount", "Receipt", "Exchange", "Fabric"),
            difficulty = "Beginner (Level 0-20)"
        ),
        ConversationScenario(
            id = "airport_flight",
            title = "At the Airport Check-In & Security",
            hindiTitle = "एयरपोर्ट पर बोर्डिंग और सुरक्षा जांच",
            iconName = "flight",
            category = "Travel",
            aiRole = "Airline Ground Staff",
            userRole = "Passenger flying to London",
            initialAiMessageEnglish = "Good morning! May I please see your passport and flight booking ticket?",
            initialAiMessageHindi = "सुप्रभात! क्या मैं आपका पासपोर्ट और फ्लाइट टिकट देख सकता हूँ?",
            starterPrompts = listOf(
                "Here is my passport and e-ticket.",
                "Could I please get a window seat?",
                "How many check-in bags am I allowed?",
                "Which gate does the flight board from?"
            ),
            targetVocabulary = listOf("Boarding pass", "Window seat", "Cabin luggage", "Security check", "Gate", "Terminal"),
            difficulty = "Intermediate (Level 21-60)"
        ),
        ConversationScenario(
            id = "daily_small_talk",
            title = "Meeting a Friend & Weekend Plans",
            hindiTitle = "दोस्त से मिलना और वीकेंड की योजना",
            iconName = "chat",
            category = "Casual English",
            aiRole = "College Friend (Aryan)",
            userRole = "Friend having coffee",
            initialAiMessageEnglish = "Hey buddy! Long time no see! How has your week been going so far?",
            initialAiMessageHindi = "अरे दोस्त! बहुत दिनों बाद मिले! तुम्हारा यह हफ्ता कैसा चल रहा है?",
            starterPrompts = listOf(
                "Hey! I've been quite busy with work and studies.",
                "What are your plans for this weekend?",
                "Do you want to catch a movie on Sunday?",
                "How is everyone in your family doing?"
            ),
            targetVocabulary = listOf("Catch up", "Weekend", "Hectic", "Relax", "Movie", "Plans"),
            difficulty = "Beginner (Level 0-20)"
        )
    )
}
