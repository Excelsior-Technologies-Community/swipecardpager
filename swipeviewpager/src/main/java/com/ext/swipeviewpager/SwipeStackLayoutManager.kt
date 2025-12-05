package com.ext.swipeviewpager

import androidx.recyclerview.widget.RecyclerView

class SwipeStackLayoutManager(
    private val topVisibleCards: Int = 3,
    private val cardOffset: Float = 30f,
    private val scaleOffset: Float = 0.05f,
    private val tiltAngle: Float = 0f,
    private val tiltDirection: TiltDirection = TiltDirection.LEFT,
    private val pivotPosition: PivotPosition = PivotPosition.BOTTOM
) : RecyclerView.LayoutManager() {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)

        val count = itemCount.coerceAtMost(topVisibleCards)
        for (i in 0 until count) {
            val view = recycler.getViewForPosition(i)
            addView(view)
            measureChildWithMargins(view, 0, 0)
            layoutDecorated(view, 0, 0, width, view.measuredHeight)

            // Set pivot point
            view.pivotX = (view.width / 2).toFloat()
            view.pivotY = when (pivotPosition) {
                PivotPosition.TOP -> 0f
                PivotPosition.CENTER -> (view.height / 2).toFloat()
                PivotPosition.BOTTOM -> view.height.toFloat()
            }

            val scale = 1 - scaleOffset * i
            val transY = cardOffset * i
            val rotation = if (tiltDirection == TiltDirection.LEFT) -tiltAngle * i else tiltAngle * i

            view.scaleX = scale
            view.scaleY = scale
            view.translationY = transY
            view.rotation = rotation
        }
    }
}
