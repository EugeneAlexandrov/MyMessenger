package com.mybclym.mymessenger.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.models.CommonModel
import com.mybclym.mymessenger.utilits.*
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.contacts_item.view.*
import kotlinx.android.synthetic.main.fragment_contacts.*

class ContactsFragment : BaseFragment(R.layout.fragment_contacts) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FirebaseRecyclerAdapter<CommonModel, ContactsHolder>
    private lateinit var refContacts: DatabaseReference
    private lateinit var refUsers: DatabaseReference
    private lateinit var usersListener: AppValueEventListener
    private var listenersMap = hashMapOf<DatabaseReference, AppValueEventListener>()


    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Контакты"
        initRecyclerView()
    }

    override fun onPause() {
        super.onPause()
        adapter.stopListening()
        listenersMap.forEach {
            it.key.removeEventListener(it.value)
        }
    }

    private fun initRecyclerView() {
        recyclerView = contacts_recyclerView
        refContacts = REF_DATABASE_ROOT.child(NODE_PHONES_CONTACTS).child(UID)
        val options = FirebaseRecyclerOptions.Builder<CommonModel>()
            .setQuery(refContacts, CommonModel::class.java)
            .build()
        adapter = object : FirebaseRecyclerAdapter<CommonModel, ContactsHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.contacts_item, parent, false)
                return ContactsHolder(view)
            }

            override fun onBindViewHolder(
                holder: ContactsHolder,
                position: Int,
                model: CommonModel
            ) {
                refUsers = REF_DATABASE_ROOT.child(NODE_USERS).child(model.id)
                usersListener = AppValueEventListener {
                    val contact: CommonModel = it.getCommonModel()
                    holder.name.text = contact.fullname
                    holder.status.text = contact.status
                    holder.photo.downloadAndSetImage(contact.photoUrl)
                    holder.itemView.setOnClickListener { replaceFragment(SingleChatFragment(contact)) }
                }
                refUsers.addValueEventListener(usersListener)
                listenersMap[refUsers] = usersListener
            }

        }
        recyclerView.adapter = adapter
        adapter.startListening()
    }

    class ContactsHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name: TextView = view.contacts_item_name
        val status: TextView = view.contacts_item_status
        val photo: CircleImageView = view.contacts_item_photo
    }
}


