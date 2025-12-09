package com.ext.swipeviewpager

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

enum class SwipeOrientation { VERTICAL, HORIZONTAL }

class SwipeCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val recyclerView: RecyclerView
    private var orientation: SwipeOrientation = SwipeOrientation.VERTICAL
    private var tiltAngle: Float = 10f
    private var topVisibleCards = 3
    private var cardOffset = 30f
    private var scaleOffset = 0.05f
    private var tiltDirection: TiltDirection = TiltDirection.LEFT  // default tilt
    private var pivotPosition: PivotPosition = PivotPosition.BOTTOM // default current behavior
    private var swipeListener: OnItemSwipedListener? = null
    init {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.view_swipe_card, this, true)
        recyclerView = view.findViewById(R.id.recycler)
    }

    // Set adapter
    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        recyclerView.adapter = adapter
        setupRecyclerView()
    }

    // Set vertical or horizontal swipe with tilt angle
    fun setSwipeOrientation(orientation: SwipeOrientation, tiltAngle: Float = 0f) {
        this.orientation = orientation
        this.tiltAngle = tiltAngle
        setupRecyclerView()
    }

    // Set tilt direction (LEFT or RIGHT)
    fun setTiltDirection(direction: TiltDirection) {
        tiltDirection = direction
        recyclerView.layoutManager?.requestLayout()
    }

    fun setPivotPosition(position: PivotPosition) {
        pivotPosition = position
        recyclerView.layoutManager?.requestLayout()
    }

    private fun setupRecyclerView() {
        recyclerView.itemAnimator = null
        if (orientation == SwipeOrientation.VERTICAL) {
            recyclerView.layoutManager = LinearLayoutManager(context)
            enableVerticalSwipe()
        } else {
            recyclerView.layoutManager =
                SwipeStackLayoutManager(topVisibleCards, cardOffset, scaleOffset, tiltAngle, tiltDirection,pivotPosition)
            enableHorizontalSwipe()
        }
    }

    private fun enableVerticalSwipe() {
        val helper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder) = false
            override fun onSwiped(vh: RecyclerView.ViewHolder, dir: Int) {
                vh.itemView.clearAnimation()
                vh.itemView.translationX = 0f
                vh.itemView.translationY = 0f
                vh.itemView.alpha = 1f

                swipeListener?.onItemSwiped(vh.adapterPosition, dir)

                recyclerView.post {
                    recyclerView.invalidate()
                    recyclerView.requestLayout()
                }
            }
        })
        helper.attachToRecyclerView(recyclerView)
    }

    private fun enableHorizontalSwipe() {
        val helper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder) = false

            override fun onChildDraw(
                c: android.graphics.Canvas,
                rv: RecyclerView,
                vh: RecyclerView.ViewHolder,
                dX: Float, dY: Float,
                actionState: Int, isActive: Boolean
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    for (i in 1 until rv.childCount) {
                        val child = rv.getChildAt(i)
                        val scale = 1 - scaleOffset * i + scaleOffset * (Math.abs(dX) / rv.width)
                        val transY = cardOffset * i - cardOffset * (Math.abs(dX) / rv.width)
                        val rotation = if (tiltDirection == TiltDirection.LEFT) -tiltAngle * i else tiltAngle * i
                        child.scaleX = scale
                        child.scaleY = scale
                        child.translationY = transY
                        child.rotation = rotation
                    }
                }
                super.onChildDraw(c, rv, vh, dX, dY, actionState, isActive)
            }

            override fun onSwiped(vh: RecyclerView.ViewHolder, dir: Int) {
                vh.itemView.clearAnimation()
                vh.itemView.translationX = 0f
                vh.itemView.translationY = 0f
                vh.itemView.alpha = 1f

                swipeListener?.onItemSwiped(vh.adapterPosition, dir)

                recyclerView.post {
                    recyclerView.invalidate()
                    recyclerView.requestLayout()
                }
            }

        })
        helper.attachToRecyclerView(recyclerView)
    }
    fun setOnItemSwipedListener(listener: OnItemSwipedListener) {
        swipeListener = listener
    }

}
