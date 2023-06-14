package com.app.poojastoreowner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class RecordBottomSheetDialog(var unitList: MutableList<UnitItem>, var unitInterface: UnitInterface) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_record_layout, container, false)

        val algoButton = view.findViewById<FloatingActionButton>(R.id.addUnit)
        val metrics = view.findViewById<TextInputEditText>(R.id.metricsEdittext)
        val price = view.findViewById<TextInputEditText>(R.id.priceEditText)

        algoButton.setOnClickListener {
            unitInterface.addUnit(UnitItem(1, metrics.text.toString(), price.text.toString(), true))
            dismiss()
        }

        return view
    }
}

