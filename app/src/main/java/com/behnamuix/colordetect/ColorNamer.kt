package com.behnamuix.colordetect // مطمئن شو که پکیج درست باشه

class ColorMapper {

    // یک Map از کدهای هگز (رشته‌ای) به اسم رنگ‌ها.
    // این لیست رو می‌تونی هر طور که نیاز داری، گسترش بدی.
    private val colorMap = mapOf(
        "#F44336" to "قرمز", // Red
        "#E91E63" to "صورتی", // Pink
        "#9C27B0" to "بنفش", // Purple
        "#673AB7" to "بنفش تیره", // Deep Purple
        "#3F51B5" to "نیلی", // Indigo
        "#2196F3" to "آبی", // Blue
        "#03A9F4" to "آبی روشن", // Light Blue
        "#00BCD4" to "آبی فیروزه‌ای", // Cyan
        "#009688" to "تیل (سبز آبی)", // Teal
        "#4CAF50" to "سبز", // Green
        "#8BC34A" to "سبز روشن", // Light Green
        "#CDDC39" to "لیموئی", // Lime
        "#FFEB3B" to "زرد", // Yellow
        "#FFC107" to "کهربایی", // Amber
        "#FF9800" to "نارنجی", // Orange
        "#FF5722" to "نارنجی تیره", // Deep Orange
        "#795548" to "قهوه‌ای", // Brown
        "#9E9E9E" to "خاکستری", // Grey
        "#607D8B" to "آبی خاکستری", // Blue Grey
        "#000000" to "سیاه", // Black
        "#FFFFFF" to "سفید" // White
        // می‌تونی اینجا رنگ‌های بیشتری رو با کدهای هگز دقیقشون اضافه کنی
        // مثال:
        // "#ADD8E6" to "آبی آسمانی روشن",
        // "#FF6347" to "گوجه‌ای",
        // "#F08080" to "مرجانی روشن"
    )

    /**
     * کد هگز رنگ رو می‌گیره و اسم رنگ متناظر رو برمی‌گردونه.
     * اگر کد هگز در Map پیدا نشد، "نامشخص" رو برمی‌گردونه.
     */
    fun getColorName(hexCode: String): String {
        // مطمئن می‌شیم که کد هگز با # شروع بشه و به حروف بزرگ تبدیل بشه
        val standardizedHex = if (hexCode.startsWith("#")) hexCode.uppercase() else "#${hexCode.uppercase()}"
        return colorMap[standardizedHex] ?: "رنگ نامشخص"
    }
}