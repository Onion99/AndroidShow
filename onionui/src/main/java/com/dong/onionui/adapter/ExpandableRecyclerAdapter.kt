package com.dong.onionui.adapter

import android.content.Context
import android.os.Handler
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.dong.onionui.model.MnType

abstract class ExpandableRecyclerAdapter(private var context: Context) :
    RecyclerView.Adapter<ExpandableRecyclerAdapter.ViewHolder>() {
    private var allItems = mutableListOf<ListItem>()
    private var visibleItems = mutableListOf<ListItem>()
    private var indexList: MutableList<Int> = ArrayList()
    private var expandMap = SparseIntArray()
    private var mode = 0

    class ListItem(var ItemType: Int)

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getItemCount(): Int {
        return visibleItems.size
    }

    protected fun inflate(resourceID: Int, viewGroup: ViewGroup?): View {
        return LayoutInflater.from(context).inflate(resourceID, viewGroup, false)
    }

    open inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    open inner class HeaderViewHolder(view: View, var arrow: ImageView) : ViewHolder(view) {
        private fun handleClick() {
            if (toggleExpandedItems(layoutPosition, false)) {
                openArrow(arrow)
            } else {
                closeArrow(arrow)
            }
            // refresh item Id
            Handler().postDelayed({ notifyItemRangeChanged(0, allItems.size) }, 1500)
        }

        fun bind(position: Int) {
            arrow.rotation = if (isExpanded(position)) 180F else 0F
        }

        init {
            view.setOnClickListener { handleClick() }
        }
    }

    fun toggleExpandedItems(position: Int, notify: Boolean): Boolean {
        return if (isExpanded(position)) {
            collapseItems(position, notify)
            false
        } else {
            expandItems(position, notify)
            if (mode == MODE_ACCORDION) {
                collapseAllExcept(position)
            }
            true
        }
    }

    private fun expandItems(position: Int, notify: Boolean) {
        var count = 0
        val index = indexList[position]
        var insert = position
        for (i in index + 1 until allItems.size) {
            val type = allItems[i].ItemType
            if (i < allItems.size && type != HEADER && type != DIVIDER && type != NORMAL) {
                insert++
                count++
                visibleItems.add(insert, allItems[i])
                indexList.add(insert, i)
            } else {
                break
            }
        }
        notifyItemRangeInserted(position + 1, count)
        val allItemsPosition = indexList[position]
        expandMap.put(allItemsPosition, 1)
        if (notify) {
            notifyItemChanged(position)
        }
    }

    private fun collapseItems(position: Int, notify: Boolean) {
        var count = 0
        val index = indexList[position]
        for (i in index + 1 until allItems.size) {
            val type = allItems[i]!!.ItemType
            if (i < allItems.size && type != HEADER && type != DIVIDER && type != NORMAL) {
                count++
                visibleItems.removeAt(position + 1)
                indexList.removeAt(position + 1)
            } else {
                break
            }
        }
        notifyItemRangeRemoved(position + 1, count)
        val allItemsPosition = indexList[position]
        expandMap.delete(allItemsPosition)
        if (notify) {
            notifyItemChanged(position)
        }
    }

    inner class StaticViewHolder(view: View) : ViewHolder(view)
    inner class ItemViewHolder(view: View) : ViewHolder(view)

    protected fun isExpanded(position: Int): Boolean {
        val allItemsPosition = indexList[position]
        return expandMap[allItemsPosition, -1] >= 0
    }

    override fun getItemViewType(position: Int): Int {
        return visibleItems[position].ItemType
    }

    fun setItems(items: MutableList<ListItem>) {
        allItems = items
        val visibleItems: MutableList<ListItem> = ArrayList()
        expandMap.clear()
        indexList.clear()
        for (i in items.indices) {
            val type = allItems[i].ItemType
            if (type == HEADER || type == DIVIDER || type == NORMAL) {
                indexList.add(i)
                visibleItems.add(items[i])
            }
        }
        this.visibleItems = visibleItems
        notifyDataSetChanged()
    }

    protected fun notifyItemInserted(allItemsPosition: Int, visiblePosition: Int) {
        incrementIndexList(allItemsPosition, visiblePosition, 1)
        incrementExpandMapAfter(allItemsPosition, 1)
        if (visiblePosition >= 0) {
            notifyItemInserted(visiblePosition)
        }
    }

    protected fun removeItemAt(visiblePosition: Int) {
        val allItemsPosition = indexList[visiblePosition]
        allItems.removeAt(allItemsPosition)
        visibleItems.removeAt(visiblePosition)
        incrementIndexList(allItemsPosition, visiblePosition, -1)
        incrementExpandMapAfter(allItemsPosition, -1)
        notifyItemRemoved(visiblePosition)
    }

    private fun incrementExpandMapAfter(position: Int, direction: Int) {
        val newExpandMap = SparseIntArray()
        for (i in 0 until expandMap.size()) {
            val index = expandMap.keyAt(i)
            newExpandMap.put(if (index < position) index else index + direction, 1)
        }
        expandMap = newExpandMap
    }

    private fun incrementIndexList(allItemsPosition: Int, visiblePosition: Int, direction: Int) {
        val newIndexList: MutableList<Int> = ArrayList()
        for (i in indexList.indices) {
            if (i == visiblePosition) {
                if (direction > 0) {
                    newIndexList.add(allItemsPosition)
                }
            }
            val position = indexList[i]
            newIndexList.add(if (position < allItemsPosition) position else position + direction)
        }
        indexList = newIndexList
    }

    fun collapseAll() {
        collapseAllExcept(-1)
    }

    private fun collapseAllExcept(position: Int) {
        for (i in visibleItems.indices.reversed()) {
            val type = getItemViewType(i)
            if (i != position && (type == HEADER || type == DIVIDER || type == NORMAL)) {
                if (isExpanded(i)) {
                    collapseItems(i, true)
                }
            }
        }
    }

    fun expandAll() {
        for (i in visibleItems.indices.reversed()) {
            val type = getItemViewType(i)
            if (type == HEADER || type == DIVIDER || type == NORMAL) {
                if (!isExpanded(i)) {
                    expandItems(i, true)
                }
            }
        }
    }

    companion object {
        val NORMAL = MnType.NOR.value
        val HEADER = MnType.HEAD.value
        val SUB_HEADER = MnType.SUB.value
        val DIVIDER = MnType.DIV.value
        private const val ARROW_ROTATION_DURATION = 500
        const val MODE_NORMAL = 0
        const val MODE_ACCORDION = 1
        fun openArrow(view: View) {
            view.animate().setDuration(ARROW_ROTATION_DURATION.toLong()).rotation(180f)
        }

        fun closeArrow(view: View) {
            view.animate().setDuration(ARROW_ROTATION_DURATION.toLong()).rotation(0f)
        }
    }
}