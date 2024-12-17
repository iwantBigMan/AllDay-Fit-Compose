package com.example.allday_fit_compose


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment

class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {



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
                    onEditGoals = { WeeklyGoalInputDialog() }
                )
            }
        }
    }
}

@Composable
fun DialogContent(onClick : () -> Unit) {
    WeeklyGoalInputDialog(
        onClose = onClick,
        onAddGoal = {},
        onFinish = {},
        goalList = listOf("운동 30분", "스트레칭 10분")
    )
}

@Preview
@Composable
fun ExerciseRecordScreen(
    currentDate: String = "2023.12",
    weeklyRecords: List<WeeklyRecord> = emptyList(),
    weekGoals: List<WeekGoal> = emptyList(),
    onStartExercise: () -> Unit = {},
    onEditGoals: () -> Unit = {WeeklyGoalInputDialog()}
) {
    Scaffold(
        topBar = {
            Text(
                text = stringResource(R.string.exercise_record),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.blue))
                    .padding(10.dp)
                    .padding(horizontal = 13.dp)
                    .background(colorResource(id = R.color.blue)),
                textAlign = TextAlign.Start,
                color = androidx.compose.ui.graphics.Color.White,
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Date
            item {
                Text(
                    text = currentDate,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    textAlign = TextAlign.Center
                )
            }

            // Weekly Records
            item {
                WeeklyRecordSection(weeklyRecords)
            }

            // Exercise Button
            item {
                Button(
                    onClick = onStartExercise,
                    border = BorderStroke(1.dp, colorResource(id = R.color.white)),
                    modifier = Modifier
                        .size(350.dp, 20.dp)
                        .padding(vertical = 20.dp)
                        .padding(horizontal = 20.dp)
                        .aspectRatio(5f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.navy),
                    )
                ) {
                    Text(
                        text = stringResource(R.string.exercise_start),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
            // Proverb
            item {
                Text(
                    text = "기운과 끈기는 모든 것을 이겨낸다.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorResource(id = R.color.blue))
                        .padding(vertical = 15.dp),
                    color = androidx.compose.ui.graphics.Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    fontSize = 17.sp,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Week Goal Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp)
                        .padding(start = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.week_goal),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.weight(1f),
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    )
                    IconButton(onClick = onEditGoals) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.fix_goal_list_btn)
                        )
                    }
                }
            }

            // Week Goals
            items(weekGoals) { goal ->
                WeekGoalItem(goal)
            }
        }
    }
}

data class WeeklyRecord(
    val day: String,
    val completed: Boolean = false
)

data class WeekGoal(
    val description: String,
    val isCompleted: Boolean = false
)

@Composable
fun WeeklyRecordSection(records: List<WeeklyRecord>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
            WeeklyRecordItem(
                day = day,
                isCompleted = records.find { it.day == day }?.completed ?: false
            )
        }
    }
}

@Composable
fun WeeklyRecordItem(
    day: String,
    isCompleted: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(4.dp)
    ) {
        Text(text = day)
        Box(
            modifier = Modifier
                .size(30.dp)
                .background(
                    color = if (isCompleted) androidx.compose.ui.graphics.Color.Green else androidx.compose.ui.graphics.Color.Gray,
                    shape = MaterialTheme.shapes.small
                )
        )
    }
}

@Composable
fun WeekGoalItem(goal: WeekGoal) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = goal.isCompleted,
            onCheckedChange = { /* Handle goal completion */ }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = goal.description,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}