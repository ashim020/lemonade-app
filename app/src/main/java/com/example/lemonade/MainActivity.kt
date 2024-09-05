package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadeApp() {
    LemonadeImageAndText(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonadeImageAndText(modifier: Modifier = Modifier) {
    var step by remember { mutableIntStateOf(1) }
    var tapCount by remember { mutableIntStateOf(0) }
    var squeezeGoal by remember { mutableIntStateOf(1) }

    val imageResource = when (step) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }

    val textResource = when (step) {
        1 -> R.string.tap_lemon
        2 -> R.string.squeeze_lemon
        3 -> R.string.drink_lemonade
        else -> R.string.empty_glass
    }

    val contentDescriptionResource = when (step) {
        1 -> R.string.lemon_tree_content_description
        2 -> R.string.lemon_content_description
        3 -> R.string.lemonade_glass_content_description
        else -> R.string.empty_glass_content_description
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Lemonade",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorResource(R.color.header_yellow)
                )
            )
        }
    ) { innerPadding ->
        // Apply the padding from Scaffold to the Column
        Column(
            modifier = modifier
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    when (step) {
                        1 -> {
                            step = 2
                            tapCount = 0
                            squeezeGoal = (2..4).random()
                        }

                        2 -> {
                            tapCount++
                            if (tapCount >= squeezeGoal) {
                                step = 3
                            }
                        }

                        3 -> {
                            step = 4
                        }

                        else -> {
                            step = 1
                        }
                    }
                },
                content = {
                    Image(
                        painter = painterResource(imageResource),
                        contentDescription = stringResource(contentDescriptionResource)
                    )
                },
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.button_blue))
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                stringResource(textResource),
                fontSize = 18.sp
            )

        }
    }
}