package com.example.localuser.retrofittest.kotlinstudy

class TestCompanionObject {
    companion object{
        const val value1 = 1;
        @JvmField var value2 = 2;
        @JvmField val value3 = 3
        var value4 : Int
            get() {
                return 4
            }
            set(value) {}
    }
}