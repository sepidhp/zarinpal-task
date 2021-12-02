package com.zarinpal.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class LinearDividerDecoration constructor(
    private val orientation: Int,
    private val spacing: Int,
    private val includeEdge: Boolean
) : ItemDecoration() {

    companion object {
        const val VERTICAL = 1
        const val HORIZONTAL = 2
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        if (orientation == VERTICAL) {
            if (includeEdge) {
                outRect.left = spacing
                outRect.right = spacing
                if (parent.getChildAdapterPosition(view) == 0) outRect.top =
                    spacing else outRect.top = spacing / 2
                if (parent.getChildAdapterPosition(view) == parent.adapter!!.itemCount - 1) outRect.bottom =
                    spacing else outRect.bottom = spacing / 2
            } else {
                outRect.left = 0
                outRect.right = 0
                if (parent.getChildAdapterPosition(view) == 0) outRect.top = 0 else outRect.top =
                    spacing / 2
                if (parent.getChildAdapterPosition(view) == parent.adapter!!.itemCount - 1) outRect.bottom =
                    0 else outRect.bottom = spacing / 2
            }
        } else {
            if (includeEdge) {
                outRect.top = spacing
                outRect.bottom = spacing
                if (parent.getChildAdapterPosition(view) == 0) outRect.left =
                    spacing else outRect.left = spacing / 2
                if (parent.getChildAdapterPosition(view) == parent.adapter!!.itemCount - 1) outRect.right =
                    spacing else outRect.right = spacing / 2
            } else {
                outRect.top = 0
                outRect.bottom = 0
                if (parent.getChildAdapterPosition(view) == 0) outRect.left =
                    0 else outRect.left = spacing / 2
                if (parent.getChildAdapterPosition(view) == parent.adapter!!.itemCount - 1) outRect.right =
                    0 else outRect.right = spacing / 2
            }
        }
    }
}