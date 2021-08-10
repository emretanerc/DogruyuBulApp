package com.etcmobileapps.dogrunusuogren.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.etcmobileapps.dogrunusuogren.R

import com.etcmobileapps.dogrunusuogren.model.Score



class ScoreboardRowAdapter(private val context: Context, private val mScores: List<Score>, private val mRowLayout: Int) : RecyclerView.Adapter<ScoreboardRowAdapter.ScoreViewHolder>() {
    var placement: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.scoreboard_row, parent, false)

        placement=placement+1
        return ScoreViewHolder(view)

    }

    override fun getItemCount(): Int {

        return mScores.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()

    }

    override fun getItemViewType(position: Int): Int {

        return position
    }




    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {


        var value = mScores

        if (holder != null) {

            holder.id?.text = (position+1).toString()
            if (position+1==1) {
                holder.id.setTextColor(Color.RED)
            } else if (position+1==2) {
                holder.id.setTextColor(Color.RED)
            }
            else if (position+1==3) {
                holder.id.setTextColor(Color.RED)
            }


            val prefences = context.getSharedPreferences("SCORE", Context.MODE_PRIVATE)
            var  userName = prefences.getString("KEY_USERNAME",null)

            if (userName.equals(value[position].name)) {

                holder.scoreLayout.setBackgroundResource(R.drawable.trueicon)
                holder.name.setTextColor(Color.WHITE)
                holder.score.setTextColor(Color.WHITE)
                holder.id.setTextColor(Color.WHITE)

            }

            holder.name?.text = value[position].name.toString()
            holder.score?.text = value[position].score.toString() + " Puan"

        }


        holder.containerView.setOnClickListener {
          //  Toast.makeText(context, "Clicked on: " + holder.title.text, Toast.LENGTH_SHORT).show();
        }
    }



    class ScoreViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {
        val id = containerView.findViewById<TextView>(R.id.idTv)
        val name = containerView.findViewById<TextView>(R.id.userNameTv)
        val score = containerView.findViewById<TextView>(R.id.scoreValueTv)
        val scoreLayout = containerView.findViewById<LinearLayout>(R.id.optionLayout)
    }


}
