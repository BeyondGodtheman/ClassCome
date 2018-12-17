package com.sunny.classcome.bean

import android.text.TextUtils


class PayResult(rawResult: Map<String, String>?) {

    private var resultStatus: String? = null
    private var result: String? = null
    private var memo: String? = null

    init {
        rawResult?.let {
            for (key in it.keys) {
                when {
                    TextUtils.equals(key, "resultStatus") -> resultStatus = rawResult[key]
                    TextUtils.equals(key, "result") -> result = rawResult[key]
                    TextUtils.equals(key, "memo") -> memo = rawResult[key]
                }
            }
        }
    }

    /**
     * @return the resultStatus
     */
    fun getResultStatus(): String? {
        return resultStatus
    }

    /**
     * @return the memo
     */
    fun getMemo(): String? {
        return memo
    }

    /**
     * @return the result
     */
    fun getResult(): String? {
        return result
    }
}