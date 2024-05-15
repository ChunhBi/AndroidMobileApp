package com.example.criminalintent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ArticleDetailViewModel(val article: Article) : ViewModel() {

}
class ArticleDetailViewModelFactory(private val article: Article
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ArticleDetailViewModel(article) as T
    }
}