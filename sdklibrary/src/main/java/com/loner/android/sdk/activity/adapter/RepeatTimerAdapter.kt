package com.loner.android.sdk.activity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.loner.android.sdk.R
import com.loner.android.sdk.activity.ActivityInterface.RepeatTimerListener
import com.loner.android.sdk.data.timerconfiguration.TimerConfiguration

class RepeatTimerAdapter: BaseAdapter {
    private var context: Context
    private var repeatList: IntArray
    private var repeatTimerListener: RepeatTimerListener

    constructor(context: Context, repeatList: IntArray, repeatTimerListener: RepeatTimerListener) {
        this.context = context
        this.repeatList = repeatList
        this.repeatTimerListener = repeatTimerListener
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder: ViewHolder
        val rowView:View
        if (convertView == null) {
            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            rowView = inflater.inflate(R.layout.repeat_list, parent, false)
            viewHolder.mRepeatText =rowView.findViewById(R.id.repeat_item_text) as TextView
            viewHolder.selectionImageView =rowView.findViewById(R.id.selectImage) as ImageView
            viewHolder.rowRelativeLayout = rowView.findViewById(R.id.layEnglish) as RelativeLayout
            rowView.tag = viewHolder

        } else {
            rowView = convertView
            viewHolder = convertView.tag as ViewHolder
        }
        if (context.getText(repeatList[position]).toString() == TimerConfiguration.getInstance().getListRepeatTimerType(context)) {
            viewHolder.selectionImageView?.visibility = View.VISIBLE
        } else {
            viewHolder.selectionImageView?.visibility = View.INVISIBLE
        }
        viewHolder.mRepeatText.text = context.getText(repeatList[position]).toString()
        viewHolder.rowRelativeLayout.setOnClickListener{
            repeatTimerListener.setRepeat(context.getText(repeatList[position]).toString())
        }
        return rowView
    }

    override fun getCount(): Int {
        return repeatList.size
    }

    override fun getItem(position: Int): Any {
        return repeatList[position]

    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }

    private class ViewHolder {
        lateinit var mRepeatText: TextView
        lateinit var rowRelativeLayout: RelativeLayout
        lateinit var selectionImageView: ImageView
    }
}