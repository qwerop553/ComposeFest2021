package com.example.basicscodelab2

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.material.*
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basicscodelab2.ui.theme.BasicsCodelab2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Compose 에선 SetContent 를 통해 레이아웃을 정의한다.
        // 기존처럼 XML 레이아웃 파일을 사용하지 않는다.
        setContent {
            BasicsCodelab2Theme {
                MyApp()
            }
        }
    }
}

// Composable 함수를 묶어 사용함으로써
// 너무 많은 함수가 가독성을 떨어트리는 것을 방지하고, 재사용을 가능케 한다.
@Composable
private fun MyApp() {

    // property delegate 를 통해 .value 로 접근하지 않아도 된다.
    // remember 을 쓰면 activity 가 삭제될때 (configuration changes, like rotation)
    // 삭제가 된다. 그래서 remember 대신 rememberSaveable 을 쓴다.
    var shouldShowOnBoarding by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnBoarding) {
        OnboardingScreen(onContinueClicked = { shouldShowOnBoarding = false })
    } else {
        Greetings()
    }
}

// 앱 실행 시 처음에 나타나는, continue 선택 시 사라져야 하는 composable
// 사라지게 하기 위해서 '숨기는' 게 아닌 '포함하지 않게 해야' 한다
@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            // align 을 통해 composables 의 배치를 결정한다.
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Basics Codelab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ) {
                Text("Continue")
            }
        }
    }
}

@Composable
private fun Greetings(names: List<String> = List(1000) { "$it" }) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

// @Composable 함수는 @Composable 함수를 콜할 수 있다.
// Greeting 은 컴포저블 함수 Text 를 콜한다.
@Composable
fun Greeting(name: String) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
    }
}

@Composable
fun CardContent(name: String) {
    // remember 을 통해 Greeting 호출시 expanded 가 false 로 초기화를 막는다.
    // compose 객체들은 state 나 mutableStateOf 변수가 변동되었을때
    // 변동을 인지하고 recomposition 을 수행한다.

    var expanded by remember { mutableStateOf(false) }

    // extraPadding 은 state 에 의존하며 단순계산이므로
    // remember 하지 않아도 된다.
    // val extraPadding by animateDpAsState(
    //    if (expanded) 48.dp else 0.dp, spring(
    //        dampingRatio = Spring.DampingRatioMediumBouncy,
    //        stiffness = Spring.StiffnessLow
    //    )
    // )

    // Surface 는 color 가 primary 일 때
    // text color 를 자동으로 onPrimaryColor 로 바꾼다. (opinionated)
    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "Hello,")
            Text(
                text = name,
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                Text(
                    text = ("나무아비타불.나무아비타불.").repeat(4)
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Filled.ExpandLess else Filled.ExpandMore,
                contentDescription = if (expanded)
                    stringResource(R.string.show_less)
                else
                    stringResource(R.string.show_more)
            )

        }
    }
}



// 다크모드 프리뷰 생성
@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
// param 이 없는 composable 함수에 @Preview 를 주어 미리볼 수 있음
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelab2Theme {
        Greetings()
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicsCodelab2Theme {
        OnboardingScreen(onContinueClicked = {})
    }
}

