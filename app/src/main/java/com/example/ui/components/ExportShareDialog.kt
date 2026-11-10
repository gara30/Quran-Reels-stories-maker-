package com.example.ui.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.Ayah
import com.example.data.model.StudioConfig
import com.example.export.VideoShareHelper
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.QuranEmeraldCard
import com.example.ui.theme.QuranEmeraldDark
import com.example.ui.theme.QuranGoldLight
import com.example.ui.theme.QuranGoldPrimary
import com.example.ui.theme.QuranTextPrimary
import com.example.ui.theme.QuranTextSecondary

@Composable
fun ExportShareDialog(
    config: StudioConfig,
    currentAyah: Ayah?,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = QuranEmeraldDark,
            border = androidx.compose.foundation.BorderStroke(1.2.dp, QuranGoldPrimary.copy(alpha = 0.5f)),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("export_share_dialog")
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
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
                                imageVector = Icons.Default.Movie,
                                contentDescription = null,
                                tint = Color(0xFF1E1700),
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        Text(
                            text = "تصدير ومشاركة الفيديو",
                            fontFamily = AmiriFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 19.sp,
                            color = QuranGoldLight
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = QuranTextSecondary)
                    }
                }

                // Summary Card
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
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("السورة الكريمة:", fontSize = 12.sp, color = QuranTextSecondary)
                            Text("سورة ${config.selectedSurah.nameArabic}", fontWeight = FontWeight.Bold, color = QuranGoldLight, fontSize = 13.sp)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("نطاق الآيات:", fontSize = 12.sp, color = QuranTextSecondary)
                            Text("الآيات ${config.startVerse} إلى ${config.endVerse}", fontWeight = FontWeight.Bold, color = QuranTextPrimary, fontSize = 13.sp)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("القارئ المختار:", fontSize = 12.sp, color = QuranTextSecondary)
                            Text(config.selectedReciter.nameArabic, fontWeight = FontWeight.Bold, color = QuranTextPrimary, fontSize = 13.sp)
                        }
                        if (config.showTranslation) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("الترجمة (السعودية):", fontSize = 12.sp, color = QuranTextSecondary)
                                Text(
                                    config.selectedTranslation.nameArabic,
                                    fontWeight = FontWeight.Bold,
                                    color = QuranGoldLight,
                                    fontSize = 12.sp
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("تأثير الانتقال:", fontSize = 12.sp, color = QuranTextSecondary)
                            Text(
                                "${config.transitionType.emoji} ${config.transitionType.titleArabic}",
                                fontWeight = FontWeight.Bold,
                                color = QuranGoldLight,
                                fontSize = 12.sp
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("أبعاد الإخراج:", fontSize = 12.sp, color = QuranTextSecondary)
                            Text("9:16 Vertical (Reels / TikTok / Stories)", fontWeight = FontWeight.Bold, color = QuranGoldPrimary, fontSize = 12.sp)
                        }
                    }
                }

                // ضمان خلو التطبيق من الإعلانات 100%
                Surface(
                    color = Color(0xFF0F2B20),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1E8262).copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("🛡️", fontSize = 14.sp)
                        Text(
                            text = "تطبيق قرآني نقي لوجه الله تعالى • خالٍ تماماً من أي إعلانات بنسبة 100%",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFA5F0D2)
                        )
                    }
                }

                Text(
                    text = "الفيديو جاهز للمشاركة مباشرة على جميع المنصات الاجتماعية مع الصوت والرسم العثماني الفاخر:",
                    fontSize = 12.sp,
                    color = QuranTextSecondary,
                    lineHeight = 17.sp
                )

                // Helper to get formatted ayah for sharing
                fun getEffectiveAyah(): Ayah {
                    val rawAyah = currentAyah ?: Ayah(
                        surahNumber = config.selectedSurah.number,
                        numberInSurah = config.startVerse,
                        numberInQuran = 1,
                        textUthmani = "بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ",
                        translation = "In the name of Allah, the Entirely Merciful"
                    )
                    val trans = rawAyah.getTranslationFor(config.selectedTranslation.id)
                    return if (config.showTranslation && trans.isNotBlank()) {
                        rawAyah.copy(translation = "$trans\n(${config.selectedTranslation.nameArabic} - مجمع الملك فهد بالمدينة المنورة)")
                    } else if (!config.showTranslation) {
                        rawAyah.copy(translation = "")
                    } else rawAyah
                }

                // Share Story Button
                Button(
                    onClick = {
                        VideoShareHelper.shareAyahStory(
                            context = context,
                            ayah = getEffectiveAyah(),
                            surah = config.selectedSurah,
                            reciter = config.selectedReciter,
                            storyBitmap = null
                        )
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
                        .testTag("share_story_btn")
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(20.dp))
                        Text(
                            "مشاركة قصة الآية القرآنية الآن",
                            fontFamily = AmiriFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }

                // Copy Text Button
                OutlinedButton(
                    onClick = {
                        VideoShareHelper.shareAyahStory(
                            context = context,
                            ayah = getEffectiveAyah(),
                            surah = config.selectedSurah,
                            reciter = config.selectedReciter,
                            storyBitmap = null
                        )
                        onDismiss()
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = QuranGoldLight
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, QuranGoldPrimary.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                        Text("مشاركة النص والترجمة ورابط التلاوة", fontSize = 13.sp)
                    }
                }
            }
        }
    }
}
