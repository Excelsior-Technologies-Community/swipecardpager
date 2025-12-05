package com.ext.swipecardpager

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ext.swipeviewpager.PivotPosition
import com.ext.swipeviewpager.SwipeCardView
import com.ext.swipeviewpager.SwipeOrientation
import com.ext.swipeviewpager.TiltDirection

class MainActivity : AppCompatActivity() {

    private lateinit var swipeView: SwipeCardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()

        // Window insets padding (optional)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Now find the view
        swipeView = findViewById(R.id.swipeView)

        // Prepare data and adapter
        val list = mutableListOf("Jay","Nisha","Rahul","Amit","Riya")
        val adapter = CardAdapter(list)

        // Set adapter and enable swipe
        swipeView.setSwipeOrientation(SwipeOrientation.HORIZONTAL)
        swipeView.setTiltDirection(TiltDirection.LEFT)
        swipeView.setPivotPosition(PivotPosition.TOP)
        swipeView.setAdapter(adapter)

    }
}
