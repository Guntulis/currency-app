package com.example.currencyapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.R.layout
import com.example.currencyapp.data.model.CurrencyRate
import com.example.currencyapp.ui.adapter.CurrencyRatesAdapter.ViewHolder
import kotlinx.android.synthetic.main.currency_list_item.view.*

class CurrencyRatesAdapter(private val context: Context) : RecyclerView.Adapter<ViewHolder>() {
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
                currencyRateImage.setImageResource(it)
            }
            currencyRateShortName.text = currencyRate.shortName
            currencyRateLongName.text = currencyRate.shortName
            currencyRateValue.text = String.format("%.2f", currencyRate.rate)
            itemView.setOnClickListener {
                currencyClickListener?.let { it(currencyRate) }
            }
        }
    }

    fun setItems(newRates: List<CurrencyRate>) {
        currencyRates.clear()
        currencyRates.addAll(newRates)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val currencyRateImage: AppCompatImageView = view.currencyImage
        val currencyRateShortName: TextView = view.currencyShortName
        val currencyRateLongName: TextView = view.currencyLongName
        val currencyRateValue: TextView = view.currencyRate
    }
}