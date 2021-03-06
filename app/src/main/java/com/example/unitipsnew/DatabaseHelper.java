package com.example.unitipsnew;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.unitipsnew.Eventi.Evento;
import com.example.unitipsnew.Recensioni.Corso;
import com.example.unitipsnew.Recensioni.Recensione;
import com.example.unitipsnew.Tips.Commento;
import com.example.unitipsnew.Tips.Tip;
import com.example.unitipsnew.Utente.Utente;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "uniTipsDB";

    // Table Names
    private static final String TABLE_USER = "utente";
    private static final String TABLE_CORSO = "corso";
    private static final String TABLE_RECENSIONE = "recensione";
    private static final String TABLE_EVENTO = "evento";
    private static final String TABLE_TIP = "tip";
    private static final String TABLE_COMMENTO = "commento";

    // USER Table - column names
    private static final String KEY_MATRICOLA = "matricola";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NOME = "nome";
    private static final String KEY_COGNOME = "cognome";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_AVATAR = "avatar";

    // CORSO Table - column names
    private static final String KEY_ID_CORSO = "id";
    private static final String KEY_NOME_CORSO = "nome";
    private static final String KEY_PROFESSORE = "professore";
    private static final String KEY_RECENSIONI = "recensioni";

    // RECENSIONE Table - column names
    private static final String KEY_ID_RECENSIONE = "id";
    private static final String KEY_ID_CORSO_RECENSIONE = "idCorso";
    private static final String KEY_MATRICOLA_RECENSIONE = "matricola";
    private static final String KEY_TITOLO_RECENSIONE = "titolo";
    private static final String KEY_TESTO_RECENSIONE = "testo";
    private static final String KEY_DATA_RECENSIONE = "data";

    // EVENTO Table - column names
    private static final String KEY_ID_EVENTO = "id";
    private static final String KEY_IMMAGINE_EVENTO = "immagine";
    private static final String KEY_INTERESSATI = "interessati";
    private static final String KEY_TITOLO_EVENTO = "titolo";
    private static final String KEY_DESCRIZIONE_EVENTO = "descrizione";
    private static final String KEY_LUOGO_EVENTO = "luogo";
    private static final String KEY_DATA_EVENTO = "data";

    // TIP Table - column names
    private static final String KEY_ID_TIP = "id";
    private static final String KEY_MATRICOLA_TIP = "matricola";
    private static final String KEY_TITOLO_TIP = "titolo";
    private static final String KEY_TESTO_TIP = "testo";
    private static final String KEY_LIKE_TIP = "likes";
    private static final String KEY_DISLIKE_TIP = "dislikes";
    private static final String KEY_COMMENTI_TIP = "commenti";
    private static final String KEY_DATA_TIP = "data";

    // COMMENTO Table - column names
    private static final String KEY_ID_COMMENTO = "id";
    private static final String KEY_ID_TIP_COMMENTO = "idTip";
    private static final String KEY_MATRICOLA_COMMENTO = "matricola";
    private static final String KEY_TESTO_COMMENTO = "testo";
    private static final String KEY_DATA_COMMENTO = "data";

    // Table Create Statements
    // USER table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "(" +
            KEY_MATRICOLA + " LONG PRIMARY KEY," +
            KEY_EMAIL + " TEXT," +
            KEY_NOME + " TEXT," +
            KEY_COGNOME + " TEXT," +
            KEY_PASSWORD + " TEXT," +
            KEY_AVATAR + " TEXT" + ")";

    // CORSO table create statement
    private static final String CREATE_TABLE_CORSO = "CREATE TABLE " + TABLE_CORSO + "(" +
            KEY_ID_CORSO + " LONG PRIMARY KEY," +
            KEY_NOME_CORSO + " TEXT," +
            KEY_PROFESSORE + " TEXT," +
            KEY_RECENSIONI + " INT" + ")";

    // RECENSIONE table create statement
    private static final String CREATE_TABLE_RECENSIONE = "CREATE TABLE " + TABLE_RECENSIONE + "(" +
            KEY_ID_RECENSIONE + " LONG PRIMARY KEY," +
            KEY_ID_CORSO_RECENSIONE + " LONG," +
            KEY_MATRICOLA_RECENSIONE + " LONG," +
            KEY_TITOLO_RECENSIONE + " TEXT," +
            KEY_TESTO_RECENSIONE + " TEXT," +
            KEY_DATA_RECENSIONE + " DATE" + ")";

    // EVENTO table create statement
    private static final String CREATE_TABLE_EVENTO = "CREATE TABLE " + TABLE_EVENTO + "(" +
            KEY_ID_EVENTO + " INT PRIMARY KEY," +
            KEY_IMMAGINE_EVENTO + " TEXT," +
            KEY_INTERESSATI + " TEXT," +
            KEY_TITOLO_EVENTO + " TEXT," +
            KEY_DESCRIZIONE_EVENTO + " TEXT," +
            KEY_LUOGO_EVENTO + " TEXT," +
            KEY_DATA_EVENTO + " DATE" + ")";

    // TIP table create statement
    private static final String CREATE_TABLE_TIP = "CREATE TABLE " + TABLE_TIP + "(" +
            KEY_ID_TIP + " INT PRIMARY KEY," +
            KEY_TITOLO_TIP + " TEXT," +
            KEY_TESTO_TIP + " TEXT," +
            KEY_LIKE_TIP + " TEXT," +
            KEY_DISLIKE_TIP + " TEXT," +
            KEY_COMMENTI_TIP + " TEXT," +
            KEY_DATA_TIP + " DATE" + ")";

    // COMMENTO table create statement
    private static final String CREATE_TABLE_COMMENTO = "CREATE TABLE " + TABLE_COMMENTO + "(" +
            KEY_ID_COMMENTO + " INT PRIMARY KEY," +
            KEY_ID_TIP_COMMENTO + " LONG," +
            KEY_MATRICOLA_COMMENTO + " TEXT," +
            KEY_TESTO_COMMENTO + " TEXT," +
            KEY_DATA_COMMENTO + " INT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating required tables
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_CORSO);
        db.execSQL(CREATE_TABLE_RECENSIONE);
        db.execSQL(CREATE_TABLE_EVENTO);
        db.execSQL(CREATE_TABLE_TIP);
        db.execSQL(CREATE_TABLE_COMMENTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CORSO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECENSIONE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTO);

        // create new tables
        onCreate(db);
    }

