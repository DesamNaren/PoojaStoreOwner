package com.app.poojastoreowner

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class RecordBottomSheetDialog(
    private var unitInterface: UnitInterface
) : BottomSheetDialogFragment() {

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
            val isValid = checkRecordValidations(metrics.text.toString(), price.text.toString())
            if (isValid) {
                //show alert and return
                checkForRecordExist(
                    UnitItem(
                        1,
                        metrics.text.toString().trim(),
                        price.text.toString(),
                        true
                    )
                )
                dismiss()
            }
        }

        return view
    }

    private fun checkRecordValidations(metrics: String?, price: String?): Boolean {
        if (metrics == null || TextUtils.isEmpty(metrics) || metrics.toInt() == 0) return false
        if (price == null || TextUtils.isEmpty(metrics) || price.toDouble() == 0.0) return false
        return true
    }

    private fun checkForRecordExist(unitItem: UnitItem) {
        unitInterface.updateUnit(unitItem)
    }
}

