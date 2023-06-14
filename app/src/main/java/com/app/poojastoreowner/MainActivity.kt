package com.app.poojastoreowner

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity(), UnitInterface {

    private lateinit var rootLayout : RelativeLayout
    private lateinit var unitsRecyclerView : RecyclerView
    private lateinit var title : LinearLayout
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

        val openBottomSheet = findViewById<MovableFloatingActionButton>(R.id.addRecord)

        openBottomSheet.setOnClickListener {
            unitList.add(UnitItem(1, "1", "1", true))
            unitList.add(UnitItem(1, "1", "1", true))
            unitList.add(UnitItem(1, "1", "1", true))
            unitList.add(UnitItem(1, "1", "1", true))
            unitList.add(UnitItem(1, "1", "1", true))
            unitList.add(UnitItem(1, "1", "1", true))
            unitList.add(UnitItem(1, "1", "1", true))
            val bottomSheet = RecordBottomSheetDialog(unitList, this@MainActivity)
            bottomSheet.show(supportFragmentManager, "ModalBottomSheet")
        }

//        val unitItem = UnitItem(unitIdCount++, "", "", false)
//        unitList.add(unitItem)
        unitAdapter = UnitAdapter(unitList, this)
        setUnitAdapter()

//        addRecord()

    }


    private fun setUnitAdapter(){
        unitsRecyclerView.layoutManager = LinearLayoutManager(this)
        unitsRecyclerView.adapter = unitAdapter
        unitsRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

    }

    override fun addUnit(unitItem: UnitItem) {
        unitList.add(unitItem)
        unitAdapter.notifyDataSetChanged()

        if(unitList.size>0){
            title.visibility = View.VISIBLE
        }else{
            title.visibility = View.GONE

        }
    }

    override fun removeUnit(unitItem: UnitItem) {
        unitList.remove(unitItem)
        unitAdapter.notifyDataSetChanged()

        if(unitList.size>0){
            title.visibility = View.VISIBLE
        }else{
            title.visibility = View.GONE

        }
    }
}

interface UnitInterface{
    fun addUnit(unitItem: UnitItem)
    fun removeUnit(unitItem: UnitItem)
}