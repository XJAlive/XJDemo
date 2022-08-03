package com.xj.demo.mvvn

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import com.xj.demo.BR
import com.xj.demo.R
import com.xj.demo.Student
import com.xj.demo.databinding.ActivityUserBinding

/**
 * Created by xiej on 2021/3/1
 */
class DataBindingtActivity : AppCompatActivity() {

    private lateinit var contentViewBinding: ActivityUserBinding

    private val studentViewModel: DataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contentViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_user)
        contentViewBinding.lifecycleOwner = this

        val student = Student("xiej", "12")

//        studentViewModel.studentLiveData.observe(this) {
//            contentViewBinding.user = it
//        }
//
//        studentViewModel.updatePerson(Student("xiej", "24"))

//        student.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
//            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
//                when (propertyId) {
//                    BR.user -> {
//                        Log.i("xj", "user属性变更")
//                    }
//                    BR.name -> {
//                        Log.i("xj", "name属性变更")
//                    }
//                }
//            }
//        })
        contentViewBinding.user = student
        contentViewBinding.btnChange.setOnClickListener {
            contentViewBinding.tvContent.text = "xiaoming"
            Log.i("xj", "name=${student.name}")
        }

    }

}


