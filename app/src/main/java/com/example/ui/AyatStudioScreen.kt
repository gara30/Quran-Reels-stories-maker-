package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Animation
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.FormatColorText
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.AiGeneratorDialog
import com.example.ui.components.BackgroundSheet
import com.example.ui.components.ExportShareDialog
import com.example.ui.components.FeedbackDialog
import com.example.ui.components.ReciterSelectorSheet
import com.example.ui.components.StudioReelView
import com.example.ui.components.SurahVerseSelectorSheet
import com.example.ui.components.TranslationSelectorSheet
import com.example.ui.components.TransitionsSelectorSheet
import com.example.ui.components.TypographySheet
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.QuranEmeraldCard
import com.example.ui.theme.QuranEmeraldDark
import com.example.ui.theme.QuranEmeraldSurface
import com.example.ui.theme.QuranGoldLight
import com.example.ui.theme.QuranGoldPrimary
import com.example.ui.theme.QuranTextPrimary
import com.example.ui.theme.QuranTextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AyatStudioScreen(
    viewModel: AyatStudioViewModel,
    modifier: Modifier = Modifier
) {
    val config by viewModel.config.collectAsStateWithLifecycle()
    val verses by viewModel.versesList.collectAsStateWithLifecycle()
    val activeVerseNumber by viewModel.activeVerseNumber.collectAsStateWithLifecycle()
    val isPlaying by viewModel.isPlaying.collectAsStateWithLifecycle()
    val playbackProgress by viewModel.playbackProgress.collectAsStateWithLifecycle()
    val activeSheet by viewModel.activeSheet.collectAsStateWithLifecycle()

    val currentAyah = remember(verses, activeVerseNumber) {
        verses.find { it.numberInSurah == activeVerseNumber }
            ?: verses.firstOrNull()
    }

    Scaffold(
        containerColor = QuranEmeraldDark,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            Surface(
                color = QuranEmeraldSurface,
                border = androidx.compose.foundation.BorderStroke(0.5.dp, Color.White.copy(alpha = 0.08f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // App Brand
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            color = QuranGoldPrimary,
                            shape = CircleShape,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Movie,
                                contentDescription = null,
                                tint = Color(0xFF1E1700),
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "صانع فيديو الآيات",
                                fontFamily = AmiriFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 19.sp,
                                color = QuranGoldLight
                            )
                            Text(
                                text = "Quran Reels & Stories Maker (9:16)",
                                fontSize = 10.sp,
                                color = QuranTextSecondary
                            )
                        }
                    }

                    // Top Actions: Feedback & Export Buttons
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Feedback & Suggestions Button
                        Surface(
                            color = QuranEmeraldCard,
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, QuranGoldPrimary.copy(alpha = 0.4f)),
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { viewModel.openSheet(StudioSheetType.FEEDBACK) }
                                .testTag("top_feedback_btn")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.RateReview,
                                    contentDescription = "Feedback",
                                    tint = QuranGoldLight,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "اقتراحاتك",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = QuranGoldLight
                                )
                            }
                        }

                        // Export / Share Button
                        Button(
                            onClick = { viewModel.openSheet(StudioSheetType.EXPORT_SHARE) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = QuranGoldPrimary,
                                contentColor = Color(0xFF1E1700)
                            ),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                            modifier = Modifier.testTag("top_export_btn")
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "تصدير ومشاركة",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            // Studio Tools Bottom Navigation Bar
            Surface(
                color = QuranEmeraldSurface,
                border = androidx.compose.foundation.BorderStroke(0.5.dp, Color.White.copy(alpha = 0.1f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    item {
                        StudioToolItem(
                            icon = Icons.Default.MenuBook,
                            title = "السورة والآيات",
                            subtext = "${config.selectedSurah.nameArabic} (${config.startVerse}-${config.endVerse})",
                            tag = "nav_surah",
                            onClick = { viewModel.openSheet(StudioSheetType.SURAH_VERSE) }
                        )
                    }

                    item {
                        StudioToolItem(
                            icon = Icons.Default.RecordVoiceOver,
                            title = "القارئ",
                            subtext = config.selectedReciter.nameArabic.split(" ").take(2).joinToString(" "),
                            tag = "nav_reciter",
                            onClick = { viewModel.openSheet(StudioSheetType.RECITER) }
                        )
                    }

                    item {
                        StudioToolItem(
                            icon = Icons.Default.Translate,
                            title = "الترجمة 🇸🇦",
                            subtext = config.selectedTranslation.nameArabic.split(" ").take(2).joinToString(" "),
                            tag = "nav_translation",
                            onClick = { viewModel.openSheet(StudioSheetType.TRANSLATION) }
                        )
                    }

                    item {
                        StudioToolItem(
                            icon = Icons.Default.Animation,
                            title = "الانتقالات 🎬",
                            subtext = "${config.transitionType.emoji} ${config.transitionType.titleArabic.split(" ").take(2).joinToString(" ")}",
                            tag = "nav_transitions",
                            onClick = { viewModel.openSheet(StudioSheetType.TRANSITIONS) }
                        )
                    }

                    item {
                        StudioToolItem(
                            icon = Icons.Default.FormatColorText,
                            title = "الخط والمظهر",
                            subtext = config.fontType.displayNameArabic,
                            tag = "nav_style",
                            onClick = { viewModel.openSheet(StudioSheetType.TYPOGRAPHY) }
                        )
                    }

                    item {
                        StudioToolItem(
                            icon = Icons.Default.Wallpaper,
                            title = "الخلفيات والـ AI",
                            subtext = "معرض وذكاء",
                            tag = "nav_backgrounds",
                            onClick = { viewModel.openSheet(StudioSheetType.BACKGROUND) }
                        )
                    }
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Verse Range Navigation Pill Bar
            Surface(
                color = QuranEmeraldCard,
                shape = RoundedCornerShape(20.dp),
                border = androidx.compose.foundation.BorderStroke(0.5.dp, Color.White.copy(alpha = 0.12f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { viewModel.previousVerse() },
                        enabled = activeVerseNumber > config.startVerse,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Previous Verse",
                            tint = if (activeVerseNumber > config.startVerse) QuranGoldLight else Color.White.copy(alpha = 0.3f)
                        )
                    }

                    // Verses horizontal pills
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                    ) {
                        val verseRange = (config.startVerse..config.endVerse).toList()
                        items(verseRange) { vNum ->
                            val isCurrent = vNum == activeVerseNumber
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        if (isCurrent) QuranGoldPrimary else Color.White.copy(alpha = 0.08f)
                                    )
                                    .clickable { viewModel.setActiveVerse(vNum) }
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Text(
                                    text = "آية $vNum",
                                    fontSize = 11.sp,
                                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isCurrent) Color.Black else QuranTextPrimary
                                )
                            }
                        }
                    }

                    IconButton(
                        onClick = { viewModel.nextVerse() },
                        enabled = activeVerseNumber < config.endVerse,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChevronLeft,
                            contentDescription = "Next Verse",
                            tint = if (activeVerseNumber < config.endVerse) QuranGoldLight else Color.White.copy(alpha = 0.3f)
                        )
                    }
                }
            }

            // Central 9:16 Video Reel Stage with Floating Controls
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    StudioReelView(
                        config = config,
                        currentAyah = currentAyah,
                        isPlaying = isPlaying,
                        playbackProgress = playbackProgress,
                        getAiBitmap = { key -> viewModel.aiBackgroundService.getGeneratedBitmap(key) },
                        modifier = Modifier.fillMaxHeight()
                    )

                    // Audio Playback Bar Overlay at bottom of canvas
                    Surface(
                        color = Color.Black.copy(alpha = 0.65f),
                        shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.92f)
                            .padding(bottom = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            if (isPlaying) {
                                LinearProgressIndicator(
                                    progress = { playbackProgress },
                                    color = QuranGoldPrimary,
                                    trackColor = Color.White.copy(alpha = 0.2f),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(3.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (isPlaying) "جاري تشغيل تلاوة الآية..." else "معاينة الفيديو الحي (9:16)",
                                    fontSize = 11.sp,
                                    color = QuranGoldLight
                                )

                                FilledIconButton(
                                    onClick = { viewModel.playPause() },
                                    colors = IconButtonDefaults.filledIconButtonColors(
                                        containerColor = QuranGoldPrimary,
                                        contentColor = Color(0xFF1E1700)
                                    ),
                                    modifier = Modifier
                                        .size(34.dp)
                                        .testTag("play_pause_btn")
                                ) {
                                    Icon(
                                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                        contentDescription = if (isPlaying) "Pause" else "Play",
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Modal Bottom Sheets & Dialogs
    when (activeSheet) {
        StudioSheetType.SURAH_VERSE -> {
            SurahVerseSelectorSheet(
                currentSurah = config.selectedSurah,
                startVerse = config.startVerse,
                endVerse = config.endVerse,
                onSurahSelected = { viewModel.selectSurah(it) },
                onRangeSelected = { start, end -> viewModel.setVerseRange(start, end) },
                onDismiss = { viewModel.closeSheet() }
            )
        }
        StudioSheetType.RECITER -> {
            ReciterSelectorSheet(
                currentReciter = config.selectedReciter,
                onReciterSelected = { viewModel.selectReciter(it) },
                onDismiss = { viewModel.closeSheet() }
            )
        }
        StudioSheetType.TRANSLATION -> {
            TranslationSelectorSheet(
                config = config,
                onTranslationSelected = { viewModel.selectTranslation(it) },
                onToggleShowTranslation = { viewModel.toggleTranslation() },
                onToggleShowArabic = { viewModel.toggleShowArabicText() },
                onTranslationFontSizeChanged = { viewModel.setTranslationFontSize(it) },
                onDismiss = { viewModel.closeSheet() }
            )
        }
        StudioSheetType.TRANSITIONS -> {
            TransitionsSelectorSheet(
                config = config,
                onTransitionSelected = { viewModel.selectTransition(it) },
                onDurationChanged = { viewModel.setTransitionDuration(it) },
                onDismiss = { viewModel.closeSheet() }
            )
        }
        StudioSheetType.TYPOGRAPHY -> {
            TypographySheet(
                config = config,
                onFontSelected = { viewModel.selectFont(it) },
                onFontSizeChanged = { viewModel.setFontSize(it) },
                onFontColorChanged = { viewModel.setFontColor(it) },
                onOverlayDarknessChanged = { viewModel.setOverlayDarkness(it) },
                onToggleTranslation = { viewModel.toggleTranslation() },
                onToggleWaveform = { viewModel.toggleWaveform() },
                onToggleSurahHeader = { viewModel.toggleSurahHeader() },
                onOpenTransitions = { viewModel.openSheet(StudioSheetType.TRANSITIONS) },
                onDismiss = { viewModel.closeSheet() }
            )
        }
        StudioSheetType.BACKGROUND -> {
            BackgroundSheet(
                selectedBackground = config.selectedBackground,
                onBackgroundSelected = { viewModel.selectBackground(it) },
                onGalleryMediaSelected = { viewModel.setGalleryBackground(it) },
                onOpenAiGenerator = { viewModel.openSheet(StudioSheetType.AI_GENERATOR) },
                onDismiss = { viewModel.closeSheet() }
            )
        }
        StudioSheetType.AI_GENERATOR -> {
            AiGeneratorDialog(
                aiBackgroundService = viewModel.aiBackgroundService,
                onBackgroundGenerated = { bitmap ->
                    viewModel.addGeneratedAiBackground(bitmap)
                },
                onDismiss = { viewModel.closeSheet() }
            )
        }
        StudioSheetType.EXPORT_SHARE -> {
            ExportShareDialog(
                config = config,
                currentAyah = currentAyah,
                onDismiss = { viewModel.closeSheet() }
            )
        }
        StudioSheetType.FEEDBACK -> {
            FeedbackDialog(
                developerEmail = "aboudej30@gmail.com",
                onDismiss = { viewModel.closeSheet() }
            )
        }
        null -> {}
    }
}

@Composable
private fun StudioToolItem(
    icon: ImageVector,
    title: String,
    subtext: String,
    tag: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .testTag(tag)
    ) {
        Surface(
            color = QuranEmeraldCard,
            shape = CircleShape,
            border = androidx.compose.foundation.BorderStroke(1.dp, QuranGoldPrimary.copy(alpha = 0.4f)),
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = QuranGoldLight,
                modifier = Modifier.padding(8.dp)
            )
        }
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            color = QuranTextPrimary,
            maxLines = 1
        )
        Text(
            text = subtext,
            fontSize = 9.sp,
            color = QuranTextSecondary,
            maxLines = 1
        )
    }
}
