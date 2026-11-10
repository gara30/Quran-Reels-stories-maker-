package com.example.data.source

import androidx.compose.ui.graphics.Color
import com.example.R
import com.example.data.model.Ayah
import com.example.data.model.BackgroundOption
import com.example.data.model.BackgroundType
import com.example.data.model.Reciter
import com.example.data.model.Surah

object QuranDataSource {

    val reciters = listOf(
        Reciter(
            id = "alafasy",
            nameArabic = "مشاري راشد العفاسي",
            nameEnglish = "Mishary Rashid Alafasy",
            style = "حفص عن عاصم - مرتل",
            audioFolder = "Alafasy_128kbps"
        ),
        Reciter(
            id = "abdul_basit_murattal",
            nameArabic = "عبد الباسط عبد الصمد",
            nameEnglish = "Abdul Basit Abdul Samad",
            style = "حفص عن عاصم - مرتل",
            audioFolder = "Abdul_Basit_Murattal_192kbps"
        ),
        Reciter(
            id = "abdul_basit_mujawwad",
            nameArabic = "عبد الباسط عبد الصمد (المجود)",
            nameEnglish = "Abdul Basit (Mujawwad)",
            style = "حفص عن عاصم - مجود نادر",
            audioFolder = "AbdulSamad_64kbps_QuranExplorer.Com"
        ),
        Reciter(
            id = "al_husary_murattal",
            nameArabic = "محمود خليل الحصري",
            nameEnglish = "Mahmoud Khalil Al-Husary",
            style = "حفص عن عاصم - مرتل متقن",
            audioFolder = "Husary_128kbps"
        ),
        Reciter(
            id = "al_husary_muallim",
            nameArabic = "محمود خليل الحصري (المعلم)",
            nameEnglish = "Mahmoud Khalil Al-Husary (Muallim)",
            style = "المصحف المعلم - بطيء",
            audioFolder = "Husary_Muallim_128kbps"
        ),
        Reciter(
            id = "al_minshawy_murattal",
            nameArabic = "محمد صديق المنشاوي",
            nameEnglish = "Mohamed Siddiq Al-Minshawi",
            style = "حفص عن عاصم - خاشع",
            audioFolder = "Minshawy_Murattal_128kbps"
        ),
        Reciter(
            id = "al_minshawy_mujawwad",
            nameArabic = "محمد صديق المنشاوي (المجود)",
            nameEnglish = "Al-Minshawi (Mujawwad)",
            style = "حفص عن عاصم - مجود",
            audioFolder = "Minshawy_Mujawwad_192kbps"
        ),
        Reciter(
            id = "al_ghamdi",
            nameArabic = "سعد الغامدي",
            nameEnglish = "Saad Al-Ghamdi",
            style = "حفص عن عاصم - مرتل",
            audioFolder = "Ghamadi_40kbps"
        ),
        Reciter(
            id = "al_muaiqly",
            nameArabic = "ماهر المعيقلي",
            nameEnglish = "Maher Al-Muaiqly",
            style = "إمام المسجد الحرام بمكة",
            audioFolder = "MaherAlMuaiqly128kbps"
        ),
        Reciter(
            id = "as_sudais",
            nameArabic = "عبد الرحمن السديس",
            nameEnglish = "Abdurrahman As-Sudais",
            style = "إمام المسجد الحرام بمكة",
            audioFolder = "Abdurrahmaan_As-Sudais_192kbps"
        ),
        Reciter(
            id = "ash_shuraym",
            nameArabic = "سعود الشريم",
            nameEnglish = "Saood Ash-Shuraym",
            style = "إمام المسجد الحرام بمكة",
            audioFolder = "Saood_ash-Shuraym_128kbps"
        ),
        Reciter(
            id = "yasser_ad_dussary",
            nameArabic = "ياسر الدوسري",
            nameEnglish = "Yasser Ad-Dussary",
            style = "إمام المسجد الحرام بمكة",
            audioFolder = "Yasser_Ad-Dussary_128kbps"
        ),
        Reciter(
            id = "ash_shaatree",
            nameArabic = "أبو بكر الشاطري",
            nameEnglish = "Abu Bakr Ash-Shaatree",
            style = "حفص عن عاصم - شجي",
            audioFolder = "Abu_Bakr_Ash-Shaatree_128kbps"
        ),
        Reciter(
            id = "al_hudhaify",
            nameArabic = "علي بن عبد الرحمن الحذيفي",
            nameEnglish = "Ali Al-Hudhaify",
            style = "إمام المسجد النبوي الشريف",
            audioFolder = "Hudhaify_128kbps"
        ),
        Reciter(
            id = "al_ajamy",
            nameArabic = "أحمد بن علي العجمي",
            nameEnglish = "Ahmed Al-Ajamy",
            style = "حفص عن عاصم - مرتل",
            audioFolder = "Ahmed_ibn_Ali_al-Ajamy_128kbps_KetabAllah.net"
        ),
        Reciter(
            id = "fares_abbad",
            nameArabic = "فارس عباد",
            nameEnglish = "Fares Abbad",
            style = "حفص عن عاصم - ندي خاشع",
            audioFolder = "Fares_Abbad_64kbps"
        ),
        Reciter(
            id = "nasser_alqatami",
            nameArabic = "ناصر القطامي",
            nameEnglish = "Nasser Al-Qatami",
            style = "حفص عن عاصم - شجي",
            audioFolder = "Nasser_Alqatami_128kbps"
        ),
        Reciter(
            id = "idrees_abkar",
            nameArabic = "إدريس أبكر",
            nameEnglish = "Idrees Abkar",
            style = "حفص عن عاصم - خاشع جداً",
            audioFolder = "Idrees_Abkar_128kbps"
        ),
        Reciter(
            id = "al_banna",
            nameArabic = "محمود علي البنا",
            nameEnglish = "Mahmoud Ali Al-Banna",
            style = "حفص عن عاصم - مرتل كلاسيكي",
            audioFolder = "Banna_32kbps"
        ),
        Reciter(
            id = "abdullah_basfar",
            nameArabic = "عبد الله بصفر",
            nameEnglish = "Abdullah Basfar",
            style = "حفص عن عاصم - مرتل هادئ",
            audioFolder = "Abdullah_Basfar_192kbps"
        )
    )

