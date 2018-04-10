package com.monsordi.na_at;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Soriano on 09/04/18.
 */

public class DialogUtils {

    private Context context;
    private StandardDialogTasks standardDialogTasks;

    public DialogUtils(Context context, StandardDialogTasks standardDialogTasks) {
        this.context = context;
        this.standardDialogTasks = standardDialogTasks;
    }

    //Shows a standard dialog
    public void showDialog(String title,String message){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        standardDialogTasks.onPositiveButtonSelected(dialog);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        standardDialogTasks.onNegativeButtonSelected(dialog);
                    }
                }).show();
    }

    public interface StandardDialogTasks{
        void onPositiveButtonSelected(DialogInterface dialog);
        void onNegativeButtonSelected(DialogInterface dialog);
    }
}
