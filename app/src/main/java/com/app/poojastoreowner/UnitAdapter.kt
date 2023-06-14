package com.app.poojastoreowner

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class UnitAdapter(
    private val unitItems: MutableList<UnitItem>,
    private var unitInterface: UnitInterface
) :
    RecyclerView.Adapter<UnitAdapter.UnitViewHolder>() {

    class UnitViewHolder(view: View, unitInterface: UnitInterface) : ViewHolder(view) {
        var unitQtyEditText: MaterialTextView
        var unitPriceEditText: MaterialTextView
        var unitUpdateButton: ImageView

        init {
            unitQtyEditText = view.findViewById(R.id.viewMetrics)
            unitPriceEditText = view.findViewById(R.id.viewPrice)
            unitUpdateButton = view.findViewById(R.id.deleteRecord)

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnitViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_record_layout, parent, false)
        return UnitViewHolder(view, unitInterface)
    }

    override fun onBindViewHolder(holder: UnitViewHolder, position: Int) {
        val unitItem = unitItems[position]
        holder.unitQtyEditText.setText(unitItem.unitQty)
        holder.unitPriceEditText.setText(unitItem.unitValue)

        holder.unitUpdateButton.setOnClickListener {
           unitInterface.removeUnit(unitItem)
        }
    }

    override fun getItemCount(): Int {
        return unitItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
