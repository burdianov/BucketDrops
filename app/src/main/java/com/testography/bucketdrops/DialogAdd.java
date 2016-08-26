package com.testography.bucketdrops;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.testography.bucketdrops.beans.Drop;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DialogAdd extends DialogFragment {

    private ImageButton mBtnClose;
    private EditText mInputWhat;
    private DatePicker mInputWhen;
    private Button mBtnAddIt;

    private View.OnClickListener mBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.btn_add_it:
                    addAction();
                    break;
            }
            dismiss();
        }
    };

    // TODO process date
    private void addAction() {
        String what = mInputWhat.getText().toString();
        long now = System.currentTimeMillis();

        RealmConfiguration configuration = new RealmConfiguration.Builder
                (getActivity()).build();
        Realm.setDefaultConfiguration(configuration);
        Realm realm = Realm.getDefaultInstance();
        Drop drop = new Drop(what, now, 0, false);

        realm.beginTransaction();
        realm.copyToRealm(drop);
        realm.commitTransaction();
        realm.close();
    }

    public DialogAdd() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mInputWhat = (EditText) view.findViewById(R.id.et_drop);
        mBtnClose = (ImageButton) view.findViewById(R.id.btn_close);
        mInputWhen = (DatePicker) view.findViewById(R.id.bpv_date);
        mBtnAddIt = (Button) view.findViewById(R.id.btn_add_it);

        mBtnClose.setOnClickListener(mBtnClickListener);
        mBtnAddIt.setOnClickListener(mBtnClickListener);
    }
}
