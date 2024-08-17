package com.example.quiz_game

import android.widget.Toast
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.quiz_game.ui.theme.Quiz_GameTheme
import java.util.regex.Pattern

@Composable
fun LoginScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.wp13063009),
            contentDescription = "background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White.copy(alpha = 0.1f))
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column {
                Logo()
                LoginForm(navController=navController)
            }
        }
    }
}

@Composable
fun LoginForm(modifier: Modifier = Modifier,navController:NavHostController){
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val allFieldsFilled = name.isNotBlank() && email.isNotBlank() && phone.isNotBlank()

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.8f)
        )
    ){
        Column(
            modifier = Modifier
                .background(color = Color.Transparent)
                .padding(bottom = 30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")

            val colors = listOf(
                Color(0xFFFFA500),  // Orange
                Color(0xFF90EE90), // Vert
                Color(0xFFFFEB3B), // Jaune
                Color(0xFF4285F4), // Bleu
                Color(0xFF808080), // Gris
            )
            val scale by infiniteTransition.animateFloat(
                initialValue = 0.8f,
                targetValue = 1.5f,
                animationSpec = infiniteRepeatable(tween(1200), RepeatMode.Reverse),
                label = "scale"
            )

            val animatedColor by infiniteTransition.animateColor(
                initialValue = colors[0],
                targetValue = colors[1],
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        this.durationMillis = 4000
                        colors[1] at 1000
                        colors[2] at 2000
                        colors[3] at 3000
                        colors[0] at 4000
                    },
                    repeatMode = RepeatMode.Restart
                ),
                label = "color"
            )

            Text(
                text = "WELCOME",
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif,
                color = animatedColor,
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .padding(top = 30.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        transformOrigin = TransformOrigin.Center
                    }
                    .align(Alignment.CenterHorizontally),
            )

            Text(
                text ="Entrez vos informations pour commencer le quiz",
                fontSize = 11.sp,
                fontFamily = FontFamily.Serif,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 30.dp)
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nom", color = Color.Black) },
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .alpha(0.7f),
                shape = RoundedCornerShape(20.dp),

            )

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = !isValidEmail(it)
                },
                label = { Text("Email",color = Color.Black) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .alpha(0.7f),
                isError = emailError,
                shape = RoundedCornerShape(20.dp)
            )
            if (emailError) {
                Text(
                    text = "Email invalide",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 20.dp, top = 5.dp)
                )
            }

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone",color = Color.Black) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .alpha(0.7f)
                    .padding(bottom = 10.dp),
                shape = RoundedCornerShape(20.dp)
            )

            Spacer(modifier = Modifier.weight(3f))

            Button(
                onClick = {
                    if (emailError) {
                        Toast.makeText(context, "Veuillez entrer un email valide.", Toast.LENGTH_SHORT).show()
                    } else if (allFieldsFilled) {
                        // Gérer les données ici, par exemple, les afficher dans un Toast
                        navController.navigate("quiz")

                    } else {
                        Toast.makeText(context, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.size(width = 170.dp, height = 45.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = androidx.compose.ui.graphics.Color.Black,
                    containerColor =  if (allFieldsFilled) Color.White else Color.Transparent.copy(alpha = 0.1f)
                )
            ) {
                Text(
                    text = "Let's go",
                    fontFamily = FontFamily.Serif,
                    fontSize = 16.sp
                )
            }
        }
    }
}

fun isValidEmail(email: String): Boolean {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    return Pattern.compile(emailPattern).matcher(email).matches()
}


