package id.mailstudio.core.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class TopBottomItemDecoration(
    private val topSize: Int,
    private val bottomSize: Int,
    private val spaceHeight: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = topSize
            }
            bottom =
                if (parent.getChildAdapterPosition(view) == parent.adapter?.itemCount?.minus(1))
                    bottomSize
                else
                    spaceHeight
        }
    }
}
