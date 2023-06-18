package it.uninsubria.progetto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.row.textViewSubchapter

class MainActivity : AppCompatActivity() {

    val SECOND_ACTIVITY = 1
    private val TAG = "main_activity_data"
    var database = FirebaseDatabase.getInstance()
    var mUserReference = database.getReference("users")

    private val mBook: MutableList<Book> = ArrayList()
    private val nAdapter: MyAdapter = MyAdapter(this, mBook)
    private var mUsersChildListener: ChildEventListener = getUsersChildEventListener()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mUserReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach { userSnapshot ->
                    val book = userSnapshot.getValue(Book::class.java)
                    var i = 0
                    val user = Book(book?.name ?: "", book?.chapter ?: "", book?.subchapter ?: "", book?.text ?: "", i++)
                    val userId = user.toString()
                    mUserReference.child(userId).setValue(user)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })
        /*


         val toyUsers = arrayOf(
            "BASIC_RULES Step-By-Step_Characters Choose_a_Race Text",
            "BASIC_RULES Step-By-Step_Characters Choose_a_Class Text1",
            "BASIC_RULES Step-By-Step_Characters Determine_Ability_Scores Text2",
            )


        var i = 1
        mUserReference.forEach {
            val pos = it.split(" ")
            val u = Book(pos[0], pos[1], pos[2], pos[3], i++)
            val userId = u.toString()
       //     description = u.toDesc().toString()
            mUserReference!!.child(userId).setValue(u)
        }
        */

// pass data to the Adapter
        val list_view = findViewById<View>(R.id.list_view) as ListView
        list_view.adapter = nAdapter
        list_view.itemsCanFocus = true

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
                val newUser = snapshot.getValue(Book::class.java)
                mBook.add(newUser!!)
                nAdapter.notifyDataSetChanged()


            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildChanged:" + snapshot.key!!)
                val newUser = snapshot.getValue(Book::class.java)
                val userKey = snapshot.key
                mBook.find { e -> e.toString().equals(userKey) }?.set(newUser!!)
                nAdapter.notifyDataSetChanged()
            }
            override fun onChildRemoved(snapshot: DataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + snapshot.key!!)
                val newUser = snapshot.getValue(Book::class.java)
                val userKey = snapshot.key
                var u = mBook.find { e -> e.toString().equals(userKey) }
                mBook.remove(u)
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
        mBook.clear()

    }
}

/*      fun visualize(v: View){
        val intent = Intent(this@MainActivity, visualize::class.java)
        val desc = desc.text.toString()
        intent.putExtra("main_activity_data", desc)
        startActivityForResult(intent, SECOND_ACTIVITY)

    }
*/