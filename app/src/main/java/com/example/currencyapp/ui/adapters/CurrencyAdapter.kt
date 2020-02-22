package com.example.currencyapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.currencyapp.R
import com.example.currencyapp.R.layout
import com.example.currencyapp.data.model.CurrencyRate
import kotlinx.android.synthetic.main.currency_list_item.view.*

class CurrencyAdapter (val context: Context) : RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {
    private val currencyRates = ArrayList<CurrencyRate>()
    var currencyClickListener: ((CurrencyRate) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                layout.currency_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return currencyRates.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currencyRate = currencyRates[position]
        holder.run {
            currencyRate.flag?.let {
                Glide.with(context).load(it)
                    .placeholder(R.drawable.ic_flag)
                    .into(currencyRateImage)
            }
            currencyRateShortName.text = currencyRate.shortName
            itemView.setOnClickListener {
                currencyClickListener?.let { it(currencyRate) }
            }
        }
    }

    fun setItems(newVenues: List<CurrencyRate>) {
        currencyRates.clear()
        currencyRates.addAll(newVenues)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val currencyRateImage: AppCompatImageView = view.currencyImage
        val currencyRateShortName: TextView = view.currencyName
        val currencyRateLongName: TextView = view.currencyName
    }
}