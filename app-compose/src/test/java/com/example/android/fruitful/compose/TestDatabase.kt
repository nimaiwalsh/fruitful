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

package com.example.android.fruitful.compose

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.android.fruitful.compose.data.SeedData
import com.example.android.fruitful.compose.db.AppDatabase
import kotlinx.coroutines.runBlocking

fun createTestDatabase(): AppDatabase {
    return Room
        .inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
        .allowMainThreadQueries()
        .build()
        .apply {
            runBlocking {
                with(taskDao()) {
                    insertUsers(SeedData.Users)
                    insertTags(SeedData.Tags)
                    insertTasks(SeedData.Tasks)
                    insertTaskTags(SeedData.TaskTags)
                    insertUserTasks(SeedData.UserTasks)
                }
            }
        }
}
