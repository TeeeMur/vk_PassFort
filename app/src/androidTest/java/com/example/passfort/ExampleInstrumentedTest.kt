package com.example.passfort

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.passfort.dbentity.PasswordRecordEntity
import com.example.passfort.model.PassFortDB
import com.example.passfort.model.dao.PasswordDao
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private lateinit var passwordDao: PasswordDao
    private lateinit var db: PassFortDB

    @Before
    fun createDb() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(appContext, PassFortDB::class.java).build()
        passwordDao = db.getPasswordDao()
    }

    @Test
    fun useAppContext() {
        runBlocking { passwordDao.upsertObj(
            PasswordRecordEntity(0, "name1", "login1", "password1", LocalDate.now(), 60, 1, false, LocalDateTime.now())
        ) }
    }
}