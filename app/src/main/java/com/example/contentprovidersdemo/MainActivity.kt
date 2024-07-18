package com.example.contentprovidersdemo

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        getSMS()
        getCallLog()
        getSystemPreferences()
       // getAppsData()
        addTask(101,"AAA",1,true)
        addTask(102,"BBB",2,true)
        addTask(103,"CCC",3,true)
        addTask(104,"DDD",6,false)
        addTask(105,"EEE",4,true)
        getAllTasks()
        getTaskById(101)
    }
    private fun addTask(taskId:Int, title: String, priority: Int, isComplete: Boolean){
        var contentUri = Uri.parse("content://com.example.contentprovidersdemo")
        contentUri = Uri.withAppendedPath(contentUri,"tasks")
        val values = ContentValues()

        values.put("task_id","$taskId")
        values.put("task_title", title)
        values.put("task_priority","$priority")
        values.put("task_completion","$isComplete")

       val uriToNewTask = contentResolver.insert(contentUri,values)

        Log.d("New Task", uriToNewTask.toString())

        val cursor = contentResolver.query(uriToNewTask!!,null,null,null)
        for(eachColumns in cursor!!.columnNames){
            Log.d("NEW TASK", eachColumns)
        }
        cursor.close()
    }
    private fun getAllTasks(){
        var contentUri = Uri.parse("content://com.example.contentprovidersdemo")
        contentUri = Uri.withAppendedPath(contentUri,"tasks")
        val cursor = contentResolver.query(contentUri,null,null,null)
        for(eachColumns in cursor!!.columnNames){
            Log.d("ALL TASK", eachColumns)
        }
        cursor.close()
    }
    private fun getTaskById(id: Int){
        var contentUri = Uri.parse("content://com.example.contentprovidersdemo")
        contentUri = Uri.withAppendedPath(contentUri,"tasks")
        contentUri = Uri.withAppendedPath(contentUri,id.toString())
        val cursor = contentResolver.query(contentUri,null,null,null)
        while (cursor!!.moveToNext()){
            Log.d("By ID","${cursor.getInt(0)} -- ${cursor.getString(1)} -- ${cursor.getInt(2)}")
        }
    }


    private fun getSMS(){
        val contentUri = Uri.parse("content://sms")
        val cursor = contentResolver.query(contentUri,null,null,null)
        for(eachColumns in cursor!!.columnNames){
            Log.d("SMS Columns","$eachColumns")
        }
        cursor.close()

    }
    private fun getCallLog(){
        val contentUri = android.provider.CallLog.Calls.CONTENT_URI
        val cursor = contentResolver.query(contentUri,null,null,null)
        for(eachColumns in cursor!!.columnNames){
            Log.d("CALL LOG","$eachColumns")
        }
        cursor.close()
    }
    private fun getSystemPreferences(){
        val contentUri = android.provider.Settings.System.CONTENT_URI
        val cursor = contentResolver.query(contentUri,null,null,null)
        for(eachColumns in cursor!!.columnNames){
            Log.d("SYSTEM SETTINGS","$eachColumns")
        }
        cursor.close()
    }
//    private fun getAppsData(){
//        val authority = "com.example.storedataapp/databases/db_users"
//        val contentUri = Uri.parse("content://$authority/data")
//        val cursor = contentResolver.query(contentUri,null,null,null)
//        for(eachColumns in cursor!!.columnNames){
//            Log.d("APP DATA","${eachColumns}")
//        }
//        cursor.close()
//    }
}