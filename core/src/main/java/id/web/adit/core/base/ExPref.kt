@file:Suppress("unused", "KDocUnresolvedReference")

package id.web.adit.core.base

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.web.adit.core.utils.ext.logD

// —————————————————————————————————————————————————————————————————
// @formatter:off ==> don't delete me
/** ————————————————————————————————————————————————————————————————
 *
 *
 * @author : Aditya Pratama
 * @create : Mei 2018
 *
 * @spec   : Key Value Storage like HAWK, but more simple ~
 *
 * @update : since June 2018 MDVKPref support object <T>
 *
 * @basic_sample :
 *
 * [PUT]    -> val a = "TEST".also { MDVKPref.putString(KEY, it) }
 *
 * [GET]    -> val b = MDVKPref.getString(KEY)
 * ------------------------------------------------------------------------------------
 */
object ExPref {

    private var mSharedPreferences: SharedPreferences? = null

    // init directly in baseApplication (require)
    fun init(context: Context?) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }
    private fun validateInitialization() {
        if (mSharedPreferences == null) {
            throw RuntimeException(" init this class to your fucking extends application class ~")
        }
    }


    // —————————————————————————————————————————————————————————————————
    /** [Integer]
    // —————————————————————————————————————————————————————————————————*/
    fun putInt(key: String?, value: Int) {
        val editor = mSharedPreferences!!.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return if (isKeyExists(key)) {
            mSharedPreferences!!.getInt(key, defaultValue)
        } else defaultValue
    }


    // —————————————————————————————————————————————————————————————————
    /** [Boolean]
    // —————————————————————————————————————————————————————————————————*/
    fun putBoolean(key: String?, value: Boolean) {
        val editor = mSharedPreferences!!.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return if (isKeyExists(key)) {
            mSharedPreferences!!.getBoolean(key, defaultValue)
        } else defaultValue
    }

    // —————————————————————————————————————————————————————————————————
    /** [Float]
    // —————————————————————————————————————————————————————————————————*/
    fun putFloat(key: String?, value: Float) {
        val editor = mSharedPreferences!!.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    fun getFloat(key: String, defaultValue: Float = 0F): Float {
        return if (isKeyExists(key)) {
            mSharedPreferences!!.getFloat(key, defaultValue)
        } else defaultValue
    }


    // —————————————————————————————————————————————————————————————————
    /** [Long]
    // —————————————————————————————————————————————————————————————————*/
    fun putLong(key: String?, value: Long) {
        val editor = mSharedPreferences!!.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun getLong(key: String, defaultValue: Long = 0L): Long {
        return if (isKeyExists(key)) {
            mSharedPreferences!!.getLong(key, defaultValue)
        } else defaultValue
    }


    // —————————————————————————————————————————————————————————————————
    /** [String]
    // —————————————————————————————————————————————————————————————————*/
    fun putString(key: String?, value: String?) {
        val editor = mSharedPreferences!!.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String, defaultValue: String? = ""): String? {
        return if (isKeyExists(key)) {
            mSharedPreferences!!.getString(key, defaultValue)
        } else defaultValue
    }

    // —————————————————————————————————————————————————————————————————
    /** [Object]
    // —————————————————————————————————————————————————————————————————*/
    fun <T> putObject(key: String?, `object`: T) {
        val objectString = Gson().toJson(`object`)
        val editor = mSharedPreferences!!.edit()
        editor.putString(key, objectString)
        editor.apply()
    }

    fun <T> getObject(key: String, classType: Class<T>? = null): T? {
        if (isKeyExists(key)) {
            val objectString =
                mSharedPreferences!!.getString(key, null)
            if (objectString != null) {
                return Gson().fromJson(objectString, classType)
            }
        }
        return null
    }


    // —————————————————————————————————————————————————————————————————
    /** [ObjectList]
    // —————————————————————————————————————————————————————————————————*/
    fun <T> putObjectsList(key: String?, objectList: List<T>?) {
        val objectString = Gson().toJson(objectList)
        val editor = mSharedPreferences!!.edit()
        editor.putString(key, objectString)
        editor.apply()
    }

    fun <T> getObjectsList(key: String, classType: Class<T>?): List<T>? {
        if (isKeyExists(key)) {
            val objectString = mSharedPreferences!!.getString(key, null)
            if (objectString != null) {
                val t: ArrayList<T> = Gson().fromJson(objectString, object : TypeToken<List<T>?>() {}.type)
                val finalList: MutableList<T> = ArrayList()
                for (i in t.indices) {
                    val s = t[i].toString()
                    finalList.add(Gson().fromJson(s, classType))
                }
                return finalList
            }
        }
        return null
    }

    fun clearAllPref() {
        val editor = mSharedPreferences!!.edit()
        editor.clear()
        editor.apply()
    }

    fun deleteSomePref(key: String): Boolean {
        if (isKeyExists(key)) {
            val editor = mSharedPreferences!!.edit()
            editor.remove(key)
            editor.apply()
            return true
        }
        return false
    }

    private fun isKeyExists(key: String): Boolean = mSharedPreferences!!.all.containsKey(key)

    // —————————————————————————————————————————————————————————————————
    /** [UTILS]
    // —————————————————————————————————————————————————————————————————*/
    fun getAllPrefToString(context: Context): String {
        var output = ""
        val keys = PreferenceManager.getDefaultSharedPreferences(context).all
        for ((key, value) in keys) {
            output += "$key : $value \n".also { logD(it) }
        }
        return output
    }

    fun getAllPrefToList(context: Context): MutableList<String> {
        val output = mutableListOf<String>()
        val keys = PreferenceManager.getDefaultSharedPreferences(context).all
        for ((key, value) in keys) {
            output += "$key : $value"
        }
        return output
    }

}
