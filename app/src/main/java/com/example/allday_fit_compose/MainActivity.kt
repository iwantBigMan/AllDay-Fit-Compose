package com.example.allday_fit_compose


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

/*
  아침에 캘린더 밑 바텀 내비게이션 아이템 터치 이벤트
 */


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Preview
@Composable
fun MainScreen() {
    // 네비게이션 컨트롤러 생성
    val navController = rememberNavController()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // 상단 툴바
        Toolbar()

        // 메인 컨텐츠 네비게이션 영역
        Box(
            modifier = Modifier
                .weight(1f)
                .background(colorResource(id = R.color.white))
                .fillMaxWidth()
        ) {
            MainNavHost(navController = navController)
        }

        // 하단 네비게이션
        MainBottomNavigation(navController = navController)
    }
}

@Suppress("UNREACHABLE_CODE")
@Composable
fun Toolbar() {
    val titleFont = FontFamily(
        Font(R.font.i_am_a_player, FontWeight.Normal)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.blue))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            color = Color.White,
            fontFamily = titleFont
        )
    }
}

@Composable
fun MainNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "MainFragment"// 시작 화면

    ) {
        composable("communityMainFragment") {
            Text("Community Screen") // 실제 UI 컴포저블로 교체
        }
        composable("dietRecordFragment") {
            Text("Diet Record Screen")
        }
        composable("MainFragment") {
            // 메인화면 컴포저블로 교체
            MainFragmentContent()
        }
        composable("exerciseStatusFragment") {
            Text("Exercise Status Screen")
        }
        composable("settingMainFragment") {
            Text("Settings Screen")
        }
    }
}

@Composable
fun MainFragmentContent() {
    ExerciseRecordScreen(
        currentDate = "2023.12",
        weeklyRecords = listOf(
            WeeklyRecord("Sun", completed = true),
            WeeklyRecord("Mon"),
            WeeklyRecord("Tue", completed = true),
            WeeklyRecord("Wed"),
            WeeklyRecord("Thu"),
            WeeklyRecord("Fri", completed = true),
            WeeklyRecord("Sat")
        ),
        weekGoals = listOf(
            WeekGoal("운동 3회 이상"),
            WeekGoal("하루 1만 보폭"),
            WeekGoal("하루 2L 물 마시기")
        ),
        onStartExercise = {},
        onEditGoals = {}
    )
}


@Composable
fun MainBottomNavigation(navController: NavHostController) {
    // 메뉴 항목 정의
    val items = listOf(
        BottomNavItem(
            route = "communityMainFragment",
            icon = R.drawable.ic_commity,
            label = R.string.commity
        ),
        BottomNavItem(
            route = "dietRecordFragment",
            icon = R.drawable.ic_diet,
            label = R.string.diet_record
        ),
        BottomNavItem(
            route = "MainFragment",
            icon = R.drawable.ic_main,
            label = R.string.main_page
        ),
        BottomNavItem(
            route = "exerciseStatusFragment",
            icon = R.drawable.ic_mypage,
            label = R.string.exercise_sit
        ),
        BottomNavItem(
            route = "settingMainFragment",
            icon = R.drawable.ic_menu,
            label = R.string.preferences
        )
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxWidth()
    ) {
        items.forEach { item ->
            val isSelected = navController.currentDestination?.route == item.route

            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = stringResource(id = item.label),
                        // 아이콘의 크기를 24dp로 설정
                        modifier = Modifier.size(20.dp)
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = item.label), fontSize = 6.sp
                    )
                },
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(item.route) {
                            // 현재 라우트 스택 유지
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                alwaysShowLabel = true
            )
        }
    }
}

// 데이터 클래스 정의
data class BottomNavItem(
    val route: String,
    val icon: Int,
    val label: Int
)


@Composable
fun BottomNavigation(
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .background(Color.White)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = MaterialTheme.shapes.small
            ),
        horizontalArrangement = Arrangement.SpaceEvenly,  // 균등한 간격으로 배치
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}

@Composable
fun BottomNavigationItem(
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit,
    selected: Boolean,
    onClick: () -> Unit,
    alwaysShowLabel: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        modifier = modifier
            .fillMaxWidth(1f / 5f)  // 5개의 아이템이므로 각각 1/5 너비
            .clickable { onClick() }
            //아이템 간격 일정하게
            .padding(horizontal = 8.dp)
            // 상단 하단 패딩 8
            .padding(vertical = 8.dp)
    )

    {
        icon()
        if (alwaysShowLabel) {
            Spacer(modifier = Modifier.height(4.dp))
            label()
        }
    }
}





