/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nims.fruitful.app.usecase

import androidx.room.withTransaction
import com.nims.fruitful.app.data.User
import com.nims.fruitful.app.data.UserTask
import com.nims.fruitful.app.db.AppDatabase
import com.nims.fruitful.app.db.dao.TaskDao
import javax.inject.Inject

/**
 * Toggles the star state for the task.
 */
class ToggleTaskStarStateUseCase @Inject constructor(
    private val db: AppDatabase,
    private val taskDao: TaskDao = db.taskDao()
) {
    /**
     * Toggles the star state for the task.
     */
    suspend operator fun invoke(taskId: Long, currentUser: User) {
        db.withTransaction {
            val userTask = taskDao.getUserTask(taskId, currentUser.id)
            if (userTask != null) {
                taskDao.deleteUserTasks(listOf(userTask))
            } else {
                taskDao.insertUserTasks(listOf(UserTask(userId = currentUser.id, taskId = taskId)))
            }
        }
    }
}
