package com.example.currencyapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.R.layout
import com.example.currencyapp.data.model.CurrencyRate
import com.example.currencyapp.data.model.CurrencyRate.Companion.TYPE_BASE
import com.example.currencyapp.data.model.CurrencyRate.Companion.TYPE_NORMAL
import com.example.currencyapp.ui.adapter.CurrencyRatesAdapter.ViewHolder
import com.example.currencyapp.ui.util.format
import com.example.currencyapp.ui.util.moveCursorAtEnd
import kotlinx.android.synthetic.main.currency_list_item.view.*

class CurrencyRatesAdapter(private val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    private val currencyRates = ArrayList<CurrencyRate>()
    var currencyClickListener: ((CurrencyRate, Int) -> Unit)? = null

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
            currencyRateImage.setImageResource(currencyRate.flagResId)
            currencyRateShortName.text = currencyRate.currencyIsoCode
            currencyRateLongName.text = context.getString(currencyRate.currencyNameResId)
            currencyRateValue.isEnabled = currencyRate.type == TYPE_BASE
            currencyRateValue.setText(currencyRate.rate.format())
            if (currencyRate.type == TYPE_NORMAL) {
                itemView.setOnClickListener {
                    currencyClickListener?.let { it(currencyRate, position) }
                }
            } else {
                currencyRateValue.requestFocus()
                currencyRateValue.moveCursorAtEnd()
            }
        }
    }

    fun setItems(newRates: List<CurrencyRate>) {
        currencyRates.clear()
        currencyRates.addAll(newRates)
        notifyDataSetChanged()
    }

    fun moveItemToTop(item: CurrencyRate, pos: Int) {
        item.type = TYPE_BASE
        currencyRates[0].type = TYPE_NORMAL
        currencyRates.removeAt(pos)
        currencyRates.add(0, item)
        notifyItemMoved(pos, 0)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val currencyRateImage: AppCompatImageView = view.currencyImage
        val currencyRateShortName: TextView = view.currencyShortName
        val currencyRateLongName: TextView = view.currencyLongName
        val currencyRateValue: EditText = view.currencyRate
    }
}