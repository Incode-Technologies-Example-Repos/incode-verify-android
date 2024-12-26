package com.incode.verify.demo.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.incode.verify.demo.ui.theme.IncodeTypography

@Composable
fun IncodeButton(
    modifier: Modifier = Modifier,
    @StringRes text: Int,
    onClick: () -> Unit,
    textStyle: TextStyle = IncodeTypography.labelMedium
) {
    Button(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 52.dp)
        ),
        onClick = onClick,
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Text(
            text = stringResource(id = text),
            style = textStyle
        )
    }
}
