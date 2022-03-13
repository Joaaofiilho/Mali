package com.joaoferreira.mali.core

import android.app.Application
import com.joaoferreira.data.database.MaliDatabase

class MaliApplication: Application() {
    val database: MaliDatabase by lazy { MaliDatabase.getDatabase(this) }
}