package com.elorri.android.communication.db;

import android.net.Uri;
import android.provider.ContactsContract;

import com.elorri.android.communication.extra.Tools;

/**
 * Created by Elorri on 13/04/2016.
 */
public class AndroidDAO {



    public interface ContactQuery {

        // Cursor Column identifiers
        int COL_ID = 0;
        int COL_LOOKUP_KEY = 1;
        int COL_CONTACT_NAME = 2;
        int COL_PHOTO_THUMBNAIL_URI = 3;

        // An identifier for the loader
        int LOADER_ID = 0;

        // A content URI for the Contacts table
        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;

        String SELECTION =
                (Tools.hasHoneycomb() ? ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                        ContactsContract.Contacts.DISPLAY_NAME) +
                        "<>''" + " AND " + ContactsContract.Contacts.IN_VISIBLE_GROUP + "=1";

        String SORT_ORDER =
                Tools.hasHoneycomb() ? ContactsContract.Contacts.SORT_KEY_PRIMARY : ContactsContract
                        .Contacts.DISPLAY_NAME;

        String[] PROJECTION = {

                // The contact's row id
                ContactsContract.Contacts._ID,

                // A pointer to the contact that is guaranteed to be more permanent than _ID. Given
                // a contact's current _ID value and LOOKUP_KEY, the Contacts Provider can generate
                // a "permanent" contact URI.
                ContactsContract.Contacts.LOOKUP_KEY,

                // In platform version 3.0 and later, the Contacts table contains
                // DISPLAY_NAME_PRIMARY, which either contains the contact's displayable name or
                // some other useful identifier such as an email address. This column isn't
                // available in earlier versions of Android, so you must use Contacts.DISPLAY_NAME
                // instead.
                Tools.hasHoneycomb() ? ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                        ContactsContract.Contacts.DISPLAY_NAME,

                // In Android 3.0 and later, the thumbnail image is pointed to by
                // PHOTO_THUMBNAIL_URI. In earlier versions, there is no direct pointer; instead,
                // you generate the pointer from the contact's ID value and constants defined in
                // android.provider.ContactsContract.Contacts.
                Tools.hasHoneycomb() ? ContactsContract.Contacts.PHOTO_THUMBNAIL_URI : ContactsContract.Contacts._ID
        };

    }


}
