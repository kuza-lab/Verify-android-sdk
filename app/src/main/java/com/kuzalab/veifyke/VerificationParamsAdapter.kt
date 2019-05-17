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
import com.kuzalab.verifysdk.models.ParamsResponse

class VerificationParamsAdapter(
    private var modelList: List<ParamsResponse>?
) : RecyclerView.Adapter<ParamsItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParamsItemViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context).inflate(R.layout.verification_params, parent, false)



        return ParamsItemViewHolder(itemView!!)
    }


    override fun onBindViewHolder(holder: ParamsItemViewHolder, position: Int) {

        val model = modelList!![position]
        holder.parameter_name.text = model.parameterName
        holder.parameter_value.text = model.parameterValue
        holder.is_verified.text = model.isVerified.toString()
        holder.status.text = model.status


    }


    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }


}