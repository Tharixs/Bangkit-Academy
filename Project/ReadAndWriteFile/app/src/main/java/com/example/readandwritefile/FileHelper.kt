package com.example.readandwritefile

import android.content.Context

internal object FileHelper {

    fun witeToFile(fileModel: FileModel, contex: Context) {
        contex.openFileOutput(fileModel.fileName, Context.MODE_PRIVATE).use {
            it.write(fileModel.data?.toByteArray())
        }
    }

    fun readFromFile(fileName: String, contex: Context): FileModel {
        val fileModel = FileModel()
        fileModel.fileName = fileName
        fileModel.data = contex.openFileInput(fileName).bufferedReader().useLines { lines ->
            lines.fold("") { some, text ->
                "$some\n$text"
            }
        }
        return fileModel
    }

}