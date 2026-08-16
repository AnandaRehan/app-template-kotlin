package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

enum class AppColorPalette(val label: String, val primaryColor: Color) {
    POLISH("Professional Polish", PolishPrimary),
    INDIGO("Indigo Blue", IndigoPrimary),
    EMERALD("Emerald Green", EmeraldPrimary),
    SUNSET("Sunset Orange", SunsetPrimary),
    VIOLET("Deep Violet", VioletPrimary)
}

enum class ThemeMode(val label: String) {
    SYSTEM("System Default"),
    LIGHT("Light"),
    DARK("Dark")
}

private fun createLightColorScheme(palette: AppColorPalette): androidx.compose.material3.ColorScheme {
    return when (palette) {
        AppColorPalette.POLISH -> lightColorScheme(
            primary = PolishPrimary,
            onPrimary = PolishOnPrimary,
            primaryContainer = PolishPrimaryContainer,
            onPrimaryContainer = PolishOnPrimaryContainer,
            secondary = PolishSecondary,
            onSecondary = PolishOnSecondary,
            secondaryContainer = PolishSecondaryContainer,
            onSecondaryContainer = PolishOnSecondaryContainer,
            tertiary = PolishTertiary,
            onTertiary = PolishOnTertiary,
            tertiaryContainer = PolishTertiaryContainer,
            onTertiaryContainer = PolishOnTertiaryContainer,
            background = PolishSurfaceLight,
            onBackground = PolishOnSurfaceLight,
            surface = PolishSurfaceLight,
            onSurface = PolishOnSurfaceLight,
            surfaceVariant = PolishSurfaceVariantLight,
            onSurfaceVariant = PolishOnSurfaceVariantLight,
            outline = PolishOutlineLight,
            outlineVariant = PolishOutlineVariantLight
        )
        AppColorPalette.INDIGO -> lightColorScheme(
            primary = IndigoPrimary,
            onPrimary = Color.White,
            primaryContainer = Color(0xFFEEF2FF),
            onPrimaryContainer = Color(0xFF312E81),
            secondary = Color(0xFF0D9488),
            onSecondary = Color.White,
            secondaryContainer = Color(0xFFCCFBF1),
            onSecondaryContainer = Color(0xFF115E59),
            tertiary = Color(0xFFD97706),
            background = Color(0xFFF8FAFC),
            surface = Color(0xFFF8FAFC),
            onSurface = Color(0xFF0F172A),
            surfaceVariant = Color(0xFFF1F5F9),
            onSurfaceVariant = Color(0xFF64748B),
            outline = Color(0xFFCBD5E1),
            outlineVariant = Color(0xFFE2E8F0)
        )
        AppColorPalette.EMERALD -> lightColorScheme(
            primary = EmeraldPrimary,
            onPrimary = Color.White,
            primaryContainer = Color(0xFFD1FAE5),
            onPrimaryContainer = Color(0xFF064E3B),
            secondary = Color(0xFF0284C7),
            onSecondary = Color.White,
            secondaryContainer = Color(0xFFE0F2FE),
            onSecondaryContainer = Color(0xFF0C4A6E),
            tertiary = Color(0xFF8B5CF6),
            background = PolishSurfaceLight,
            surface = PolishSurfaceLight,
            onSurface = PolishOnSurfaceLight,
            surfaceVariant = PolishSurfaceVariantLight,
            onSurfaceVariant = PolishOnSurfaceVariantLight,
            outline = PolishOutlineLight,
            outlineVariant = PolishOutlineVariantLight
        )
        AppColorPalette.SUNSET -> lightColorScheme(
            primary = SunsetPrimary,
            onPrimary = Color.White,
            primaryContainer = Color(0xFFFFEDD5),
            onPrimaryContainer = Color(0xFF7C2D12),
            secondary = Color(0xFFDB2777),
            onSecondary = Color.White,
            secondaryContainer = Color(0xFFFCE7F3),
            onSecondaryContainer = Color(0xFF831843),
            tertiary = Color(0xFF0284C7),
            background = PolishSurfaceLight,
            surface = PolishSurfaceLight,
            onSurface = PolishOnSurfaceLight,
            surfaceVariant = PolishSurfaceVariantLight,
            onSurfaceVariant = PolishOnSurfaceVariantLight,
            outline = PolishOutlineLight,
            outlineVariant = PolishOutlineVariantLight
        )
        AppColorPalette.VIOLET -> lightColorScheme(
            primary = VioletPrimary,
            onPrimary = Color.White,
            primaryContainer = Color(0xFFEDE9FE),
            onPrimaryContainer = Color(0xFF4C1D95),
            secondary = Color(0xFF059669),
            onSecondary = Color.White,
            secondaryContainer = Color(0xFFD1FAE5),
            onSecondaryContainer = Color(0xFF064E3B),
            tertiary = Color(0xFFD97706),
            background = PolishSurfaceLight,
            surface = PolishSurfaceLight,
            onSurface = PolishOnSurfaceLight,
            surfaceVariant = PolishSurfaceVariantLight,
            onSurfaceVariant = PolishOnSurfaceVariantLight,
            outline = PolishOutlineLight,
            outlineVariant = PolishOutlineVariantLight
        )
    }
}

