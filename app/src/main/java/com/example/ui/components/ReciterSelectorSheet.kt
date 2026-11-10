package com.example.ui.components

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.data.model.Reciter
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
fun ReciterSelectorSheet(
    currentReciter: Reciter,
    onReciterSelected: (Reciter) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("الكل") }

    val categories = listOf("الكل", "أئمة الحرمين", "المرتل المشهور", "المجود والمعلم", "تلاوات شجية وخاشعة")

    val filteredReciters = remember(searchQuery, selectedCategory) {
        QuranDataSource.reciters.filter { reciter ->
            val matchesSearch = searchQuery.isBlank() ||
                    reciter.nameArabic.contains(searchQuery, ignoreCase = true) ||
                    reciter.nameEnglish.contains(searchQuery, ignoreCase = true) ||
                    reciter.style.contains(searchQuery, ignoreCase = true)

            val matchesCategory = when (selectedCategory) {
                "أئمة الحرمين" -> reciter.style.contains("الحرم") || reciter.style.contains("المسجد")
                "المجود والمعلم" -> reciter.style.contains("مجود") || reciter.style.contains("معلم")
                "تلاوات شجية وخاشعة" -> reciter.style.contains("شجي") || reciter.style.contains("خاشع")
                "المرتل المشهور" -> reciter.style.contains("مرتل")
                else -> true
            }

            matchesSearch && matchesCategory
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = QuranEmeraldDark,
        dragHandle = null,
        modifier = Modifier.testTag("reciter_sheet")
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
                            imageVector = Icons.Default.RecordVoiceOver,
                            contentDescription = null,
                            tint = Color(0xFF1E1700),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "مكتبة كبار القراء والتلاوات",
                            fontFamily = AmiriFontFamily,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = QuranGoldLight
                        )
                        Text(
                            text = "${QuranDataSource.reciters.size} قارئاً معتمداً مع مزامنة الآيات",
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

            Spacer(modifier = Modifier.height(12.dp))

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("ابحث باسم القارئ (مثلاً: العفاسي، المنشاوي، السديس)...", fontSize = 13.sp) },
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

            Spacer(modifier = Modifier.height(10.dp))

            // Category Filter Chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categories) { category ->
                    val isSelected = category == selectedCategory
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = category },
                        label = { Text(category, fontSize = 12.sp) },
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
                        shape = RoundedCornerShape(20)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Reciters List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(filteredReciters, key = { it.id }) { reciter ->
                    val isSelected = reciter.id == currentReciter.id
                    Surface(
                        color = if (isSelected) QuranEmeraldCard else Color.White.copy(alpha = 0.04f),
                        shape = RoundedCornerShape(16.dp),
                        border = if (isSelected) androidx.compose.foundation.BorderStroke(1.2.dp, QuranGoldPrimary) else null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onReciterSelected(reciter)
                                onDismiss()
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                Surface(
                                    color = if (isSelected) QuranGoldPrimary else Color.White.copy(alpha = 0.08f),
                                    shape = CircleShape,
                                    modifier = Modifier.size(42.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.RecordVoiceOver,
                                        contentDescription = null,
                                        tint = if (isSelected) Color(0xFF1E1700) else QuranGoldLight,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                }

                                Column {
                                    Text(
                                        text = reciter.nameArabic,
                                        fontFamily = AmiriFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 17.sp,
                                        color = if (isSelected) QuranGoldLight else QuranTextPrimary
                                    )
                                    Text(
                                        text = "${reciter.nameEnglish} • ${reciter.style}",
                                        fontSize = 12.sp,
                                        color = QuranTextSecondary
                                    )
                                }
                            }

                            if (isSelected) {
                                Surface(
                                    color = QuranGoldPrimary,
                                    shape = CircleShape,
                                    modifier = Modifier.size(26.dp)
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

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
