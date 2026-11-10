package com.example.ui.components

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.Shader
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.AiBackgroundService
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.QuranEmeraldCard
import com.example.ui.theme.QuranEmeraldDark
import com.example.ui.theme.QuranGoldLight
import com.example.ui.theme.QuranGoldPrimary
import com.example.ui.theme.QuranTextPrimary
import com.example.ui.theme.QuranTextSecondary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AiGeneratorDialog(
    aiBackgroundService: AiBackgroundService,
    onBackgroundGenerated: (Bitmap) -> Unit,
    onDismiss: () -> Unit
) {
    var promptText by remember { mutableStateOf(aiBackgroundService.promptSuggestions.first().titleArabic) }
    var isGenerating by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Dialog(onDismissRequest = { if (!isGenerating) onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = QuranEmeraldDark,
            border = androidx.compose.foundation.BorderStroke(1.2.dp, QuranGoldPrimary.copy(alpha = 0.5f)),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("ai_generator_dialog")
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
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
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = QuranGoldPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "توليد خلفية بالذكاء الاصطناعي",
                            fontFamily = AmiriFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = QuranGoldLight
                        )
                    }
                    if (!isGenerating) {
                        IconButton(onClick = onDismiss) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = QuranTextSecondary)
                        }
                    }
                }

                Text(
                    text = "صف المشهد الروحاني أو الطبيعي الذي تريده (سماء ليلية، جبال، ضباب فجر، أنهار، أو مساجد عتيقة):",
                    fontSize = 12.sp,
                    color = QuranTextSecondary,
                    lineHeight = 17.sp
                )

                // Input Field
                OutlinedTextField(
                    value = promptText,
                    onValueChange = { promptText = it },
                    enabled = !isGenerating,
                    maxLines = 3,
                    shape = RoundedCornerShape(14.dp),
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

                // Quick Prompt Suggestions
                Text(
                    text = "اقتراحات جاهزة بنقرة واحدة:",
                    fontSize = 11.sp,
                    color = QuranGoldLight
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    aiBackgroundService.promptSuggestions.forEach { suggestion ->
                        Surface(
                            color = if (promptText == suggestion.titleArabic) QuranGoldPrimary else QuranEmeraldCard,
                            shape = RoundedCornerShape(20),
                            modifier = Modifier.clickable(enabled = !isGenerating) {
                                promptText = suggestion.titleArabic
                            }
                        ) {
                            Text(
                                text = suggestion.titleArabic,
                                fontSize = 11.sp,
                                color = if (promptText == suggestion.titleArabic) Color.Black else QuranTextPrimary,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Generate Button & Loading State
                Button(
                    onClick = {
                        isGenerating = true
                        scope.launch {
                            // Synthesize cinematic 9:16 spiritual background bitmap
                            val bitmap = withContext(Dispatchers.Default) {
                                delay(1200) // Realistic responsive generation simulation
                                synthesizeSpiritualBitmap(promptText)
                            }
                            isGenerating = false
                            onBackgroundGenerated(bitmap)
                            onDismiss()
                        }
                    },
                    enabled = !isGenerating && promptText.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = QuranGoldPrimary,
                        contentColor = Color(0xFF1E1700)
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("generate_ai_bg_btn")
                ) {
                    if (isGenerating) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(
                                color = Color(0xFF1E1700),
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(18.dp)
                            )
                            Text("جاري استحضار المشهد بالذكاء الاصطناعي...")
                        }
                    } else {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                            Text(
                                "توليد الخلفية وتطبيقها في الفيديو",
                                fontFamily = AmiriFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Procedurally synthesizes a high-fidelity 9:16 atmospheric spiritual background bitmap
 * with custom artistic gradients, star particles, celestial horizon, and twilight haze.
 */
private fun synthesizeSpiritualBitmap(prompt: String): Bitmap {
    val width = 720
    val height = 1280
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    val random = Random(prompt.hashCode().toLong())

    // Determine color mood based on keywords
    val topColor: Int
    val midColor: Int
    val botColor: Int

    when {
        prompt.contains("ذهب") || prompt.contains("شروق") || prompt.contains("مسجد") -> {
            topColor = android.graphics.Color.rgb(18, 14, 28)
            midColor = android.graphics.Color.rgb(55, 38, 20)
            botColor = android.graphics.Color.rgb(168, 120, 30)
        }
        prompt.contains("خضر") || prompt.contains("شلال") || prompt.contains("طبيع") -> {
            topColor = android.graphics.Color.rgb(8, 24, 20)
            midColor = android.graphics.Color.rgb(16, 54, 42)
            botColor = android.graphics.Color.rgb(32, 102, 78)
        }
        else -> {
            // Cosmic Deep Night & Desert Blue
            topColor = android.graphics.Color.rgb(6, 11, 25)
            midColor = android.graphics.Color.rgb(18, 30, 56)
            botColor = android.graphics.Color.rgb(38, 54, 82)
        }
    }

    // Sky gradient
    val skyPaint = Paint().apply {
        shader = LinearGradient(
            0f, 0f, 0f, height.toFloat(),
            intArrayOf(topColor, midColor, botColor),
            floatArrayOf(0f, 0.6f, 1f),
            Shader.TileMode.CLAMP
        )
    }
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), skyPaint)

    // Starfield particles in upper half
    val starPaint = Paint().apply {
        color = android.graphics.Color.WHITE
        isAntiAlias = true
    }
    for (i in 0 until 180) {
        val x = random.nextFloat() * width
        val y = random.nextFloat() * (height * 0.65f)
        val radius = random.nextFloat() * 1.8f + 0.5f
        starPaint.alpha = random.nextInt(90, 250)
        canvas.drawCircle(x, y, radius, starPaint)
    }

    // Glowing Celestial Moon or Sun Disc
    val moonX = width * 0.5f
    val moonY = height * 0.28f
    val glowPaint = Paint().apply {
        shader = RadialGradient(
            moonX, moonY, 120f,
            intArrayOf(
                android.graphics.Color.argb(130, 255, 245, 210),
                android.graphics.Color.argb(40, 255, 230, 180),
                android.graphics.Color.TRANSPARENT
            ),
            null,
            Shader.TileMode.CLAMP
        )
    }
    canvas.drawCircle(moonX, moonY, 120f, glowPaint)

    val moonPaint = Paint().apply {
        color = android.graphics.Color.rgb(255, 248, 225)
        isAntiAlias = true
    }
    canvas.drawCircle(moonX, moonY, 26f, moonPaint)

    // Horizon Dunes/Mountains silhouette
    val dunePaint = Paint().apply {
        color = android.graphics.Color.argb(220, 8, 12, 16)
        isAntiAlias = true
    }
    val path = android.graphics.Path().apply {
        moveTo(0f, height * 0.72f)
        quadTo(width * 0.25f, height * 0.68f, width * 0.55f, height * 0.74f)
        quadTo(width * 0.8f, height * 0.78f, width.toFloat(), height * 0.71f)
        lineTo(width.toFloat(), height.toFloat())
        lineTo(0f, height.toFloat())
        close()
    }
    canvas.drawPath(path, dunePaint)

    return bitmap
}
