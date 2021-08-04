package com.example.mobilesappdev.adapters

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat.getMainExecutor
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilesappdev.MainActivity
import com.example.mobilesappdev.R
import com.example.mobilesappdev.ResourceActivity
import com.example.mobilesappdev.fragments.*
import kotlinx.android.synthetic.main.card_view.view.*
import android.os.Handler


//This is very dirty and hard coded and not reuseable due to into being statically set
class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    //Information came from: https://www.alzheimers.org.uk/
    private val itemTitleArray = arrayOf(
        "'The system isn’t set up to be fair'",
        "‘I wanted to look after Mum, but spent years wrangling with an unfair system’",
        "‘As long as Grandma keeps smiling, we know she is doing okay’",
        "Behind the Cure The Care System film: ‘It was an ode to my mum and dad’",
        "‘Mum received fantastic care. Why isn't it the same for everyone?’",
        "‘Bonnie deserves the best quality care’",
        "'Juggling work, home-schooling, and full-time care'"
    )
    private val itemInfoArray = arrayOf(
        "Derek cares for his wife Margaret, who has Alzheimer's disease, but their experience of the care system has been difficult and they want more support.",
        "Following his mother's death, Jonathan is calling on Government to keep its promise and rebuild the social care system.",
        "Meera’s grandmother has mixed dementia. Meera shares how the family cares for her grandma with the help of agency carers.",
        "Actor Kate Dickie shares why she took part in our hard-hitting Cure The Care System film, and a message to other people affected by dementia.",
        "The support Julia's mum, Gladys, received was exceptional. Julia wants to help families not as lucky as her own to receive the same.",
        "In March 2020, David moved in with his best friend Bonnie, who has dementia, to take on her full-time care, but the support provided to him has been minimal.",
        "Sara's family has been caring for her dad, Brian, who has frontotemporal dementia (FTD). They've lived through the flawed care system and want Government to act."
    )
    private val itemImages = intArrayOf(
        R.drawable.story00,
        R.drawable.story01,
        R.drawable.story02,
        R.drawable.story03,
        R.drawable.story04,
        R.drawable.story05,
        R.drawable.story06
    )


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardImage: ImageView
        var cardTitle: TextView
        var cardInfo: TextView

        init {
            cardImage = itemView.findViewById(R.id._cardImage)
            cardTitle = itemView.findViewById(R.id._cardTitleText)
            cardInfo = itemView.findViewById(R.id._cardInfoText)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val _view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return ViewHolder(_view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardTitle.text = itemTitleArray[position]
        holder.cardInfo.text = itemInfoArray[position]
        holder.cardImage.setImageResource(itemImages[position])
        var intent : Intent
        var options : ActivityOptionsCompat
        var image = holder.cardImage

        val handler = Handler()

        holder.itemView.setOnClickListener { _view: View ->

            holder.itemView.visibility = View.INVISIBLE
            when (position) {
                0 -> {

                    intent = Intent(_view.context,Story00::class.java)
                    options = ActivityOptionsCompat.makeSceneTransitionAnimation(_view.context as Activity, image, "card_transition")

                    _view.context.startActivity(intent, options.toBundle())
                }
                1 -> {
                    intent = Intent(_view.context, Story01::class.java)
                    options = ActivityOptionsCompat.makeSceneTransitionAnimation(_view.context as Activity, image, "card_transition")

                    _view.context.startActivity(intent, options.toBundle())
                }
                2 -> {
                    intent = Intent(_view.context,Story02::class.java)
                    options = ActivityOptionsCompat.makeSceneTransitionAnimation(_view.context as Activity, image, "card_transition")

                    _view.context.startActivity(intent, options.toBundle())
                }
                3 -> {
                    intent = Intent(_view.context,Story03::class.java)
                    options = ActivityOptionsCompat.makeSceneTransitionAnimation(_view.context as Activity, image, "card_transition")

                    _view.context.startActivity(intent, options.toBundle())
                }
                4 -> {
                    intent = Intent(_view.context,Story04::class.java)
                    options = ActivityOptionsCompat.makeSceneTransitionAnimation(_view.context as Activity, image, "card_transition")

                    _view.context.startActivity(intent, options.toBundle())
                }
                5 -> {
                    intent = Intent(_view.context,Story05::class.java)
                    options = ActivityOptionsCompat.makeSceneTransitionAnimation(_view.context as Activity, image, "card_transition")

                    _view.context.startActivity(intent, options.toBundle())
                }
                6 -> {
                    intent = Intent(_view.context,Story06::class.java)
                    options = ActivityOptionsCompat.makeSceneTransitionAnimation(_view.context as Activity, image, "card_transition")

                    _view.context.startActivity(intent, options.toBundle())
                }
                else -> return@setOnClickListener

            }

            handler.postDelayed({
                holder.itemView.visibility = View.VISIBLE
            }, 1000)
        }
    }



    override fun getItemCount(): Int {
        return itemTitleArray.size
    }


}