/*##################################################################################################
                                            USER
##################################################################################################*/

    /*
     * Creating a user
     */
    public long createUser(Utente user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MATRICOLA, user.getMatricola());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_NOME, user.getNome());
        values.put(KEY_COGNOME, user.getCognome());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_AVATAR, user.getImmagine());

        // insert row
        return db.insert(TABLE_USER, null, values);
    }

    /*
     * Updating a user
     */
    public int updateUser(Utente user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MATRICOLA, user.getMatricola());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_NOME, user.getNome());
        values.put(KEY_COGNOME, user.getCognome());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_AVATAR, user.getImmagine());

        // updating row
        return db.update(TABLE_USER, values, KEY_MATRICOLA + " = ?",
                new String[]{String.valueOf(user.getMatricola())});
    }

    /*
     * Deleting a user
     */
    public void deleteUser(long user_matricola) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_MATRICOLA + " = ?",
                new String[]{String.valueOf(user_matricola)});
    }

    /*
     * get single user
     */
    public Utente getUser(double matricola) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                    + KEY_MATRICOLA + " = " + matricola;

            Cursor c = db.rawQuery(selectQuery, null);
            Utente u = new Utente();
            if (c != null) {
                c.moveToFirst();
                u.setMatricola(c.getLong(c.getColumnIndex(KEY_MATRICOLA)));
                u.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
                u.setNome(c.getString(c.getColumnIndex(KEY_NOME)));
                u.setCognome(c.getString(c.getColumnIndex(KEY_COGNOME)));
                u.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
                u.setImmagine(c.getString(c.getColumnIndex(KEY_AVATAR)));
                c.close();
            }
            return u;
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * getting all users
     * */
    public List<Utente> getAllUsers() {
        List<Utente> users = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Utente u = new Utente();
                u.setMatricola(c.getLong(c.getColumnIndex(KEY_MATRICOLA)));
                u.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
                u.setNome(c.getString(c.getColumnIndex(KEY_NOME)));
                u.setCognome(c.getString(c.getColumnIndex(KEY_COGNOME)));
                u.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
                u.setImmagine(c.getString(c.getColumnIndex(KEY_AVATAR)));

                // adding to users list
                users.add(u);
            } while (c.moveToNext());
            c.close();
        }

        return users;
    }

