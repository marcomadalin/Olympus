package com.marcomadalin.olympus.presentation.view

import androidx.recyclerview.widget.ItemTouchHelper


abstract class DragGesture : ItemTouchHelper.SimpleCallback (
    ItemTouchHelper.START or ItemTouchHelper.END or ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
}