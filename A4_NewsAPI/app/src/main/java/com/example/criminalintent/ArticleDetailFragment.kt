package com.example.criminalintent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.criminalintent.databinding.FragmentArticleDetailBinding

class ArticleDetailFragment: Fragment() {
    private var _binding: FragmentArticleDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val args: ArticleDetailFragmentArgs by navArgs()

    private val articleDetailViewModel: ArticleDetailViewModel by viewModels {
        ArticleDetailViewModelFactory(args.article)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentArticleDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUi(args.article)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUi (article: Article) {
        binding.apply {
            articleTitle.text = article.title
            articleDescription.text = article.description
            articleDate.text = article.publishedAt
            articleAuthor.text = article.author
//            articleLink.text = article.url
        }
    }
}