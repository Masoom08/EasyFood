package st.masoom.easyfood

import android.app.Application
import st.masoom.easyfood.Local.MealDatabase
import st.masoom.easyfood.Local.MealEntity

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MealDatabase.getDatabase(this)
    }
}
