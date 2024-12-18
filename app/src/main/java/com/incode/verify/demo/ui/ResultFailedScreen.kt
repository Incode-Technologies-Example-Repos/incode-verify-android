package com.incode.verify.demo.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.incode.verify.demo.ActionHandler
import com.incode.verify.demo.ActionHandlerAdapter
import com.incode.verify.demo.R
import com.incode.verify.demo.ui.theme.IncodeVerifyAndroidTheme
import com.incode.verify.demo.ui.theme.Typography

@Composable
fun ResultFailed(innerPadding: PaddingValues, actionHandler: ActionHandler) {
    Column(
        modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Header(
            innerPadding,
            title = stringResource(id = R.string.result_failed_title),
            subtitle = stringResource(id = R.string.result_failed_subtitle),
        )
        Column(modifier = Modifier.padding(top = 30.dp, start = 24.dp, end = 24.dp)) {
            Text(
                modifier = Modifier.padding(bottom = 24.dp),
                text = stringResource(id = R.string.result_failed_tips_title),
                style = Typography.headlineSmall.copy(
                    color = Color(0xFF2B2B2B),
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = stringResource(id = R.string.result_failed_tips_row_1),
                style = Typography.bodyMedium
            )
            Text(
                modifier = Modifier.padding(vertical = 10.dp),
                text = stringResource(id = R.string.result_failed_tips_row_2),
                style = Typography.bodyMedium
            )
            Text(
                modifier = Modifier.padding(vertical = 10.dp),
                text = stringResource(id = R.string.result_failed_tips_row_3),
                style = Typography.bodyMedium
            )
            Text(
                modifier = Modifier.padding(vertical = 10.dp),
                text = stringResource(id = R.string.result_failed_tips_row_4),
                style = Typography.bodyMedium
            )

            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { actionHandler.openHome() },
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = stringResource(id = R.string.generic_try_again))
            }

            ClickableText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                text = AnnotatedString(stringResource(id = R.string.generic_help)),
                onClick = { actionHandler.openHelp() },
                style = Typography.labelMedium.copy(
                    textDecoration = TextDecoration.Underline,
                    color = Color(0xFF2B2B2B),
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    IncodeVerifyAndroidTheme {
        ResultFailed(innerPadding = PaddingValues(), actionHandler = ActionHandlerAdapter())
    }
}
