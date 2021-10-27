package io.test.app.presentation

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.test.app.R

class ArticleItemDecoration(resources: Resources) : RecyclerView.ItemDecoration() {

    private val sideOffset = resources.getDimensionPixelOffset(R.dimen.article_item_side_margin)
    private val topOffset = resources.getDimensionPixelOffset(R.dimen.article_item_top_margin)
    private val bottomOffset = resources.getDimensionPixelOffset(R.dimen.article_item_bottom_margin)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = sideOffset
        outRect.right = sideOffset
        outRect.top = topOffset
        outRect.bottom = bottomOffset
    }
}