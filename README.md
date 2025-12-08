**SwipeCardPager**

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9-blue?logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-green)](LICENSE)
[![API](https://img.shields.io/badge/API-24%2B-orange)](#)
---
**SwipeCardPager**
A **Swipe Card Pager** library for Android in Kotlin, **supporting horizontal & vertical swipe, customizable tilt direction, and stacked card animations**. Users can display any content (images, text, videos, or custom views) inside cards and make their UI interactive like popular dating apps.
---
## Preview

![Preview 1](assets/image1.jpg)

---


**Features**

Swipe cards horizontally (stacked/fanned) or vertically (simple swipe).

Supports customizable tilt direction (left or right).

Supports tilt from top, center, or bottom of the card stack.

Customizable tilt angle, number of visible cards, card offset, and scale offset.

Fully adapter-driven — you can display any layout inside the cards.

Smooth swipe animations with stacked card effect.

Easy to integrate with just one custom view.

---
**Screenshots**
---
**Installation**

Add Dependency
```
dependencies {
	        implementation("com.github.Excelsior-Technologies-Community:swipecardpager:v1.0.0")
	}
```
---

**Usage**

**Step 1: Add SwipeCardView to XML**
```
<com.ext.swipeviewpager.SwipeCardView
    android:id="@+id/swipeView"
    android:layout_width="match_parent"
    android:layout_height="400dp"/>
```
**Step 2: Create a Card Adapter**
```
class CardAdapter(private val items: List<CardItem>) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    class CardViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.imageView)
        val title = view.findViewById<TextView>(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = items[position]
        holder.image.setImageResource(item.imageRes)
        holder.title.text = item.title
    }

    override fun getItemCount() = items.size
}
```
**Step 3: Customize SwipeCardView in Activity/Fragment**
```
val swipeView = findViewById<SwipeCardView>(R.id.swipeView)

// Set adapter
val adapter = CardAdapter(listOfItems)
swipeView.setAdapter(adapter)

// Horizontal swipe with tilt
swipeView.setSwipeOrientation(SwipeOrientation.HORIZONTAL, tiltAngle = 15f)

// Optional: Tilt direction
swipeView.setTiltDirection(TiltDirection.LEFT)

// Optional: Pivot position (TOP, CENTER, BOTTOM)
swipeView.setPivotPosition(PivotPosition.TOP)
```
**Step 4: Vertical swipe**
```
swipeView.setSwipeOrientation(SwipeOrientation.VERTICAL)
```
---

| Property         | Description                                         | Default |
|-----------------|-----------------------------------------------------|---------|
| topVisibleCards  | Number of visible cards in horizontal stack        | 3       |
| cardOffset       | Vertical offset between stacked cards              | 30f     |
| scaleOffset      | Scale reduction between stacked cards              | 0.05f   |
| tiltAngle        | Angle of tilt/fan for stacked cards                | 10f     |
| tiltDirection    | Direction of tilt (`LEFT` or `RIGHT`)              | LEFT    |
| pivotPosition    | Pivot point for tilt (`TOP`, `CENTER`, `BOTTOM`)   | BOTTOM  |

---
**XML Layout for Cards**
```
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="300dp"
    android:layout_height="400dp"
    android:background="@drawable/card_bg">

    <ImageView
        android:id="@+id/imageView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:scaleType="centerCrop"/>

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|center"
        android:textColor="@android:color/white"
        android:textSize="18sp"
        android:padding="16dp"/>
</FrameLayout>
```
**You can replace this layout with any custom content.**
---

