package com.example.allday_fit_compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.DialogFragment

class WeeklyGoalInputDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                WeeklyGoalInputDialogPreview()
            }
        }
    }
}

@Preview
@Composable
fun WeeklyGoalInputDialogPreview() {
    WeeklyGoalInputDialog(
        onClose = {},
        onAddGoal = {},
        onFinish = {},
        goalList = listOf("운동 30분", "스트레칭 10분")
    )
}

@Composable
fun WeeklyGoalInputDialog(
    onClose: () -> Unit,
    onAddGoal: (String) -> Unit,
    onFinish: () -> Unit,
    goalList: List<String>
) {
    ConstraintLayout(
        modifier = Modifier
            .wrapContentSize()
            .background(colorResource(id = R.color.none))
            .padding(10.dp)
    ) {
        val (closeBtn, dialogTitle, goalListView, goalEdit, addGoalBtn, finishBtn) = createRefs()

        IconButton(
            onClick = onClose,
            modifier = Modifier
                .constrainAs(closeBtn) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
                .size(48.dp)
                .background(colorResource(id = R.color.none))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }

        Text(
            text = stringResource(id = R.string.week_goal),
            modifier = Modifier.constrainAs(dialogTitle) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(closeBtn.top)
                bottom.linkTo(closeBtn.bottom)
            }
        )

        LazyColumn(
            modifier = Modifier
                .constrainAs(goalListView) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(dialogTitle.bottom)
                    bottom.linkTo(goalEdit.top)
                }
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            items(goalList.size) { index ->
                Text(text = goalList[index], modifier = Modifier.padding(8.dp))
            }
        }

        var goalText by remember { mutableStateOf("") }

        OutlinedTextField(
            value = goalText,
            onValueChange = { goalText = it },
            placeholder = { Text(text = stringResource(id = R.string.exercise_goal_add_edit_hint)) },
            modifier = Modifier
                .constrainAs(goalEdit) {
                    start.linkTo(parent.start)
                    end.linkTo(addGoalBtn.start)
                    top.linkTo(goalListView.bottom)
                    bottom.linkTo(finishBtn.top)
                }
                .fillMaxWidth()
                .padding(bottom = 30.dp)
                .height(50.dp),
            textStyle = LocalTextStyle.current.copy(fontSize = 12.sp),
            singleLine = true
        )

        IconButton(
            onClick = { onAddGoal(goalText) },
            modifier = Modifier
                .constrainAs(addGoalBtn) {
                    end.linkTo(goalEdit.end)
                    top.linkTo(goalEdit.top)
                    bottom.linkTo(goalEdit.bottom)
                }
                .size(48.dp)
                .background(colorResource(id = R.color.none))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = null,
                tint = Color.Black
            )
        }

        Button(
            onClick = onFinish,
            modifier = Modifier
                .constrainAs(finishBtn) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    top.linkTo(goalEdit.bottom)
                }
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = stringResource(id = R.string.edit_finish))
        }
    }
}