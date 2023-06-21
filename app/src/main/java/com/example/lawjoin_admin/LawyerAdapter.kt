package com.example.lawjoin_admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lawjoin_admin.data.model.Lawyer
import com.google.firebase.database.FirebaseDatabase

class LawyerAdapter(private val lawyerList: MutableList<Lawyer>) :
    RecyclerView.Adapter<LawyerAdapter.LawyerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LawyerViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lawyer, parent, false)
        return LawyerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LawyerViewHolder, position: Int) {
        val lawyer = lawyerList[position]
        holder.bind(lawyer)
    }

    override fun getItemCount(): Int {
        return lawyerList.size
    }

    inner class LawyerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val approveButton: Button = itemView.findViewById(R.id.approveButton)
        private val rejectButton: Button = itemView.findViewById(R.id.rejectButton)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val emailTextView: TextView = itemView.findViewById(R.id.emailTextView)
        private val officeNameTextView: TextView = itemView.findViewById(R.id.officeNameTextView)
        private val officePhoneTextView: TextView = itemView.findViewById(R.id.officePhoneTextView)
        private val officeLocationTextView: TextView = itemView.findViewById(R.id.officeLocationTextView)
        private val openingTimeTextView: TextView = itemView.findViewById(R.id.openingTimeTextView)
        private val closingTimeTextView: TextView = itemView.findViewById(R.id.closingTimeTextView)
        private val careerTextView: TextView = itemView.findViewById(R.id.careerTextView)
        private val introduceTextView: TextView = itemView.findViewById(R.id.introduceTextView)
        private val categoriesTextView: TextView = itemView.findViewById(R.id.categoriesTextView)
        private val certificateTextView: TextView = itemView.findViewById(R.id.certificateTextView)
        private val basicCounselTimeTextView: TextView = itemView.findViewById(R.id.basicCounselTimeTextView)

        fun bind(lawyer: Lawyer) {
            nameTextView.text = "변호사 이름: ${lawyer.name}"
            emailTextView.text = "이메일: ${lawyer.email}"
            officeNameTextView.text = "법률 사무소 이름: ${lawyer.office.name}"
            officePhoneTextView.text = "법률 사무소 전화번호: ${lawyer.office.phone}"
            officeLocationTextView.text = "법률 사무소 위치: ${lawyer.office.location}"
            openingTimeTextView.text = "법률 사무소 운영시간: ${lawyer.office.openingTime}"
            closingTimeTextView.text = "법률 사무소 종료시간: ${lawyer.office.closingTime}"
            careerTextView.text = "경력: ${lawyer.career.joinToString(", ")}"
            introduceTextView.text = "소개: ${lawyer.introduce}"
            categoriesTextView.text = "분야: ${lawyer.categories.joinToString(", ")}"
            certificateTextView.text = "자격증: ${lawyer.certificate.joinToString(", ")}"
            basicCounselTimeTextView.text = "기본 상담 시간: ${lawyer.basicCounselTime}분"

            approveButton.setOnClickListener {
                FirebaseDatabase.getInstance().reference
                    .child("lawyers")
                    .child("lawyer")
                    .child(lawyer.uid)
                    .setValue(lawyer)

                lawyerList.remove(lawyer)
                FirebaseDatabase.getInstance().reference
                    .child("lawyers")
                    .child("lawyer_wait")
                    .child(lawyer.uid).removeValue()
                notifyDataSetChanged()
            }
            rejectButton.setOnClickListener {
                FirebaseDatabase.getInstance().reference
                    .child("lawyers")
                    .child("lawyer_wait")
                    .child(lawyer.uid).removeValue()
                lawyerList.remove(lawyer)
                notifyDataSetChanged()
            }
        }
    }
}
