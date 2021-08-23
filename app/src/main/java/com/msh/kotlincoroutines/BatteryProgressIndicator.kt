package com.msh.kotlincoroutines

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.google.android.material.internal.ThemeEnforcement
import com.msh.kotlincoroutines.databinding.BatterProgressIndicatorBinding

/**
 * @author : 马世豪
 * time : 2021/5/28 11
 * email : ma_shihao@yeah.net
 * des : 电池 进度条
 */
public class BatteryProgressIndicator//radius  color
    (context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    private var binding: BatterProgressIndicatorBinding? = null


    var progress = 0
        set(value) {
            field = if (field > 100) {
                100
            } else {
                value
            }
            initProgress()
        }
    var indicatorColor:Int = Color.parseColor("#5BC190")
        set(value) {
            field = value
            initIndicatorColor()
        }

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.BatteryProgressIndicator)
        val trackColor = attributes.getColor(
            R.styleable.BatteryProgressIndicator_trackColor,
            Color.parseColor("#08FFFFFF")
        )
        indicatorColor =
            attributes.getColor(
                R.styleable.BatteryProgressIndicator_indicatorColor,
                Color.parseColor("#5BC190")
            )
        progress = attributes.getInt(R.styleable.BatteryProgressIndicator_progress, 0)
        val trackCornerRadius =
            attributes.getDimension(R.styleable.BatteryProgressIndicator_trackCornerRadius, 8f)
        binding = BatterProgressIndicatorBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
        binding?.apply {
            //radius  color
            this.background.radius = trackCornerRadius
            this.indicator.radius = trackCornerRadius
            this.background.backgroundTintList = ColorStateList.valueOf(trackColor)
            this.indicator.backgroundTintList = ColorStateList.valueOf(indicatorColor)
        }
        initProgress()
        attributes.recycle()
    }

    private fun initProgress() {
        binding?.apply {
            ConstraintSet().let {
                it.clone(parent)
                it.constrainPercentWidth(R.id.indicator, progress / 100f)
                it.applyTo(parent)
            }
        }
    }

    private fun initIndicatorColor() {
        binding?.apply {
            this.indicator.backgroundTintList = ColorStateList.valueOf(indicatorColor)
        }
    }

}