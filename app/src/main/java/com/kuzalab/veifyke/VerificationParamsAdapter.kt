package com.kuzalab.veifyke

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.agenda.Adapters.ParamsItemViewHolder
import com.kuzalab.verifysdk.models.ParamsResponse

class VerificationParamsAdapter(
    private var modelList: List<ParamsResponse>?
) : RecyclerView.Adapter<ParamsItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParamsItemViewHolder {
        var itemView: View? = null

        itemView = LayoutInflater.from(parent.context).inflate(R.layout.verification_params, parent, false)



        return ParamsItemViewHolder(itemView!!)
    }


    override fun onBindViewHolder(holder: ParamsItemViewHolder, position: Int) {

        val model = modelList!![position]
        holder.parameter_name.text = "" + model.parameterName
        holder.parameter_value.text = "" + model.parameterValue
        holder.is_verified.text = "" + model.isVerified.toString()
        holder.status.text = model.status


    }


    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }


    fun refresh(modelList: List<ParamsResponse>) {
        this.modelList = modelList
        notifyDataSetChanged()

    }
}