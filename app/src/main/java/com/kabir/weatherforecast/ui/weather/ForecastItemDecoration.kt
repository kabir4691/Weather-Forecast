package com.kabir.weatherforecast.ui.weather

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

internal class ForecastItemDecoration constructor(context: Context) :
    RecyclerView.ItemDecoration() {

    private var spacing: Int

    init {
        val resource = context.resources
        val displayMetrics = resource.displayMetrics
        spacing = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12f, displayMetrics).toInt()
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val adapterPosition = parent.getChildAdapterPosition(view)
        if (adapterPosition == RecyclerView.NO_POSITION) {
            return
        }
        if (adapterPosition != 0) {
            outRect.left = spacing
        }
    }
}