class RecyclerViewAdapter1 : RecyclerView.Adapter<RecyclerViewAdapter1.ViewHolder>() {

    //Activities
    private val itemTitleArray = arrayOf(
        "Remember the past",
        "Social Groups",
        "Exercise together",
        "Cooking & Baking",
        "Word Games",
    )
    private val itemInfoArray = arrayOf(
        "People with dementia are more likely to remember older memories. Reminiscence therapy uses prompts, like: photographs, familiar objects or music to help someone recall those memories",
        "A dementia diagnosis can come as a shock to the person with the condition and people involved with them. Although, there is always a source of help around for everyone.",
        "Exercise is an important part of life. Just taking a walk is essential for our physical and mental health and provides any benefits",
        "Cooking & Baking are events and activities you and others can enjoy together. Whilst some people with dementia can follow a recipe, it is still a great thing to enjoy with others and to get involved in.",
        "Playing word games are fun and a good activity to involve others. It also helps benefit in word recalling and helps with cognitive stimulation",
    )
    private val itemImages = intArrayOf(
        R.drawable.remember_past,
        R.drawable.social_group,
        R.drawable.exercise,
        R.drawable.cooking,
        R.drawable.word_games,
    )


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardImage: ImageView
        var cardTitle: TextView
        var cardInfo: TextView

        init {
            cardImage = itemView.findViewById(R.id._cardImage)
            cardTitle = itemView.findViewById(R.id._cardTitleText)
            cardInfo = itemView.findViewById(R.id._cardInfoText)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val _view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return ViewHolder(_view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardTitle.text = itemTitleArray[position]
        holder.cardInfo.text = itemInfoArray[position]
        holder.cardImage.setImageResource(itemImages[position])

        var intent : Intent

        holder.itemView.setOnClickListener { _view: View ->
            when (position.toInt()) {
                0 -> {


                }
                1->
                {

                }

            }
        }
    }


    override fun getItemCount(): Int {
        return itemTitleArray.size
    }


}

class RecyclerViewAdapter2 : RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder>() {

    //Activities
    private val itemTitleArray = arrayOf(
        "About Dementia",
        "Dementia Research",
        "Living with Dementia",
        "Self Care",
        "Books",
        "Financial",
        "Legal Help",

    )
    private val itemInfoArray = arrayOf(
        "Learn more about dementia and how it affects people.",
        "Learn more about what is currently being researched.",
        "Tips for communicating with people with Dementia.",
        "Tips to help you or others struggling with Dementia.",
        "List of books to read.",
        "Find out what aid you eligible for.",
        "Information on legal affairs for someone with Dementia.",

    )
    private val itemImages = intArrayOf(
        R.drawable.bain_v2,
        R.drawable.brain_v3,
        R.drawable.living_with_dementia_v2,
        R.drawable.self_care_v2,
        R.drawable.book_v2,
        R.drawable.coin_stack_v2,
        R.drawable.legal_v2,
    )


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardImage: ImageView
        var cardTitle: TextView
        var cardInfo: TextView

        init {
            cardImage = itemView.findViewById(R.id.imageResource)
            cardTitle = itemView.findViewById(R.id.titleResource)
            cardInfo = itemView.findViewById(R.id.summaryResource)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val _view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.resources_card, parent, false)
        return ViewHolder(_view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardTitle.text = itemTitleArray[position]
        holder.cardInfo.text = itemInfoArray[position]
        holder.cardImage.setImageResource(itemImages[position])

        var intent : Intent

        var options : ActivityOptionsCompat
        var image = holder.cardImage

        val handler = Handler()

        holder.itemView.setOnClickListener { _view: View ->
            when (position) {
                0 -> {
                    intent = Intent(_view.context,Resource00::class.java)
                    options = ActivityOptionsCompat.makeSceneTransitionAnimation(_view.context as Activity, image, "card_transition")

                    _view.context.startActivity(intent, options.toBundle())

                }
                1->
                {
                    intent = Intent(_view.context,Resource01::class.java)
                    options = ActivityOptionsCompat.makeSceneTransitionAnimation(_view.context as Activity, image, "card_transition")

                    _view.context.startActivity(intent, options.toBundle())

                }
                2->
                {
                    Toast.makeText(_view.context, "Currently Disabled", Toast.LENGTH_SHORT).show()
                }
                3->
                {
                    Toast.makeText(_view.context, "Currently Disabled", Toast.LENGTH_SHORT).show()
                }
                4->
                {
                    Toast.makeText(_view.context, "Currently Disabled", Toast.LENGTH_SHORT).show()
                }
                5->
                {
                    Toast.makeText(_view.context, "Currently Disabled", Toast.LENGTH_SHORT).show()
                }
                6->
                {
                    Toast.makeText(_view.context, "Currently Disabled", Toast.LENGTH_SHORT).show()
                }
                else -> return@setOnClickListener

            }

            handler.postDelayed({
                holder.itemView.visibility = View.VISIBLE
            }, 1000)
        }
    }


    override fun getItemCount(): Int {
        return itemTitleArray.size
    }


}