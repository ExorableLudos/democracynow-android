/*
 * Copyright (C) 2017 Democracy Droid
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
package com.workingagenda.democracydroid.ui.main.mvp

import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuItem
import com.workingagenda.democracydroid.R
import com.workingagenda.democracydroid.ui.FragmentRefreshListener
import com.workingagenda.democracydroid.ui.about.AboutActivity
import com.workingagenda.democracydroid.ui.main.MainActivity
import com.workingagenda.democracydroid.ui.settings.SettingsActivity
import com.workingagenda.democracydroid.util.SharedPreferenceManager

class MainModel(private var activity:MainActivity,
                private val preferenceManager: SharedPreferenceManager) {

    fun onOptionsItemSelected(item: MenuItem): Boolean{
        val id = item.itemId
        when(id){
            R.id.action_settings -> {
                val intent = Intent(activity, SettingsActivity::class.java)
                activity.startActivity(intent)
                return true
            }
            R.id.action_refresh -> {
                item.isEnabled = false
                for (x in activity.supportFragmentManager.fragments) {
                    (x as? FragmentRefreshListener)?.refresh()
                }
                item.isEnabled = true
                return true
            }
            R.id.action_donate -> actionViewIntent("https://www.democracynow.org/donate")
            R.id.action_exclusives -> actionViewIntent("https://www.democracynow.org/categories/web_exclusive")
            R.id.action_site -> actionViewIntent("http://www.democracynow.org/")
            R.id.action_about -> {
                val intent = Intent(activity, AboutActivity::class.java)
                activity.startActivityForResult(intent, 0)
            }
        }
        return true
    }

    private fun actionViewIntent(url:String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        activity.startActivity(i)
    }

    fun onCreateOptionsMenu(menu: Menu): Boolean {
        activity.menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    fun getTabPreference():Int{
        return preferenceManager.getTabPreference()
    }
}