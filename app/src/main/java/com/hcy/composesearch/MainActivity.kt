package com.hcy.composesearch

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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

                        }
                    }
                }
            }
        }
    }



}

@Composable
fun SearchhistoryItems(text: String, modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = text,
                color = Color(0xFFB6B6B6),
                fontSize = 12.sp
            )
        }

        Divider(color = Color(0xFFCCCCCC), modifier = Modifier.padding(5.dp, 0.dp))
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
fun showSearchHistoryItem() {
    SearchhistoryItems(text = "123456")
}

@Composable
fun addSearch(modifier: Modifier = Modifier, doSearch: (serchKey: String) -> Unit) {
    var toastString by remember {
        mutableStateOf("")
    }

    var text by remember {
        mutableStateOf("")
    }
    var clearVisiable by remember {
        mutableStateOf(false)
    }

    val rList = remember {
        mutableStateListOf<String>()
    }
    Column(modifier.padding(bottom = 20.dp)) {

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
                                .weight(1f)
                                .padding(10.dp, 0.dp),
                        ) {
                            if (!clearVisiable) {
                                Text(
                                    text = "请输入关键字",
                                    modifier = Modifier.wrapContentSize(),
                                    style = TextStyle(
                                        color = Color(0xFF707070),
                                        fontSize = 15.sp
                                    )
                                )
                            }
                            BasicTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                value = text,
                                onValueChange = {
                                    text = it
                                    clearVisiable = it.isNotEmpty()
                                },
                                keyboardActions = KeyboardActions(onSearch = {
                                    doSearch.invoke(text)
                                    text = ""
                                    clearVisiable = false
                                }),
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
                        if (TextUtils.isEmpty(text)) {
                            toastString = "请输入关键字！"
                        } else {
                            doSearch.invoke(text)
                            if (rList.contains(text)) {
                                rList.remove(text)
                            }
                            rList.add(0, text)
                            text = ""
                            clearVisiable = false
                        }
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
        if (rList.isEmpty().not()) {
            HistoryView(hisList = rList) {
                text = it
                clearVisiable = true
            }
        }
    }
    if (!TextUtils.isEmpty(toastString)) {
        Box(Modifier.padding(20.dp)) {
            Snackbar(action = {
                CoroutineScope(Dispatchers.Default).launch {
                    delay(1000)
                    toastString = ""
                }
            }) {
                Text(text = toastString)
            }
        }

    }


}


@Composable
fun HistoryView(hisList: SnapshotStateList<String>, onClick: (item: String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 5.dp, end = 5.dp, top = 2.dp, bottom = 10.dp)
    ) {
        Card(
            Modifier
                .fillMaxWidth()
                .heightIn(40.dp, 200.dp)
        ) {
            Column(Modifier.padding(10.dp)) {

                Row() {
                    Text(text = "搜索历史", fontSize = 12.sp, modifier = Modifier.weight(1f))
                    Image(
                        modifier = Modifier.clickable {
                            hisList.clear()
                        },
                        painter = painterResource(id = R.drawable.ic_baseline_delete_sweep_24),
                        colorFilter = ColorFilter.tint(Color(0xFF707070)),
                        contentDescription = "删除搜索历史"
                    )
                }

                LazyColumn(
                    Modifier
                        .heightIn(20.dp, 160.dp)
                ) {
                    items(hisList) { item ->
                        SearchhistoryItems(modifier = Modifier.clickable {
                            onClick.invoke(item)
                        }, text = item)
                    }
                }
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
