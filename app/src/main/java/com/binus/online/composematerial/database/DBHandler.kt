package com.binus.online.composematerial.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.binus.online.composematerial.domain.model.VictimModel
import com.binus.online.composematerial.utils.GeneralUtils

class DBHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "covid_tracking"
        private const val DATABASE_VERSION = 1
        object Victim: BaseColumns {
            const val TABLE_NAME = "victim"
            const val COLUMN_NAME = "name"
            const val COLUMN_GENDER = "gender"
            const val COLUMN_AGE = "age"
            const val COLUMN_STATUS = "status"
            const val COLUMN_DESC = "description"
            const val COLUMN_UPDATED_AT = "updated_at"
        }

        private const val CREATE_TABLE_VICTIM = """
            CREATE TABLE ${Victim.TABLE_NAME} (
                ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${Victim.COLUMN_NAME} TEXT,
                ${Victim.COLUMN_GENDER} TEXT,
                ${Victim.COLUMN_AGE} INTEGER,
                ${Victim.COLUMN_STATUS} TEXT,
                ${Victim.COLUMN_DESC} TEXT,
                ${Victim.COLUMN_UPDATED_AT} TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
        """
        private const val DROP_TABLE_VICTIM = "DROP TABLE IF EXISTS ${Victim.TABLE_NAME}"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_VICTIM)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE_VICTIM)
        onCreate(db)
    }

    fun getDataVictim(name: String? = null, status: String? = null): List<VictimModel> {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(
            Victim.TABLE_NAME,
            null,
            "${Victim.COLUMN_NAME} = ? OR ${Victim.COLUMN_STATUS} = ?",
            arrayOf(name, status),
            null,
            null,
            null
        )
        val victims = mutableListOf<VictimModel>()
        with(cursor) {
            while (moveToNext()) {
                victims.add(
                    VictimModel(
                        getInt(getColumnIndexOrThrow(BaseColumns._ID)),
                        getString(getColumnIndexOrThrow(Victim.COLUMN_NAME)),
                        VictimModel.Gender.valueOf(getString(getColumnIndexOrThrow(Victim.COLUMN_GENDER))),
                        getInt(getColumnIndexOrThrow(Victim.COLUMN_AGE)),
                        VictimModel.Status.valueOf(getString(getColumnIndexOrThrow(Victim.COLUMN_STATUS))),
                        getString(getColumnIndexOrThrow(Victim.COLUMN_DESC)),
                        GeneralUtils.formatDateTime(getString(getColumnIndexOrThrow(Victim.COLUMN_UPDATED_AT))),
                    )
                )
            }
        }
        cursor.close()
        db.close()

        return victims
    }

    fun insertVictim(name: String, gender: VictimModel.Gender, age: Int, status: VictimModel.Status, description: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(Victim.COLUMN_NAME, name)
            put(Victim.COLUMN_GENDER, gender.name)
            put(Victim.COLUMN_AGE, age)
            put(Victim.COLUMN_STATUS, status.name)
            put(Victim.COLUMN_DESC, description)
        }
        val insertId = db.insert(Victim.TABLE_NAME, null, contentValues)
        return insertId != -1L
    }

    fun getVictimById(victimId: String): VictimModel? {
        val db = this.readableDatabase
        var victim: VictimModel? = null
        val cursor = db.query(
            Victim.TABLE_NAME,
            null,
            "${BaseColumns._ID} = ?",
            arrayOf(victimId),
            null, null, null
        )

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(Victim.COLUMN_NAME))
                val gender = cursor.getString(cursor.getColumnIndexOrThrow(Victim.COLUMN_GENDER))
                val age = cursor.getInt(cursor.getColumnIndexOrThrow(Victim.COLUMN_AGE))
                val status = cursor.getString(cursor.getColumnIndexOrThrow(Victim.COLUMN_STATUS))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(Victim.COLUMN_DESC))
                val createdAt = cursor.getString(cursor.getColumnIndexOrThrow(Victim.COLUMN_UPDATED_AT))

                victim = VictimModel(id, name, VictimModel.Gender.valueOf(gender), age, VictimModel.Status.valueOf(status), description, GeneralUtils.formatDateTime(createdAt))
            }
            cursor.close()
        }
        return victim
    }
}