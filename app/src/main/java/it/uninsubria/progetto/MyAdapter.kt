package it.uninsubria.progetto

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MyAdapter(private val context: Context, val data: MutableList<Book>) : BaseAdapter() {
    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var NewView = convertView
        if (convertView == null)
            NewView = LayoutInflater.from(context).inflate(R.layout.row, parent, false)
        if (NewView != null) {

            val BookChapter = NewView.findViewById<TextView>(R.id.textViewChapter)
            val BookSubchapter = NewView.findViewById<TextView>(R.id.textViewSubchapter)
            BookChapter.text = data[position].chapter
            BookSubchapter.text = data[position].subchapter
        }
        return NewView
    }
}