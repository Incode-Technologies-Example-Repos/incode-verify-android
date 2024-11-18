package com.incode.verify.demo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.incode.verify.demo.ActionHandler
import com.incode.verify.demo.ActionHandlerAdapter
import com.incode.verify.demo.R
import com.incode.verify.demo.ui.theme.IncodeVerifyAndroidTheme
import com.incode.verify.demo.ui.theme.Purple
import com.incode.verify.demo.ui.theme.Purple10
import com.incode.verify.demo.ui.theme.Typography

@Composable
fun Home(modifier: Modifier = Modifier, actionHandler: ActionHandler, showIntroButton: Boolean) {
    val introButtonState = remember {
        mutableStateOf(showIntroButton)
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Header(
            modifier,
            title = stringResource(id = R.string.intro_title),
            subtitle = stringResource(id = R.string.intro_subtitle),
        )
        Column(modifier = Modifier.padding(top = 30.dp, start = 24.dp, end = 24.dp)) {
            Instruction(
                painterResource(id = R.drawable.icon_instruction_1),
                stringResource(id = R.string.intro_instruction_1)
            )
            Instruction(
                painterResource(id = R.drawable.icon_instruction_2),
                stringResource(id = R.string.intro_instruction_2)
            )
            Instruction(
                painterResource(id = R.drawable.icon_instruction_3),
                stringResource(id = R.string.intro_instruction_3)
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )

        if (introButtonState.value) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 28.dp)
            ) {

                Button(
                    modifier = Modifier
                        .height(52.dp)
                        .fillMaxWidth(),
                    onClick = { introButtonState.value = false },
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = stringResource(id = R.string.intro_verify_with_incode),
                        style = Typography.bodyLarge.copy(fontSize = 18.sp)
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .height(38.dp)
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(Color(0x1A00001A))
                )
                Text(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(horizontal = 12.dp),
                    text = stringResource(id = R.string.intro_verify_with_incode),
                    style = Typography.bodySmall
                )
            }
            val buttonModifier = Modifier
                .weight(1f)
                .height(52.dp)
                .fillMaxWidth()
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 28.dp)
            ) {
                Button(
                    modifier = buttonModifier,
                    onClick = { actionHandler.openWebView() },
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors().copy(containerColor = Purple10)
                ) {
                    Text(color = Purple, text = stringResource(id = R.string.intro_webview))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    modifier = buttonModifier
                        .weight(1f),
                    onClick = { actionHandler.openInstantApp() },
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(text = stringResource(id = R.string.intro_instant_app))
                }
            }
        }

        Row(
            modifier = Modifier
                .padding(bottom = 40.dp)
        ) {
            Text(
                text = stringResource(id = R.string.intro_terms),
                style = Typography.labelSmall
            )
            ClickableText(
                modifier = Modifier.padding(start = 8.dp),
                text = AnnotatedString(stringResource(id = R.string.intro_learn_more)),
                onClick = { actionHandler.openTerms() },
                style = Typography.labelSmall.copy(
                    textDecoration = TextDecoration.Underline,
                    color = Color.Blue
                )
            )

        }
    }
}

@Composable
fun Instruction(icon: Painter, text: String) {
    Row {
        Image(painter = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text, style = Typography.bodyMedium)
    }
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun Header(modifier: Modifier = Modifier, title: String, subtitle: String) {
    Box(
        Modifier
            .background(
                Brush.linearGradient(colors = listOf(Color(0xFF8531EF), Color(0xFF54258F)))
            )
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(modifier = modifier.padding(start = 24.dp, end = 24.dp)) {
            Image(
                modifier = Modifier.offset(x = (-12).dp),
                painter = painterResource(id = R.drawable.logo),
                colorFilter = ColorFilter.tint(Color.White),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = title, style = Typography.headlineMedium)
            Spacer(modifier = Modifier.height(14.dp))
            Text(text = subtitle, style = Typography.headlineSmall)
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview()
@Composable
private fun Preview() {
    IncodeVerifyAndroidTheme {
        Home(actionHandler = ActionHandlerAdapter(), showIntroButton = true)
    }
}