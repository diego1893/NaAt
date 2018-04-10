package com.monsordi.na_at;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import com.monsordi.na_at.sqlite.FeedReaderContract.FeedEntry;
import com.monsordi.na_at.sqlite.SqlController;
import com.monsordi.na_at.web.JsonManager;
import com.monsordi.na_at.web.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewEntryActivity extends AppCompatActivity implements View.OnClickListener,
        DialogUtils.StandardDialogTasks, Response.Listener,Response.ErrorListener{

    public static final int RESULT_INTERNET_ERROR = 100;

    @BindView(R.id.new_entry_image)
    CircularImageView profileImage;
    @BindView(R.id.new_entry_name)
    EditText nameEditText;
    @BindView(R.id.new_entry_email)
    EditText emailEditText;
    @BindView(R.id.new_entry_job)
    EditText jobEditText;
    @BindView(R.id.new_entry_done_button)
    FloatingActionButton doneButton;

    private boolean isImageSelected = true;
    private int id = -1;

    private SqlController sqlController;
    private DialogUtils dialogUtils;

    private String name,email,job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        ButterKnife.bind(this);
        doneButton.setOnClickListener(this);

        sqlController = new SqlController(this);
        dialogUtils = new DialogUtils(this,this);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            id = extras.getInt(getString(R.string.worker_key));
            Cursor cursor = sqlController.selectColumnsFromDbWhereId(FeedEntry.TABLE_NAME,new String[]{FeedEntry.COLUMN_NAME,FeedEntry.COLUMN_EMAIL,FeedEntry.COLUMN_JOB,FeedEntry.COLUMN_IMAGE},String.valueOf(id));
            Worker currentWorker = new Worker(cursor);
            setUI(currentWorker);
        }
    }


    @Override
    public void onPositiveButtonSelected(DialogInterface dialog) {
        name = getTextFrom(nameEditText);
        email = getTextFrom(emailEditText);
        job = getTextFrom(jobEditText);
        String requestingUrl = JsonManager.getImageRequestingUrl(email);

        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,requestingUrl,null,this,this);
        volleySingleton.addToRequestQueue(jsonObjectRequest);
    }



    @Override
    public void onNegativeButtonSelected(DialogInterface dialog) {
        dialog.dismiss();
    }

    private void setUI(Worker currentWorker) {
        nameEditText.setText(currentWorker.getName());
        emailEditText.setText(currentWorker.getEmail());
        jobEditText.setText(currentWorker.getJob());
        Glide.with(this)
                .load(currentWorker.getImageUrl())
                .into(profileImage);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        setResult(RESULT_INTERNET_ERROR);
        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    public void onResponse(Object response) {
        String imageUrl = null;

        try {
            imageUrl = JsonManager.getImageUrl((JSONObject) response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Worker worker = new Worker(name,email,job,imageUrl);

        if(id!=-1)
            sqlController.editDataWhereId(FeedEntry.TABLE_NAME,worker,String.valueOf(id));
        else
            sqlController.saveData(FeedEntry.TABLE_NAME,worker);

        setResult(RESULT_OK);
        NavUtils.navigateUpFromSameTask(this);
    }

    //**********************************************************************************************************

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.new_entry_done_button:
                if(isImageSelected && !isEditTextEmpty(nameEditText) && !isEditTextEmpty(emailEditText) && !isEditTextEmpty(jobEditText)){
                    dialogUtils.showDialog(getString(R.string.save_entry),getString(R.string.save_entry_warning));
                }else
                    Toast.makeText(this,getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //**********************************************************************************************************

    private String getTextFrom(EditText editText) {
        return editText.getText().toString().trim();
    }

    private boolean isEditTextEmpty(EditText editText) {
        return TextUtils.isEmpty(editText.getText().toString().trim());
    }
}
