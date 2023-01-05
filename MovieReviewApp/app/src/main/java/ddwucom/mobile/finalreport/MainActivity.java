package ddwucom.mobile.finalreport;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;

// 과제명: 영화 리뷰 관리 앱
// 분반: 02 분반
// 학번: 20200937 성명: 유승연
// 제출일: 2022년 6월 24일
public class MainActivity extends AppCompatActivity {

    final int REQ_CODE = 100;
    final int REQ_CODE2 = 200;
    final int REQ_CODE3 = 300;

    ArrayList<Movie> movieList;
    MyAdapter myAdapter;
    ListView listView;

    MovieDBManager movieDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        movieList = new ArrayList<>();


        myAdapter = new MyAdapter(this, R.layout.custom_adapter_view, movieList);
        listView.setAdapter(myAdapter);

        movieDBManager = new MovieDBManager(this);

        listView.setOnItemClickListener(itemClickListener);
        listView.setOnItemLongClickListener(itemLongClickListener);

    }

    @Override
    protected void onResume() {
        super.onResume();
        movieList.clear();
        movieList.addAll(movieDBManager.getAllMovie());
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }

    public void onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addMovie:
                Intent intent = new Intent(this, AddActivity.class);
                startActivityForResult(intent, REQ_CODE);
                break;
            case R.id.app_bar_search:
                Intent searchIntent = new Intent(this, SearchActivity.class);
                searchIntent.putExtra("movies",movieList);
                startActivityForResult(searchIntent, REQ_CODE3);
                break;
            case R.id.producer:
                final ConstraintLayout devLayout =
                        (ConstraintLayout) View.inflate(this, R.layout.dev_dialog_layout, null);
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("개발자 소개")
                        .setView(devLayout)
                        .setPositiveButton("확인", null)
                        .show();
                break;
            case R.id.quit:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle("앱 종료")
                        .setMessage("앱을 종료하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.finishAffinity(MainActivity.this);
                                System.exit(0);
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE:
                if (resultCode == RESULT_OK) {
                    String title = data.getStringExtra("title");
                    Toast.makeText(this, title + " 추가 완료", Toast.LENGTH_SHORT).show();
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "영화 추가 취소", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQ_CODE2:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, "영화 수정 완료", Toast.LENGTH_SHORT).show();
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "영화 수정 취소", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    AdapterView.OnItemClickListener itemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    Movie movie = movieList.get(pos);
                    Intent modIntent = new Intent(MainActivity.this, ModifyActivity.class);
                    modIntent.putExtra("movie",movie);
                    startActivityForResult(modIntent, REQ_CODE2);
                }
            };

    AdapterView.OnItemLongClickListener itemLongClickListener =
            new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                    final int pos = position;
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(MainActivity.this);
                    builder3.setTitle("영화 삭제")
                            .setMessage(movieList.get(pos).getTitle() + " 영화를 삭제하시겠습니까?")
                            .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    boolean result = movieDBManager.removeMovie(movieList.get(pos).get_id());
                                    if(result) {
                                        Toast.makeText(MainActivity.this, "삭제 완료", Toast.LENGTH_SHORT).show();
                                        movieList.clear();
                                        movieList.addAll(movieDBManager.getAllMovie());
                                        myAdapter.notifyDataSetChanged();
                                    } else {
                                        Toast.makeText(MainActivity.this, "삭제 실패", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton("취소", null)
                            .setCancelable(false)
                            .show();
                    return true;
                }
            };


}