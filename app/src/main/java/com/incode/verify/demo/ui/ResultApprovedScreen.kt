package com.incode.verify.demo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.incode.verify.demo.ActionHandler
import com.incode.verify.demo.ActionHandlerAdapter
import com.incode.verify.demo.R
import com.incode.verify.demo.ui.theme.IncodeTypography
import com.incode.verify.demo.ui.theme.IncodeVerifyAndroidTheme
import com.incode.verify.demo.ui.theme.bodyMediumAlt
import com.incode.verify.demo.ui.theme.headlineSmallAlt


@Composable
fun ResultApproved(innerPadding: PaddingValues, actionHandler: ActionHandler) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
        Modifier
            .fillMaxHeight()
            .padding(innerPadding)
            .then(Modifier.padding(top = 60.dp, bottom = 24.dp, start = 24.dp, end = 24.dp))
    ) {
        Text(
            text = stringResource(id = R.string.result_approved_title),
            style = IncodeTypography.headlineLarge
        )
        Text(
            modifier = Modifier.padding(bottom = 60.dp, top = 16.dp),
            text = stringResource(id = R.string.result_approved_subtitle),
            style = IncodeTypography.headlineSmallAlt
        )
        Image(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .weight(1f),
            painter = painterResource(id = R.drawable.id),
            contentDescription = null
        )

        Text(
            modifier = Modifier.padding(top = 66.dp),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.result_approved_note),
            style = IncodeTypography.bodyMediumAlt
        )
        IncodeButton(
            modifier = Modifier.padding(top = 60.dp),
            text = R.string.generic_done,
            onClick = { actionHandler.openHome() },
            textStyle = IncodeTypography.labelMedium.copy(fontSize = 20.sp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    IncodeVerifyAndroidTheme {
        ResultApproved(innerPadding = PaddingValues(), actionHandler = ActionHandlerAdapter())
    }
}
