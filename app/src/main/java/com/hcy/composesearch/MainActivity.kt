package com.hcy.composesearch

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hcy.composesearch.ui.theme.ComPoseSearchTheme


/**
 * github token
 * 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
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComPoseSearchTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column() {
                        addSearch(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ) {
                            Log.e("TAG", "DefaultPreview: do Search $it")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun addSearch(modifier: Modifier = Modifier, doSearch: (serchKey: String) -> Unit) {
    var text by remember {
        mutableStateOf("")
    }
    var clearVisiable by remember {
        mutableStateOf(false)
    }

    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .background(Color(0x1A000000)),
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Box(
                Modifier
                    .focusModifier()
                    .padding(5.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .weight(5f),
            ) {
                Row(verticalAlignment = CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_search_24),
                        contentDescription = "Iocn",
                        colorFilter = ColorFilter.tint(Color(0xFF707070))
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                    ) {
                        if (!clearVisiable) {
                            Text(text = "请输入", modifier = Modifier.wrapContentSize()
                            ,style = TextStyle(
                                    color = Color(0xFF707070),
                                    fontSize = 15.sp
                                ))
                        }
                        BasicTextField(
                            modifier= Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            value = text,
                            onValueChange = {
                                text = it
                                clearVisiable = it.isNotEmpty()
                            },
                            textStyle = TextStyle(
                                color = Color(0xFF353535),
                                fontSize = 15.sp
                            )
                        )
                    }

                    if (clearVisiable) {
                        Image(
                            modifier = Modifier.clickable {
                                text = ""
                                clearVisiable = false
                            },
                            painter = painterResource(id = R.drawable.ic_baseline_clear_all_24),
                            contentDescription = "Iocn",
                            colorFilter = ColorFilter.tint(Color(0xFF707070))
                        )
                    }

                }
            }

            Spacer(
                modifier = Modifier
                    .height(36.dp)
                    .width(1.dp)
                    .padding(0.dp, 3.dp)
                    .background(Color(0xFFBBBBBB))
            )

            Box(modifier = Modifier
                .clickable {
                    doSearch.invoke(text)
                }
                .width(44.dp)
                .height(44.dp)
                .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "搜索",
                    style = MaterialTheme.typography.body1
                )
            }

        }
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ComPoseSearchTheme {
//        Greeting("Android")
        Column() {
            addSearch(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Log.e("TAG", "DefaultPreview: do Search $it")
            }


        }

    }
}
