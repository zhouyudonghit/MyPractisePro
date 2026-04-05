package com.example.localuser.retrofittest.kotlinstudy

class Test {
    var number: Int = 0
        get() {
            return 2
        }
        set(value) {
            field = value
        }

    private var number2: Double
        get() {
            return 2.0
        }
        set(value) {}
}