    fun getAudioUrl(reciter: Reciter, surahNumber: Int, verseNumber: Int): String {
        val sNum = surahNumber.toString().padStart(3, '0')
        val vNum = verseNumber.toString().padStart(3, '0')
        return "https://everyayah.com/data/${reciter.audioFolder}/$sNum$vNum.mp3"
    }

    val defaultTranslations = listOf(
        com.example.data.model.TranslationOption(
            id = "en",
            resourceId = 131,
            languageNameArabic = "الإنجليزية (مجمع الملك فهد)",
            languageNameNative = "English (King Fahd Complex)",
            flag = "🇸🇦",
            authorName = "مجمع الملك فهد لطباعة المصحف الشريف بالمدينة المنورة"
        ),
        com.example.data.model.TranslationOption(
            id = "ar_tafseer",
            resourceId = 16,
            languageNameArabic = "التفسير الميسر (السعودية)",
            languageNameNative = "التفسير الميسر",
            flag = "🇸🇦",
            authorName = "مجمع الملك فهد لطباعة المصحف الشريف بالمدينة المنورة"
        ),
        com.example.data.model.TranslationOption(
            id = "fr",
            resourceId = 136,
            languageNameArabic = "الفرنسية (مجمع الملك فهد)",
            languageNameNative = "Français (Complexe Roi Fahd)",
            flag = "🇸🇦",
            authorName = "مجمع الملك فهد لطباعة المصحف الشريف بالمدينة المنورة"
        ),
        com.example.data.model.TranslationOption(
            id = "ur",
            resourceId = 234,
            languageNameArabic = "الأردية (مجمع الملك فهد)",
            languageNameNative = "اردو (شاہ فہد کمپلیکس)",
            flag = "🇸🇦",
            authorName = "مجمع الملك فهد لطباعة المصحف الشريف بالمدينة المنورة"
        ),
        com.example.data.model.TranslationOption(
            id = "id",
            resourceId = 33,
            languageNameArabic = "الإندونيسية (مجمع الملك فهد)",
            languageNameNative = "Bahasa Indonesia (Kompleks Raja Fahd)",
            flag = "🇸🇦",
            authorName = "مجمع الملك فهد لطباعة المصحف الشريف بالمدينة المنورة"
        ),
        com.example.data.model.TranslationOption(
            id = "tr",
            resourceId = 77,
            languageNameArabic = "التركية (مجمع الملك فهد)",
            languageNameNative = "Türkçe (Kral Fehd Kompleksi)",
            flag = "🇸🇦",
            authorName = "مجمع الملك فهد لطباعة المصحف الشريف بالمدينة المنورة"
        ),
        com.example.data.model.TranslationOption(
            id = "es",
            resourceId = 83,
            languageNameArabic = "الإسبانية (مجمع الملك فهد)",
            languageNameNative = "Español (Complejo Rey Fahd)",
            flag = "🇸🇦",
            authorName = "مجمع الملك فهد لطباعة المصحف الشريف بالمدينة المنورة"
        ),
        com.example.data.model.TranslationOption(
            id = "ru",
            resourceId = 79,
            languageNameArabic = "الروسية (مجمع الملك فهد)",
            languageNameNative = "Русский (Комплекс Короля Фахда)",
            flag = "🇸🇦",
            authorName = "مجمع الملك فهد لطباعة المصحف الشريف بالمدينة المنورة"
        )
    )

