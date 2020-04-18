package com.eratoiklio.quiztest.model

enum class Categories(val id: Int, val displayName: String) {
    HISTORY(23, "History"),
    MYTHOLOGY(20, "Mythology");

    override fun toString(): String {
        return displayName
    }
}