package com.bignerdranch.android.simpleboggle

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bignerdranch.android.simpleboggle.databinding.FragmentBoardBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.Random

const val vowels = "AEIOU"
const val double_score_letter = "SZPXQ"

class board : Fragment(), BoardListener{
    private var listener: ControlListener? = null
    private lateinit var buttonArray: Array<Button>
    private var lastBtn: Button? = null
    private lateinit var wordDictionary: Set<String>
    private lateinit var usedWords: MutableSet<String>

    private var _binding: FragmentBoardBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBoardBinding.inflate(inflater, container, false )
        binding.apply {
            buttonArray = arrayOf(btn11,btn12,btn13,btn14,btn21,btn22,btn23,btn24,btn31,btn32,btn33,btn34,btn41,btn42,btn43,btn44)
            for (button in buttonArray) {
                button.setOnClickListener { onBtnClicked(button) }
            }
            clearBtn.setOnClickListener { onClearClicked() }
            submitBtn.setOnClickListener { onSubmitClicked() }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        enableAllButton()
//        wordDictionary = File("res/raw/words.txt").readLines().toSet()
        val inputStream = resources.openRawResource(R.raw.words)
        val reader = BufferedReader(InputStreamReader(inputStream))

        wordDictionary = reader.readLines().toSet()
        usedWords = mutableSetOf()
        newBoard()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setListener(listener: ControlListener) {
        this.listener = listener
    }
    @SuppressLint("SetTextI18n")
    private fun onBtnClicked(btn: Button) {
        if (!isNeighbor(btn, lastBtn)) {
            Toast.makeText(context, "You may only select connected letters!", Toast.LENGTH_SHORT).show()
            return
        }
        btn.backgroundTintList = ColorStateList.valueOf(Color.GRAY)
        btn.isEnabled = false

        binding.wordView.text = binding.wordView.text.toString() + btn.text.toString()
        lastBtn = btn
    }
    private fun onClearClicked() {
        binding.wordView.text = ""
        enableAllButton()
    }
    private fun onSubmitClicked() {
        val word = binding.wordView.text.toString()
        if (word.length < 4){
            Toast.makeText(context, "Word should have at least 4 letters!", Toast.LENGTH_SHORT).show()
            return
        }
        if (word in usedWords) {
            Toast.makeText(context, "Word should not be entered twice!", Toast.LENGTH_SHORT).show()
            return
        }
        var vowelCount = 0
        var multiple = 1
        var wordScore = 0
        for (letter in word) {
            if (letter in vowels)
                vowelCount++
            if (letter in double_score_letter ) {
                multiple *= 2
            }
        }
        if (vowelCount < 2) {
            Toast.makeText(context, "Word should have at least 2 vowels!", Toast.LENGTH_SHORT).show()
            return
        }
        if (checkWord(word.lowercase())) {
            wordScore = ( word.length + vowelCount*4 ) * multiple
            Toast.makeText(context, "That’s correct, +${wordScore}!", Toast.LENGTH_SHORT).show()
            usedWords.add(word)
        } else {
            Toast.makeText(context, "No such word! -10!", Toast.LENGTH_SHORT).show()
            wordScore = -10
        }
        listener?.setScore(listener!!.getScore() + wordScore)
        binding.wordView.text = ""
        enableAllButton()
    }
    private fun enableAllButton() {
        for (btn in buttonArray) {
            btn.backgroundTintList = ColorStateList.valueOf(Color.LTGRAY)
            btn.isEnabled = true
        }
        lastBtn = null
    }
    private fun isNeighbor(btn1: Button, btn2:Button?): Boolean {
        if (btn2 == null) return true
        val i = buttonArray.indexOf(btn1)
        val j = buttonArray.indexOf(btn2)
        val r1 = i % 4
        val c1 = i / 4
        val r2 = j % 4
        val c2 = j / 4

        return r1-r2 <= 1 && r2-r1 <= 1 && c1-c2 <= 1 && c2-c1 <= 1
    }
    private fun checkWord(word: String): Boolean {
        return word in wordDictionary
    }
    private fun newBoard() {
        val random = Random()
        for (btn in buttonArray) {
            btn.text = ('A' + random.nextInt(26)).toString()
        }
    }
    override fun resetBoard() {
        newBoard()
        binding.wordView.text = ""
        enableAllButton()
        usedWords.clear()
    }
}