    val defaultBackgrounds = listOf(
        // معالم إسلامية ومساجد شريفة
        BackgroundOption(
            id = "bg_kaaba_mecca",
            title = "الكعبة المشرفة - مكة المكرمة",
            category = "مساجد ومعالم",
            type = BackgroundType.DRAWABLE_PRESET,
            drawableRes = R.drawable.bg_kaaba_mecca
        ),
        BackgroundOption(
            id = "bg_medina_mosque",
            title = "المسجد النبوي - المدينة المنورة",
            category = "مساجد ومعالم",
            type = BackgroundType.DRAWABLE_PRESET,
            drawableRes = R.drawable.bg_medina_mosque
        ),

        // فيديوهات متحركة سينمائية (Video Loops)
        BackgroundOption(
            id = "video_rain_loop",
            title = "فيديو قطرات المطر والسكينة",
            category = "فيديوهات متحركة",
            type = BackgroundType.VIDEO_LOOP_PRESET,
            isVideo = true,
            motionType = com.example.data.model.MotionAnimationType.RAIN_DROPLETS,
            drawableRes = R.drawable.bg_night_dunes
        ),
        BackgroundOption(
            id = "video_cosmos_loop",
            title = "فيديو حركة النجوم والمجرات",
            category = "فيديوهات متحركة",
            type = BackgroundType.VIDEO_LOOP_PRESET,
            isVideo = true,
            motionType = com.example.data.model.MotionAnimationType.STARRY_COSMOS,
            drawableRes = R.drawable.bg_night_dunes
        ),
        BackgroundOption(
            id = "video_golden_loop",
            title = "فيديو ذرات الضياء والذهب الطافي",
            category = "فيديوهات متحركة",
            type = BackgroundType.VIDEO_LOOP_PRESET,
            isVideo = true,
            motionType = com.example.data.model.MotionAnimationType.GOLDEN_DUST,
            drawableRes = R.drawable.bg_kaaba_mecca
        ),
        BackgroundOption(
            id = "video_aurora_loop",
            title = "فيديو أمواج الشفق المتلألئة",
            category = "فيديوهات متحركة",
            type = BackgroundType.VIDEO_LOOP_PRESET,
            isVideo = true,
            motionType = com.example.data.model.MotionAnimationType.AURORA_WAVE,
            drawableRes = R.drawable.bg_emerald_waterfall
        ),

        // طبيعة وشلالات وجبال
        BackgroundOption(
            id = "bg_emerald_waterfall",
            title = "شلالات الجنة والغابات الخضراء",
            category = "طبيعة وشلالات",
            type = BackgroundType.DRAWABLE_PRESET,
            drawableRes = R.drawable.bg_emerald_waterfall
        ),
        BackgroundOption(
            id = "bg_misty_dawn",
            title = "سحاب الفجر وقمم الجبال الشامخة",
            category = "طبيعة وشلالات",
            type = BackgroundType.DRAWABLE_PRESET,
            drawableRes = R.drawable.bg_misty_dawn
        ),
        BackgroundOption(
            id = "bg_night_dunes",
            title = "ليالي الصحراء وهدوء الرمال",
            category = "طبيعة وشلالات",
            type = BackgroundType.DRAWABLE_PRESET,
            drawableRes = R.drawable.bg_night_dunes
        ),

        // تدرجات ملكية فاخرة
        BackgroundOption(
            id = "grad_emerald_night",
            title = "الزمرد الليلي الهادئ",
            category = "تدرجات ملكية",
            type = BackgroundType.GRADIENT_PRESET,
            gradientColors = listOf(Color(0xFF041C1A), Color(0xFF0D322E), Color(0xFF020E0D))
        ),
        BackgroundOption(
            id = "grad_royal_gold",
            title = "الأفق الذهبي المظلم",
            category = "تدرجات ملكية",
            type = BackgroundType.GRADIENT_PRESET,
            gradientColors = listOf(Color(0xFF1E1700), Color(0xFF382B00), Color(0xFF0A0700))
        ),
        BackgroundOption(
            id = "grad_cosmic_blue",
            title = "السديم الكوني الفاحم",
            category = "تدرجات ملكية",
            type = BackgroundType.GRADIENT_PRESET,
            gradientColors = listOf(Color(0xFF0A1128), Color(0xFF1C2541), Color(0xFF000511))
        )
    )

