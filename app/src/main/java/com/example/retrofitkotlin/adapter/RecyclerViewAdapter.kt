package com.example.retrofitkotlin.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitkotlin.databinding.RecyclerRowBinding
import com.example.retrofitkotlin.model.CryptoModel

class RecyclerViewAdapter(private val cryptoList: ArrayList<CryptoModel>, private val listener: Listener) :
    RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>(), Filterable {

    private var cryptoListFull: ArrayList<CryptoModel> = ArrayList(cryptoList)

    interface Listener {
        fun onItemClick(cryptoModel: CryptoModel)
    }

    private val colors: Array<String> = arrayOf(
        "#13bd27", "#29c1e1", "#b129e1", "#d3df13", "#f6bd0c", "#a1fb93", "#0d9de3", "#ffe48f"
    )

    class RowHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(binding)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.onItemClick(cryptoList[position])
        }
        holder.itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))

        holder.binding.textName.text = cryptoList[position].currency
        holder.binding.textPrice.text = cryptoList[position].price
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList: ArrayList<CryptoModel> = ArrayList()

                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(cryptoListFull)
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()
                    for (item in cryptoListFull) {
                        if (item.currency.toLowerCase().contains(filterPattern)) {
                            filteredList.add(item)
                        }
                    }
                }

                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                cryptoList.clear()
                cryptoList.addAll(results?.values as ArrayList<CryptoModel>)
                notifyDataSetChanged()
            }

        }
    }

    fun setData(cryptoList: ArrayList<CryptoModel>) {
        this.cryptoList.clear()
        this.cryptoList.addAll(cryptoList)
        this.cryptoListFull = ArrayList(cryptoList)
        notifyDataSetChanged()
    }

}