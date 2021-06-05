package com.hcy.composesearch.ui.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author HCY
 * @date 16:28
 * description：
 */
class MainViewMoudel: ViewModel() {
    val datas= arrayListOf<String>()


    fun addItem(s:String){
        datas.add(s)
    }

    val lvDatas = MutableLiveData<ArrayList<String>>(datas)


    private val _addKey=MutableLiveData("")
    val addKey:LiveData<String> get() = _addKey

}
