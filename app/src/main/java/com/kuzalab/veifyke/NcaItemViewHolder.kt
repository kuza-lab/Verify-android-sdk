package com.dev.agenda.Adapters


import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kuzalab.veifyke.R


class NcaItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    var itemVew: View


    var registration_no: TextView
    var contractor_name: TextView
    var town: TextView
    var category: TextView
    var _class: TextView

    init {
        this.itemVew = itemView

        registration_no = itemView.findViewById(R.id.registration_no)
        contractor_name = itemView.findViewById(R.id.contractor_name)
        town = itemView.findViewById(R.id.town)
        category = itemView.findViewById(R.id.category)
        _class = itemView.findViewById(R.id._class)


    }


}
