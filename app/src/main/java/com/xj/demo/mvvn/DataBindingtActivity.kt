package com.xj.demo.mvvn

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.xj.demo.R
import com.xj.demo.databinding.ActivityUserBinding
import kotlinx.coroutines.launch

/**
 * Created by xiej on 2021/3/1
 */
class DataBindingtActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityUserBinding

//    private val studentViewModel: DataViewModel by viewModels()

    private val viewModel: LiveDataViewModel by viewModels()

    private val mainViewModel: StateFlowViewModel by viewModels()

    @SuppressLint("LogConditional")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_user)
        viewBinding.lifecycleOwner = this

//        val student = Student("xiej", "12")

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
//        contentViewBinding.user = student
//        contentViewBinding.btnChange.setOnClickListener {
//            contentViewBinding.tvContent.text = "xiaoming"
//            Log.i("xj", "name=${student.name}")
//        }

//        val reportEventModel = ViewModelProvider(this).get<LiveDataViewModel>()
//        reportEventModel.reportEvent.observe(this) {
//            Log.i("xj", "reportEventModel--->value = $it")
//        }

//        viewModel.reportEvent.wrapLiveData()
//            .onSuccess {
//                viewBinding.tvContent.text = it.orEmpty()
//            }.onError { _, msg ->
//                viewBinding.tvContent.text = ""
//                ToastUtils.showShort(msg)
//            }.observe(this)

        viewBinding.btnChange.setOnClickListener {
//            viewModel.fetchData()
//            mainViewModel.fetchData()
            mainViewModel.updateSelect("nike")
        }

//        lifecycleScope.launch {
//            mainViewModel.select.collect {
//                viewBinding.tvContent.text = it
//            }
//        }

        lifecycleScope.launch {
            mainViewModel.shoe.collect {
                viewBinding.tvContent.text = it
            }
        }

    }

}


