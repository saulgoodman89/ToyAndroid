package com.keg.imagepicker

import android.graphics.Color
import android.net.Uri
import android.provider.CalendarContract.Colors
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp

@Composable
fun ImagePickerScreen(viewModel: ImagePickerViewModel = ImagePickerViewModel()) {
    val images by viewModel.images.collectAsState()

    /*
        다중 이미지 선택이 가능한 안드로이드 제공 이미지 피커

    val pickImagesLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        viewModel.addImages(uris)
    }     */

    // MultipleVisualMedia 사용. 기존과 동일.
    val pickImagesLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()
    ) {
        uris: List<Uri> ->
        viewModel.addImages(uris)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                pickImagesLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "이미지 선택 진행")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 선택한 이미지와 순서를 text로 표시한다.
        if (images.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(images) { index, uri ->
                    SelectedImageItem(uri = uri, order = index + 1)
                }
            }
        }
    }
}

@Composable
fun SelectedImageItem(uri: Uri, order: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 순서 번호
        Text(
            text = "$order",
            modifier = Modifier.padding(end = 16.dp)
        )

        // uri 표시
        Text(
            text = "${uri.toString()}"
        )
    }
}

@Composable
fun mainScreen() {
    Box(Modifier.size(120.dp,80.dp)) {
        ColorBox(
            Modifier
                .customLayout(250,200)
                .background(color= androidx.compose.ui.graphics.Color.Red)
        )
    }
}

@Composable
fun ColorBox(modifier: Modifier) {
    Box(Modifier.padding(1.dp).size(width=50.dp,height=10.dp).then(modifier)) {

    }
}

/*
    custom layout modifier 생성
    자식 composable을 x,y와 배치하기 위한 커스템 레이아웃
 */
@Composable
fun Modifier.customLayout(x:Int,y:Int) = layout {
    measurable, constraints->   // measurable : 모디파이어가 호출된 자식 요소가 배치될 정보 , constraints : 자식이 이용 할 수 있는 최대 높이와 폭
    val placeable = measurable.measure(constraints) // 자식의 실제 크기와 배치 정보를 담고 있는 객체
    layout(placeable.width , placeable.height) { // 최종 레이아웃의 너비와 높이를 설정.
        placeable.placeRelative(x,y)    // 측정된 자식을 (x,y) 좌표에 배치한다.
    }
}