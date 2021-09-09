package it.uninsubria.progetto

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row.*

class MainActivity : AppCompatActivity() {


    private val TAG = "MainActivity"
    private var mUserReference: DatabaseReference? =
        FirebaseDatabase.getInstance().getReference("users")
    private val mChapters: MutableList<Chapters> = ArrayList()
    private val nAdapter: MyAdapter = MyAdapter(this, mChapters)
    private var mUsersChildListener: ChildEventListener = getUsersChildEventListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toyUsers = arrayOf(
            "Virat Kohli", "Rohit Sharma", "Steve Smith",
            "Kane Williamson", "Ross Taylor", "Mario Rossi",
            "Giuseppe verdi", "Pippo Baudo",
            "Virat Kohli", "Rohit Sharma", "Steve Smith",
            "Kane Williamson", "Ross Taylor", "Mario Rossi",
            "Giuseppe Verdi", "Pippo Baudo"
        )
        var i = 1
        toyUsers.forEach {
            val pos = it.split(" ")
            val u = Chapters(pos[0], pos[1], i++)
            val userId = u.toString()
            mUserReference!!.child(userId).setValue(u)
        }
// pass data to the Adapter
        list_view.adapter = nAdapter



    }

    override fun onStart() {
        super.onStart()
        if (mUsersChildListener == null)
            mUsersChildListener = getUsersChildEventListener()
        mUserReference!!.addChildEventListener(mUsersChildListener)
    }

    private fun getUsersChildEventListener(): ChildEventListener {
        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildAdded:" + snapshot.key!!)
                val newUser = snapshot.getValue(Chapters::class.java)
                mChapters.add(newUser!!)
                nAdapter.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildChanged:" + snapshot.key!!)
                val newUser = snapshot.getValue(Chapters::class.java)
                val userKey = snapshot.key
                mChapters.find { e -> e.toString().equals(userKey) }?.set(newUser!!)
                nAdapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + snapshot.key!!)
                val newUser = snapshot.getValue(Chapters::class.java)
                val userKey = snapshot.key
                var u = mChapters.find { e -> e.toString().equals(userKey) }
                mChapters.remove(u)
                nAdapter.notifyDataSetChanged()
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildMoved:" + snapshot.key!!)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onChildCancelled:" + error.toException())
            }

        }
        return childEventListener
    }
    fun share(v : View) {
        val message = textViewSubchapter.text.toString()
        // Creating intent with action send
        val intent = Intent(Intent.ACTION_SEND)

        // Setting Intent type
        intent.type = "text"

        // Setting whatsapp package name
        intent.setPackage("com.whatsapp")

        // Give your message here
        intent.putExtra(Intent.EXTRA_TEXT, message)

        // Checking whether whatsapp is installed or not
        if (intent.resolveActivity(packageManager) == null) {
            Toast.makeText(this, "Please install whatsapp first.", Toast.LENGTH_SHORT).show()
            return
        }

        // Starting Whatsapp
        startActivity(intent)
    }





    override fun onStop() {
        super.onStop()
        if (mUsersChildListener != null)
            mUserReference!!.removeEventListener(mUsersChildListener)
    }


    class MyAdapter(private val context: Context, val data: MutableList<Chapters>) : BaseAdapter() {
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
                val personName = NewView.findViewById<TextView>(R.id.textViewChapter)
                val personSurname = NewView.findViewById<TextView>(R.id.textViewSubchapter)

                personName.text = data[position].name
                personSurname.text = data[position].surname

            }
            return NewView
        }
    }




}

