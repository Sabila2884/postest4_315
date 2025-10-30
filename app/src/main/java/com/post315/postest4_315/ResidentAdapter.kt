package com.post315.postest4_315

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.post315.postest4_315.data.Resident

class ResidentAdapter(private var residents: List<Resident>) :
    RecyclerView.Adapter<ResidentAdapter.ResidentViewHolder>() {

    class ResidentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMain: TextView = itemView.findViewById(R.id.tvMain)
        val tvNIK: TextView = itemView.findViewById(R.id.tvNIK)
        val tvAlamat: TextView = itemView.findViewById(R.id.tvAlamat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResidentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_resident, parent, false)
        return ResidentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResidentViewHolder, position: Int) {
        val resident = residents[position]
        holder.tvMain.text =
            "${position + 1}. ${resident.name} (${resident.gender}) - ${resident.maritalStatus}"
        holder.tvNIK.text = "NIK: ${resident.nik}"
        holder.tvAlamat.text =
            "Alamat: RT ${resident.rt}/RW ${resident.rw}, ${resident.desa}, ${resident.kecamatan}, ${resident.kabupaten}"
    }

    override fun getItemCount(): Int = residents.size

    fun updateData(newResidents: List<Resident>) {
        residents = newResidents
        notifyDataSetChanged()
    }
}
