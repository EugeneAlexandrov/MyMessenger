package com.mybclym.mymessenger.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.databinding.FragmentChatsBinding

class ChatsFragment : Fragment() {

    private var fragmentChatsBinding: FragmentChatsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatsBinding.inflate(inflater, container, false)
        fragmentChatsBinding = binding
        return binding.root
    }

    override fun onDestroyView() {
        fragmentChatsBinding = null
        super.onDestroyView()
    }
}