package it.uninsubria.progetto

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView




import kotlinx.android.synthetic.main.row.*
import kotlinx.android.synthetic.main.row.view.*



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

            NewView.setClickable(true)
            NewView.setFocusable(true)



            val BookChapter = NewView.findViewById<TextView>(R.id.textViewChapter)
            val BookSubchapter = NewView.findViewById<TextView>(R.id.textViewSubchapter)
            val Bookdesc = NewView.findViewById<TextView>(R.id.desc)
            BookChapter.text = data[position].chapter
            BookSubchapter.text = data[position].subchapter
            Bookdesc.text = data[position].text

            NewView.button.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    val intent = Intent(context, visualize::class.java)
                    val desc = Bookdesc.text.toString()
                    intent.putExtra("main_activity_data", desc)
                    context.startActivity(intent)

                }
            })
        }
        return NewView
    }
}

