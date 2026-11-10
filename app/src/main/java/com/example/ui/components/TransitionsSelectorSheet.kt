package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.StudioConfig
import com.example.data.model.VerseTransitionType
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.QuranEmeraldCard
import com.example.ui.theme.QuranEmeraldDark
import com.example.ui.theme.QuranGoldLight
import com.example.ui.theme.QuranGoldPrimary
import com.example.ui.theme.QuranTextPrimary
import com.example.ui.theme.QuranTextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransitionsSelectorSheet(
    config: StudioConfig,
    onTransitionSelected: (VerseTransitionType) -> Unit,
    onDurationChanged: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var previewCounter by remember { mutableIntStateOf(0) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = QuranEmeraldDark,
        dragHandle = null,
        modifier = Modifier.testTag("transitions_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = Color(0xFF1E1700),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "مكتبة التأثيرات الانتقالية للآيات 🎬",
                            fontFamily = AmiriFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = QuranGoldLight
                        )
                        Text(
                            text = "تأثيرات بصرية راقية عند تبديل وظهور الآيات",
                            fontSize = 11.sp,
                            color = QuranTextSecondary
                        )
                    }
                }
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = QuranTextSecondary
                    )
                }
            }

            // Interactive Live Preview Box
            Surface(
                color = QuranEmeraldCard,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, QuranGoldPrimary.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "معاينة حية للتأثير الانتقالي:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = QuranGoldLight
                        )
                        Surface(
                            color = QuranGoldPrimary.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(0.5.dp, QuranGoldPrimary.copy(alpha = 0.4f)),
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { previewCounter++ }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Replay",
                                    tint = QuranGoldLight,
                                    modifier = Modifier.size(13.dp)
                                )
                                Text(
                                    text = "إعادة تجربة الحركة 🔄",
                                    fontSize = 11.sp,
                                    color = QuranGoldLight
                                )
                            }
                        }
                    }

                    // Dynamic Transition Container
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(88.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Black.copy(alpha = 0.5f))
                            .padding(horizontal = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AnimatedContent(
                            targetState = previewCounter,
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
                            label = "preview_transition"
                        ) { count ->
                            val sampleText = if (count % 2 == 0) {
                                "﴾ وَقُل رَّبِّ زِدْنِي عِلْمًا ﴿"
                            } else {
                                "﴾ إِنَّ مَعَ ٱلْعُسْرِ يُسْرًا ﴿"
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = sampleText,
                                    fontFamily = AmiriFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 19.sp,
                                    color = QuranGoldLight,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "${config.transitionType.emoji} ${config.transitionType.titleArabic}",
                                    fontSize = 11.sp,
                                    color = Color.White.copy(alpha = 0.75f)
                                )
                            }
                        }
                    }
                }
            }

            // Duration & Speed Control
            Surface(
                color = QuranEmeraldCard,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Speed,
                                contentDescription = null,
                                tint = QuranGoldLight,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "سرعة وزمن الانتقال:",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = QuranTextPrimary
                            )
                        }
                        Text(
                            text = "${config.transitionDurationMs} ms",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = QuranGoldLight
                        )
                    }

                    // Speed Preset Chips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val presets = listOf(
                            Pair("⚡ سريع (350ms)", 350),
                            Pair("⚖️ متوازن (600ms)", 600),
                            Pair("🕊️ هادئ (900ms)", 900)
                        )
                        presets.forEach { (label, duration) ->
                            val isSelected = config.transitionDurationMs == duration
                            Surface(
                                color = if (isSelected) QuranGoldPrimary else Color.White.copy(alpha = 0.08f),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        onDurationChanged(duration)
                                        previewCounter++
                                    }
                            ) {
                                Text(
                                    text = label,
                                    fontSize = 10.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) Color.Black else QuranTextPrimary,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 6.dp)
                                )
                            }
                        }
                    }

                    Slider(
                        value = config.transitionDurationMs.toFloat(),
                        onValueChange = { onDurationChanged(it.toInt()) },
                        valueRange = 250f..1200f,
                        steps = 18,
                        colors = SliderDefaults.colors(
                            thumbColor = QuranGoldPrimary,
                            activeTrackColor = QuranGoldLight,
                            inactiveTrackColor = Color.White.copy(alpha = 0.2f)
                        )
                    )
                }
            }

            // Transition Presets List
            Text(
                text = "اختر نمط الحركة والانتقال:",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = QuranGoldLight
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(VerseTransitionType.entries) { transition ->
                    val isSelected = config.transitionType == transition

                    Surface(
                        color = if (isSelected) Color(0xFF143029) else QuranEmeraldCard,
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(
                            width = if (isSelected) 1.5.dp else 0.5.dp,
                            color = if (isSelected) QuranGoldPrimary else Color.White.copy(alpha = 0.1f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .clickable {
                                onTransitionSelected(transition)
                                previewCounter++
                            }
                            .testTag("transition_item_${transition.id}")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Surface(
                                    color = if (isSelected) QuranGoldPrimary else Color.White.copy(alpha = 0.08f),
                                    shape = CircleShape,
                                    modifier = Modifier.size(38.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = transition.emoji,
                                            fontSize = 18.sp
                                        )
                                    }
                                }
                                Column {
                                    Text(
                                        text = transition.titleArabic,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = if (isSelected) QuranGoldLight else QuranTextPrimary
                                    )
                                    Text(
                                        text = transition.descriptionArabic,
                                        fontSize = 11.sp,
                                        color = QuranTextSecondary,
                                        lineHeight = 15.sp
                                    )
                                }
                            }

                            if (isSelected) {
                                Surface(
                                    color = QuranGoldPrimary,
                                    shape = CircleShape,
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected",
                                        tint = Color(0xFF1E1700),
                                        modifier = Modifier.padding(4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Apply / Dismiss Button
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = QuranGoldPrimary,
                    contentColor = Color(0xFF1E1700)
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = "اعتماد التأثير وإغلاق",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}
