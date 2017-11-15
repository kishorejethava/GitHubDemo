package net.giniguru.githubdemo.user;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.NonNull;
import android.util.Log;

import net.giniguru.githubdemo.model.User;
import net.giniguru.githubdemo.storage.DatabaseUtil;

import java.util.ArrayList;

/**
 * Created by KK on 9/29/2017.
 */

public class UserDAO {
    static String TABLE_NAME ="User";
    private static String TAG=UserDAO.class.getName();
    public static void insertUser(String id, String login, String local_avatar_url, String avatar_ur){
        SQLiteDatabase database= null;
        SQLiteStatement statement =null;

        try {

            database = DatabaseUtil.getDatabaseInstance();
            String INSERT_USER = "INSERT OR REPLACE INTO " +TABLE_NAME+
                    "(id,login,local_avatar_url,avatar_url) VALUES (?,?,?,?)";
            statement = database.compileStatement(INSERT_USER);
            statement.bindString(1, id);
            statement.bindString(2, login);
            statement.bindString(3, local_avatar_url);
            statement.bindString(4, avatar_ur);
            long idInserted = statement.executeInsert();

            Log.i(TAG,"insertUser : "+idInserted);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DatabaseUtil.closeResource(database,statement,null);
        }
    }

    /**
     * get user list from database
     * @return
     */
    @NonNull
    public static ArrayList<User> getUserList(){
        Cursor cursor =null;
        SQLiteDatabase database = null;
        ArrayList<User> listUser= new ArrayList<>();
        try {
            database =DatabaseUtil.getDatabaseInstance();
            String SELECT_ALL_USER = "SELECT * FROM "+TABLE_NAME + " ORDER BY id ASC";
            cursor = database.rawQuery(SELECT_ALL_USER,null);
            while (cursor.moveToNext()) {
                User user = new User();
                user.setId(cursor.getString(cursor.getColumnIndex("id")));
                user.setLogin(cursor.getString(cursor.getColumnIndex("login")));
                user.setAvatar_url(cursor.getString(cursor.getColumnIndex("local_avatar_url")));
                listUser.add(user);
                Log.i(TAG,"getUserList row:"+user.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DatabaseUtil.closeResource(database,null,cursor);
        }
        return listUser;
    }
    public static void clearAllUsers(){
        SQLiteDatabase database = null;
        try {
            database =DatabaseUtil.getDatabaseInstance();
            int result = database.delete(TABLE_NAME, null, null);
            Log.i(TAG,"clearAllUsers result:"+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
