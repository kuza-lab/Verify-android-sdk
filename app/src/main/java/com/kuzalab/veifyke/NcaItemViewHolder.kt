/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 8:29 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 8:24 AM
 *
 */

package com.kuzalab.veifyke


import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class NcaItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    var itemVew: View = itemView


    var registration_no: TextView = itemView.findViewById(R.id.registration_no)
    var contractor_name: TextView = itemView.findViewById(R.id.contractor_name)
    var town: TextView = itemView.findViewById(R.id.town)
    var category: TextView = itemView.findViewById(R.id.category)
    var _class: TextView = itemView.findViewById(R.id._class)


}