    // Complete index of all 114 Surahs
    val allSurahs = listOf(
        Surah(1, "الفَاتِحَة", "Al-Fatihah", "The Opener", 7, "Meccan"),
        Surah(2, "البَقَرَة", "Al-Baqarah", "The Cow", 286, "Medinan"),
        Surah(3, "آل عِمْرَان", "Ali 'Imran", "Family of Imran", 200, "Medinan"),
        Surah(4, "النِّسَاء", "An-Nisa", "The Women", 176, "Medinan"),
        Surah(5, "المَائِدَة", "Al-Ma'idah", "The Table Spread", 120, "Medinan"),
        Surah(6, "الأَنْعَام", "Al-An'am", "The Cattle", 165, "Meccan"),
        Surah(7, "الأَعْرَاف", "Al-A'raf", "The Heights", 206, "Meccan"),
        Surah(8, "الأَنْفَال", "Al-Anfal", "The Spoils of War", 75, "Medinan"),
        Surah(9, "التَّوْبَة", "At-Tawbah", "The Repentance", 129, "Medinan"),
        Surah(10, "يُونُس", "Yunus", "Jonah", 109, "Meccan"),
        Surah(11, "هُود", "Hud", "Hud", 123, "Meccan"),
        Surah(12, "يُوسُف", "Yusuf", "Joseph", 111, "Meccan"),
        Surah(13, "الرَّعْد", "Ar-Ra'd", "The Thunder", 43, "Medinan"),
        Surah(14, "إِبْرَاهِيم", "Ibrahim", "Abraham", 52, "Meccan"),
        Surah(15, "الحِجْر", "Al-Hijr", "The Rocky Tract", 99, "Meccan"),
        Surah(16, "النَّحْل", "An-Nahl", "The Bee", 128, "Meccan"),
        Surah(17, "الإِسْرَاء", "Al-Isra", "The Night Journey", 111, "Meccan"),
        Surah(18, "الكَهْف", "Al-Kahf", "The Cave", 110, "Meccan"),
        Surah(19, "مَرْيَم", "Maryam", "Mary", 98, "Meccan"),
        Surah(20, "طه", "Taha", "Ta-Ha", 135, "Meccan"),
        Surah(21, "الأَنْبِيَاء", "Al-Anbiya", "The Prophets", 112, "Meccan"),
        Surah(22, "الحَجّ", "Al-Hajj", "The Pilgrimage", 78, "Medinan"),
        Surah(23, "المُؤْمِنُون", "Al-Mu'minun", "The Believers", 118, "Meccan"),
        Surah(24, "النُّور", "An-Nur", "The Light", 64, "Medinan"),
        Surah(25, "الفُرْقَان", "Al-Furqan", "The Criterion", 77, "Meccan"),
        Surah(26, "الشُّعَرَاء", "Ash-Shu'ara", "The Poets", 227, "Meccan"),
        Surah(27, "النَّمْل", "An-Naml", "The Ant", 93, "Meccan"),
        Surah(28, "القَصَص", "Al-Qasas", "The Stories", 88, "Meccan"),
        Surah(29, "العَنْكَبُوت", "Al-'Ankabut", "The Spider", 69, "Meccan"),
        Surah(30, "الرُّوم", "Ar-Rum", "The Romans", 60, "Meccan"),
        Surah(31, "لُقْمَان", "Luqman", "Luqman", 34, "Meccan"),
        Surah(32, "السَّجْدَة", "As-Sajdah", "The Prostration", 30, "Meccan"),
        Surah(33, "الأَحْزَاب", "Al-Ahzab", "The Combined Forces", 73, "Medinan"),
        Surah(34, "سَبَأ", "Saba", "Sheba", 54, "Meccan"),
        Surah(35, "فَاطِر", "Fatir", "Originator", 45, "Meccan"),
        Surah(36, "يس", "Ya-Sin", "Ya-Sin", 83, "Meccan"),
        Surah(37, "الصَّافَّات", "As-Saffat", "Those who set the Ranks", 182, "Meccan"),
        Surah(38, "ص", "Sad", "The Letter 'Sad'", 88, "Meccan"),
        Surah(39, "الزُّمَر", "Az-Zumar", "The Troops", 75, "Meccan"),
        Surah(40, "غَافِر", "Ghafir", "The Forgiver", 85, "Meccan"),
        Surah(41, "فُصِّلَت", "Fussilat", "Explained in Detail", 54, "Meccan"),
        Surah(42, "الشُّورَى", "Ash-Shuraa", "The Consultation", 53, "Meccan"),
        Surah(43, "الزُّخْرُف", "Az-Zukhruf", "The Ornaments of Gold", 89, "Meccan"),
        Surah(44, "الدُّخَان", "Ad-Dukhan", "The Smoke", 59, "Meccan"),
        Surah(45, "الجَاثِيَة", "Al-Jathiyah", "The Crouching", 37, "Meccan"),
        Surah(46, "الأَحْقَاف", "Al-Ahqaf", "The Wind-Curved Sandhills", 35, "Meccan"),
        Surah(47, "مُحَمَّد", "Muhammad", "Muhammad", 38, "Medinan"),
        Surah(48, "الفَتْح", "Al-Fath", "The Victory", 29, "Medinan"),
        Surah(49, "الحُجُرَات", "Al-Hujurat", "The Rooms", 18, "Medinan"),
        Surah(50, "ق", "Qaf", "The Letter 'Qaf'", 45, "Meccan"),
        Surah(51, "الذَّارِيَات", "Adh-Dhariyat", "The Winnowing Winds", 60, "Meccan"),
        Surah(52, "الطُّور", "At-Tur", "The Mount", 49, "Meccan"),
        Surah(53, "النَّجْم", "An-Najm", "The Star", 62, "Meccan"),
        Surah(54, "القَمَر", "Al-Qamar", "The Moon", 55, "Meccan"),
        Surah(55, "الرَّحْمَن", "Ar-Rahman", "The Beneficent", 78, "Medinan"),
        Surah(56, "الوَاقِعَة", "Al-Waqi'ah", "The Inevitable", 96, "Meccan"),
        Surah(57, "الحَدِيد", "Al-Hadid", "The Iron", 29, "Medinan"),
        Surah(58, "المُجَادِلَة", "Al-Mujadila", "The Pleading Woman", 22, "Medinan"),
        Surah(59, "الحَشْر", "Al-Hashr", "The Exile", 24, "Medinan"),
        Surah(60, "المُمْتَحَنَة", "Al-Mumtahanah", "She that is to be examined", 13, "Medinan"),
        Surah(61, "الصَّفّ", "As-Saff", "The Ranks", 14, "Medinan"),
        Surah(62, "الجُمُعَة", "Al-Jumu'ah", "The Congregation, Friday", 11, "Medinan"),
        Surah(63, "المُنَافِقُون", "Al-Munafiqun", "The Hypocrites", 11, "Medinan"),
        Surah(64, "التَّغَابُن", "At-Taghabun", "The Mutual Disillusion", 18, "Medinan"),
        Surah(65, "الطَّلَاق", "At-Talaq", "The Divorce", 12, "Medinan"),
        Surah(66, "التَّحْرِيم", "At-Tahrim", "The Prohibition", 12, "Medinan"),
        Surah(67, "المُلْك", "Al-Mulk", "The Sovereignty", 30, "Meccan"),
        Surah(68, "القَلَم", "Al-Qalam", "The Pen", 52, "Meccan"),
        Surah(69, "الحَاقَّة", "Al-Haqqah", "The Reality", 52, "Meccan"),
        Surah(70, "المَعَارِج", "Al-Ma'arij", "The Ascending Stairways", 44, "Meccan"),
        Surah(71, "نُوح", "Nuh", "Noah", 28, "Meccan"),
        Surah(72, "الجِنّ", "Al-Jinn", "The Jinn", 28, "Meccan"),
        Surah(73, "المُزَّمِّل", "Al-Muzzammil", "The Enshrouded One", 20, "Meccan"),
        Surah(74, "المُدَّثِّر", "Al-Muddaththir", "The Cloaked One", 56, "Meccan"),
        Surah(75, "القِيَامَة", "Al-Qiyamah", "The Resurrection", 40, "Meccan"),
        Surah(76, "الإِنْسَان", "Al-Insan", "The Man", 31, "Medinan"),
        Surah(77, "المُرْسَلَات", "Al-Mursalat", "The Emissaries", 50, "Meccan"),
        Surah(78, "النَّبَأ", "An-Naba", "The Tidings", 40, "Meccan"),
        Surah(79, "النَّازِعَات", "An-Nazi'at", "Those who drag forth", 46, "Meccan"),
        Surah(80, "عَبَسَ", "'Abasa", "He Frowned", 42, "Meccan"),
        Surah(81, "التَّكْوِير", "At-Takwir", "The Overthrowing", 29, "Meccan"),
        Surah(82, "الانْفِطَار", "Al-Infitar", "The Cleaving", 19, "Meccan"),
        Surah(83, "المُطَفِّفِين", "Al-Mutaffifin", "The Defrauding", 36, "Meccan"),
        Surah(84, "الانْشِقَاق", "Al-Inshiqaq", "The Splitting Open", 25, "Meccan"),
        Surah(85, "البُرُوج", "Al-Buruj", "The Mansions of the Stars", 22, "Meccan"),
        Surah(86, "الطَّارِق", "At-Tariq", "The Morning Star", 17, "Meccan"),
        Surah(87, "الأَعْلَى", "Al-A'la", "The Most High", 19, "Meccan"),
        Surah(88, "الغَاشِيَة", "Al-Ghashiyah", "The Overwhelming", 26, "Meccan"),
        Surah(89, "الفَجْر", "Al-Fajr", "The Dawn", 30, "Meccan"),
        Surah(90, "البَلَد", "Al-Balad", "The City", 20, "Meccan"),
        Surah(91, "الشَّمْس", "Ash-Shams", "The Sun", 15, "Meccan"),
        Surah(92, "اللَّيْل", "Al-Layl", "The Night", 21, "Meccan"),
        Surah(93, "الضُّحَى", "Ad-Duhaa", "The Morning Hours", 11, "Meccan"),
        Surah(94, "الشَّرْح", "Ash-Sharh", "The Relief", 8, "Meccan"),
        Surah(95, "التِّين", "At-Tin", "The Fig", 8, "Meccan"),
        Surah(96, "العَلَق", "Al-'Alaq", "The Clot", 19, "Meccan"),
        Surah(97, "القَدْر", "Al-Qadr", "The Power", 5, "Meccan"),
        Surah(98, "البَيِّنَة", "Al-Bayyinah", "The Clear Proof", 8, "Medinan"),
        Surah(99, "الزَّلْزَلَة", "Az-Zalzalah", "The Earthquake", 8, "Medinan"),
        Surah(100, "العَادِيَات", "Al-'Adiyat", "The Courser", 11, "Meccan"),
        Surah(101, "القَارِعَة", "Al-Qari'ah", "The Calamity", 11, "Meccan"),
        Surah(102, "التَّكَاثُر", "At-Takathur", "The Rivalry in World Increase", 8, "Meccan"),
        Surah(103, "العَصْر", "Al-'Asr", "The Declining Day", 3, "Meccan"),
        Surah(104, "الهُمَزَة", "Al-Humazah", "The Traducer", 9, "Meccan"),
        Surah(105, "الفِيل", "Al-Fil", "The Elephant", 5, "Meccan"),
        Surah(106, "قُرَيْش", "Quraysh", "Quraysh", 4, "Meccan"),
        Surah(107, "المَاعُون", "Al-Ma'un", "The Small Kindness", 7, "Meccan"),
        Surah(108, "الكَوْثَر", "Al-Kawthar", "The Abundance", 3, "Meccan"),
        Surah(109, "الكَافِرُون", "Al-Kafirun", "The Disbelievers", 6, "Meccan"),
        Surah(110, "النَّصْر", "An-Nasr", "The Divine Support", 3, "Medinan"),
        Surah(111, "المَسَد", "Al-Masad", "The Palm Fiber", 5, "Meccan"),
        Surah(112, "الإِخْلَاص", "Al-Ikhlas", "The Sincerity", 4, "Meccan"),
        Surah(113, "الفَلَق", "Al-Falaq", "The Daybreak", 5, "Meccan"),
        Surah(114, "النَّاس", "An-Nas", "Mankind", 6, "Meccan")
    )

