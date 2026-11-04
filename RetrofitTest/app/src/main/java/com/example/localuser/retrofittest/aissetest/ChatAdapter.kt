package com.example.localuser.retrofittest.aissetest

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.localuser.retrofittest.R
import com.example.localuser.retrofittest.aissetest.model.ChatMessage

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.VH>() {

    private val items = mutableListOf<ChatMessage>()

    fun submit(list: List<ChatMessage>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tv: TextView = view.findViewById(R.id.tv_message)
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_message, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val msg = items[position]
        val isUser = msg.role == "user"
        val lp = holder.tv.layoutParams as FrameLayout.LayoutParams
        lp.gravity = if (isUser) Gravity.END else Gravity.START
        holder.tv.layoutParams = lp
        holder.tv.text = msg.content.ifEmpty { "..." }
        holder.tv.setTextColor(if (isUser) Color.WHITE else Color.BLACK)
        holder.tv.background = GradientDrawable().apply {
            setColor(if (isUser) 0xFF6750A4.toInt() else 0xFFFFFFFF.toInt())
            cornerRadius = 40f
        }
    }
}