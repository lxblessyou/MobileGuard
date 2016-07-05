package com.home.user.mobileguard.Utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.home.user.mobileguard.bean.ContactBean;

import java.util.List;

/**
 * 联系人操作类
 * Created by user on 16-7-4.
 */
public class ReadContacts {
    /**
     *
     * 读取联系人
     * @param context
     * @param datas
     */
    public static void getContacts(Context context, List<ContactBean> datas) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursorID = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);
        while (cursorID.moveToNext()) {
            ContactBean cb = new ContactBean();
            String _id = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor cursorType = contentResolver.query(Uri.parse("content://com.android.contacts/data"),
                    new String[]{"data1","mimetype"},
                    "raw_contact_id = ?", new String[]{_id}, null);
            while (cursorType.moveToNext()) {
                String display_name = cursorType.getString(cursorType.getColumnIndex("data1"));
                String content_type = cursorType.getString(cursorType.getColumnIndex("mimetype"));

                if (content_type.equals("vnd.android.cursor.item/name")) {
                    cb.setName(display_name);
                } else if (content_type.equals("vnd.android.cursor.item/phone_v2")) {
                    cb.setSafeNum(display_name);
                }
            }
            datas.add(cb);
            cursorType.close();
        }
        cursorID.close();
    }
}
