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
        if (convertView == null) {
            viewHolder = RepeatTimerAdapter.ViewHolder()
            val inflater = LayoutInflater.from(context)
            convertView = inflater.inflate(R.layout.repeat_list, parent, false)
            viewHolder.mRepeatText = convertView.findViewById(R.id.repeat_item_text) as TextView
            viewHolder.selectionImageView = convertView.findViewById(R.id.selectImage) as ImageView
            viewHolder.rowRelativeLayout = convertView.findViewById(R.id.layEnglish) as RelativeLayout
            convertView.setTag(viewHolder)

        } else {
            viewHolder = convertView.tag as ViewHolder
        }
        if (context.getText(repeatList[position]).toString() == TimerConfiguration.getInstance().getListRepeatTimerType()) {
            viewHolder.selectionImageView?.visibility = View.VISIBLE
        } else {
            viewHolder.selectionImageView?.visibility = View.INVISIBLE
        }
        viewHolder.mRepeatText.setText(context.getText(repeatList[position]).toString())
        viewHolder.rowRelativeLayout.setOnClickListener(View.OnClickListener { repeatTimerListener .setRepeat(context.getText(repeatList[position]).toString()) })

        return convertView
    }

    override fun getCount(): Int {
        return repeatList.size
    }

    override fun getItem(position: Int): Any {
        return  0

    }

    override fun getItemId(position: Int): Long {
       return  0
    }

    private class ViewHolder {
        internal var mRepeatText: TextView? = null
        internal var rowRelativeLayout: RelativeLayout? = null
        internal var selectionImageView: ImageView? = null
    }
}