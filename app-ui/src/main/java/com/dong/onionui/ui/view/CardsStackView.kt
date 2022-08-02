package com.dong.onionui.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dong.onionui.databinding.ItemCardBinding
import com.dong.onionui.databinding.ViewCardStackBinding
import kotlin.math.abs

class CardsStackView  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var cardStackAdapter = CardStackAdapter()
    private var binding : ViewCardStackBinding
    private var inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    init{
        binding = ViewCardStackBinding.inflate(inflater,this,true)
        binding.vpCardStack.adapter = cardStackAdapter
    }

    fun setUpCardStack(items: ArrayList<CardModel>){
        cardStackAdapter.setItems(items)
        setPageTransformation(items)
        val number: Int = Int.MAX_VALUE / items.size / 2
        binding.vpCardStack.setCurrentItem(number * items.size, false)
    }

    private fun setPageTransformation(items: ArrayList<CardModel>) {
        val numberOfCards = items.size
        val translationFactor = when (Resources.getSystem().displayMetrics.heightPixels) {
            in 500..1000 -> {
                7f
            }
            in 1000..1500 -> {
                6f
            }
            in 1500..2000 -> {
                4.0f
            }
            in 2000..2500 -> {
                3.3f
            }
            in 2500..3000 -> {
                3.0f
            }
            else -> {
                3.5f
            }
        }
        if (numberOfCards >= 3) {
            binding.vpCardStack.offscreenPageLimit = 3
            binding.vpCardStack.setPageTransformer(
                SliderTransformerHorizontal(
                    3,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, translationFactor, context.resources.displayMetrics)
                )
            )
        } else {
            binding.vpCardStack.offscreenPageLimit = numberOfCards
            binding.vpCardStack.setPageTransformer(
                SliderTransformerHorizontal(
                    numberOfCards,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, translationFactor, context.resources.displayMetrics)
                )
            )
        }
    }
}

class SliderTransformerHorizontal(
    private val offscreenPageLimit: Int,
    private val defaultTranslationFactor: Float
) : ViewPager2.PageTransformer {

    companion object {
        private const val DEFAULT_TRANSLATION_X = .0f
        private const val DEFAULT_TRANSLATION_Y = .0f
        private const val DEFAULT_TRANSLATION_FACTOR_X = 1f

        private const val SCALE_FACTOR = .10f
        private const val DEFAULT_SCALE = 1f

        private const val ALPHA_FACTOR = .0f
        private const val DEFAULT_ALPHA = 1f

        private const val TAG = "SliderTransformer"
    }

    override fun transformPage(page: View, position: Float) {
        page.apply {
            ViewCompat.setElevation(page, -abs(position))

            val scaleFactor = -SCALE_FACTOR * position + DEFAULT_SCALE
            val alphaFactor = -ALPHA_FACTOR * position + DEFAULT_ALPHA

            when {
                position <= 0f -> {
                    translationX = DEFAULT_TRANSLATION_X
                    translationY = DEFAULT_TRANSLATION_Y
                    scaleX = DEFAULT_SCALE
                    scaleY = DEFAULT_SCALE
                    //Current visible
                    alpha = DEFAULT_ALPHA + position
                    Log.d(TAG, "Position => $position  TranslationX -> $translationX  TranslationY -> $translationY ScaleX -> $scaleX  ScaleY -> $scaleY Alpha -> $alpha"  )
                }
                position <= offscreenPageLimit - 1 -> {
                    scaleX = scaleFactor
                    scaleY = scaleFactor

                    // To Move Page From it's position to up side(plus side) on Y-Axis
                    translationY = (width / defaultTranslationFactor) * position

                    // To Move Page From it's position to left side(minus side) on X-Axis
                    translationX = -(width / DEFAULT_TRANSLATION_FACTOR_X) * position
                    alpha = alphaFactor
                }
                else -> {
                    translationX = DEFAULT_TRANSLATION_X
                    translationY = DEFAULT_TRANSLATION_Y
                    scaleX = DEFAULT_SCALE
                    scaleY = DEFAULT_SCALE
                    alpha = DEFAULT_ALPHA

                    Log.d(TAG, "Position else => $position  TranslationX -> $translationX  TranslationY -> $translationY ScaleX -> $scaleX  ScaleY -> $scaleY Alpha -> $alpha")
                }
            }
        }
    }
}


data class CardModel(
    val primaryTitle: String = "",
    val secondaryTitle: String = "",
    val image: Any? = null
)

class CardStackAdapter():BaseAdapter<CardModel>(){

    override fun createItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = CardStackViewHolder(
        ItemCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )


    override fun bindItemViewHolder(
        holder: RecyclerView.ViewHolder,
        item: CardModel,
        actualPosition: Int,
        position: Int
    ) {
        (holder as CardStackViewHolder).bind(item, position)
    }


    inner class CardStackViewHolder(private val itemCardBinding: ItemCardBinding) : RecyclerView.ViewHolder(itemCardBinding.root) {

        @SuppressLint("ClickableViewAccessibility")
        fun bind(cardModel: CardModel, position: Int) {
            itemCardBinding.tvPrimary.text = cardModel.primaryTitle
        }
    }
}

abstract class BaseAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = arrayListOf<T>()

    abstract fun createItemViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder

    abstract fun bindItemViewHolder(
        holder: RecyclerView.ViewHolder,
        item: T,
        actualPosition: Int,
        position: Int
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return createItemViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val positionInList = position % items.size
        bindItemViewHolder(holder, items[positionInList], position, positionInList)
    }

    fun getActualItemCount() = items.size

    override fun getItemCount(): Int {
        return if (items.size > 1) Int.MAX_VALUE else items.size
    }

    fun setItems(items: ArrayList<T>, clearPreviousElements: Boolean = false) {
        if (clearPreviousElements) {
            this.items.clear()
        }
        this.items.addAll(items)
        notifyItemRangeInserted(0,items.size)
    }

    fun getItems(): ArrayList<T> {
        return this.items
    }

    fun clear() {
        items.clear()
        notifyItemRangeRemoved(0,items.size)
    }
}