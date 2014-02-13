Handy SQLite Helper for Android
===============================

A library for shipping SQLite databases within application assets and optionally placing them in external storage

Setup
=====
Maven
-----
    <dependency>
        <groupId>org.deejdev.database.handysqlite</groupId>
        <artifactId>handysqlite</artifactId>
        <version>1.0</version>
        <type>aar</type>
    </dependency>
Gradle
------
    dependencies {
        ...
        compile 'org.deejdev.database.handysqlite:handysqlite:1.0'
    }

Usage
=====

Using the library is similar to using framework's `SQLiteOpenHelper`. Create a subclass of `HandySQLiteHelper` as you do when using `SQLiteOpenHelper`, and pass `context`, `InputStream`, destination `File` and `version` to constructor.

Examples
--------
Basic usage. Database is read from asset and extracted into standard database directory.

    public class OpenHelper extends HandySQLiteHelper {
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "db.sqlite"; // both asset name and destination file name

        public OpenHelper(Context context, InputStream input, File destinationPath, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, getInput(context), context.getDatabasePath(DATABASE_NAME), null, DATABASE_VERSION);
        }

        private static InputStream getInput(Context context) {
            try {
                AssetManager assets = context.getAssets();
                return assets.open(DATABASE_NAME);
            } catch (IOException e) {
                // Should not happen normally
                throw new HandySQLiteHelperException("Error reading database from assets", e);
            }
        }
    }

To place database in external storage specify a corresponding path

    public OpenHelper(Context context, InputStream input, File destinationPath, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, getInput(context), new File("/mnt/sdcard/some/random/path"), null, DATABASE_VERSION);
    }

If you are developing for Android 2.2, and your database is larger than 1MB, consider splitting it into smaller chunks.
See [this issue](http://code.google.com/p/android/issues/detail?id=37)

    private static InputStream getInput(Context context) {
        try {
            AssetManager assets = context.getAssets();
            ArrayList<InputStream> chunks = new ArrayList<InputStream>();
            chunks.add(assets.open("database_aa"));
            chunks.add(assets.open("database_ab"));
            chunks.add(assets.open("database_ac"));
            chunks.add(assets.open("database_ad"));
            return new SequenceInputStream(Collections.enumeration(chunks));
        } catch (IOException e) {
            // Should not happen normally
            throw new EmbeddedSQLiteException("Error reading database from assets", e);
        }
    }

License
=======

    Copyright 2014 Maxim Naumov

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.