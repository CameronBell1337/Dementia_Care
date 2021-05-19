package com.example.mobilesappdev.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilesappdev.R
import kotlinx.android.synthetic.main.card_view.view.*

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>()
{
    private val itemTitleArray = arrayOf("text1", "text2", "text3", "text4", "text5", "text6")
    private val itemInfoArray = arrayOf(
        "What is Lorem Ipsum Lorem Ipsum is simply dummy text of the printing and typesetting industry Lorem Ipsum has been the industry's standard dummy text ever since the 1500s when an unknown printer took a galley of type and scrambled it to make a type specimen book it has?",
        "What is Lorem Ipsum Lorem Ipsum is simply dummy text of the printing and typesetting industry Lorem Ipsum has been the industry's standard dummy text ever since the 1500s when an unknown printer took a galley of type and scrambled it to make a type specimen book it has?",
        "What is Lorem Ipsum Lorem Ipsum is simply dummy text of the printing and typesetting industry Lorem Ipsum has been the industry's standard dummy text ever since the 1500s when an unknown printer took a galley of type and scrambled it to make a type specimen book it has?",
        "What is Lorem Ipsum Lorem Ipsum is simply dummy text of the printing and typesetting industry Lorem Ipsum has been the industry's standard dummy text ever since the 1500s when an unknown printer took a galley of type and scrambled it to make a type specimen book it has?",
        "What is Lorem Ipsum Lorem Ipsum is simply dummy text of the printing and typesetting industry Lorem Ipsum has been the industry's standard dummy text ever since the 1500s when an unknown printer took a galley of type and scrambled it to make a type specimen book it has?",
        "What is Lorem Ipsum Lorem Ipsum is simply dummy text of the printing and typesetting industry Lorem Ipsum has been the industry's standard dummy text ever since the 1500s when an unknown printer took a galley of type and scrambled it to make a type specimen book it has?")
    private val itemImages = intArrayOf(
        R.drawable.ic_new_launcher_background,
        R.drawable.ic_new_launcher_background,
        R.drawable.ic_new_launcher_background,
        R.drawable.ic_new_launcher_background,
        R.drawable.ic_new_launcher_background,
        R.drawable.ic_launcher_background
    )


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var cardImage : ImageView
        var cardTitle : TextView
        var cardInfo : TextView

        init
        {
            cardImage = itemView.findViewById(R.id._cardImage)
            cardTitle = itemView.findViewById(R.id._cardTitleText)
            cardInfo = itemView.findViewById(R.id._cardInfoText)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val _view : View = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return ViewHolder(_view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.cardTitle.text = itemTitleArray[position]
        holder.cardInfo.text = itemInfoArray[position]
        holder.cardImage.setImageResource(itemImages[position])

        holder.itemView.setOnClickListener {_view : View ->
            Toast.makeText(_view.context, "Clicked on the item$position", Toast.LENGTH_SHORT).show()

            when (position.toInt()) {
                1 ->
                {

                }
            }
        }
    }

    override fun getItemCount(): Int {
        return itemTitleArray.size
    }


}