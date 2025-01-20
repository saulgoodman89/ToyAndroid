package com.keg.imagepicker

import android.graphics.Color
import android.net.Uri
import android.provider.CalendarContract.Colors
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.size.Size

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
           LazyVerticalGrid(
               columns = GridCells.Fixed(3),
               // 새로 , 가로 간격을 설정하여 사진들이 떨어져 보이게 수정
               verticalArrangement = Arrangement.spacedBy(2.dp), // 세로간격 2dp
               horizontalArrangement = Arrangement.spacedBy(2.dp),  // 가로간격 2dp
               modifier = Modifier.fillMaxWidth()
              //     .aspectRatio(1f)
           ) {
               itemsIndexed(images) { index, uri ->
                   SelectedImageItem(uri = uri)
               }
           }
        }
    }
}

@Composable
fun SelectedImageItem(uri: Uri) {
    /*
    AsyncImage(model = uri,
        contentDescription = "Selected picture",
        /*
            contentScale : 컴포저블에서 이미지 스케일링 방식을 지정.
            원본 이미지 비율은 유지하면서 주어진 공간을 채우기 위해 이미지를 확대 하거나 축소 ex) Gallery
            주어진 공간을 위해 이미지의 중앙을 기준으로 잘라낸다.

            다른 옵션
            ContentScale.Fit : 이미지에 주어진 공간에 맞게 축소,확대 하지만 이미지 비율 유지.
            ContentScale.FillBounds : 이미지 비율을 무시하고 주어진 공간을 완전히 채우도록 스캐일링.
            ContentScale.Inside : 주어진 공간 안에 완전히 들어가도록 축소,확대 하지만 비율 유지. 이미지가 주어진 공간을 완전히
            채우지 않을 수 있다.

         */
        //contentScale = ContentScale.Fit, 실제 사용 시 공간을 완전히 채우지 않는다.
        // contentScale = ContentScale.FillBounds,  실제 사용 시 공간은 채우지만 확대 되어 보임.
        //   contentScale = ContentScale.Inside, 공간을 채우지 못함.
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f) // Composable의 가로세로 비율을 정한다. 1f : 정사각형
            .layoutId("IMG")
    )

    Box(
        modifier = Modifier
            .width(10.dp)
            .height(10.dp)
            .clip(CircleShape)
            .background(androidx.compose.ui.graphics.Color.Red)
            .offset(x = (-4).dp, y = 4.dp) // 약간의 오프셋 조정
            .layoutId("BADGE")
    ) {
        Text(
            text = "10",
            color = androidx.compose.ui.graphics.Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }*/
        Layout(
            content = {
                AsyncImage(model = uri,
                    contentDescription = "Selected picture",
                    /*
                        contentScale : 컴포저블에서 이미지 스케일링 방식을 지정.
                        원본 이미지 비율은 유지하면서 주어진 공간을 채우기 위해 이미지를 확대 하거나 축소 ex) Gallery
                        주어진 공간을 위해 이미지의 중앙을 기준으로 잘라낸다.

                        다른 옵션
                        ContentScale.Fit : 이미지에 주어진 공간에 맞게 축소,확대 하지만 이미지 비율 유지.
                        ContentScale.FillBounds : 이미지 비율을 무시하고 주어진 공간을 완전히 채우도록 스캐일링.
                        ContentScale.Inside : 주어진 공간 안에 완전히 들어가도록 축소,확대 하지만 비율 유지. 이미지가 주어진 공간을 완전히
                        채우지 않을 수 있다.

                     */
                    //contentScale = ContentScale.Fit, 실제 사용 시 공간을 완전히 채우지 않는다.
                    // contentScale = ContentScale.FillBounds,  실제 사용 시 공간은 채우지만 확대 되어 보임.
                    //   contentScale = ContentScale.Inside, 공간을 채우지 못함.
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f) // Composable의 가로세로 비율을 정한다. 1f : 정사각형
                        .layoutId("IMG")
                )

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(androidx.compose.ui.graphics.Color.Transparent)
                        .border(width = 3.dp, color = androidx.compose.ui.graphics.Color.White , shape = CircleShape)
                        .layoutId("BADGE")
                ) {
                    Text(
                        text = "10",
                        color = androidx.compose.ui.graphics.Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

            }
        ) {  measurables,constraints ->
            // measurables를 layoutId 기준으로 매핑
            val measurableMap = measurables.associateBy { it.layoutId!! }

            // IMG , BADGE layout 측정
            val imagePlaceable = measurableMap["IMG"]?.measure(constraints) ?: error("Component A not found")
            val badgePlaceableB = measurableMap["BADGE"]?.measure(
                constraints.copy(
                    minWidth = 0,
                    minHeight = 0,
                    maxWidth = constraints.maxWidth / 4 ,
                    maxHeight = 80
                )
            ) ?: error("Component B not found")

            // 부모 레이아웃의 크기 결정
            val width = imagePlaceable.width
            val height = imagePlaceable.height

            layout(width, height) {
                // 이미지 배치
                imagePlaceable.placeRelative(0, 0)

                // 뱃지를 이미지 우측 상단에 배치하기 위해 x,y position 설정
                val xPosition = imagePlaceable.width - imagePlaceable.width / 3
                val yPosition = imagePlaceable.height / 14
                Log.e("LSA","xPosition : $xPosition   yPosition : $yPosition")
                badgePlaceableB.placeRelative(xPosition, yPosition)
            }
        }
}