/*##################################################################################################
                                            CORSO
##################################################################################################*/

    /*
     * Creating a corso
     */
    public long createCorso(Corso corso) {
        SQLiteDatabase db = this.getWritableDatabase();

        long lastId = 7;
        if (corso.getId() != -1) {
            lastId = corso.getId() - 1;
        } else if (!getAllCorsi().isEmpty()) {
            lastId = getAllCorsi().get(getAllCorsi().size() - 1).getId();
        }

        ContentValues values = new ContentValues();
        values.put(KEY_ID_CORSO, lastId + 1);
        values.put(KEY_NOME_CORSO, corso.getNomeCorso());
        values.put(KEY_PROFESSORE, corso.getNomeProfessore());
        values.put(KEY_RECENSIONI, 0);

        // insert row
        return db.insert(TABLE_CORSO, null, values);
    }

    /*
     * get single corso
     */
    public Corso getCorso(long corso_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_CORSO + " WHERE "
                + KEY_ID_CORSO + " = " + corso_id;

        Cursor c = db.rawQuery(selectQuery, null);
        Corso corso = new Corso();
        if (c != null) {
            c.moveToFirst();
            corso.setId(c.getLong(c.getColumnIndex(KEY_ID_CORSO)));
            corso.setNomeCorso(c.getString(c.getColumnIndex(KEY_NOME_CORSO)));
            corso.setNomeProfessore(c.getString(c.getColumnIndex(KEY_PROFESSORE)));
            corso.setNumeroRecensioni(c.getLong(c.getColumnIndex(KEY_RECENSIONI)));
            c.close();
        }
        return corso;
    }

    /*
     * getting all corso
     * */
    public List<Corso> getAllCorsi() {
        List<Corso> corsi = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CORSO;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Corso corso = new Corso();
                corso.setId(c.getLong(c.getColumnIndex(KEY_ID_CORSO)));
                corso.setNomeCorso(c.getString(c.getColumnIndex(KEY_NOME_CORSO)));
                corso.setNomeProfessore(c.getString(c.getColumnIndex(KEY_PROFESSORE)));
                corso.setNumeroRecensioni(c.getLong(c.getColumnIndex(KEY_RECENSIONI)));

                // adding to corso list
                corsi.add(corso);
            } while (c.moveToNext());
            c.close();
        }

        return corsi;
    }

    /*
     * getting all corsi with match name
     * */
    public List<Corso> getCorsiNome(String nome) {
        List<Corso> corsi = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CORSO;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Corso corso = new Corso();
                corso.setId(c.getLong(c.getColumnIndex(KEY_ID_CORSO)));
                corso.setNomeCorso(c.getString(c.getColumnIndex(KEY_NOME_CORSO)));
                corso.setNomeProfessore(c.getString(c.getColumnIndex(KEY_PROFESSORE)));
                corso.setNumeroRecensioni(c.getLong(c.getColumnIndex(KEY_RECENSIONI)));

                // adding to corso list

                if (corso.getNomeCorso().toLowerCase().contains(nome.toLowerCase())) {
                    corsi.add(corso);
                }
            } while (c.moveToNext());
            c.close();
        }

        return corsi;
    }

    /*
     * getting all corsi with match prof
     * */
    public List<Corso> getCorsiProf(String nome) {
        List<Corso> corsi = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CORSO;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Corso corso = new Corso();
                corso.setId(c.getLong(c.getColumnIndex(KEY_ID_CORSO)));
                corso.setNomeCorso(c.getString(c.getColumnIndex(KEY_NOME_CORSO)));
                corso.setNomeProfessore(c.getString(c.getColumnIndex(KEY_PROFESSORE)));
                corso.setNumeroRecensioni(c.getLong(c.getColumnIndex(KEY_RECENSIONI)));

                // adding to corso list

                if (corso.getNomeProfessore().toLowerCase().contains(nome.toLowerCase())) {
                    corsi.add(corso);
                }
            } while (c.moveToNext());
            c.close();
        }

        return corsi;
    }

    /*
     * Updating a corso
     */
    public int updateCorso(Corso corso) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_CORSO, corso.getId());
        values.put(KEY_NOME_CORSO, corso.getNomeCorso());
        values.put(KEY_PROFESSORE, corso.getNomeProfessore());
        values.put(KEY_RECENSIONI, corso.getNumeroRecensioni());

        // updating row
        return db.update(TABLE_CORSO, values, KEY_ID_CORSO + " = ?",
                new String[]{String.valueOf(corso.getId())});
    }

    /*
     * Deleting a corso
     */
    public void deleteCorso(long corso_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        // before deleting corso
        // check if recensioni under this corso should also be deleted

        // get all todos under this tag
        List<Recensione> allRecensioniOfCorso = getAllRecensionesByCorso(corso_id);

        // delete all todos
        for (Recensione recensione : allRecensioniOfCorso) {
            // delete recensione
            deleteRecensione(recensione.getId_recensione());
        }

        // now delete the tag
        db.delete(TABLE_CORSO, KEY_ID_CORSO + " = ?",
                new String[]{String.valueOf(corso_id)});
    }

