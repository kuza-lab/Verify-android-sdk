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


class ParamsItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    var itemVew: View = itemView

    var parameter_name: TextView = itemView.findViewById(R.id.parameter_name)
    var parameter_value: TextView = itemView.findViewById(R.id.parameter_value)
    var is_verified: TextView = itemView.findViewById(R.id.is_verified)
    var status: TextView = itemView.findViewById(R.id.status)


}
