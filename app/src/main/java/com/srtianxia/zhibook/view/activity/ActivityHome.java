package com.srtianxia.zhibook.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.facebook.drawee.view.SimpleDraweeView;
import com.srtianxia.zhibook.R;
import com.srtianxia.zhibook.app.BaseActivity;
import com.srtianxia.zhibook.presenter.HomePresenter;
import com.srtianxia.zhibook.view.IView.IActivityHome;
import com.srtianxia.zhibook.view.fragment.FragmentQuestion;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ActivityHome extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,CropHandler,IActivityHome {
    private static final String TAG = "ActivityHome";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.nav_view)
    NavigationView navView;

    private HomePresenter presenter;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    private CropParams cropParams = new CropParams();
    private View dialogView;
    private MaterialDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new HomePresenter(this);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.toolbar_home);
        setSupportActionBar(toolbar);

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        FragmentQuestion fragmentQuestion = new FragmentQuestion();
        transaction.replace(R.id.fragment_container, fragmentQuestion);
        transaction.commit();


        SimpleDraweeView draweeView = (SimpleDraweeView) navView.getHeaderView(0).findViewById(R.id.img_person_head);
        draweeView.setImageURI(Uri.parse("http://www.91danji.com/attachments/201509/27/13/4cevsjye7.jpg"));
        draweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item[] = new String[]{"相册", "拍照"};
                new MaterialDialog.Builder(ActivityHome.this)
                        .theme(Theme.LIGHT)
                        .title("选择")
                        .items(item)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                if (which == 0){
                                    Intent intent = CropHelper.buildCropFromGalleryIntent(new CropParams());
                                    CropHelper.clearCachedCropFile(cropParams.uri);
                                    startActivityForResult(intent, CropHelper.REQUEST_CROP);
                                }else {

                                }
                            }
                        }).show();
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropHelper.handleResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_home_unlogin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_login) {
            dialogView = new MaterialDialog.Builder(this)
                    .customView(R.layout.ui_layout_dialog,false).build().getCustomView();
            TextView btlogin = (TextView) dialogView.findViewById(R.id.bt_login);
            btlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.login();
                }
            });
            TextView btRegiser = (TextView) dialogView.findViewById(R.id.bt_register);
            btRegiser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.register();
                }
            });
            dialog = new MaterialDialog.Builder(this)
                    .customView(dialogView,false)
                    .title("登录").show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_find) {
            Intent intent = new Intent(this,ActivityFind.class);
            startActivity(intent);
        }  else if (id == R.id.nav_note){
            Intent intent = new Intent(this,ActivityNote.class);
            startActivity(intent);
        }else if (id == R.id.nav_chat){
            Intent intent = new Intent(this,ActivityChat.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onPhotoCropped(Uri uri) {
        presenter.upLoadHead(uri);
    }

    @Override
    public void onCropCancel() {

    }

    @Override
    public void onCropFailed(String message) {
    }

    @Override
    public CropParams getCropParams() {
        return cropParams;
    }

    @Override
    public Activity getContext() {
        return getContext();
    }


    @Override
    public String getUsername() {
        return ((TextInputLayout)dialogView.findViewById(R.id.input_username)).getEditText().getText().toString();
    }

    @Override
    public String getPassword() {
        return ((TextInputLayout)dialogView.findViewById(R.id.input_password)).getEditText().getText().toString();
    }

    @Override
    public void loginSuccess() {
        dialog.dismiss();
        Toast.makeText(ActivityHome.this, "登陆成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFailure() {
        Toast.makeText(ActivityHome.this, "登录失败，账号或者密码错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registerSuccess() {

    }

    @Override
    public void registerFailure() {

    }
}
