package com.example.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.StudioConfig
import com.example.data.model.TranslationOption
import com.example.data.source.QuranDataSource
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.QuranEmeraldCard
import com.example.ui.theme.QuranEmeraldDark
import com.example.ui.theme.QuranGoldLight
import com.example.ui.theme.QuranGoldPrimary
import com.example.ui.theme.QuranTextPrimary
import com.example.ui.theme.QuranTextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TranslationSelectorSheet(
    config: StudioConfig,
    onTranslationSelected: (TranslationOption) -> Unit,
    onToggleShowTranslation: () -> Unit,
    onToggleShowArabic: () -> Unit,
    onTranslationFontSizeChanged: (Float) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = QuranEmeraldDark,
        dragHandle = null,
        modifier = Modifier.testTag("translation_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
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
                            imageVector = Icons.Default.Translate,
                            contentDescription = null,
                            tint = Color(0xFF1E1700),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "لغة الترجمة والعرض المزدوج",
                            fontFamily = AmiriFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = QuranGoldLight
                        )
                        Text(
                            text = "ترجمات مجمع الملك فهد المعتمدة في السعودية",
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

            // Saudi King Fahd Complex Official Badge
            Surface(
                color = Color(0xFF0F2B20),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1E8262).copy(alpha = 0.6f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("🇸🇦", fontSize = 20.sp)
                    Column {
                        Text(
                            text = "المعتمدة في المملكة العربية السعودية",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = Color(0xFFA5F0D2)
                        )
                        Text(
                            text = "إصدارات مجمع الملك فهد لطباعة المصحف الشريف بالمدينة المنورة",
                            fontSize = 10.sp,
                            color = QuranTextSecondary
                        )
                    }
                }
            }

            // Dual Display Settings Card
            Surface(
                color = QuranEmeraldCard,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Show Both Together (Arabic + Translation)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "عرض النص العربي والترجمة معاً",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = QuranTextPrimary
                            )
                            Text(
                                text = "ظهور الرسم العثماني والترجمة في نفس الفيديو",
                                fontSize = 10.sp,
                                color = QuranTextSecondary
                            )
                        }
                        Switch(
                            checked = config.showTranslation && config.showArabicText,
                            onCheckedChange = {
                                if (!config.showTranslation) onToggleShowTranslation()
                                if (!config.showArabicText) onToggleShowArabic()
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = QuranGoldPrimary,
                                checkedTrackColor = QuranGoldLight.copy(alpha = 0.4f)
                            )
                        )
                    }

                    // Translation Font Size Slider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "حجم خط الترجمة:",
                            fontSize = 12.sp,
                            color = QuranTextSecondary
                        )
                        Text(
                            text = "${config.translationFontSizeSp.toInt()} sp",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = QuranGoldLight
                        )
                    }
                    Slider(
                        value = config.translationFontSizeSp,
                        onValueChange = onTranslationFontSizeChanged,
                        valueRange = 11f..22f,
                        colors = SliderDefaults.colors(
                            thumbColor = QuranGoldPrimary,
                            activeTrackColor = QuranGoldLight,
                            inactiveTrackColor = Color.White.copy(alpha = 0.2f)
                        )
                    )
                }
            }

            Text(
                text = "اختر لغة الترجمة المعتمدة:",
                fontSize = 13.sp,
                color = QuranTextSecondary
            )

            // Translations list
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(QuranDataSource.defaultTranslations, key = { it.id }) { translation ->
                    val isSelected = translation.id == config.selectedTranslation.id
                    Surface(
                        color = if (isSelected) QuranEmeraldCard else Color.White.copy(alpha = 0.04f),
                        shape = RoundedCornerShape(14.dp),
                        border = if (isSelected) androidx.compose.foundation.BorderStroke(1.2.dp, QuranGoldPrimary) else null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onTranslationSelected(translation)
                                onDismiss()
                            }
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
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(translation.flag, fontSize = 24.sp)

                                Column {
                                    Text(
                                        text = translation.languageNameArabic,
                                        fontFamily = AmiriFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = if (isSelected) QuranGoldLight else QuranTextPrimary
                                    )
                                    Text(
                                        text = "${translation.languageNameNative} • ${translation.authorName}",
                                        fontSize = 11.sp,
                                        color = QuranTextSecondary,
                                        maxLines = 1
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

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
