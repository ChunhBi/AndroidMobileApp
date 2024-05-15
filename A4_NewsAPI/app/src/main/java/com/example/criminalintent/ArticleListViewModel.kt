package com.example.criminalintent

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ArticleListViewModel : ViewModel() {
    private val _articles: MutableStateFlow<List<Article>> = MutableStateFlow(emptyList())
    val articles : StateFlow<List<Article>>
        get() = _articles.asStateFlow()

    fun getArticles(description: String){
        val client = OkHttpClient()
        val request : Request = Request.Builder()
            .url("https://newsapi.org/v2/everything?apiKey=6bf4b48283d4454b87c7533aa794254c&sortBy=popularity&q=$description")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Datafetch", "fetch failed")
            }
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()

                    if (responseBody != null) {
                        val gson = Gson()
                        val newsResponse: NewsResponse = gson.fromJson(responseBody, NewsResponse::class.java)
                        _articles.value = newsResponse.articles
                    }
                    else {
                        Log.d("Datafetch", "data body empty!")
                    }
                } else {
                    Log.d("Datafetch", "fetch failed")
                }
                response.close()
            }
        })
    }

    init {
        getArticles("Technology")
    }
}