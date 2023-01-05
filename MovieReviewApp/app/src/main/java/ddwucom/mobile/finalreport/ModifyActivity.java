package ddwucom.mobile.finalreport;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ModifyActivity extends AppCompatActivity {

    Movie movie;
    EditText modTitle;
    EditText modDate;
    EditText modRate;
    EditText modCharacters;
    EditText modDirector;
    EditText modReview;
    ImageView modImage;
    private DatePickerDialog.OnDateSetListener callbackMethod;

    MovieDBManager movieDBManager;

    int imageSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        modImage = findViewById(R.id.modImage);
        modTitle = findViewById(R.id.modTitle);
        modDate = findViewById(R.id.modDate);
        modRate = findViewById(R.id.modRate);
        modCharacters = findViewById(R.id.modCharacters);
        modDirector = findViewById(R.id.modDirector);
        modReview = findViewById(R.id.modReview);

        movie = (Movie) getIntent().getSerializableExtra("movie");

        imageSource = movie.getImageSource();

        modTitle.setText(movie.getTitle());
        modDate.setText(movie.getReleaseDate());
        modRate.setText(movie.getRate());
        modCharacters.setText(movie.getCharacters());
        modDirector.setText(movie.getDirector());
        modReview.setText(movie.getReview());

        switch (movie.getTitle()) {
            case "탑건:매버릭":
                modImage.setImageResource(R.mipmap.topgun);
                break;
            case "범죄도시2":
                modImage.setImageResource(R.mipmap.crimecity);
                break;
            case "마녀 Part2. The Other One":
                modImage.setImageResource(R.mipmap.witch);
                break;
            case "버즈 라이트이어":
                modImage.setImageResource(R.mipmap.lightyear);
                break;
            case "쥬라기 월드:도미니언":
                modImage.setImageResource(R.mipmap.jurassic);
                break;
            default:
                modImage.setImageResource(R.mipmap.ic_launcher);
                break;
        }

        movieDBManager = new MovieDBManager(this);

        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                modDate.setText(y + "년 " + (m+1) + "월 " + d + "일");
            }
        };
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnModify:
                if(modTitle.getText().toString().equals("")) {
                    Toast.makeText(this, "영화 제목을 입력하세요.", Toast.LENGTH_SHORT).show();
                    break;
                } else if (modDate.getText().toString().equals("")) {
                    Toast.makeText(this, "개봉일을 입력하세요.", Toast.LENGTH_SHORT).show();
                    break;
                } else if (modRate.getText().toString().equals("")) {
                    Toast.makeText(this, "평점을 입력하세요.", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    movie.setTitle(modTitle.getText().toString());
                    movie.setReleaseDate(modDate.getText().toString());
                    movie.setRate(modRate.getText().toString());
                    movie.setCharacters(modCharacters.getText().toString());
                    movie.setDirector(modDirector.getText().toString());
                    movie.setReview(modReview.getText().toString());

                    if(movieDBManager.modifyMovie(movie)) {
                        Intent resultIntent = new Intent();
//                        resultIntent.putExtra("movie", movie);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    } else {
                        setResult(RESULT_CANCELED);
                    }
                }
                break;
            case R.id.btnCancelModify:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.btnModDate:
                DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, 2022, 6 - 1, 24);
                dialog.show();
                break;
        }
    }
}
