package com.example.thang.smartmoney.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.ArrayMap;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class FirebaseSync {

    private static FirebaseUser user;
    private static FirebaseDatabase firebaseDatabase;
    private static DatabaseReference reference;
    private static SQLiteDatabase dbinstance;
    private static ArrayMap<String, Callable> funcs;

    public static String[] DATA_TABLE = {"giaodich", "category", "vi", "ngan_sach"};

    public static void afterSync(String name, Callable func) {
        funcs.remove(name);
        funcs.put(name, func);
    }

    public static void Init(Context ctx)
    {
        dbinstance = Database.getInstance(ctx).getWritableDatabase();

        if (user == null) user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        if (firebaseDatabase == null) firebaseDatabase = FirebaseDatabase.getInstance();

        reference = firebaseDatabase.getReference(uid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    child.getRef().addValueEventListener(new ValueHandler(child.getKey()));
                }
                dispatchFuncs();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        funcs = new ArrayMap<>();
    }

    public static FirebaseUser getUser() throws RuntimeException
    {
        if (user == null) throw new RuntimeException("FirebaseUser is not init");
        return user;
    }

    private static FirebaseDatabase getDatabase() throws RuntimeException
    {
        if (firebaseDatabase == null) throw new RuntimeException("FirebaseDatabase is not init");
        return firebaseDatabase;
    }

    private static SQLiteDatabase getDbinstance() throws RuntimeException
    {
        if (dbinstance == null) throw new RuntimeException("FirebaseDatabase is not init");
        return dbinstance;
    }

    private static DatabaseReference getReference() throws RuntimeException
    {
        if (reference == null) throw new RuntimeException("FirebaseDatabase is not init");
        return reference;
    }

    public static void syncRow(String tbname, Cursor cursor) {
        String[] names = cursor.getColumnNames();
        String key = cursor.getString(cursor.getColumnIndex("id"));
        for (String name : names) {
            if (name.equals(key)) continue;
            String value = cursor.getString(cursor.getColumnIndex(name));
            getReference().child(tbname).child(key).child(name).setValue(value);
        }
    }

    public static void syncTable(String tablename) {
        Cursor cursor = getDbinstance().rawQuery("SELECT * FROM " + tablename, null);
        if (cursor.moveToFirst()) {
            do {
                syncRow(tablename, cursor);
            } while (cursor.moveToNext());
        }
    }

    public static void syncDatabase() {
        for (String tablename : DATA_TABLE) {
            Log.d("Sync", "Syncing " + tablename);
            syncTable(tablename);
            Log.d("Sync", "Sync " + tablename + " done");
        }
        syncRemote();
    }

    private static void syncRemote() {
        for (final String tablename : DATA_TABLE) {
            DatabaseReference tableData = getReference().child(tablename);
            tableData.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> listChild = dataSnapshot.getChildren();
                    long effectCount = 0;
                    for (DataSnapshot child : listChild) {
                        ContentValues values = new ContentValues();
                        Iterable<DataSnapshot> colNames = child.getChildren();
                        for (DataSnapshot name : colNames) {
                            values.put(name.getKey(), (String)name.getValue());
                        }
                        long res = dbinstance.insertWithOnConflict(tablename, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                        if (res > 0) ++effectCount;
                    }

                    Log.d("Sync", "Sync new " + effectCount + " row(s) on remote");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("Sync", databaseError.getMessage());
                }
            });
        }
    }

    private static void dispatchFuncs()
    {
        for (Callable func : funcs.values()) {
            try {
                func.call();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    static class ValueHandler implements ValueEventListener {
        static String parent;

        public ValueHandler(String key)
        {
            parent = key;
        }

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                Log.d("Sync", dataSnapshot.getKey() + " exists");
            } else {
                getReference().child(parent).child(dataSnapshot.getKey()).setValue(dataSnapshot.getValue());
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }
}
