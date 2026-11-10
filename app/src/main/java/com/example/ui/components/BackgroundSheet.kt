package com.example.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BackgroundOption
import com.example.data.model.BackgroundType
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
fun BackgroundSheet(
    selectedBackground: BackgroundOption,
    onBackgroundSelected: (BackgroundOption) -> Unit,
    onGalleryMediaSelected: (Uri) -> Unit,
    onOpenAiGenerator: () -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedCategory by remember { mutableStateOf("الكل") }

    val categories = listOf("الكل", "فيديوهات متحركة", "مساجد ومعالم", "طبيعة وشلالات", "تدرجات ملكية")

    val filteredBackgrounds = remember(selectedCategory) {
        if (selectedCategory == "الكل") {
            QuranDataSource.defaultBackgrounds
        } else {
            QuranDataSource.defaultBackgrounds.filter { it.category == selectedCategory }
        }
    }

    // Android Zero-permission Photo/Video Picker
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            onGalleryMediaSelected(uri)
            onDismiss()
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = QuranEmeraldDark,
        dragHandle = null,
        modifier = Modifier.testTag("background_sheet")
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
                            imageVector = Icons.Default.Videocam,
                            contentDescription = null,
                            tint = Color(0xFF1E1700),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "مكتبة خلفيات الفيديو والصور",
                            fontFamily = AmiriFontFamily,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = QuranGoldLight
                        )
                        Text(
                            text = "فيديوهات متحركة، مساجد مقدسة، طبيعة، وذكاء اصطناعي",
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

            // Quick External Source Action Cards: Gallery & AI
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Gallery Picker Button
                Surface(
                    color = QuranEmeraldCard,
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.15f)),
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
                            )
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            color = Color.White.copy(alpha = 0.1f),
                            shape = CircleShape,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoLibrary,
                                contentDescription = "Gallery",
                                tint = QuranGoldLight,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "معرض الهاتف",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = QuranTextPrimary
                            )
                            Text(
                                text = "فيديوهات أو صور خاصة",
                                fontSize = 10.sp,
                                color = QuranTextSecondary
                            )
                        }
                    }
                }

                // AI Studio Button
                Surface(
                    color = QuranEmeraldCard,
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, QuranGoldPrimary),
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            onOpenAiGenerator()
                            onDismiss()
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
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
                                contentDescription = "AI",
                                tint = Color(0xFF1E1700),
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "توليد بالذكاء الاصطناعي",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = QuranGoldLight
                            )
                            Text(
                                text = "أجواء سينمائية بـ Gemini",
                                fontSize = 10.sp,
                                color = QuranGoldPrimary.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }

            // Categories Filter Tabs
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

            // Grid of 9:16 Video and Image Background Cards
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(filteredBackgrounds, key = { it.id }) { bg ->
                    val isSelected = bg.id == selectedBackground.id
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(9f / 16f)
                                .clip(RoundedCornerShape(14.dp))
                                .border(
                                    width = if (isSelected) 2.5.dp else 1.dp,
                                    color = if (isSelected) QuranGoldPrimary else Color.White.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(14.dp)
                                )
                                .clickable {
                                    onBackgroundSelected(bg)
                                    onDismiss()
                                }
                        ) {
                            if ((bg.type == BackgroundType.DRAWABLE_PRESET || bg.type == BackgroundType.VIDEO_LOOP_PRESET) && bg.drawableRes != null) {
                                Image(
                                    painter = painterResource(id = bg.drawableRes),
                                    contentDescription = bg.title,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else if (bg.type == BackgroundType.GRADIENT_PRESET && bg.gradientColors != null) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Brush.verticalGradient(bg.gradientColors))
                                )
                            }

                            // Video Tag Badge on thumbnail
                            if (bg.isVideo) {
                                Surface(
                                    color = Color.Black.copy(alpha = 0.65f),
                                    shape = RoundedCornerShape(bottomEnd = 8.dp),
                                    modifier = Modifier.align(Alignment.TopStart)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Videocam,
                                            contentDescription = null,
                                            tint = QuranGoldLight,
                                            modifier = Modifier.size(11.dp)
                                        )
                                        Text(
                                            text = "فيديو",
                                            fontSize = 9.sp,
                                            color = QuranGoldLight,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }

                            // Selection Checkmark
                            if (isSelected) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Black.copy(alpha = 0.4f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Surface(
                                        color = QuranGoldPrimary,
                                        shape = CircleShape,
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Selected",
                                            tint = Color(0xFF1E1700),
                                            modifier = Modifier.padding(5.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = bg.title,
                            fontSize = 10.sp,
                            color = if (isSelected) QuranGoldLight else QuranTextSecondary,
                            maxLines = 2,
                            textAlign = TextAlign.Center,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            lineHeight = 13.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
