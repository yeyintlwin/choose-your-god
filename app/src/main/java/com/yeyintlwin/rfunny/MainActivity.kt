package com.yeyintlwin.rfunny

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.yeyintlwin.rfunny.utils.Rabbit
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val assetPath = "file:///android_asset/"
    private var fuckPath = "data"
    private var tempAnswer = mutableListOf<String>()

    private var back_press: Long = 0

    override fun onClick(v: View) {
        answer_2.visibility = View.GONE
        when (v) {
            answer_0 -> fuckPath += "/" + tempAnswer[0] + ".a"
            answer_1 -> fuckPath += "/" + tempAnswer[1] + ".a"
            answer_2 -> fuckPath += "/" + tempAnswer[2] + ".a"
        }
        loadAssets(fuckPath)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        answer_0.setOnClickListener(this)
        answer_1.setOnClickListener(this)
        answer_2.setOnClickListener(this)

        tempAnswer = ArrayList()

        loadAssets(fuckPath)

    }

    @SuppressLint("SetTextI18n")
    private fun loadAssets(path: String) {
        tempAnswer.clear()
        try {
            val fileNames = assets.list(path)
            for (fileName in fileNames!!) {

                val file = File(assetPath + fileName)

                println(file.absolutePath)
                val name = file.name

                val format = name.substring(name.lastIndexOf('.'))

                if (format == ".fa") {
                    println("target: " + removeFormat(name))
                    fuckLayout.visibility = View.GONE
                    finalAnswer.visibility = View.VISIBLE
                    finalAnswerText.text = "သင်က\n" + removeFormat(name)
                    finalAnswerText.text = Rabbit.uni2zg(finalAnswerText.text.toString())
                    return
                }

                if (format == ".q") {
                    questionText.text = removeFormat(name)
                }

                if (format == ".a") {
                    tempAnswer.add(removeFormat(name))
                }


            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("Fuck", e.toString())
        }

        answer_0.text = tempAnswer[0]
        answer_1.text = tempAnswer[1]
        if (tempAnswer.size == 3) {
            answer_2.text = tempAnswer[2]
        }

        questionText.text = Rabbit.uni2zg(questionText.text.toString())
        answer_0.text = Rabbit.uni2zg(answer_0.text.toString())
        answer_1.text = Rabbit.uni2zg(answer_1.text.toString())
        answer_2.text = Rabbit.uni2zg(answer_2.text.toString())

    }

    private fun removeFormat(fileName: String): String {
        return fileName.substring(0, fileName.lastIndexOf('.'))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            startActivity(Intent(this, AboutsActivity::class.java))
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (back_press + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        }
        Toast.makeText(this, "Press back bottom again to exist!", Toast.LENGTH_SHORT).show()
        back_press = System.currentTimeMillis()


    }
}
