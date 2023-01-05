package ddwucom.mobile.finalreport;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    final int REQ_CODE2 = 200;

    ArrayList<Movie> movieList;
    MyAdapter myAdapter;
    ListView listView;

    EditText etSearch;

    MovieDBManager movieDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        etSearch = (EditText) findViewById(R.id.etSearch);

        listView = (ListView) findViewById(R.id.searchListView);

        movieDBManager = new MovieDBManager(this);

        movieList = (ArrayList<Movie>)getIntent().getSerializableExtra("movies");

        myAdapter = new MyAdapter(this, R.layout.custom_adapter_view, movieList);
        listView.setAdapter(myAdapter);



        listView.setOnItemClickListener(itemClickListener);
        listView.setOnItemLongClickListener(itemLongClickListener);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String title = etSearch.getText().toString();
                movieList.clear();
                movieList.addAll(movieDBManager.searchMovieByTitle(title));
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        movieList.clear();
        movieList.addAll(movieDBManager.getAllMovie());
        myAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
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
                    Intent modIntent = new Intent(SearchActivity.this, ModifyActivity.class);
                    modIntent.putExtra("movie",movie);
                    startActivityForResult(modIntent, REQ_CODE2);
                }
            };

    AdapterView.OnItemLongClickListener itemLongClickListener =
            new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                    final int pos = position;
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(SearchActivity.this);
                    builder3.setTitle("영화 삭제")
                            .setMessage(movieList.get(pos).getTitle() + " 영화를 삭제하시겠습니까?")
                            .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    boolean result = movieDBManager.removeMovie(movieList.get(pos).get_id());
                                    if(result) {
                                        Toast.makeText(SearchActivity.this, "삭제 완료", Toast.LENGTH_SHORT).show();
                                        movieList.clear();
                                        movieList.addAll(movieDBManager.getAllMovie());
                                        myAdapter.notifyDataSetChanged();
                                    } else {
                                        Toast.makeText(SearchActivity.this, "삭제 실패", Toast.LENGTH_SHORT).show();
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
