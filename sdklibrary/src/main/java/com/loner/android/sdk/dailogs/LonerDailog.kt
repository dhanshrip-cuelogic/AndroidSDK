package com.loner.android.sdk.dailogs



class LonerDailog private constructor (){

    companion object {
        @Volatile
        private var lonerDailogInstance: LonerDailog? = null

        fun getInstance(): LonerDailog {
            return lonerDailogInstance ?: synchronized(this) {
                LonerDailog().also {
                    lonerDailogInstance = it
                }
            }
        }
    }
}