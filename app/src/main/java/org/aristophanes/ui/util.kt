package org.aristophanes.ui

import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable

@Composable
fun defaultTint() = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)