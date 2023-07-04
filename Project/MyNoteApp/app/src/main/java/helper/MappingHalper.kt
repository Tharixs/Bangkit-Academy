package helper

import Entity.Note
import android.database.Cursor
import db.DatabaseConstract.NoteColumns.Companion.DATE
import db.DatabaseConstract.NoteColumns.Companion.DESCRIPTION
import db.DatabaseConstract.NoteColumns.Companion.TITLE
import db.DatabaseConstract.NoteColumns.Companion._ID

object MappingHelper {

    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<Note> {
        val notesList = ArrayList<Note>()
        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(_ID))
                val title = getString(getColumnIndexOrThrow(TITLE))
                val description = getString(getColumnIndexOrThrow(DESCRIPTION))
                val date = getString(getColumnIndexOrThrow(DATE))
                notesList.add(Note(id, title, description, date))
            }
        }
        return notesList
    }
}