private fun createDarkColorScheme(palette: AppColorPalette): androidx.compose.material3.ColorScheme {
    return when (palette) {
        AppColorPalette.POLISH -> darkColorScheme(
            primary = PolishHeroLavender,
            onPrimary = PolishDeepPurple,
            primaryContainer = Color(0xFF4F378B),
            onPrimaryContainer = PolishPrimaryContainer,
            secondary = Color(0xFFCCC2DC),
            onSecondary = Color(0xFF332D41),
            secondaryContainer = Color(0xFF4A4458),
            onSecondaryContainer = Color(0xFFE8DEF8),
            tertiary = Color(0xFFEFB8C8),
            onTertiary = Color(0xFF492532),
            background = PolishSurfaceDark,
            onBackground = PolishOnSurfaceDark,
            surface = PolishSurfaceDark,
            onSurface = PolishOnSurfaceDark,
            surfaceVariant = PolishSurfaceVariantDark,
            onSurfaceVariant = PolishOnSurfaceVariantDark,
            outline = PolishOutlineDark,
            outlineVariant = PolishOutlineVariantDark
        )
        AppColorPalette.INDIGO -> darkColorScheme(
            primary = Color(0xFF818CF8),
            onPrimary = Color(0xFF1E1B4B),
            primaryContainer = Color(0xFF3730A3),
            onPrimaryContainer = Color(0xFFE0E7FF),
            secondary = Color(0xFF2DD4BF),
            onSecondary = Color(0xFF042F2E),
            secondaryContainer = Color(0xFF134E4A),
            onSecondaryContainer = Color(0xFF99F6E4),
            tertiary = Color(0xFFFBBF24),
            background = Color(0xFF0F172A),
            surface = Color(0xFF0F172A),
            onSurface = Color(0xFFF8FAFC),
            surfaceVariant = Color(0xFF1E293B),
            onSurfaceVariant = Color(0xFF94A3B8),
            outline = Color(0xFF475569),
            outlineVariant = Color(0xFF334155)
        )
        AppColorPalette.EMERALD -> darkColorScheme(
            primary = Color(0xFF6EE7B7),
            onPrimary = Color(0xFF064E3B),
            primaryContainer = Color(0xFF065F46),
            onPrimaryContainer = Color(0xFFA7F3D0),
            secondary = Color(0xFF7DD3FC),
            onSecondary = Color(0xFF0C4A6E),
            background = PolishSurfaceDark,
            surface = PolishSurfaceDark,
            onSurface = PolishOnSurfaceDark,
            surfaceVariant = PolishSurfaceVariantDark,
            onSurfaceVariant = PolishOnSurfaceVariantDark,
            outline = PolishOutlineDark,
            outlineVariant = PolishOutlineVariantDark
        )
        AppColorPalette.SUNSET -> darkColorScheme(
            primary = Color(0xFFFDBA74),
            onPrimary = Color(0xFF7C2D12),
            primaryContainer = Color(0xFF9A3412),
            onPrimaryContainer = Color(0xFFFFEDD5),
            secondary = Color(0xFFF472B6),
            onSecondary = Color(0xFF831843),
            background = PolishSurfaceDark,
            surface = PolishSurfaceDark,
            onSurface = PolishOnSurfaceDark,
            surfaceVariant = PolishSurfaceVariantDark,
            onSurfaceVariant = PolishOnSurfaceVariantDark,
            outline = PolishOutlineDark,
            outlineVariant = PolishOutlineVariantDark
        )
        AppColorPalette.VIOLET -> darkColorScheme(
            primary = Color(0xFFC4B5FD),
            onPrimary = Color(0xFF4C1D95),
            primaryContainer = Color(0xFF5B21B6),
            onPrimaryContainer = Color(0xFFEDE9FE),
            secondary = Color(0xFF6EE7B7),
            onSecondary = Color(0xFF064E3B),
            background = PolishSurfaceDark,
            surface = PolishSurfaceDark,
            onSurface = PolishOnSurfaceDark,
            surfaceVariant = PolishSurfaceVariantDark,
            onSurfaceVariant = PolishOnSurfaceVariantDark,
            outline = PolishOutlineDark,
            outlineVariant = PolishOutlineVariantDark
        )
    }
}

@Composable
fun MyApplicationTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    palette: AppColorPalette = AppColorPalette.POLISH,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val context = LocalContext.current
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> createDarkColorScheme(palette)
        else -> createLightColorScheme(palette)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
