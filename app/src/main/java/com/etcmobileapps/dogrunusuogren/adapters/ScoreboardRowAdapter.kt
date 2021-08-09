package com.etcmobileapps.dogrunusuogren.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.etcmobileapps.dogrunusuogren.R
import com.etcmobileapps.dogrunusuogren.databinding.FragmentMenuBinding
import com.etcmobileapps.dogrunusuogren.databinding.ScoreboardRowBinding
import com.etcmobileapps.dogrunusuogren.model.Score

private var _binding: ScoreboardRowBinding? = null
private val  binding get() = _binding!!

class ScoreboardRowAdapter(private val context: Context, private val mScores: List<Score>, private val mRowLayout: Int) : RecyclerView.Adapter<ListAdapter.ScoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(mRowLayout, parent, false)
        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.positionNumber?.text = context.resources.getString(R.string.question_num, position + 1)


        holder.containerView.setOnClickListener {
            Toast.makeText(context, "Clicked on: " + holder.title.text, Toast.LENGTH_SHORT).show();
        }
    }

    override fun getItemCount(): Int {
        return mScores.size
    }

    class ScoreViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {
        val id = binding.userName ;
        val name = binding.userName;
        val score = binding.score;
    }
}
