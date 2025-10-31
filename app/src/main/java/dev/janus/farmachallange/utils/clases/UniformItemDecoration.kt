package dev.janus.farmachallange.utils.clases

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class UniformItemDecoration(private val padding: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) {
            return
        }
        outRect.left = padding
        outRect.right = padding
        outRect.bottom = padding
        outRect.top = padding
    }
}