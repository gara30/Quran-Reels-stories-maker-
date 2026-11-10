package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
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
import com.example.data.model.Surah
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
fun SurahVerseSelectorSheet(
    currentSurah: Surah,
    startVerse: Int,
    endVerse: Int,
    onSurahSelected: (Surah) -> Unit,
    onRangeSelected: (Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var searchQuery by remember { mutableStateOf("") }
    var selectedTempSurah by remember { mutableStateOf(currentSurah) }

    var sliderPosition by remember(selectedTempSurah) {
        val maxV = selectedTempSurah.versesCount.toFloat()
        val s = startVerse.toFloat().coerceIn(1f, maxV)
        val e = endVerse.toFloat().coerceIn(s, maxV)
        mutableStateOf(s..e)
    }

    val filteredSurahs = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            QuranDataSource.allSurahs
        } else {
            QuranDataSource.allSurahs.filter {
                it.nameArabic.contains(searchQuery, ignoreCase = true) ||
                        it.nameEnglish.contains(searchQuery, ignoreCase = true) ||
                        it.number.toString() == searchQuery.trim()
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = QuranEmeraldDark,
        dragHandle = null,
        modifier = Modifier.testTag("surah_verse_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "اختيار السورة ونطاق الآيات",
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

            Spacer(modifier = Modifier.height(12.dp))

            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("ابحث باسم السورة (مثلاً: الفاتحة، الكهف)...", fontSize = 13.sp) },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null, tint = QuranGoldPrimary)
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = QuranGoldPrimary,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                    focusedContainerColor = QuranEmeraldCard,
                    unfocusedContainerColor = QuranEmeraldCard,
                    focusedTextColor = QuranTextPrimary,
                    unfocusedTextColor = QuranTextPrimary
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Verse Range Section
            Surface(
                color = QuranEmeraldCard,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "نطاق الآيات المختارة:",
                            fontSize = 13.sp,
                            color = QuranTextSecondary
                        )
                        Text(
                            text = "من الآية ${sliderPosition.start.toInt()} إلى ${sliderPosition.endInclusive.toInt()}",
                            fontFamily = AmiriFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = QuranGoldPrimary
                        )
                    }

                    if (selectedTempSurah.versesCount > 1) {
                        RangeSlider(
                            value = sliderPosition,
                            onValueChange = { range ->
                                sliderPosition = range
                            },
                            valueRange = 1f..selectedTempSurah.versesCount.toFloat(),
                            steps = (selectedTempSurah.versesCount - 2).coerceAtLeast(0),
                            colors = SliderDefaults.colors(
                                thumbColor = QuranGoldPrimary,
                                activeTrackColor = QuranGoldLight,
                                inactiveTrackColor = Color.White.copy(alpha = 0.2f)
                            ),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "قائمة السور الكريمة (114 سورة):",
                fontSize = 13.sp,
                color = QuranTextSecondary,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            // Surahs list
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredSurahs, key = { it.number }) { surah ->
                    val isSelected = surah.number == selectedTempSurah.number
                    Surface(
                        color = if (isSelected) QuranEmeraldCard.copy(alpha = 0.9f) else Color.Transparent,
                        shape = RoundedCornerShape(14.dp),
                        border = if (isSelected) androidx.compose.foundation.BorderStroke(1.dp, QuranGoldPrimary) else null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedTempSurah = surah
                                val maxV = surah.versesCount.toFloat()
                                sliderPosition = 1f..3f.coerceAtMost(maxV)
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
                                // Surah Number Badge
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) QuranGoldPrimary else Color.White.copy(alpha = 0.1f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${surah.number}",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.Black else QuranTextPrimary
                                    )
                                }

                                Column {
                                    Text(
                                        text = "سورة ${surah.nameArabic}",
                                        fontFamily = AmiriFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = if (isSelected) QuranGoldLight else QuranTextPrimary
                                    )
                                    Text(
                                        text = "${surah.nameEnglish} • ${surah.versesCount} آيات • ${if (surah.revelationType == "Meccan") "مكية" else "مدنية"}",
                                        fontSize = 11.sp,
                                        color = QuranTextSecondary
                                    )
                                }
                            }

                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = QuranGoldPrimary
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Button
            Button(
                onClick = {
                    onSurahSelected(selectedTempSurah)
                    onRangeSelected(sliderPosition.start.toInt(), sliderPosition.endInclusive.toInt())
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = QuranGoldPrimary,
                    contentColor = Color(0xFF1E1700)
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("apply_surah_btn")
            ) {
                Text(
                    text = "تطبيق السورة والنطاق (${selectedTempSurah.nameArabic}: ${sliderPosition.start.toInt()}-${sliderPosition.endInclusive.toInt()})",
                    fontFamily = AmiriFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}
