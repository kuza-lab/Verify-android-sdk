/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 8:29 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 8:24 AM
 *
 */

package com.kuzalab.veifyke

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kuzalab.verifysdk.models.NcaContractor

class NcaParamsAdapter(
    private var modelList: List<NcaContractor>?
) : RecyclerView.Adapter<NcaItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NcaItemViewHolder {
        var itemView: View? = LayoutInflater.from(parent.context).inflate(R.layout.nca_contractor_item, parent, false)



        return NcaItemViewHolder(itemView!!)
    }


    override fun onBindViewHolder(holder: NcaItemViewHolder, position: Int) {

        val model = modelList!![position]
        holder.registration_no.text = model.registrationNo
        holder.contractor_name.text = model.contractorName
        holder.town.text = model.town
        holder.category.text = model.category
        holder._class.text = model._class


    }


    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }


}