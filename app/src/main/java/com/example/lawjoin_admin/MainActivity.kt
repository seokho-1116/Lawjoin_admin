package com.example.lawjoin_admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lawjoin_admin.data.model.Lawyer
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var lawyerRecyclerView: RecyclerView
    private lateinit var lawyerAdapter: LawyerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lawyerRecyclerView = findViewById(R.id.lawyerRecyclerView)
        lawyerRecyclerView.layoutManager = LinearLayoutManager(this)
        lawyerAdapter = LawyerAdapter(mutableListOf())
        lawyerRecyclerView.adapter = lawyerAdapter

        // Firebase 실시간 데이터베이스 초기화
        database = FirebaseDatabase.getInstance().reference.child("lawyers").child("lawyer_wait")

        // 데이터베이스에 있는 변호사 리스트를 불러오기 위한 이벤트 리스너 등록
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val lawyerList = mutableListOf<Lawyer>()

                for (snapshot in dataSnapshot.children) {
                    val lawyer = snapshot.getValue(Lawyer::class.java)
                    lawyer?.let { lawyerList.add(it) }
                }

                lawyerAdapter = LawyerAdapter(lawyerList)
                lawyerRecyclerView.adapter = lawyerAdapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 오류 처리
            }
        })
    }
}
