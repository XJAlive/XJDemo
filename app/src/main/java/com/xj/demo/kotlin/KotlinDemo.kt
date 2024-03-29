package com.xj.demo.kotlin

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

/**
 * DSL
 */
class TextWatcherImpl : TextWatcher {

    var afterTextChanged: ((Editable?) -> Unit)? = null
    var beforeTextChange: ((s: CharSequence?, start: Int, count: Int, after: Int) -> Unit)? = null
    var onTextChanged: ((s: CharSequence?, start: Int, before: Int, count: Int) -> Unit)? = null

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        beforeTextChange?.invoke(s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged?.invoke(s, start, before, count)
    }

    override fun afterTextChanged(s: Editable?) {
        afterTextChanged?.invoke(s)
    }
}


fun TextView.addTextWatcher(initWatch: TextWatcherImpl.() -> Unit) {
    val textWatcherImpl = TextWatcherImpl()
    textWatcherImpl.initWatch()
    addTextChangedListener(textWatcherImpl)
}


fun testDsl(view: TextView) {
    view.addTextWatcher(fun TextWatcherImpl.() {
        (fun(it: Editable?) {
            val value = "3$it"
            println("afterChanged")
        }).also { afterTextChanged = it }

        beforeTextChange = { s, start, count, after ->
            println("beforeChanged")
        }
    })


}