@Composable
fun CascadeLayout(
    modifier: Modifier = Modifier,
    spacing: Int = 0,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        var indent = 0
        layout(constraints.maxWidth, constraints.maxHeight) {
            var yCoord = 0
            Log.e("LSA","constraints.maxWidth : ${constraints.maxWidth} , maxHeight : ${constraints.maxHeight} \n minWidth : ${constraints.minWidth}" +
                    "  minHeight : ${constraints.minHeight}")

            /*
                constraints : 부모가 자식에 줄 수 있는 가로,세로 크기의 범위를 담고있는 정보.
                minWidth , minheight 이상 maxWidth , maxHeight 이하로 자식 컴포저블의 크기를 측정해야한다.
                ex) 쉐어하우스 계약 , 집 주인이 내가 최대 사용 할 수 있는 방 크기를 정해준다.

               measurable : 아직 측정되지 않은 자식 컴포넌트.
               min , max가 정해지지 않은 상태이고 measure(constraints) 함수를 호출해야 구체적인 크기가 결정.
               ex) 집 주인이 정해준 제약사항을 가지고 어떤 방을 쓸지 측정한다.

               placeables : 측정이 완료 배치가 가능한 자식 컴포넌트 리스트
               ex)방 측정이 다 끝난 상태이고 집주인에게 어느 방에 배치해달라고 전달.
             */
            val placeables = measurables.map { measurable ->
                Log.d("LSA","called placeables ###")
                measurable.measure(constraints)
            }

            placeables.forEach { placeable ->
                /*
                    placeRelative(x,y)
                    자식 위젯을 특정 위치에 배치하는 역활을 하는 함수.
                    각 자식 위젯의 위치를 결정하는데 사용.
                 */
                placeable.placeRelative(x = indent, y = yCoord)
                indent += placeable.width + spacing
                yCoord += placeable.height + spacing
            }
        }
    }
}



@Composable
fun mainScreen() {
    /*
    Box(Modifier.size(120.dp,80.dp)) {
        ColorBox(
            Modifier
                .customLayout(250,200)
                .background(color= androidx.compose.ui.graphics.Color.Red)
        )
    }*/
    Box {
        CascadeLayout(spacing = 20) {
            Box(modifier = Modifier.size(60.dp).background(androidx.compose.ui.graphics.Color.Blue))
            Box(modifier = Modifier.size(80.dp, 40.dp).background(androidx.compose.ui.graphics.Color.Red))
            Box(modifier = Modifier.size(90.dp, 100.dp).background(androidx.compose.ui.graphics.Color.Cyan))
            Box(modifier = Modifier.size(50.dp).background(androidx.compose.ui.graphics.Color.Magenta))
            Box(modifier = Modifier.size(70.dp).background(androidx.compose.ui.graphics.Color.Green))
        }
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