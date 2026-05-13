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
fun AnimatedHangman(modifier: Modifier = Modifier) {
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

        val cyan = Color(0xFF00F5FF).copy(alpha = glowAlpha)
        val pink = Color(0xFFFF2D78).copy(alpha = glowAlpha)
        val gold = Color(0xFFF5C542)
        val thick = 4f  // slightly thicker so it's more visible

        // ── Gallows (scales with canvas size) ──
        // Base
        drawLine(cyan, Offset(w * 0.05f, h * 0.92f), Offset(w * 0.95f, h * 0.92f), thick)
        // Pole
        drawLine(cyan, Offset(w * 0.2f, h * 0.92f), Offset(w * 0.2f, h * 0.05f), thick)
        // Top beam
        drawLine(cyan, Offset(w * 0.2f, h * 0.05f), Offset(w * 0.65f, h * 0.05f), thick)
        // Rope
        drawLine(cyan, Offset(w * 0.65f, h * 0.05f), Offset(w * 0.65f, h * 0.18f), thick)

        // ── Figure (all positions relative to canvas size) ──
        val cx       = w * 0.65f   // center x — hangs from rope
        val headY    = h * 0.27f   // head center y
        val headR    = w * 0.1f    // head radius

        // Head
        drawCircle(
            color  = pink,
            radius = headR,
            center = Offset(cx, headY),
            style  = Stroke(thick)
        )

        // Body
        val bodyTop    = Offset(cx, headY + headR)
        val bodyBottom = Offset(cx, headY + headR + h * 0.22f)
        drawLine(pink, bodyTop, bodyBottom, thick)

        // Arms
        val armY     = Offset(cx, headY + headR + h * 0.07f)
        val leftHand  = Offset(cx - w * 0.15f, headY + headR + h * 0.16f)
        val rightHand = Offset(cx + w * 0.15f, headY + headR + h * 0.16f)
        drawLine(pink, armY, leftHand, thick)
        drawLine(pink, armY, rightHand, thick)

        // Legs
        val leftFoot  = Offset(cx - w * 0.13f, headY + headR + h * 0.37f)
        val rightFoot = Offset(cx + w * 0.13f, headY + headR + h * 0.37f)
        drawLine(pink, bodyBottom, leftFoot, thick)
        drawLine(pink, bodyBottom, rightFoot, thick)

        // ── Gold joint dots ──
        listOf(leftHand, rightHand, leftFoot, rightFoot).forEach { point ->
            drawCircle(gold, radius = 6f, center = point)
        }
    }
}