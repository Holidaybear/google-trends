package tw.holidaybear.trends

import android.content.Context
import android.os.Handler
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import tw.holidaybear.trends.databinding.ViewTrendBinding

class TrendView : ConstraintLayout {

    private val TREND_ANIMATION = intArrayOf(R.anim.left_out, R.anim.right_out, R.anim.top_out, R.anim.bottom_out)
    private val TREND_COLOR = intArrayOf(R.color.valencia, R.color.selective_yellow, R.color.salem, R.color.cornflower_blue)

    private var currentColorIndex = -1
    private var currentDataIndex = 0
    private val trendHandler = Handler()

    private lateinit var binding: ViewTrendBinding
    private lateinit var trendList: List<String>
    private lateinit var trendAnimation: Animation

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        initUI()
    }

    fun playTrendList(trendList: List<String>) {
        this.trendList = trendList.shuffled()
        binding.trendCursor.visibility = View.VISIBLE
        binding.trendText.animateText(this.trendList[currentDataIndex])
    }

    private fun initUI() {
        val inflater = LayoutInflater.from(context)
        binding = ViewTrendBinding.inflate(inflater, this, true)
        binding.trendTextContainer.setBackgroundColor(ContextCompat.getColor(context, TREND_COLOR[getColorIndex()]))
        binding.trendText.setTypeWriterListener(typeWriterListener)
    }

    private fun getColorIndex(): Int {
        if (currentColorIndex == -1) {
            currentColorIndex = (Math.random() * 4).toInt()
        } else {
            currentColorIndex++
            if (currentColorIndex == TREND_COLOR.size) {
                currentColorIndex = 0
            }
        }
        return currentColorIndex
    }

    private fun getAnimationIndex(): Int {
        return (Math.random() * 4).toInt()
    }

    private fun getCurrentDataIndex(): Int {
        currentDataIndex++
        if (currentDataIndex == trendList.size) {
            currentDataIndex = 0
        }
        return currentDataIndex
    }

    private fun animateSlide() {
        binding.trendLayout.setBackgroundColor(ContextCompat.getColor(context, TREND_COLOR[getColorIndex()]))
        trendAnimation = AnimationUtils.loadAnimation(context, TREND_ANIMATION[getAnimationIndex()])
        trendAnimation.setAnimationListener(animateListener)
        binding.trendTextContainer.startAnimation(trendAnimation)
    }

    private val slideRunnable = Runnable {
        trendHandler.removeCallbacks(twinkleRunnable)
        animateSlide()
    }

    private val twinkleRunnable = object : Runnable {
        override fun run() {
            binding.trendCursor.visibility = if (binding.trendCursor.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            trendHandler.postDelayed(this, 500)
        }
    }

    private val typeWriterListener = object : TypeWriter.TypeWriterListener {
        override fun onFinish() {
            trendHandler.postDelayed(slideRunnable, 2000)
            trendHandler.postDelayed(twinkleRunnable, 200)
        }
    }

    private val animateListener = object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {}
        override fun onAnimationEnd(animation: Animation) {
            trendHandler.removeCallbacks(slideRunnable)
            binding.trendTextContainer.setBackgroundColor(ContextCompat.getColor(context, TREND_COLOR[currentColorIndex]))
            binding.trendCursor.visibility = View.VISIBLE
            binding.trendText.animateText(trendList[getCurrentDataIndex()])
        }
        override fun onAnimationRepeat(animation: Animation) {}
    }
}