/*##################################################################################################
                                            RECENSIONE
##################################################################################################*/

    /*
     * Creating a recensione
     */
    public long createRecensione(Recensione recensione) {
        SQLiteDatabase db = this.getWritableDatabase();

        long lastId = 0;
        if (!getAllRecensioni().isEmpty()) {
            lastId = getAllRecensioni().get(getAllRecensioni().size() - 1).getId_recensione();
        }
        ContentValues values = new ContentValues();
        values.put(KEY_ID_RECENSIONE, lastId + 1);
        values.put(KEY_ID_CORSO_RECENSIONE, recensione.getId_corso());
        values.put(KEY_MATRICOLA_RECENSIONE, recensione.getMatricola());
        values.put(KEY_TITOLO_RECENSIONE, recensione.getTitle());
        values.put(KEY_TESTO_RECENSIONE, recensione.getText());
        values.put(KEY_DATA_RECENSIONE, getDateTime());

        // insert row
        long recensione_id = db.insert(TABLE_RECENSIONE, null, values);

        Corso c = getCorso(recensione.getId_corso());
        c.setNumeroRecensioni(c.getNumeroRecensioni() + 1);
        updateCorso(c);

        return recensione_id;
    }

    /*
     * get single recensione
     */
    public Recensione getRecensione(long recensione_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_RECENSIONE + " WHERE "
                + KEY_ID_RECENSIONE + " = " + recensione_id;

        Cursor c = db.rawQuery(selectQuery, null);
        Recensione recensione = new Recensione();

        if (c != null) {
            c.moveToFirst();
            recensione.setId_recensione(c.getLong(c.getColumnIndex(KEY_ID_RECENSIONE)));
            recensione.setId_corso(c.getLong(c.getColumnIndex(KEY_ID_CORSO)));
            recensione.setMatricola(c.getLong(c.getColumnIndex(KEY_MATRICOLA_RECENSIONE)));
            recensione.setTitle((c.getString(c.getColumnIndex(KEY_TITOLO_RECENSIONE))));
            recensione.setText((c.getString(c.getColumnIndex(KEY_TESTO_RECENSIONE))));
            recensione.setDate((c.getString(c.getColumnIndex(KEY_DATA_RECENSIONE))));
            c.close();
        }

        return recensione;
    }

    /*
     * getting all recensione
     * */
    public List<Recensione> getAllRecensioni() {
        List<Recensione> recensioni = new ArrayList<Recensione>();
        String selectQuery = "SELECT  * FROM " + TABLE_RECENSIONE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Recensione recensione = new Recensione();
                recensione.setId_recensione(c.getLong(c.getColumnIndex(KEY_ID_RECENSIONE)));
                recensione.setId_corso(c.getLong(c.getColumnIndex(KEY_ID_CORSO)));
                recensione.setMatricola(c.getLong(c.getColumnIndex(KEY_MATRICOLA_RECENSIONE)));
                recensione.setTitle((c.getString(c.getColumnIndex(KEY_TITOLO_RECENSIONE))));
                recensione.setText((c.getString(c.getColumnIndex(KEY_TESTO_RECENSIONE))));
                recensione.setDate((c.getString(c.getColumnIndex(KEY_DATA_RECENSIONE))));

                // adding to recensioni list
                recensioni.add(recensione);
            } while (c.moveToNext());
            c.close();
        }

        return recensioni;
    }

    /*
     * getting all recensione under single corso
     * */
    public ArrayList<Recensione> getAllRecensionesByCorso(long corso_id) {
        ArrayList<Recensione> recensioni = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_RECENSIONE + " WHERE " + KEY_ID_CORSO_RECENSIONE + " = " + corso_id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Recensione recensione = new Recensione();
                recensione.setId_recensione(c.getLong(c.getColumnIndex(KEY_ID_RECENSIONE)));
                recensione.setId_corso(c.getLong(c.getColumnIndex(KEY_ID_CORSO)));
                recensione.setMatricola(c.getLong(c.getColumnIndex(KEY_MATRICOLA_RECENSIONE)));
                recensione.setTitle((c.getString(c.getColumnIndex(KEY_TITOLO_RECENSIONE))));
                recensione.setText((c.getString(c.getColumnIndex(KEY_TESTO_RECENSIONE))));
                recensione.setDate((c.getString(c.getColumnIndex(KEY_DATA_RECENSIONE))));

                // adding to recensione list
                recensioni.add(recensione);
            } while (c.moveToNext());
            c.close();
        }
        Collections.sort(recensioni, new Comparator<Recensione>() {
            public int compare(Recensione o1, Recensione o2) {
                return (((int) o2.getId_recensione()) - ((int) o1.getId_recensione()));
            }
        });
        return recensioni;
    }

    /*
     * Updating a recensione
     */
    public int updateRecensione(Recensione recensione) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_RECENSIONE, recensione.getId_recensione());
        values.put(KEY_ID_CORSO_RECENSIONE, recensione.getId_corso());
        values.put(KEY_MATRICOLA_RECENSIONE, recensione.getMatricola());
        values.put(KEY_TITOLO_RECENSIONE, recensione.getTitle());
        values.put(KEY_TESTO_RECENSIONE, recensione.getText());
        values.put(KEY_DATA_RECENSIONE, recensione.getDate());

        // updating row
        return db.update(TABLE_RECENSIONE, values, KEY_ID_RECENSIONE + " = ?",
                new String[]{String.valueOf(recensione.getId_recensione())});
    }

    /*
     * Deleting a recensione
     */
    public void deleteRecensione(long recensione_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECENSIONE, KEY_ID_RECENSIONE + " = ?",
                new String[]{String.valueOf(recensione_id)});
        Corso c = getCorso(getRecensione(recensione_id).getId_corso());
        c.setNumeroRecensioni(c.getNumeroRecensioni() - 1);
        updateCorso(c);
    }

