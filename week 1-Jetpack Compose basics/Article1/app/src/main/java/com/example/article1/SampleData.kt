package com.example.article1


class SampleData {

    companion object{
        val messages: List<Message> = listOf(
            Message("Colleague", "Test..Test..Test.."),
            Message("Colleague", "" +
                    "List of Android Versions" +
                    "List of Android Versions" +
                    "List of Android Versions" +
                    "List of Android Versions" +
                    "List of Android Versions"),
            Message("Colleague", "I think Kotlin is my favorite programming language" +
            "Aren't you?" + "Aren't you?" + "Aren't you?" + "Aren't you?" + "Aren't you?" + "Aren't you?"),
            Message("Colleague", "Hey, take a look at Jetpack compose, It's great!" +
                    "Aren't you?" + "Aren't you?" + "Aren't you?" + "Aren't you?" + "Aren't you?" + "Aren't you?"),
            Message("Colleague", "Aren't you?" + "Aren't you?" + "Aren't you?" + "Aren't you?" + "Aren't you?" + "Aren't you?" +
                    "Aren't you?" + "Aren't you?" + "Aren't you?" + "Aren't you?" + "Aren't you?" + "Aren't you?" +
                    "Aren't you?" + "Aren't you?" + "Aren't you?" + "Aren't you?" + "Aren't you?" + "Aren't you?"),
            Message("Colleague", "Go to hell")
        )
    }
}
data class Message(
    val author: String, val body: String
)