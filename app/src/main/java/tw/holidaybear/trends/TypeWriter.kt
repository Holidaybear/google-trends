package tw.holidaybear.trends

import android.content.Context
import android.os.Handler
import androidx.appcompat.widget.AppCompatTextView
import android.util.AttributeSet

class TypeWriter : AppCompatTextView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private var mText: CharSequence? = null
    private var mIndex: Int = 0
    private var mDelay: Long = 150 // Default 150ms delay
    private var mListener: TypeWriterListener? = null

    private val mHandler = Handler()
    private val mCharacterAdder = object : Runnable {
        override fun run() {
            text = mText!!.subSequence(0, mIndex++)
            if (mIndex <= mText!!.length) {
                mHandler.postDelayed(this, mDelay)
            } else if (mListener != null) {
                mListener!!.onFinish()
            }
        }
    }

    fun animateText(text: CharSequence) {
        mText = text
        mIndex = 0

        setText("")
        mHandler.removeCallbacks(mCharacterAdder)
        mHandler.postDelayed(mCharacterAdder, mDelay)
    }

    fun setCharacterDelay(millis: Long) {
        mDelay = millis
    }

    fun setTypeWriterListener(listener: TypeWriterListener) {
        mListener = listener
    }

    interface TypeWriterListener {
        fun onFinish()
    }
}