/*##################################################################################################
                                            EVENTO
##################################################################################################*/

    /*
     * Creating a evento
     */
    public long createEvento(Evento evento) {
        SQLiteDatabase db = this.getWritableDatabase();

        long lastId = 0;
        if (!getAllEventi().isEmpty()) {
            lastId = getAllEventi().get(0).getId();
        }
        ContentValues values = new ContentValues();
        values.put(KEY_ID_EVENTO, lastId + 1);
        values.put(KEY_IMMAGINE_EVENTO, evento.getImmagine());
        values.put(KEY_INTERESSATI, "");
        values.put(KEY_TITOLO_EVENTO, evento.getTitolo());
        values.put(KEY_DESCRIZIONE_EVENTO, evento.getDescrizione());
        values.put(KEY_LUOGO_EVENTO, evento.getLuogo());
        values.put(KEY_DATA_EVENTO, evento.getData());

        // insert row
        return db.insert(TABLE_EVENTO, null, values);
    }

    /*
     * Updating a evento
     */
    public int updateEvento(Evento evento) {
        SQLiteDatabase db = this.getWritableDatabase();
        String inte = "";

        ContentValues values = new ContentValues();
        values.put(KEY_ID_EVENTO, evento.getId());
        values.put(KEY_IMMAGINE_EVENTO, evento.getImmagine());
        for (long i : evento.getInteressati()) {
            inte += i + ",";
        }
        if (inte.length() > 0)
            inte = inte.substring(0, inte.length() - 1);
        values.put(KEY_INTERESSATI, inte);
        values.put(KEY_TITOLO_EVENTO, evento.getTitolo());
        values.put(KEY_DESCRIZIONE_EVENTO, evento.getDescrizione());
        values.put(KEY_LUOGO_EVENTO, evento.getLuogo());
        values.put(KEY_DATA_EVENTO, evento.getData());

        // updating row
        return db.update(TABLE_EVENTO, values, KEY_ID_EVENTO + " = ?",
                new String[]{String.valueOf(evento.getId())});
    }

    /*
     * Deleting a evento
     */
    public void deleteEvento(int id_evento) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTO, KEY_ID_EVENTO + " = ?",
                new String[]{String.valueOf(id_evento)});
    }

    /*
     * get single evento
     */
    public Evento getEvento(int id_evento) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String selectQuery = "SELECT  * FROM " + TABLE_EVENTO + " WHERE "
                    + KEY_ID_EVENTO + " = " + id_evento;

            Cursor c = db.rawQuery(selectQuery, null);

            if (c != null) {
                c.moveToFirst();

                try {
                    String inte = c.getString(c.getColumnIndex(KEY_INTERESSATI));
                    String[] s = inte.split(",");
                    ArrayList<Long> i = new ArrayList<>();
                    for (String g : s) {
                        if (!g.equals("") && !g.equals(","))
                            i.add(Long.parseLong(g));
                    }

                    Evento evento = new Evento();
                    evento.setId(c.getInt(c.getColumnIndex(KEY_ID_EVENTO)));
                    evento.setImmagine(c.getString(c.getColumnIndex(KEY_IMMAGINE_EVENTO)));
                    evento.setInteressati(i);
                    evento.setTitolo(c.getString(c.getColumnIndex(KEY_TITOLO_EVENTO)));
                    evento.setDescrizione(c.getString(c.getColumnIndex(KEY_DESCRIZIONE_EVENTO)));
                    evento.setLuogo(c.getString(c.getColumnIndex(KEY_LUOGO_EVENTO)));
                    evento.setData(c.getString(c.getColumnIndex(KEY_DATA_EVENTO)));

                    return evento;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * getting all eventi
     * */
    public List<Evento> getAllEventi() {
        List<Evento> eventi = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_EVENTO;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        String inte;
        String[] s;
        ArrayList<Long> i;
        if (c.moveToFirst()) {
            do {
                inte = c.getString(c.getColumnIndex(KEY_INTERESSATI));
                s = inte.split(",");
                i = new ArrayList<>();
                for (String g : s) {
                    if (!g.equals("") && !g.equals(","))
                        i.add(Long.parseLong(g));
                }
                Evento evento = new Evento();
                evento.setId(c.getInt(c.getColumnIndex(KEY_ID_EVENTO)));
                evento.setImmagine(c.getString(c.getColumnIndex(KEY_IMMAGINE_EVENTO)));
                evento.setInteressati(i);
                evento.setTitolo(c.getString(c.getColumnIndex(KEY_TITOLO_EVENTO)));
                evento.setDescrizione(c.getString(c.getColumnIndex(KEY_DESCRIZIONE_EVENTO)));
                evento.setLuogo(c.getString(c.getColumnIndex(KEY_LUOGO_EVENTO)));
                evento.setData(c.getString(c.getColumnIndex(KEY_DATA_EVENTO)));

                //Elimino gli eventi vecchi e aggiungo quelli futuri all'output
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date = format.parse(evento.getData());
                    if (date != null && date.before(new Date())) {
                        deleteEvento(evento.getId());
                    } else {
                        eventi.add(evento);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } while (c.moveToNext());
            c.close();
        }
        Collections.sort(eventi, new Comparator<Evento>() {
            public int compare(Evento o1, Evento o2) {
                return (o2.getId() - o1.getId());
            }
        });
        return eventi;
    }


    /*
     * getting all eventi of day
     * */
    public List<Evento> getAllEventiOfDay(Date data) {
        List<Evento> allEventi;
        List<Evento> eventi = new ArrayList<>();

        allEventi = getAllEventi();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date;

        for (Evento e : allEventi) {
            try {

                date = formatter.parse(e.getData());
                if (date != null && date.equals(data)) {
                    eventi.add(e);
                }

            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }

        return eventi;
    }

    /*
     * getting all eventi of week
     * */
    public List<Evento> getAllEventiOfweek(Date data) {
        List<Evento> allEventi;
        List<Evento> eventi = new ArrayList<>();

        allEventi = getAllEventi();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        LocalDate localDate, localData = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        int year, week, weekData = cal.get(Calendar.WEEK_OF_YEAR), yearData = localData.getYear();

        for (Evento e : allEventi) {
            try {
                if(formatter.parse(e.getData()) != null) {
                    date = formatter.parse(e.getData());
                    localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    year = localDate.getYear();
                    cal = Calendar.getInstance();
                    cal.setTime(date);
                    week = cal.get(Calendar.WEEK_OF_YEAR);
                    if (week == weekData && year == yearData) {
                        eventi.add(e);
                    }
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }

        return eventi;
    }

    /*
     * getting all eventi of month
     * */
    public List<Evento> getAllEventiOfMonth(Date data) {
        List<Evento> allEventi;
        List<Evento> eventi = new ArrayList<>();

        allEventi = getAllEventi();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        LocalDate localDate, localData = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month, monthData = localData.getMonthValue();
        int year, yearData = localData.getYear();

        for (Evento e : allEventi) {
            try {
                if(formatter.parse(e.getData()) != null) {
                    date = formatter.parse(e.getData());
                    localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    month = localDate.getMonthValue();
                    year = localData.getYear();
                    if (month == monthData && year == yearData) {
                        eventi.add(e);
                    }
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }

        return eventi;
    }

/*##################################################################################################
                                            TIP
##################################################################################################*/

    /*
     * Creating a tip
     */
    public long createTip(Tip tip) {
        SQLiteDatabase db = this.getWritableDatabase();
        String inte = "";

        long lastId = 0;
        if (!getAllTips().isEmpty()) {
            lastId = getAllTips().get(0).getId();
        }
        ContentValues values = new ContentValues();
        values.put(KEY_ID_TIP, lastId + 1);
        values.put(KEY_TITOLO_TIP, tip.getTitolo());
        values.put(KEY_TESTO_TIP, tip.getTesto());
        values.put(KEY_LIKE_TIP, "");
        values.put(KEY_DISLIKE_TIP, "");
        values.put(KEY_COMMENTI_TIP, inte);
        values.put(KEY_DATA_TIP, getDateTime());

        // insert row
        return db.insert(TABLE_TIP, null, values);
    }

    /*
     * Updating a tip
     */
    public int updateTip(Tip tip) {
        SQLiteDatabase db = this.getWritableDatabase();
        String inte = "", like = "", dis = "";

        ContentValues values = new ContentValues();
        values.put(KEY_ID_TIP, tip.getId());
        values.put(KEY_TITOLO_TIP, tip.getTitolo());
        values.put(KEY_TESTO_TIP, tip.getTesto());

        for (long i : tip.getLike()) {
            like += i + ",";
        }
        if (like.length() > 0)
            like = like.substring(0, like.length() - 1);
        values.put(KEY_LIKE_TIP, like);

        for (long i : tip.getDislike()) {
            dis += i + ",";
        }
        if (dis.length() > 0)
            dis = dis.substring(0, dis.length() - 1);
        values.put(KEY_DISLIKE_TIP, dis);

        for (Commento i : tip.getCommenti()) {
            inte += i.getId_tip() + ",";
        }
        if (inte.length() > 0)
            inte = inte.substring(0, inte.length() - 1);
        values.put(KEY_COMMENTI_TIP, inte);

        values.put(KEY_DATA_TIP, tip.getData());

        // updating row
        return db.update(TABLE_TIP, values, KEY_ID_TIP + " = ?",
                new String[]{String.valueOf(tip.getId())});
    }

    /*
     * Deleting a tip
     */
    public void deleteTip(int id_tip) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TIP, KEY_ID_TIP + " = ?",
                new String[]{String.valueOf(id_tip)});
    }

    /*
     * get single tip
     */
    public Tip getTip(int id_tip) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String selectQuery = "SELECT  * FROM " + TABLE_TIP + " WHERE "
                    + KEY_ID_TIP + " = " + id_tip;

            Cursor c = db.rawQuery(selectQuery, null);

            if (c != null) {
                c.moveToFirst();

                try {
                    String inte = c.getString(c.getColumnIndex(KEY_COMMENTI_TIP));
                    String like = c.getString(c.getColumnIndex(KEY_LIKE_TIP));
                    String dis = c.getString(c.getColumnIndex(KEY_DISLIKE_TIP));
                    String[] s = inte.split(",");
                    String[] l = like.split(",");
                    String[] d = dis.split(",");
                    ArrayList<Commento> i = new ArrayList<>();
                    ArrayList<Long> li = new ArrayList<>();
                    ArrayList<Long> di = new ArrayList<>();
                    for (String g : s) {
                        if (!g.equals("") && !g.equals(","))
                            i.add(getCommento(Integer.parseInt(g)));
                    }
                    for (String g : l) {
                        if (!g.equals("") && !g.equals(","))
                            li.add(Long.parseLong(g));
                    }
                    for (String g : d) {
                        if (!g.equals("") && !g.equals(","))
                            di.add(Long.parseLong(g));
                    }

                    Tip tip = new Tip();
                    tip.setId(c.getInt(c.getColumnIndex(KEY_ID_TIP)));
                    tip.setTitolo(c.getString(c.getColumnIndex(KEY_TITOLO_TIP)));
                    tip.setTesto(c.getString(c.getColumnIndex(KEY_TESTO_TIP)));
                    tip.setLike(li);
                    tip.setDislike(di);
                    tip.setCommenti(i);
                    tip.setData(c.getString(c.getColumnIndex(KEY_DATA_TIP)));

                    return tip;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * getting all tips
     * */
    public List<Tip> getAllTips() {
        List<Tip> tips = new ArrayList<Tip>();

        String selectQuery = "SELECT  * FROM " + TABLE_TIP;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        String inte, like, dis;
        String[] s, l, d;
        ArrayList<Commento> i;
        ArrayList<Long> li, di;
        if (c.moveToFirst()) {
            do {
                try {
                    inte = c.getString(c.getColumnIndex(KEY_COMMENTI_TIP));
                    like = c.getString(c.getColumnIndex(KEY_LIKE_TIP));
                    dis = c.getString(c.getColumnIndex(KEY_DISLIKE_TIP));
                    s = inte.split(",");
                    l = like.split(",");
                    d = dis.split(",");
                    i = new ArrayList<>();
                    li = new ArrayList<>();
                    di = new ArrayList<>();
                    for (String g : s) {
                        if (!g.equals("") && !g.equals(","))
                            i.add(getCommento(Integer.parseInt(g)));
                    }
                    for (String g : l) {
                        if (!g.equals("") && !g.equals(","))
                            li.add(Long.parseLong(g));
                    }
                    for (String g : d) {
                        if (!g.equals("") && !g.equals(","))
                            di.add(Long.parseLong(g));
                    }
                    Tip tip = new Tip();
                    tip.setId(c.getInt(c.getColumnIndex(KEY_ID_TIP)));
                    tip.setTitolo(c.getString(c.getColumnIndex(KEY_TITOLO_TIP)));
                    tip.setTesto(c.getString(c.getColumnIndex(KEY_TESTO_TIP)));
                    tip.setLike(li);
                    tip.setDislike(di);
                    tip.setCommenti(i);
                    tip.setData(c.getString(c.getColumnIndex(KEY_DATA_TIP)));

                    // adding to users list
                    tips.add(tip);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            } while (c.moveToNext());
            c.close();
        }
        Collections.sort(tips, new Comparator<Tip>() {
            public int compare(Tip o1, Tip o2) {
                return (o2.getId() - o1.getId());
            }
        });
        return tips;
    }

/*##################################################################################################
                                            COMMENTO
##################################################################################################*/

    /*
     * Creating a commento
     */
    public long createCommento(Commento commento) {
        SQLiteDatabase db = this.getWritableDatabase();

        long lastId = 0;
        if (!getAllCommenti().isEmpty()) {
            lastId = getAllCommenti().get(getAllCommenti().size() - 1).getId_commento();
        }
        ContentValues values = new ContentValues();
        values.put(KEY_ID_COMMENTO, lastId + 1);
        values.put(KEY_ID_TIP_COMMENTO, commento.getId_tip());
        values.put(KEY_MATRICOLA_COMMENTO, commento.getMatricola());
        values.put(KEY_TESTO_COMMENTO, commento.getTesto());
        values.put(KEY_DATA_COMMENTO, getDateTime());

        // insert row
        long commento_id = db.insert(TABLE_COMMENTO, null, values);

        Tip t = getTip(commento.getId_tip());
        t.getCommenti().add(commento);
        updateTip(t);

        return commento_id;
    }

    /*
     * get single commento
     */
    public Commento getCommento(long commento_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMENTO + " WHERE "
                + KEY_ID_COMMENTO + " = " + commento_id;

        Cursor c = db.rawQuery(selectQuery, null);
        Commento commento = new Commento();

        if (c != null) {
            c.moveToFirst();

            commento.setId_commento(c.getInt(c.getColumnIndex(KEY_ID_COMMENTO)));
            commento.setId_tip(c.getInt(c.getColumnIndex(KEY_ID_TIP_COMMENTO)));
            commento.setMatricola(c.getLong(c.getColumnIndex(KEY_MATRICOLA_COMMENTO)));
            commento.setTesto((c.getString(c.getColumnIndex(KEY_TESTO_COMMENTO))));
            commento.setData((c.getString(c.getColumnIndex(KEY_DATA_COMMENTO))));
            c.close();
        }

        return commento;
    }

    /*
     * getting all commento
     * */
    public List<Commento> getAllCommenti() {
        List<Commento> commenti = new ArrayList<Commento>();
        String selectQuery = "SELECT  * FROM " + TABLE_COMMENTO;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Commento commento = new Commento();
                commento.setId_commento(c.getInt(c.getColumnIndex(KEY_ID_COMMENTO)));
                commento.setId_tip(c.getInt(c.getColumnIndex(KEY_ID_TIP_COMMENTO)));
                commento.setMatricola(c.getLong(c.getColumnIndex(KEY_MATRICOLA_COMMENTO)));
                commento.setTesto((c.getString(c.getColumnIndex(KEY_TESTO_COMMENTO))));
                commento.setData((c.getString(c.getColumnIndex(KEY_DATA_COMMENTO))));

                // adding to recensioni list
                commenti.add(commento);
            } while (c.moveToNext());
            c.close();
        }

        return commenti;
    }

    /*
     * getting all commenti under single tip
     * */
    public ArrayList<Commento> getAllCommentiByTip(long tip_id) {
        ArrayList<Commento> commenti = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_COMMENTO + " WHERE " + KEY_ID_TIP_COMMENTO + " = " + tip_id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Commento commento = new Commento();
                commento.setId_commento(c.getInt(c.getColumnIndex(KEY_ID_COMMENTO)));
                commento.setId_tip(c.getInt(c.getColumnIndex(KEY_ID_TIP_COMMENTO)));
                commento.setMatricola(c.getLong(c.getColumnIndex(KEY_MATRICOLA_COMMENTO)));
                commento.setTesto((c.getString(c.getColumnIndex(KEY_TESTO_COMMENTO))));
                commento.setData((c.getString(c.getColumnIndex(KEY_DATA_COMMENTO))));
                // adding to recensione list
                commenti.add(commento);
            } while (c.moveToNext());
            c.close();
        }
        Collections.sort(commenti, new Comparator<Commento>() {
            public int compare(Commento o1, Commento o2) {
                return (( o2.getId_commento()) - (o1.getId_commento()));
            }
        });
        return commenti;
    }

    /*
     * Updating a commento
     */
    public int updateCommento(Commento commento) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_COMMENTO, commento.getId_commento());
        values.put(KEY_ID_TIP_COMMENTO, commento.getId_tip());
        values.put(KEY_MATRICOLA_COMMENTO, commento.getMatricola());
        values.put(KEY_TESTO_COMMENTO, commento.getTesto());
        values.put(KEY_DATA_COMMENTO, getDateTime());

        // updating row
        return db.update(TABLE_COMMENTO, values, KEY_ID_COMMENTO + " = ?",
                new String[]{String.valueOf(commento.getId_commento())});
    }

    /*
     * Deleting a commento
     */
    public void deleteCommento(long commento_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COMMENTO, KEY_ID_COMMENTO + " = ?",
                new String[]{String.valueOf(commento_id)});
        Tip t = getTip(getCommento(commento_id).getId_tip());
        t.getCommenti().remove(getCommento(commento_id));
        updateTip(t);
    }

    /*##################################################################################################
                                        CHIUSURA DATABASE E ALTRO
    ##################################################################################################*/

    /**
     * get datetime
     */
    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
