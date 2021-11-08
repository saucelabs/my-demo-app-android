package com.saucelabs.mydemoapp.android.utils;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.saucelabs.mydemoapp.android.database.AppDao;
import com.saucelabs.mydemoapp.android.database.AppDatabase;
import com.saucelabs.mydemoapp.android.model.ProductModel;

import java.util.List;

public class DatabaseRepository {
    private AppDao noteDao;
    private LiveData<List<ProductModel>> allNotes;
    private LiveData<Boolean> dataInserted;

    public DatabaseRepository(Application application) { //application is subclass of context
        AppDatabase database = AppDatabase.getInstance(application);
        noteDao = database.personDao();

    }

    public void insert(ProductModel note) {
        new InsertNoteAsyncTask(noteDao).execute(note);
    }


    public void insert(List<ProductModel> note) {
        new InsertProductListAsyncTask(noteDao,note).execute();
    }

    public void update(ProductModel note) {
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(ProductModel note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    public LiveData<List<ProductModel>> getAllNotes() {
        return allNotes;
    }

    private static class InsertProductListAsyncTask extends AsyncTask<ProductModel, Void, Void> { //static : doesnt have reference to the
        // repo itself otherwise it could cause memory leak!
        private AppDao noteDao;
        private List<ProductModel> list;
        private InsertProductListAsyncTask(AppDao noteDao,List<ProductModel> list) {
            this.noteDao = noteDao;
            this.list = list;
        }
        @Override
        protected Void doInBackground(ProductModel... notes) { // ...  is similar to array
            noteDao.insertProduct(list); //single note
            return null;
        }
    }

    private static class InsertNoteAsyncTask extends AsyncTask<ProductModel, Void, Void> { //static : doesnt have reference to the
        // repo itself otherwise it could cause memory leak!
        private AppDao noteDao;
        private InsertNoteAsyncTask(AppDao noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(ProductModel... notes) { // ...  is similar to array
            noteDao.insertProduct(notes[0]); //single note
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<ProductModel, Void, Void> {
        private AppDao noteDao;
        private UpdateNoteAsyncTask(AppDao noteDao) { //constructor as the class is static
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(ProductModel... notes) {
//            noteDao.Update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<ProductModel, Void, Void> {
        private AppDao noteDao;
        private DeleteNoteAsyncTask(AppDao noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(ProductModel... notes) {
//            noteDao.Delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private AppDao noteDao;
        private DeleteAllNotesAsyncTask(AppDao noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
//            noteDao.DeleteAllNotes();
            return null;
        }
    }
}
