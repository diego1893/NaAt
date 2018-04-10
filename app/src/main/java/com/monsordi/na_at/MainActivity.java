package com.monsordi.na_at;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.monsordi.na_at.sqlite.FeedReaderContract.*;
import com.monsordi.na_at.sqlite.SqlController;
import com.monsordi.na_at.web.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int NEW_TASK = 0;
    private static final int NEW_ENTRY = 1;
    private static final int UPDATE = 2;
    private static final int DELETE = 3;
    private static final int REQUEST_UPDATE = 4;


    @BindView(R.id.main_list)
    ListView listView;

    private SqlController sqlController;
    private WorkerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sqlController = new SqlController(this);

        adapter = new WorkerAdapter(this, null);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        notifyDataSetChanged();
    }

    private void notifyDataSetChanged() {
        Cursor cursor = sqlController.selectColumnsFromDb(FeedEntry.TABLE_NAME, new String[]{FeedEntry._ID, FeedEntry.COLUMN_NAME, FeedEntry.COLUMN_EMAIL, FeedEntry.COLUMN_JOB, FeedEntry.COLUMN_IMAGE});
        adapter.swapCursor(cursor);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, UPDATE, Menu.NONE, getString(R.string.update));
        menu.add(Menu.NONE, DELETE, Menu.NONE, getString(R.string.delete));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id = (int) info.id;
        switch (item.getItemId()) {
            case UPDATE:
                Intent intent = new Intent(this, NewEntryActivity.class);
                intent.putExtra(getString(R.string.worker_key), id);
                startActivityForResult(intent, REQUEST_UPDATE);
                break;

            case DELETE:
                sqlController.deleteDataWhereId(FeedEntry.TABLE_NAME, String.valueOf(id));
                notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_task:
                Intent intent = new Intent(this, NewEntryActivity.class);
                startActivityForResult(intent, NEW_ENTRY);
                break;

            case R.id.search:
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case NEW_ENTRY:
                if (resultCode == RESULT_OK)
                    notifyDataSetChanged();
                else if (resultCode == NewEntryActivity.RESULT_INTERNET_ERROR)
                    Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                break;

            case REQUEST_UPDATE:
                if (resultCode == RESULT_OK)
                    notifyDataSetChanged();
                break;
        }
    }
}
