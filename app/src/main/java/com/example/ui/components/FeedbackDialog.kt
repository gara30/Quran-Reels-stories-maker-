package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.QuranEmeraldCard
import com.example.ui.theme.QuranEmeraldDark
import com.example.ui.theme.QuranGoldLight
import com.example.ui.theme.QuranGoldPrimary
import com.example.ui.theme.QuranTextPrimary
import com.example.ui.theme.QuranTextSecondary

@Composable
fun FeedbackDialog(
    developerEmail: String = "aboudej30@gmail.com",
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var selectedCategory by remember { mutableStateOf("اقتراح ميزة جديدة") }
    var userMessage by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }

    val categories = listOf(
        "اقتراح ميزة جديدة",
        "إضافة قارئ أو خلفية",
        "ملاحظة لتحسين التطبيق",
        "رسالة شكر ودعاء"
    )

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = QuranEmeraldDark,
            border = androidx.compose.foundation.BorderStroke(1.2.dp, QuranGoldPrimary.copy(alpha = 0.5f)),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("feedback_dialog")
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
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            color = QuranGoldPrimary,
                            shape = CircleShape,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.RateReview,
                                contentDescription = null,
                                tint = Color(0xFF1E1700),
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "إرسال رسالة أو اقتراح",
                                fontFamily = AmiriFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 19.sp,
                                color = QuranGoldLight
                            )
                            Text(
                                text = "رأيك واقتراحاتك تساهم في تطوير التطبيق",
                                fontSize = 11.sp,
                                color = QuranTextSecondary
                            )
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = QuranTextSecondary)
                    }
                }

                // Category Chips
                Text(
                    text = "نوع الرسالة أو الملاحظة:",
                    fontSize = 12.sp,
                    color = QuranTextSecondary
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(categories) { category ->
                        val isSelected = category == selectedCategory
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedCategory = category },
                            label = { Text(category, fontSize = 11.sp) },
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

                // Name / Sender Field (Optional)
                OutlinedTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = { Text("اسمك أو بريدك الإلكتروني (اختياري)", fontSize = 12.sp) },
                    singleLine = true,
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

                // Message Text Field
                OutlinedTextField(
                    value = userMessage,
                    onValueChange = { userMessage = it },
                    label = { Text("اكتب رسالتك أو اقتراحك أو ملاحظتك هنا...", fontSize = 12.sp) },
                    minLines = 3,
                    maxLines = 5,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = QuranGoldPrimary,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                        focusedContainerColor = QuranEmeraldCard,
                        unfocusedContainerColor = QuranEmeraldCard,
                        focusedTextColor = QuranTextPrimary,
                        unfocusedTextColor = QuranTextPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("feedback_message_input")
                )

                // Developer note
                Surface(
                    color = QuranEmeraldCard,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null,
                            tint = QuranGoldLight,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "تصل رسالتك مباشرة للمطور: $developerEmail",
                            fontSize = 11.sp,
                            color = QuranTextSecondary
                        )
                    }
                }

                // Submit Button
                Button(
                    onClick = {
                        sendFeedbackEmail(
                            context = context,
                            recipient = developerEmail,
                            category = selectedCategory,
                            senderName = userName,
                            message = userMessage
                        )
                        Toast.makeText(context, "جزاك الله خيراً على تواصلك واقتراحك!", Toast.LENGTH_LONG).show()
                        onDismiss()
                    },
                    enabled = userMessage.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = QuranGoldPrimary,
                        contentColor = Color(0xFF1E1700)
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("send_feedback_btn")
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "إرسال الرسالة الآن",
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

private fun sendFeedbackEmail(
    context: Context,
    recipient: String,
    category: String,
    senderName: String,
    message: String
) {
    val subject = "[صانع فيديو الآيات] $category"
    val emailBody = buildString {
        append("السلام عليكم ورحمة الله وبركاته،\n\n")
        append("النوع: $category\n")
        if (senderName.isNotBlank()) {
            append("المرسل: $senderName\n")
        }
        append("------------------------------------\n")
        append("نص الرسالة / الاقتراح:\n")
        append(message)
        append("\n------------------------------------\n")
        append("مرسلة عبر تطبيق صانع فيديو الآيات (Quran Video Maker)\n")
    }

    try {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$recipient")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, emailBody)
        }
        context.startActivity(Intent.createChooser(intent, "اختر تطبيق البريد لإرسال رسالتك"))
    } catch (e: Exception) {
        // Fallback generic send intent
        try {
            val fallbackIntent = Intent(Intent.ACTION_SEND).apply {
                type = "message/rfc822"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, emailBody)
            }
            context.startActivity(Intent.createChooser(fallbackIntent, "إرسال الرسالة"))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}
