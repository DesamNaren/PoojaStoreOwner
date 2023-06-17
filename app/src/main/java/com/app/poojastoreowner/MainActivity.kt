package com.app.poojastoreowner

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), UnitInterface {

    private lateinit var rootLayout: RelativeLayout
    private lateinit var unitsRecyclerView: RecyclerView
    private lateinit var title: LinearLayout
    private lateinit var btnSubmit: ImageView
    private val unitList = mutableListOf<UnitItem>()
    private var unitIdCount = 0
    private lateinit var unitAdapter: UnitAdapter

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rootLayout = findViewById(R.id.rootLayout)
        unitsRecyclerView = findViewById(R.id.unitsRecyclerView)
        title = findViewById(R.id.title)
        btnSubmit = findViewById(R.id.btnSubmit)

        val openBottomSheet = findViewById<MovableFloatingActionButton>(R.id.addRecord)

        openBottomSheet.setOnClickListener {
            val bottomSheet = RecordBottomSheetDialog(this@MainActivity)
            bottomSheet.show(supportFragmentManager, "ModalBottomSheet")
        }

        setUnitAdapter()

        btnSubmit.setOnClickListener {
            val isRecordExist = checkForMinimumRecords()
            if (isRecordExist) {
                // submit record
            } else {
                //add at-least one record
            }
        }

    }


    private fun checkForMinimumRecords(): Boolean {
        if (unitList.size > 0) return true
        return false
    }


    private fun setUnitAdapter() {
        unitAdapter = UnitAdapter(unitList, this)
        unitsRecyclerView.layoutManager = LinearLayoutManager(this)
        unitsRecyclerView.adapter = unitAdapter
        unitsRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

    }

    override fun removeUnit(unitItem: UnitItem, position: Int) {
        unitList.remove(unitItem)
        unitAdapter.notifyItemRemoved(position)
    }

    override fun updateUnit(unitItem: UnitItem, position: Int) {
        val metricsPredicate = unitList.filter {
            it.unitQty?.toInt() == unitItem.unitQty?.toInt()
        }
        if (metricsPredicate.isNotEmpty()) {
            unitList.remove(metricsPredicate[0])
        }
        unitList.add(unitItem)
        sortByUnitQty()
        unitAdapter.notifyDataSetChanged()
    }


    private fun sortByUnitQty() {
        unitList.sortBy { it.unitQty?.toInt() }
    }
}

interface UnitInterface {
    fun removeUnit(unitItem: UnitItem, position: Int)
    fun updateUnit(unitItem: UnitItem, position: Int = -1)
}