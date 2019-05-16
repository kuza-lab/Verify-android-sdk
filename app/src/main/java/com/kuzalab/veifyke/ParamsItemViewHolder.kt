package com.dev.agenda.Adapters


import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kuzalab.veifyke.R


class ParamsItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    var itemVew: View

    var parameter_name: TextView
    var parameter_value: TextView
    var is_verified: TextView
    var status: TextView


    init {
        this.itemVew = itemView

        parameter_name = itemView.findViewById(R.id.parameter_name)
        parameter_value = itemView.findViewById(R.id.parameter_value)
        is_verified = itemView.findViewById(R.id.is_verified)
        status = itemView.findViewById(R.id.status)


    }


}
