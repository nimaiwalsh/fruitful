package com.example.android.fruitful.ui.detail

import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.fruitful.CoroutineTestRule
import com.example.android.fruitful.data.TaskDetail
import com.example.android.fruitful.data.TaskStatus
import com.example.android.fruitful.db.AppDatabase
import com.example.android.fruitful.ui.TAG_1
import com.example.android.fruitful.ui.TASK_1
import com.example.android.fruitful.ui.USER_CREATOR
import com.example.android.fruitful.ui.USER_OWNER
import com.example.android.fruitful.ui.createDatabase
import com.example.android.fruitful.usecase.FindTaskDetailUseCase
import com.example.android.fruitful.usecase.ToggleTaskStarStateUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class TaskDetailViewModelTest {

    // Room uses a dedicated thread for transactions, and it doesn't really work normally with
    // coroutines, so we need this rule to help interact with it.
    @get:Rule
    val countingTaskExecutorRule = CountingTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private lateinit var appDatabase: AppDatabase

    private fun createViewModel(): TaskDetailViewModel {
        return TaskDetailViewModel(
            FindTaskDetailUseCase(appDatabase.taskDao()),
            ToggleTaskStarStateUseCase(appDatabase),
            USER_OWNER
        )
    }

    @Before
    fun setup() {
        appDatabase = createDatabase()
    }

    @After
    fun tearDown() {
        appDatabase.close()
        countingTaskExecutorRule.drainTasks(100, TimeUnit.MILLISECONDS)
        assertThat(countingTaskExecutorRule.isIdle).isTrue()
    }

    @Test
    fun loadsDetails() = coroutineRule.runBlockingTest {
        val viewModel = createViewModel()
        var taskDetail: TaskDetail? = null
        val collectorJob = launch {
            viewModel.detail.collect {
                taskDetail = it
            }
        }

        // Initial state
        assertThat(taskDetail).isNull()

        // Load a TaskDetail.
        viewModel.taskId.value = TASK_1.id
        // Wait for Room transaction to finish.
        countingTaskExecutorRule.drainTasks(100, TimeUnit.MILLISECONDS)
        with(taskDetail!!) {
            assertThat(id).isEqualTo(TASK_1.id)
            assertThat(title).isEqualTo(TASK_1.title)
            assertThat(description).isEqualTo(TASK_1.description)
            assertThat(status).isEqualTo(TaskStatus.IN_PROGRESS)
            assertThat(owner).isEqualTo(USER_OWNER)
            assertThat(creator).isEqualTo(USER_CREATOR)
            assertThat(tags).containsExactly(TAG_1)
            assertThat(starUsers).hasSize(1)
        }

        collectorJob.cancel()
    }

    @Test
    fun toggleStarred() = coroutineRule.runBlockingTest {
        val viewModel = createViewModel()
        var isStarred = false
        val collectorJob = launch {
            viewModel.starred.collect {
                isStarred = it
            }
        }

        // Initial state.
        assertThat(isStarred).isFalse()

        // Load a TaskDetail.
        viewModel.taskId.value = TASK_1.id
        // Wait for Room transaction to finish.
        countingTaskExecutorRule.drainTasks(100, TimeUnit.MILLISECONDS)
        assertThat(isStarred).isTrue()

        // Toggle star.
        viewModel.toggleTaskStarState()
        countingTaskExecutorRule.drainTasks(100, TimeUnit.MILLISECONDS)
        assertThat(isStarred).isFalse()

        // Toggle again.
        viewModel.toggleTaskStarState()
        countingTaskExecutorRule.drainTasks(100, TimeUnit.MILLISECONDS)
        assertThat(isStarred).isTrue()

        collectorJob.cancel()
    }
}
