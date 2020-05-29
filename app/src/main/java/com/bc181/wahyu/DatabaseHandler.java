package com.bc181.wahyu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bc181.wahyu.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_film";
    private final static String TABLE_FILM = "t_film";
    private final static String KEY_ID_FILM = "ID_film";
    private final static String KEY_JUDUl = "Judul";
    private final static String KEY_TAHUN = "Tahun";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_GENRE = "Genre";
    private final static String KEY_PEMAIN = "Pemain";
    private final static String KEY_SIPNOSIS= "SIPNOSIS";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
    private Context context;


    public DatabaseHandler(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FILM = "CREATE TABLE " +  TABLE_FILM
                + "(" + KEY_ID_FILM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUl + " TEXT, " + KEY_TAHUN + " DATE, "
                + KEY_GAMBAR + " TEXT, " + KEY_GENRE + " TEXT, "
                + KEY_PEMAIN + " TEXT, " + KEY_SIPNOSIS + " TEXT);";
        db.execSQL(CREATE_TABLE_FILM);
        inisialisasiFilmAwal(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_FILM;
        db.execSQL(DROP_TABLE);
        onCreate(db);

    }

    public void tambahFilm(Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUl, dataFilm.getJudul());
        cv.put(KEY_TAHUN, sdFormat.format(dataFilm.getTahun()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_PEMAIN, dataFilm.getPemain());
        cv.put(KEY_SIPNOSIS, dataFilm.getSipnosis());


        db.insert(TABLE_FILM, null, cv);
        db.close();
    }

    public void tambahFilm(Film dataFilm, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUl, dataFilm.getJudul());
        cv.put(KEY_TAHUN, sdFormat.format(dataFilm.getTahun()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_PEMAIN, dataFilm.getPemain());
        cv.put(KEY_SIPNOSIS, dataFilm.getSipnosis());

        db.insert(TABLE_FILM, null, cv);

    }

    public void editFilm(Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUl, dataFilm.getJudul());
        cv.put(KEY_TAHUN, sdFormat.format(dataFilm.getTahun()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_PEMAIN, dataFilm.getPemain());
        cv.put(KEY_SIPNOSIS, dataFilm.getSipnosis());


        db.update(TABLE_FILM, cv, KEY_ID_FILM + "=?", new String[]{String.valueOf(dataFilm.getIdFilm())});
        db.close();
    }

    public void hapusFilm(int idFilm) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FILM, KEY_ID_FILM + "=?", new String[]{String.valueOf(idFilm)});
        db.close();
    }

    public ArrayList<Film> getAllFilm() {
        ArrayList<Film> dataFilm = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FILM;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if(csr.moveToFirst()){
            do {
                Date tempDate = new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString(2));
                }
                catch (ParseException er){
                    er.printStackTrace();
                }

                Film tempFilm = new Film(
                        csr.getInt(0),
                        csr.getString(1),
                        tempDate,
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6)
                );

                dataFilm.add(tempFilm);
            } while (csr.moveToNext());
        }
        return dataFilm;
    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inisialisasiFilmAwal(SQLiteDatabase db) {
        int idFilm = 0;
        Date tempDate = new Date();

        //menambah data Film 1
        try{
            tempDate = sdFormat.parse("2019");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Film film1 = new Film(
                idFilm,
                "The Avengers : Endgame",
                tempDate,
                storeImageFile(R.drawable.film1),
                "Action",
                "Robert Downey Jr. as Tony Stark / Iron Man. \n" +
                        "\n" +
                        "Chris Evans as Steve Rogers / Captain America. \n" +
                        "\n" +
                        "Mark Ruffalo as Bruce Banner / Hulk.\n" +
                        "\n" +
                        "Chris Hemsworth as Thor / God Of Thunder. \n" +
                        "\n" +
                        "Scarlett Johansson as Natasha Romanoff / Black Widow.\n" +
                        "\n" +
                        "Jeremy Renner as Clint Barton / Hawkeye. \n" +
                        "\n" +
                        "Don Cheadle as James Rhodey Rhodes / War Machine. \n" +
                        "\n" +
                        "Paul Rudd as Scott Lang / Ant-Man.\n" +
                        "\n" +
                        "Brie Larson as Carol Danvers / Captain Marvel.\n" +
                        "\n" +
                        "Karen Gillan as Nebula.\n" +
                        "\n" +
                        "Danai Gurira as Okoye.\n" +
                        "\n" +
                        "Benedict Wong as Wong.\n" +
                        "Jon Favreau as Happy Hogan.\n" +
                        "\n" +
                        "Bradley Cooper as Rocket.\n" +
                        "\n" +
                        "Gwyneth Paltrow as Pepper Potts.\n" +
                        "\n" +
                        "Josh Brolin as Thanos. \n" +
                        "\n",
                "Melanjutkan Avengers Infinity War, dimana kejadian setelah Thanos berhasil mendapatkan semua infinity stones dan memusnahkan 50% semua mahluk hidup di alam semesta. Akankah para Avengers berhasil mengalahkan Thanos?"
        );

        tambahFilm(film1, db);
        idFilm++;

        // Data Film ke 2
        try{
            tempDate = sdFormat.parse("2019");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Film film2 = new Film(
                idFilm,
                "John Wick: Chapter 3",
                tempDate,
                storeImageFile(R.drawable.film2),
                "Laga/Cerita seru",
                "Keanu Reeves sebagai John Wick.\n" +
                        "\n" +
                        "Halle Berry sebagai Sofia, pembunuh dan sahabat John Wick.\n" +
                        "\n" +
                        "Laurence Fishburne sebagai The Bowery King, gembong kriminal bawah tanah.\n" +
                        "\n" +
                        "Mark Dacascos sebagai Zero, pembunuh utama yang ingin balas dendam.\n" +
                        "\n" +
                        "Asia Kate Dillon sebagai The Adjudicator, anggota The High Table.\n" +
                        "\n" +
                        "Lance Reddick sebagai Charon, resepsionis The Continental Hotel di New York.\n" +
                        "\n" +
                        "Anjelica Huston sebagai Director, anggota The High Table dan pelindung John Wick.\n" +
                        "\n" +
                        "Ian McShane sebagai Winston, pemilik dan manajer Continental Hotel di New York.\n" +
                        "\n" +
                        "Saïd Taghmaoui sebagai The Elder.\n" +
                        "\n" +
                        "Jason Mantzoukas sebagai Tick Tock Man, pembunuh.\n" +
                        "\n" +
                        "Robin Lord Taylor sebagai Administrator, anggota The High Table.\n",
                "Seri ketiga \"John Wick\" ini akan mengisahkan kelanjutan nasib si pembunuh bayaran usai menghabisi nyawa anggota High Table dalam The Continental. Ia sendiri diburu dan hidupnya dihargai sebesar USD 14 juta (sekitar Rp 201 miliar). Diburu oleh berbagai pembunuh bayaran hebat, John akan dibantu oleh karakter baru yang misterius bernama Sofia (Halle Berry).");
        tambahFilm(film2, db);
        idFilm++;

        // Tambah Film 3

        try{
            tempDate = sdFormat.parse("2019");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Film film3 = new Film(
                idFilm,
                "Joker",
                tempDate,
                storeImageFile(R.drawable.film3),
                "Drama/Kejahatan",
                "Joaquin Phoenix sebagai Arthur Fleck/Joker, pelawak tunggal yang menderita penyakit mental dan diabaikan oleh masyarakat.\n" +
                        "\n" +
                        "Robert De Niro sebagai Murray Franklin, pembawa acara yang berperan dalam kegagalan Arthur. \n" +
                        "\n" +
                        "Zazie Beetz sebagai Sophie Dumond, ibu tunggal dan kekasih Arthur[13] \n" +
                        "Frances Conroy sebagai Penny Fleck, ibu Arthur.\n" +
                        "\n" +
                        "Brett Cullen sebagai Thomas Wayne, dermawan miliarder yang mencalonkan diri sebagai wali kota Gotham.\n" +
                        "\n" +
                        "Douglas Hodge sebagai Alfred Pennyworth, kepala pelayan keluarga Wayne.\n" +
                        " \n" +
                        "Dante Pereira-Olson sebagai Bruce Wayne, putra Thomas Wayne yang akan menjadi musuh Joker ketika dewasa.\n" +
                        " \n" +
                        "Marc Maron sebagai Ted Marco, agen Arthur.\n" +
                        " \n" +
                        "Bill Camp sebagai petugas di Departemen Kepolisian Kota Gotham.\n" +
                        "\n" +
                        "Shea Whigham sebagai petugas di Departemen Kepolisian Kota Gotham. \n" +
                        "\n" +
                        "Glenn Fleshler sebagai pelawak.\n" +
                        "\n" +
                        "Bryan Callen sebagai penari. \n",
                "Kisah asli Joker yang belum pernah ada sebelumnya di layar lebar.");
        tambahFilm(film3, db);

        // Tambah Film 4

        try{
            tempDate = sdFormat.parse("2019");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Film film4 = new Film(
                idFilm,
                "It Chapter Two",
                tempDate,
                storeImageFile(R.drawable.film4),
                "Horor",
                "Bill Skarsgård sebagai It / Pennywise the Dancing Clown.\n" +
                        "n" +
                        "James McAvoy sebagai Bill Denbrough.\n" +
                        "\n" +
                        "Jaeden Martell sebagai Bill Denbrough muda.\n" +
                        "\n" +
                        "Jessica Chastain sebagai Beverly Marsh.\n" +
                        "\n" +
                        "Sophia Lillis sebagai Beverly Marsh muda.\n" +
                        "\n" +
                        "Jay Ryan sebagai Ben Hanscom.\n" +
                        "\n" +
                        "Jeremy Ray Taylor sebagai Ben Hanscom muda.\n" +
                        "\n" +
                        "Bill Hader sebagai Richie Tozier.\n" +
                        "\n" +
                        "Finn Wolfhard as young Richie Tozier.\n" +
                        "\n" +
                        "Isaiah Mustafa sebagai Mike Hanlon.\n" +
                        "\n" +
                        "Chosen Jacobs sebagai Mike Hanlon muda.\n" +
                        "\n" +
                        "James Ransone sebagai Eddie Kaspbrak.\n" +
                        "\n" +
                        "Jack Dylan Grazer sebagai Eddie Kaspbrak muda.\n" +
                        "\n" +
                        "Andy Bean sebagai Stan Uris.\n" +
                        "\n" +
                        "Wyatt Oleff sebagai Stan Uris muda.\n",
                "Seri kedua akan melanjutkan kisah para anggota Losers Club. Dua puluh tujuh tahun setelah pertemuan pertama mereka dengan Pennywise yang menakutkan, Losers Club telah dewasa dan berpisah. Sampai sebuah panggilan telepon membawa Losers Club kembali untuk bertemu hantu kelam dari masa lalu mereka.");
        tambahFilm(film4, db);


        // Tambah Film 5

        try{
            tempDate = sdFormat.parse("2015");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Film film5 = new Film(
                idFilm,
                "Filosofi Kopi",
                tempDate,
                storeImageFile(R.drawable.film5),
                "Drama",
                "Chicco Jerikho.\n" +
                        "\n" +
                        "Rio Dewanto.\n" +
                        "\n" +
                        "Julie Estelle.\n" +
                        "\n" +
                        "Jajang C. Noer.\n" +
                        "\n" +
                        "Slamet Rahardjo.\n",
                "Hutang bernilai ratusan juta rupiah mengancam keberadaan kedai Filosofi Kopi yang didirikan oleh Jody dan Ben. Di tengah perjuangan mengatasi hutang dan belitan konflik di antara mereka, seorang pengusaha muncul dengan tantangan yang sanggup menyelamatkan Filosofi Kopi.\n" +
                        "\n" +
                        "Dengan keahliannya meracik kopi Ben berhasil memenangkan satu miliar dari sang pengusaha, sampai kehadiran El yang mengatakan ada kopi yang lebih baik ketimbang mahakarya Ben meruntuhkan semuanya. Ben dan Jody tidak punya pilihan selain pergi mencari Kopi Tiwus yang akan menentukan kelangsungan Filosofi Kopi dan persahabatan mereka");
        tambahFilm(film5, db);




    }

}