package com.testography.bucketdrops;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.testography.bucketdrops.adapters.AdapterDrops;
import com.testography.bucketdrops.beans.Drop;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ActivityMain extends AppCompatActivity {

    Toolbar mToolbar;
    Button mBtnAdd;
    RecyclerView mRecycler;
    Realm mRealm;
    RealmResults<Drop> mResults;
    AdapterDrops mAdapter;

    private View.OnClickListener mBtnAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showDialogAdd();
        }
    };

    private RealmChangeListener mChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object element) {
            mAdapter.update(mResults);
        }
    };

    private void showDialogAdd() {
        DialogAdd dialog = new DialogAdd();
        dialog.show(getSupportFragmentManager(), "Add");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRealm = Realm.getDefaultInstance();

        mResults = mRealm.where(Drop.class).findAllAsync();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mBtnAdd = (Button) findViewById(R.id.btn_add);
        mBtnAdd.setOnClickListener(mBtnAddListener);
        mAdapter = new AdapterDrops(this, mResults);

        mRecycler = (RecyclerView) findViewById(R.id.rv_drops);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);

        setSupportActionBar(mToolbar);

        initBackgroundImage();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mResults.addChangeListener(mChangeListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mResults.removeChangeListener(mChangeListener);
    }

    private void initBackgroundImage() {
        ImageView background = (ImageView) findViewById(R.id.iv_background);
        Glide.with(this)
                .load(R.drawable.background)
                .centerCrop()
                .into(background);
    }
}
