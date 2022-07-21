package com.example.time_project.util

import android.content.Context
import android.os.Build
import android.text.*
import android.text.style.ForegroundColorSpan
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.TextView
import androidx.annotation.RequiresApi


object TextViewLinesUtil {
    /**
     * 获取文本行数
     * @param textView  控件
     * @param textViewWidth   控件的宽度  比如：全屏显示-就取手机的屏幕宽度即可。
     * @return
     */
    fun getTextViewLines(textView: TextView, textViewWidth: Int): Int {
        val width = textViewWidth - textView.compoundPaddingLeft - textView.compoundPaddingRight
        val staticLayout: StaticLayout
        staticLayout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getStaticLayout23(textView, width)
        } else {
            getStaticLayout(textView, width)
        }
        val lines = staticLayout.lineCount
        val maxLines = textView.maxLines
        return if (maxLines > lines) {
            lines
        } else maxLines
    }

    /**
     * sdk>=23
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun getStaticLayout23(textView: TextView, width: Int): StaticLayout {
        val builder = StaticLayout.Builder.obtain(
            textView.text,
            0, textView.text.length, textView.paint, width
        )
            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
            .setTextDirection(TextDirectionHeuristics.FIRSTSTRONG_LTR)
            .setLineSpacing(textView.lineSpacingExtra, textView.lineSpacingMultiplier)
            .setIncludePad(textView.includeFontPadding)
            .setBreakStrategy(textView.breakStrategy)
            .setHyphenationFrequency(textView.hyphenationFrequency)
            .setMaxLines(if (textView.maxLines == -1) Int.MAX_VALUE else textView.maxLines)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setJustificationMode(textView.justificationMode)
        }
        if (textView.ellipsize != null && textView.keyListener == null) {
            builder.setEllipsize(textView.ellipsize)
                .setEllipsizedWidth(width)
        }
        return builder.build()
    }

    /**
     * sdk<23
     */
    private fun getStaticLayout(textView: TextView, width: Int): StaticLayout {
        return StaticLayout(
            textView.text,
            0, textView.text.length,
            textView.paint, width, Layout.Alignment.ALIGN_NORMAL,
            textView.lineSpacingMultiplier,
            textView.lineSpacingExtra, textView.includeFontPadding, textView.ellipsize,
            width
        )
    }

}
