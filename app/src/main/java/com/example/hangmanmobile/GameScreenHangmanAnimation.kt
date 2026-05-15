package com.example.hangmanmobile

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun GameScreenHangmanAnimation(failureCount: Int, modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "glow")

    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1500,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Canvas(
        modifier = modifier
            .size(260.dp)
            .padding(8.dp)
    ) {
        val w = size.width
        val h = size.height

        val cyan = Color(0xFF00F5FF)
        val pink = Color(0xFFFF2D78)
        val gold = Color(0xFFF5C542)

        // glow + core helper functions
        fun glowLine(color: Color, start: Offset, end: Offset) {
            drawLine(color.copy(alpha = glowAlpha * 0.25f), start, end, strokeWidth = 14f)
            drawLine(color.copy(alpha = glowAlpha), start, end, strokeWidth = 3f)
        }

        fun glowCircle(color: Color, center: Offset, radius: Float) {
            drawCircle(
                color.copy(alpha = glowAlpha * 0.25f),
                radius + 6f,
                center,
                style = Stroke(14f)
            )
            drawCircle(color.copy(alpha = glowAlpha), radius, center, style = Stroke(3f))
        }

        // figure measurements
        val headR = w * 0.1f
        val headY = h * 0.27f
        val cx = w * 0.55f

        // ── Gallows ── Always visible
        glowLine(cyan, Offset(w * 0.08f, h * 0.92f), Offset(w * 0.75f, h * 0.92f)) // base
        glowLine(cyan, Offset(w * 0.2f, h * 0.92f), Offset(w * 0.2f, h * 0.05f))   // pole
        glowLine(cyan, Offset(w * 0.2f, h * 0.05f), Offset(w * 0.55f, h * 0.05f))  // beam
        glowLine(cyan, Offset(w * 0.2f, h * 0.18f), Offset(w * 0.35f, h * 0.05f))  // strut

        // Rope mistake 1
        if (failureCount >= 1) {
            glowLine(
                cyan,
                Offset(cx, h * 0.05f),
                Offset(cx, headY - headR)
            )
        }

        // ── Figure ──
        // Head mistake 2
        if (failureCount >= 2) {
            glowCircle(pink, Offset(cx, headY), headR)  // head
        }


        val bodyTop = Offset(cx, headY + headR)
        val bodyBottom = Offset(cx, headY + headR + h * 0.22f)

        // Body mistake 3
        if (failureCount >= 3) {
            glowLine(pink, bodyTop, bodyBottom)  // body
        }

        val armStart = Offset(cx, headY + headR + h * 0.07f)
        val leftHand = Offset(cx - w * 0.15f, headY + headR + h * 0.16f)
        val rightHand = Offset(cx + w * 0.15f, headY + headR + h * 0.16f)

        // Arms mistake 4
        if (failureCount >= 4) {
            glowLine(pink, armStart, leftHand)   // left arm
            glowLine(pink, armStart, rightHand)  // right arm
        }

        val leftFoot = Offset(cx - w * 0.13f, headY + headR + h * 0.37f)
        val rightFoot = Offset(cx + w * 0.13f, headY + headR + h * 0.37f)

        // Legs mistake 5
        if (failureCount >= 5) {
            glowLine(pink, bodyBottom, leftFoot)   // left leg
            glowLine(pink, bodyBottom, rightFoot)  // right leg
        }

        // ── Gold dots ──
        // Mistake 6
        if (failureCount >= 6) {
            listOf(leftHand, rightHand, leftFoot, rightFoot).forEach {
                drawCircle(gold.copy(alpha = 0.3f), 10f, it)  // glow
                drawCircle(gold, 5f, it)                       // core
            }
        }
    }
}