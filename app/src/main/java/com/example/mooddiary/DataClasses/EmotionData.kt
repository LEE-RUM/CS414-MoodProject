package com.example.myapp01

data class EmotionData(val emotion_scores: EmotionSore)

data class EmotionSore(
    val disgust: Float,
    val sadness: Float,
    val anger: Float,
    val joy: Float,
    val surprise: Float,
    val fear: Float,
)

/*emotion analysis okhttp
* val client = OkHttpClient()

val request = Request.Builder()
	.url("https://twinword-emotion-analysis-v1.p.rapidapi.com/analyze/?text=After%20living%20abroad%20for%20such%20a%20long%20time%2C%20seeing%20my%20family%20was%20the%20best%20present%20I%20could%20have%20ever%20wished%20for.")
	.get()
	.addHeader("X-RapidAPI-Host", "twinword-emotion-analysis-v1.p.rapidapi.com")
	.addHeader("X-RapidAPI-Key", "994bdfbf94msh3b119a9d7f448adp13da27jsnb83b6a87ab9a")
	.build()

val response = client.newCall(request).execute()
* */

/*
* {
  "emotions_detected": [],
  "emotion_scores": {
    "disgust": 0,
    "sadness": 0,
    "anger": 0,
    "joy": 0,
    "surprise": 0,
    "fear": 0
  },
  "thresholds": {
    "disgust": 0.04,
    "sadness": 0.04,
    "anger": 0.04,
    "joy": 0.04,
    "surprise": 0.04,
    "fear": 0.04
  },
  "version": "7.0.8",
  "author": "twinword inc.",
  "email": "help@twinword.com",
  "result_code": "200",
  "result_msg": "Success"
}
* */