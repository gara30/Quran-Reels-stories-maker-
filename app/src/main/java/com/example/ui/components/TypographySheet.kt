package com.example.ui.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FontType
import com.example.data.model.StudioConfig
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.QuranEmeraldCard
import com.example.ui.theme.QuranEmeraldDark
import com.example.ui.theme.QuranGoldLight
import com.example.ui.theme.QuranGoldPrimary
import com.example.ui.theme.QuranTextPrimary
import com.example.ui.theme.QuranTextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypographySheet(
    config: StudioConfig,
    onFontSelected: (FontType) -> Unit,
    onFontSizeChanged: (Float) -> Unit,
    onFontColorChanged: (Color) -> Unit,
    onOverlayDarknessChanged: (Float) -> Unit,
    onToggleTranslation: () -> Unit,
    onToggleWaveform: () -> Unit,
    onToggleSurahHeader: () -> Unit,
    onOpenTransitions: () -> Unit = {},
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val colorPalette = listOf(
        Pair("أبيض ناصع", Color(0xFFFFFFFF)),
        Pair("ذهبي فاخر", Color(0xFFFFD700)),
        Pair("عاجي دافئ", Color(0xFFFFF8E7)),
        Pair("أخضر نضر", Color(0xFFA8E6CF)),
        Pair("سماوي هادئ", Color(0xFF81D4FA))
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = QuranEmeraldDark,
        dragHandle = null,
        modifier = Modifier.testTag("typography_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "تخصيص الخط والمظهر والشفافية",
                    fontFamily = AmiriFontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = QuranGoldLight
                )
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = QuranTextSecondary
                    )
                }
            }

            // 1. Font Family
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "نوع الخط القرآني:",
                    fontSize = 13.sp,
                    color = QuranTextSecondary
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FontType.values().forEach { font ->
                        val isSelected = font == config.fontType
                        FilterChip(
                            selected = isSelected,
                            onClick = { onFontSelected(font) },
                            label = { Text(font.displayNameArabic, fontSize = 13.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = QuranGoldPrimary,
                                selectedLabelColor = Color(0xFF1E1700),
                                containerColor = QuranEmeraldCard,
                                labelColor = QuranTextPrimary
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = if (isSelected) QuranGoldPrimary else Color.White.copy(alpha = 0.15f)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }
            }

            // 2. Font Size
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "حجم الخط القرآني:",
                        fontSize = 13.sp,
                        color = QuranTextSecondary
                    )
                    Text(
                        text = "${config.fontSizeSp.toInt()} sp",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = QuranGoldLight
                    )
                }
                Slider(
                    value = config.fontSizeSp,
                    onValueChange = onFontSizeChanged,
                    valueRange = 18f..42f,
                    colors = SliderDefaults.colors(
                        thumbColor = QuranGoldPrimary,
                        activeTrackColor = QuranGoldLight,
                        inactiveTrackColor = Color.White.copy(alpha = 0.2f)
                    )
                )
            }

            // 3. Font Color Palette
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "لون خط الآيات:",
                    fontSize = 13.sp,
                    color = QuranTextSecondary
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    colorPalette.forEach { (name, color) ->
                        val isSelected = config.fontColor == color
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(color)
                                .border(
                                    width = if (isSelected) 3.dp else 1.dp,
                                    color = if (isSelected) QuranGoldPrimary else Color.White.copy(alpha = 0.3f),
                                    shape = CircleShape
                                )
                                .clickable { onFontColorChanged(color) }
                        )
                    }
                }
            }

            // 4. Background Overlay Darkness
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "شفافية طبقة التعتيم الخلفية:",
                        fontSize = 13.sp,
                        color = QuranTextSecondary
                    )
                    Text(
                        text = "${(config.overlayDarkness * 100).toInt()}%",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = QuranGoldLight
                    )
                }
                Slider(
                    value = config.overlayDarkness,
                    onValueChange = onOverlayDarknessChanged,
                    valueRange = 0.1f..0.85f,
                    colors = SliderDefaults.colors(
                        thumbColor = QuranGoldPrimary,
                        activeTrackColor = QuranGoldLight,
                        inactiveTrackColor = Color.White.copy(alpha = 0.2f)
                    )
                )
            }

            // 5. Visual Toggles
            Surface(
                color = QuranEmeraldCard,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Translation Toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "عرض الترجمة (${config.selectedTranslation.nameArabic})",
                            fontSize = 13.sp,
                            color = QuranTextPrimary
                        )
                        Switch(
                            checked = config.showTranslation,
                            onCheckedChange = { onToggleTranslation() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = QuranGoldPrimary,
                                checkedTrackColor = QuranGoldLight.copy(alpha = 0.4f)
                            )
                        )
                    }

                    // Waveform Toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "مؤشر موجات الصوت المتحرك",
                            fontSize = 13.sp,
                            color = QuranTextPrimary
                        )
                        Switch(
                            checked = config.showWaveform,
                            onCheckedChange = { onToggleWaveform() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = QuranGoldPrimary,
                                checkedTrackColor = QuranGoldLight.copy(alpha = 0.4f)
                            )
                        )
                    }

                    // Header Toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "وسم عنوان السورة والآية في الأعلى",
                            fontSize = 13.sp,
                            color = QuranTextPrimary
                        )
                        Switch(
                            checked = config.showSurahHeader,
                            onCheckedChange = { onToggleSurahHeader() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = QuranGoldPrimary,
                                checkedTrackColor = QuranGoldLight.copy(alpha = 0.4f)
                            )
                        )
                    }
                }
            }

            // 6. Transition Effect Shortcut Card
            Surface(
                color = QuranEmeraldCard,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, QuranGoldPrimary.copy(alpha = 0.35f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpenTransitions() }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(text = config.transitionType.emoji, fontSize = 20.sp)
                        Column {
                            Text(
                                text = "تأثير انتقال الآيات: ${config.transitionType.titleArabic}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = QuranGoldLight
                            )
                            Text(
                                text = "اضغط لتعديل الحركة الانتقالية والسرعة (${config.transitionDurationMs}ms) 🎬",
                                fontSize = 11.sp,
                                color = QuranTextSecondary
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.Default.ChevronLeft,
                        contentDescription = "Edit Transitions",
                        tint = QuranGoldLight
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