    // Accurate pre-bundled verses for instant offline rendering & fast previews
    private val bundledVerses = mapOf(
        1 to listOf(
            Ayah(1, 1, 1, "بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ", "In the name of Allah, the Entirely Merciful, the Especially Merciful."),
            Ayah(1, 2, 2, "ٱلْحَمْدُ لِلَّهِ رَبِّ ٱلْعَٰلَمِينَ", "[All] praise is [due] to Allah, Lord of the worlds -"),
            Ayah(1, 3, 3, "ٱلرَّحْمَٰنِ ٱلرَّحِيمِ", "The Entirely Merciful, the Especially Merciful,"),
            Ayah(1, 4, 4, "مَٰلِكِ يَوْمِ ٱلدِّينِ", "Sovereign of the Day of Recompense."),
            Ayah(1, 5, 5, "إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ", "It is You we worship and You we ask for help."),
            Ayah(1, 6, 6, "ٱهْدِنَا ٱلصِّرَٰطَ ٱلْمُسْتَقِيمَ", "Guide us to the straight path -"),
            Ayah(1, 7, 7, "صِرَٰطَ ٱلَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ ٱلْمَغْضُوبِ عَلَيْهِمْ وَلَا ٱلضَّآلِّينَ", "The path of those upon whom You have bestowed favor, not of those who have evoked [Your] anger or of those who are astray.")
        ),
        112 to listOf(
            Ayah(112, 1, 6222, "قُلْ هُوَ ٱللَّهُ أَحَدٌ", "Say, \"He is Allah, [who is] One,"),
            Ayah(112, 2, 6223, "ٱللَّهُ ٱلصَّمَدُ", "Allah, the Eternal Refuge."),
            Ayah(112, 3, 6224, "لَمْ يَلِدْ وَلَمْ يُولَدْ", "He neither begets nor is born,"),
            Ayah(112, 4, 6225, "وَلَمْ يَكُن لَّهُۥ كُفُوًا أَحَدٌۢ", "Nor is there to Him any equivalent.\"")
        ),
        113 to listOf(
            Ayah(113, 1, 6226, "قُلْ أَعُوذُ بِرَبِّ ٱلْفَلَقِ", "Say, \"I seek refuge in the Lord of daybreak"),
            Ayah(113, 2, 6227, "مِن شَرِّ مَا خَلَقَ", "From the evil of that which He created"),
            Ayah(113, 3, 6228, "وَمِن شَرِّ غَاسِقٍ إِذَا وَقَبَ", "And from the evil of darkness when it settles"),
            Ayah(113, 4, 6229, "وَمِن شَرِّ ٱلنَّفَّٰثَٰتِ فِي ٱلْعُقَدِ", "And from the evil of the blowers in knots"),
            Ayah(113, 5, 6230, "وَمِن شَرِّ حَاسِدٍ إِذَا حَسَدَ", "And from the evil of an envier when he envies.\"")
        ),
        114 to listOf(
            Ayah(114, 1, 6231, "قُلْ أَعُوذُ بِرَبِّ ٱلنَّاسِ", "Say, \"I seek refuge in the Lord of mankind,"),
            Ayah(114, 2, 6232, "مَلِكِ ٱلنَّاسِ", "The Sovereign of mankind,"),
            Ayah(114, 3, 6233, "إِلَٰهِ ٱلنَّاسِ", "The God of mankind,"),
            Ayah(114, 4, 6234, "مِن شَرِّ ٱلْوَسْوَاسِ ٱلْخَنَّاسِ", "From the evil of the retreating whisperer -"),
            Ayah(114, 5, 6235, "ٱلَّذِي يُوَسْوِسُ فِي صُدُورِ ٱلنَّاسِ", "Who whispers into the breasts of mankind -"),
            Ayah(114, 6, 6236, "مِنَ ٱلْجِنَّةِ وَٱلنَّاسِ", "From among the jinn and mankind.\"")
        ),
        108 to listOf(
            Ayah(108, 1, 6205, "إِنَّآ أَعْطَيْنَٰكَ ٱلْكَوْثَرَ", "Indeed, We have granted you, [O Muhammad], al-Kawthar."),
            Ayah(108, 2, 6206, "فَصَلِّ لِرَبِّكَ وَٱنْحَرْ", "So pray to your Lord and offer sacrifice [to Him alone]."),
            Ayah(108, 3, 6207, "إِنَّ شَانِئَكَ هُوَ ٱلْأَبْتَرُ", "Indeed, your enemy is the one cut off.")
        ),
        103 to listOf(
            Ayah(103, 1, 6177, "وَٱلْعَصْرِ", "By time,"),
            Ayah(103, 2, 6178, "إِنَّ ٱلْإِنسَٰنَ لَفِي خُسْرٍ", "Indeed, mankind is in loss,"),
            Ayah(103, 3, 6179, "إِلَّا ٱلَّذِينَ ءَامَنُوا۟ وَعَمِلُوا۟ ٱلصَّٰلِحَٰتِ وَتَوَاصَوْا۟ بِٱلْحَقِّ وَتَوَاصَوْا۟ بِٱلصَّبْرِ", "Except for those who have believed and done righteous deeds and advised each other to truth and advised each other to patience.")
        ),
        93 to listOf(
            Ayah(93, 1, 6080, "وَٱلضُّحَىٰ", "By the morning brightness"),
            Ayah(93, 2, 6081, "وَٱلَّيْلِ إِذَا سَجَىٰ", "And [by] the night when it covers with darkness,"),
            Ayah(93, 3, 6082, "مَا وَدَّعَكَ رَبُّكَ وَمَا قَلَىٰ", "Your Lord has not taken leave of you, [O Muhammad], nor has He detested [you]."),
            Ayah(93, 4, 6083, "وَلَلْءَاخِرَةُ خَيْرٌ لَّكَ مِنَ ٱلْأُولَىٰ", "And the Hereafter is better for you than the first [life]."),
            Ayah(93, 5, 6084, "وَلَسَوْفَ يُعْطِيكَ رَبُّكَ فَتَرْضَىٰٓ", "And your Lord is going to give you, and you will be satisfied.")
        ),
        94 to listOf(
            Ayah(94, 1, 6091, "أَلَمْ نَشْرَحْ لَكَ صَدْرَكَ", "Did We not expand for you, [O Muhammad], your breast?"),
            Ayah(94, 2, 6092, "وَوَضَعْنَا عَنكَ وِزْرَكَ", "And We removed from you your burden"),
            Ayah(94, 3, 6093, "ٱلَّذِيٓ أَنقَضَ ظَهْرَكَ", "Which had weighed upon your back"),
            Ayah(94, 4, 6094, "وَرَفَعْنَا لَكَ ذِكْرَكَ", "And raised high for you your repute."),
            Ayah(94, 5, 6095, "فَإِنَّ مَعَ ٱلْعُسْرِ يُسْرًا", "For indeed, with hardship [will be] ease."),
            Ayah(94, 6, 6096, "إِنَّ مَعَ ٱلْعُسْرِ يُسْرًا", "Indeed, with hardship [will be] ease."),
            Ayah(94, 7, 6097, "فَإِذَا فَرَغْتَ فَٱنصَبْ", "So when you have finished [your duties], then stand up [for worship]."),
            Ayah(94, 8, 6098, "وَإِلَىٰ رَبِّكَ فَٱرْغَب", "And to your Lord direct [your] longing.")
        ),
        67 to listOf(
            Ayah(67, 1, 5242, "تَبَٰرَكَ ٱلَّذِي بِيَدِهِ ٱلْمُلْكُ وَهُوَ عَلَىٰ كُلِّ شَيْءٍ قَدِيرٌ", "Blessed is He in whose hand is dominion, and He is over all things competent -"),
            Ayah(67, 2, 5243, "ٱلَّذِي خَلَقَ ٱلْمَوْتَ وَٱلْحَيَوٰةَ لِيَبْلُوَكُمْ أَيُّكُمْ أَحْسَنُ عَمَلًا ۚ وَهُوَ ٱلْعَزِيزُ ٱلْغَفُورُ", "[He] who created death and life to test you [as to] which of you is best in deed - and He is the Exalted in Might, the Forgiving -"),
            Ayah(67, 3, 5244, "ٱلَّذِي خَلَقَ سَبْعَ سَمَٰوَٰتٍ طِبَاقًا ۖ مَّا تَرَىٰ فِي خَلْقِ ٱلرَّحْمَٰنِ مِن تَفَٰوُتٍ ۖ فَٱرْجِعِ ٱلْبَصَرَ هَلْ تَرَىٰ مِن فُطُورٍ", "[And] who created seven heavens in layers. You see no inconsistency in the creation of the Most Merciful. So return [your] vision [to the sky]; do you see any breaks?"),
            Ayah(67, 4, 5245, "ثُمَّ ٱرْجِعِ ٱلْبَصَرَ كَرَّتَيْنِ يَنقَلِبْ إِلَيْكَ ٱلْبَصَرُ خَاسِئًا وَهُوَ حَسِيرٌ", "Then return [your] vision twice again. [Your] vision will return to you humbled while it is fatigued.")
        )
    )

    fun getBundledVerses(surahNumber: Int): List<Ayah>? {
        return bundledVerses[surahNumber]
    }
}
