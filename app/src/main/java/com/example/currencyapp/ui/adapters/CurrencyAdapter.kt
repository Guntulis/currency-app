package com.example.currencyapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.data.model.CurrencyRate

class CurrencyAdapter (val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
                    .placeholder(R.drawable.ic_storefront)
                    .into(venueImage)
            }
            venueName.text = venue.name
            venueAddress.text = venue.city
            itemView.setOnClickListener {
                venueClickListener?.let { it(venue) }
            }
        }
    }

    fun setItems(newVenues: List<Venue>) {
        venues.clear()
        venues.addAll(newVenues)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val venueImage: AppCompatImageView = view.venueImage
        val venueName: TextView = view.venueName
        val venueAddress: TextView = view.venueAddress
    }
}