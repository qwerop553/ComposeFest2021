package com.example.article1

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.article1.ui.theme.ComposeTutorialTheme

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                Conversation(SampleData.messages)
            }
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun Conversation(message: List<Message>) {

    var clickedNumber by rememberSaveable { mutableStateOf(-1) }

    LazyColumn {
        itemsIndexed(message) { index, message ->
            MessageCard(message, { clickedNumber == index }, { clickedNumber = if (clickedNumber != index) index else -1})
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun MessageCard(msg: Message, onClick: () -> Boolean, onClick2: () -> Unit) {
    // Add padding around our message
    Row(
        modifier = Modifier
            .padding(all = 8.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        val surfaceColor: Color by animateColorAsState(
            if (onClick()) MaterialTheme.colors.primary else MaterialTheme.colors.surface
        )

        Column {
            Text(
                text = "${msg.author}!",
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp),
                onClick = onClick2
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (onClick()) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun PreviewConversation() {
    ComposeTutorialTheme {
        Conversation(SampleData.messages)

    }
}

@Preview(name = "Light mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)

@ExperimentalMaterialApi
@Composable
fun PreviewMessageCard() {
    ComposeTutorialTheme {
        MessageCard(SampleData.messages[0], {true}, {})
    }
}





