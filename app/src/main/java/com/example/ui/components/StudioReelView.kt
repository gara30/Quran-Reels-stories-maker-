package com.example.ui.components

import android.graphics.Bitmap
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import com.example.data.model.VerseTransitionType
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.model.Ayah
import com.example.data.model.BackgroundType
import com.example.data.model.FontType
import com.example.data.model.MotionAnimationType
import com.example.data.model.StudioConfig
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.QuranGoldLight
import com.example.ui.theme.QuranGoldPrimary
import com.example.ui.theme.ScheherazadeFontFamily
import kotlin.math.sin

@Composable
fun StudioReelView(
    config: StudioConfig,
    currentAyah: Ayah?,
    isPlaying: Boolean,
    playbackProgress: Float,
    getAiBitmap: (String) -> Bitmap?,
    modifier: Modifier = Modifier
) {
    val selectedFontFamily = remember(config.fontType) {
        when (config.fontType) {
            FontType.AMIRI -> AmiriFontFamily
            FontType.SCHEHERAZADE -> ScheherazadeFontFamily
            FontType.SYSTEM -> FontFamily.Default
        }
    }

    val context = LocalContext.current

    // Infinite animation for audio waveform bars
    val infiniteTransition = rememberInfiniteTransition(label = "waveform_anim")
    val waveAnim1 by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "w1"
    )
    val waveAnim2 by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(550, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "w2"
    )
    val waveAnim3 by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(480, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "w3"
    )

    // Dynamic Video Loop Animations
    val videoLoopTransition = rememberInfiniteTransition(label = "video_loop_motion")
    val loopPhase by videoLoopTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "loop_phase"
    )
    val auroraPulse by videoLoopTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.65f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "aurora_pulse"
    )

    Box(
        modifier = modifier
            .aspectRatio(9f / 16f)
            .clip(RoundedCornerShape(24.dp))
            .border(1.5.dp, QuranGoldPrimary.copy(alpha = 0.3f), RoundedCornerShape(24.dp))
            .testTag("studio_reel_view")
    ) {
        // Layer 1: Background Base (Preset Image, Gallery Media, AI Generated, or Gradient)
        when (config.selectedBackground.type) {
            BackgroundType.DRAWABLE_PRESET, BackgroundType.VIDEO_LOOP_PRESET -> {
                config.selectedBackground.drawableRes?.let { resId ->
                    Image(
                        painter = painterResource(id = resId),
                        contentDescription = "Reel Background",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } ?: run {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color(0xFF041C1A), Color(0xFF0D322E), Color(0xFF020E0D))
                                )
                            )
                    )
                }
            }
            BackgroundType.GALLERY_MEDIA -> {
                config.selectedBackground.mediaUri?.let { uri ->
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(uri)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Gallery Background",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            BackgroundType.AI_GENERATED -> {
                val key = config.selectedBackground.remoteOrBitmapKey
                val bitmap = key?.let { getAiBitmap(it) }
                if (bitmap != null) {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "AI Generated Background",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color(0xFF041C1A), Color(0xFF0D322E), Color(0xFF020E0D))
                                )
                            )
                    )
                }
            }
            BackgroundType.GRADIENT_PRESET -> {
                val colors = config.selectedBackground.gradientColors
                    ?: listOf(Color(0xFF091416), Color(0xFF142C33))
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.verticalGradient(colors))
                )
            }
        }

        // Layer 1.5: Animated Video Loops & Atmospheric Particle Motion
        if (config.selectedBackground.isVideo) {
            when (config.selectedBackground.motionType) {
                MotionAnimationType.RAIN_DROPLETS -> {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val w = size.width
                        val h = size.height
                        // 40 animated rain streaks
                        for (i in 0 until 45) {
                            val seedX = (i * 137.5f) % w
                            val speed = (i % 5 + 3) * 0.2f
                            val yOffset = ((loopPhase * h * speed) + (i * 73f)) % h
                            val dropLen = 22f + (i % 4) * 8f
                            drawLine(
                                color = Color.White.copy(alpha = 0.35f + (i % 3) * 0.1f),
                                start = Offset(seedX, yOffset),
                                end = Offset(seedX - 4f, yOffset + dropLen),
                                strokeWidth = 1.6f
                            )
                        }
                    }
                }
                MotionAnimationType.STARRY_COSMOS -> {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val w = size.width
                        val h = size.height
                        for (i in 0 until 55) {
                            val seedX = (i * 89.3f) % w
                            val seedY = (i * 143.7f) % (h * 0.8f)
                            val pulse = (sin((loopPhase * 6.28f) + i) + 1f) * 0.5f
                            val alpha = 0.25f + pulse * 0.65f
                            val radius = 1.2f + (i % 3) * 0.9f
                            drawCircle(
                                color = Color(0xFFFFF8E7).copy(alpha = alpha),
                                radius = radius,
                                center = Offset(seedX, seedY)
                            )
                        }
                    }
                }
                MotionAnimationType.GOLDEN_DUST -> {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val w = size.width
                        val h = size.height
                        // Floating ascending golden light specks
                        for (i in 0 until 40) {
                            val seedX = (i * 97.1f + sin((loopPhase * 3.14f) + i) * 15f) % w
                            val yOffset = h - (((loopPhase * h) + (i * 61f)) % h)
                            val alpha = (sin(yOffset / h * 3.14f)).coerceIn(0.1f, 0.85f)
                            val radius = 1.8f + (i % 3) * 1.2f
                            drawCircle(
                                color = Color(0xFFFFD700).copy(alpha = alpha),
                                radius = radius,
                                center = Offset(seedX, yOffset)
                            )
                        }
                    }
                }
                MotionAnimationType.AURORA_WAVE -> {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val w = size.width
                        val h = size.height
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFF38B289).copy(alpha = auroraPulse * 0.6f),
                                    Color(0xFF1E8262).copy(alpha = auroraPulse * 0.25f),
                                    Color.Transparent
                                ),
                                center = Offset(w * 0.5f, h * 0.35f),
                                radius = w * 0.75f
                            ),
                            center = Offset(w * 0.5f, h * 0.35f),
                            radius = w * 0.75f
                        )
                    }
                }
                MotionAnimationType.NONE -> {}
            }
        }

        // Layer 2: Customizable Dark Vignette / Scrim Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = config.overlayDarkness))
        )

        // Gradient shadows top and bottom for cinema lighting
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.65f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.75f)
                        )
                    )
                )
        )

        // Layer 3: Foreground Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Section: Surah Header Badge and Video Badge
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                AnimatedVisibility(
                    visible = config.showSurahHeader,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Surface(
                        color = Color.Black.copy(alpha = 0.45f),
                        shape = RoundedCornerShape(50),
                        border = androidx.compose.foundation.BorderStroke(1.dp, QuranGoldPrimary.copy(alpha = 0.4f)),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = QuranGoldLight,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "سُورَةُ ${config.selectedSurah.nameArabic}",
                                fontFamily = AmiriFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = QuranGoldLight
                            )
                            Box(
                                modifier = Modifier
                                    .size(4.dp)
                                    .clip(CircleShape)
                                    .background(QuranGoldLight.copy(alpha = 0.6f))
                            )
                            Text(
                                text = "الآية ${currentAyah?.numberInSurah ?: config.startVerse} من ${config.selectedSurah.versesCount}",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.85f),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                // Video Loop Indicator Badge
                if (config.selectedBackground.isVideo) {
                    Surface(
                        color = QuranGoldPrimary.copy(alpha = 0.18f),
                        shape = RoundedCornerShape(50),
                        border = androidx.compose.foundation.BorderStroke(0.6.dp, QuranGoldPrimary.copy(alpha = 0.5f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Videocam,
                                contentDescription = null,
                                tint = QuranGoldLight,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = "خلفية فيديو سينمائية متحركة",
                                fontSize = 10.sp,
                                color = QuranGoldLight,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // Center Section: Verse Calligraphy & Translation
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
                AnimatedContent(
                    targetState = currentAyah,
                    transitionSpec = {
                        val duration = config.transitionDurationMs
                        when (config.transitionType) {
                            VerseTransitionType.SMOOTH_FADE -> {
                                fadeIn(animationSpec = tween(duration, easing = LinearOutSlowInEasing)) togetherWith
                                    fadeOut(animationSpec = tween(duration / 2, easing = FastOutLinearInEasing))
                            }
                            VerseTransitionType.SLIDE_UP -> {
                                (fadeIn(animationSpec = tween(duration)) +
                                    slideInVertically(animationSpec = tween(duration, easing = EaseOutCubic)) { it / 3 } +
                                    scaleIn(animationSpec = tween(duration), initialScale = 0.94f)) togetherWith
                                (fadeOut(animationSpec = tween(duration / 2)) +
                                    slideOutVertically(animationSpec = tween(duration / 2)) { -it / 5 })
                            }
                            VerseTransitionType.CINEMATIC_ZOOM -> {
                                (fadeIn(animationSpec = tween(duration)) +
                                    scaleIn(animationSpec = tween(duration, easing = EaseOutCubic), initialScale = 0.88f)) togetherWith
                                (fadeOut(animationSpec = tween(duration / 2)) +
                                    scaleOut(animationSpec = tween(duration / 2), targetScale = 1.06f))
                            }
                            VerseTransitionType.PAGE_SLIDE -> {
                                (fadeIn(animationSpec = tween(duration)) +
                                    slideInHorizontally(animationSpec = tween(duration, easing = EaseOutCubic)) { it / 2 }) togetherWith
                                (fadeOut(animationSpec = tween(duration / 2)) +
                                    slideOutHorizontally(animationSpec = tween(duration / 2)) { -it / 2 })
                            }
                            VerseTransitionType.LUMINOUS_GLOW -> {
                                (fadeIn(animationSpec = tween(duration)) +
                                    scaleIn(animationSpec = tween(duration), initialScale = 0.95f)) togetherWith
                                fadeOut(animationSpec = tween(duration / 2))
                            }
                            VerseTransitionType.BOUNCE_REVEAL -> {
                                (fadeIn(animationSpec = tween(duration / 2)) +
                                    scaleIn(
                                        animationSpec = spring(
                                            dampingRatio = Spring.DampingRatioMediumBouncy,
                                            stiffness = Spring.StiffnessLow
                                        ),
                                        initialScale = 0.90f
                                    ) +
                                    slideInVertically(
                                        animationSpec = spring(
                                            dampingRatio = Spring.DampingRatioMediumBouncy,
                                            stiffness = Spring.StiffnessLow
                                        )
                                    ) { it / 4 }) togetherWith
                                fadeOut(animationSpec = tween(duration / 2))
                            }
                        }
                    },
                    label = "verse_transition_content"
                ) { ayah ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (config.transitionType == VerseTransitionType.LUMINOUS_GLOW) {
                            Box(
                                modifier = Modifier
                                    .size(240.dp)
                                    .background(
                                        Brush.radialGradient(
                                            colors = listOf(
                                                QuranGoldLight.copy(alpha = 0.18f),
                                                Color.Transparent
                                            )
                                        )
                                    )
                            )
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                        if (ayah != null) {
                            // Arabic Verse Calligraphy
                            if (config.showArabicText) {
                                Text(
                                    text = "﴾ ${ayah.textUthmani} ﴿",
                                    style = TextStyle(
                                        fontFamily = selectedFontFamily,
                                        fontSize = config.fontSizeSp.sp,
                                        lineHeight = (config.fontSizeSp * 1.6f).sp,
                                        color = config.fontColor,
                                        textAlign = TextAlign.Center,
                                        shadow = Shadow(
                                            color = Color.Black.copy(alpha = 0.85f),
                                            offset = Offset(2f, 3f),
                                            blurRadius = 6f
                                        )
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp)
                                )
                            }

                            // Official King Fahd Complex Translation (Saudi Arabia)
                            val transText = ayah.getTranslationFor(config.selectedTranslation.id)
                            if (config.showTranslation && transText.isNotBlank()) {
                                if (config.showArabicText) {
                                    Spacer(modifier = Modifier.height(14.dp))
                                }
                                Surface(
                                    color = Color.Black.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(14.dp),
                                    border = androidx.compose.foundation.BorderStroke(
                                        0.6.dp,
                                        QuranGoldPrimary.copy(alpha = 0.35f)
                                    ),
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                    ) {
                                        // Saudi King Fahd Complex Language Indicator
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                                            modifier = Modifier.padding(bottom = 4.dp)
                                        ) {
                                            Text("🇸🇦", fontSize = 11.sp)
                                            Text(
                                                text = config.selectedTranslation.languageNameArabic,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = QuranGoldLight
                                            )
                                        }

                                        Text(
                                            text = "\"$transText\"",
                                            style = TextStyle(
                                                fontSize = config.translationFontSizeSp.sp,
                                                lineHeight = (config.translationFontSizeSp * 1.45f).sp,
                                                color = Color.White.copy(alpha = 0.95f),
                                                textAlign = TextAlign.Center,
                                                fontWeight = FontWeight.Normal
                                            )
                                        )
                                    }
                                }
                            }
                        } else {
                            Text(
                                text = "جاري تحميل الآيات الكريمة...",
                                style = TextStyle(
                                    fontFamily = AmiriFontFamily,
                                    fontSize = 20.sp,
                                    color = QuranGoldLight,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                    }
                }
            }
        }

            // Bottom Section: Reciter pill & Animated Waveform
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (config.showWaveform) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(24.dp)
                            .padding(bottom = 2.dp)
                    ) {
                        val heights = listOf(
                            if (isPlaying) waveAnim1 * 20f else 6f,
                            if (isPlaying) waveAnim2 * 22f else 10f,
                            if (isPlaying) waveAnim3 * 18f else 14f,
                            if (isPlaying) waveAnim1 * 24f else 8f,
                            if (isPlaying) waveAnim2 * 20f else 16f,
                            if (isPlaying) waveAnim3 * 22f else 11f,
                            if (isPlaying) waveAnim1 * 18f else 6f
                        )
                        heights.forEach { h ->
                            Box(
                                modifier = Modifier
                                    .width(3.5.dp)
                                    .height(h.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(
                                        if (isPlaying) QuranGoldPrimary else Color.White.copy(alpha = 0.4f)
                                    )
                            )
                        }
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Color.Black.copy(alpha = 0.35f))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.GraphicEq,
                        contentDescription = null,
                        tint = QuranGoldLight,
                        modifier = Modifier.size(13.dp)
                    )
                    Text(
                        text = "القارئ: ${config.selectedReciter.nameArabic}",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.85f),
                        fontWeight = FontWeight.Medium
                    )
                }

                Text(
                    text = "صانع فيديو الآيات • Quran Video Maker",
                    fontSize = 9.sp,
                    color = Color.White.copy(alpha = 0.45f),
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}
