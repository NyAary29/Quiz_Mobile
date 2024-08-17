package com.example.quiz_game

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException


@Composable
fun QuizApp() {
    val context = LocalContext.current
    val questions = getQuestionsFromJson(context, "profile.json")
    val allQuestions = combineAndSortQuestions(questions)
    val totalQuestions = allQuestions.size

    var questionIndex by remember { mutableStateOf(0) }
    val userAnswers = remember { mutableStateMapOf<String, MutableList<Int>>() }
    var results by remember { mutableStateOf<Map<String, Int>?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (results == null) {
            if (questionIndex < totalQuestions) {
                val currentQuestion = allQuestions[questionIndex]
                val profile = currentQuestion.first
                val questionText = currentQuestion.second


                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = questionText,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                val options = listOf(
                    "Totalement en accord",
                    "En accord",
                    "Neutre",
                    "En désaccord",
                    "Totalement en désaccord"
                )

                var selectedOption by remember { mutableStateOf(2) } // Default selection (Neutre)

                Column {
                    options.forEachIndexed { index, option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = selectedOption == index + 1,
                                onClick = { selectedOption = index + 1 },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.primary,
                                    unselectedColor = MaterialTheme.colorScheme.onBackground
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = option,
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            if (questionIndex > 0) {
                                questionIndex--
                            }
                        },
                        enabled = questionIndex > 0,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text(text = "Retour")
                    }

                    Button(
                        onClick = {
                            userAnswers.getOrPut(profile) { mutableListOf() }.add(selectedOption)
                            if (questionIndex < totalQuestions - 1) {
                                questionIndex++
                            } else {
                                results = calculateResults(userAnswers)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(text = "Suivant")
                    }
                }
            }
        } else {
            // Affichage des résultats
            Text(text = "Résultats:", style = MaterialTheme.typography.titleMedium)
            val top3Profiles = results!!.entries.sortedByDescending { it.value }.take(3)
            top3Profiles.forEach { (profile, score) ->
                Text(text = "$profile: $score", style = MaterialTheme.typography.headlineMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))

            val lowestProfile = results!!.entries.minByOrNull { it.value }
            lowestProfile?.let {
                Text(
                    text = "Profil avec le score le plus bas: ${it.key} :: ${it.value}",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}


fun combineAndSortQuestions(questions: Map<String, List<String>>): List<Pair<String, String>> {
    return questions.flatMap { (profile, questionList) ->
        questionList.map { question ->
            profile to question
        }
    }.sortedBy { (_, question) ->
        val matchResult = Regex("Q(\\d+)").find(question)
        matchResult?.groupValues?.get(1)?.toInt() ?: 0
    }
}

fun loadJSONFromFile(context: Context, fileName: String): String? {
    return try {
        val inputStream = context.assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        String(buffer, Charsets.UTF_8)
    } catch (ex: IOException) {
        ex.printStackTrace()
        null
    }
}

fun getQuestionsFromJson(context: Context, fileName: String): Map<String, List<String>> {
    val jsonFileString = loadJSONFromFile(context, fileName)
    val gson = Gson()
    val mapType = object : TypeToken<Map<String, List<String>>>() {}.type
    return gson.fromJson(jsonFileString, mapType)
}

fun calculateResults(userAnswers: Map<String, List<Int>>): Map<String, Int> {
    return userAnswers.mapValues { (_, answers) ->
        answers.sum()
    }
}


