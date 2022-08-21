package com.example.fourchoicequiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.fourchoicequiz.databinding.ActivityMainBinding
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var rightAnswer: String? = null
    private var rightAnswerCount = 0
    private var quizCount = 1
    private val QUIZ_COUNT = 5

    private val quizData = mutableListOf(
        mutableListOf("北海道", "札幌市", "長崎市", "福島市", "前橋市"),
        mutableListOf("青森県", "青森市", "広島市", "甲府市", "岡山市"),
        mutableListOf("岩手県", "盛岡市", "大分市", "秋田市", "福岡市"),
        mutableListOf("宮城県", "仙台市", "水戸市", "岐阜市", "福井市"),
        mutableListOf("秋田県", "秋田市", "横浜市", "鳥取市", "仙台市"),
        mutableListOf("山形県", "山形市", "青森市", "山口市", "奈良市"),
        mutableListOf("福島県", "福島市", "盛岡市", "新宿区", "京都市"),
        mutableListOf("茨城県", "水戸市", "金沢市", "名古屋市", "奈良市"),
        mutableListOf("栃木県", "宇都宮市", "札幌市", "岡山市", "奈良市"),
        mutableListOf("群馬県", "前橋市", "福岡市", "松江市", "福井市")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        quizData.shuffle()

        showNextQuiz()
    }

    fun showNextQuiz() {
         binding.countLabel.text = getString(R.string.count_label, quizCount)

        val quiz = quizData[0]

        binding.questionLabel.text = quiz[0]

        rightAnswer = quiz[1]

        quiz.removeAt(0)

        quiz.shuffle()

        binding.answerBtn1.text = quiz[0]
        binding.answerBtn2.text = quiz[1]
        binding.answerBtn3.text = quiz[2]
        binding.answerBtn4.text = quiz[3]

        quizData.removeAt(0)
    }

    fun checkAnswer(view: View) {
        val answerBtn: Button = findViewById(view.id)
        val btnText = answerBtn.text.toString()

        val alertTitle: String
        if(btnText == rightAnswer) {
            alertTitle = "正解!"
            rightAnswerCount++
        }else {
            alertTitle = "不正解..."
        }

        AlertDialog.Builder(this)
            .setTitle(alertTitle)
            .setMessage("答え : $rightAnswer")
            .setPositiveButton("OK") { dialogInterface, i ->
                checkQuizCount()
            }
            .setCancelable(false)
            .show()
    }

    fun  checkQuizCount() {
        if(quizCount == QUIZ_COUNT) {
            val intent = Intent(this@MainActivity, resultActivity::class.java)
            intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount)
            startActivity(intent)
        }else {
            quizCount++
            showNextQuiz()
        }
    }
}