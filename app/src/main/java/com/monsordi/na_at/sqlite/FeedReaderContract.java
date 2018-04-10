package com.monsordi.na_at.sqlite;

import android.provider.BaseColumns;

/**
 * Created by diego on 06/04/18.
 */

public class FeedReaderContract {

    private FeedReaderContract() {}

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "workers";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_JOB = "job";
        public static final String COLUMN_IMAGE = "image_url";
    }
}
