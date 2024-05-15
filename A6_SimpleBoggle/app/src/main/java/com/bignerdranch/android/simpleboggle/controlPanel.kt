package com.bignerdranch.android.simpleboggle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bignerdranch.android.simpleboggle.databinding.FragmentControlPanelBinding

class controlPanel: Fragment(), ControlListener {
    private var listener: BoardListener? = null
    private var score = 0
    private var _binding: FragmentControlPanelBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentControlPanelBinding.inflate(inflater, container, false )
        binding.apply {
            newGameBtn.setOnClickListener {
                newGame()
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setListener(listener: BoardListener) {
        this.listener = listener
    }

    override fun setScore(score: Int) {
        this.score = if (score>0) score else 0
        binding.scoreView.text = "SCORE: ${this.score}"

    }

    override fun getScore(): Int {
        return this.score
    }
    private fun newGame() {
        listener?.resetBoard()
    }
}