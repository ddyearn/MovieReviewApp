package ddwucom.mobile.finalreport;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    EditText etTitle;
    EditText etDate;
    EditText etRate;
    EditText etCharacters;
    EditText etDirector;
    EditText etReview;
    private DatePickerDialog.OnDateSetListener callbackMethod;

    MovieDBManager movieDBManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etTitle = findViewById(R.id.etTitle);
        etDate = findViewById(R.id.etDate);
        etRate = findViewById(R.id.etRate);
        etCharacters = findViewById(R.id.etCharacters);
        etDirector = findViewById(R.id.etDirector);
        etReview = findViewById(R.id.etReview);

        movieDBManager = new MovieDBManager(this);
        
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                etDate.setText(y + "년 " + (m+1) + "월 " + d + "일");
            }
        };
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                if(etTitle.getText().toString().equals("")) {
                    Toast.makeText(this, "영화 제목을 입력하세요.", Toast.LENGTH_SHORT).show();
                    break;
                } else if (etDate.getText().toString().equals("")) {
                    Toast.makeText(this, "개봉일을 입력하세요.", Toast.LENGTH_SHORT).show();
                    break;
                } else if (etRate.getText().toString().equals("")) {
                    Toast.makeText(this, "평점을 입력하세요.", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    boolean result = movieDBManager.addNewMovie(
                            new Movie(etTitle.getText().toString(), etDate.getText().toString(), etRate.getText().toString(),
                                    etCharacters.getText().toString(), etDirector.getText().toString(), etReview.getText().toString()));
                    if (result) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("title", etTitle.getText().toString());
                        resultIntent.putExtra("date", etDate.getText().toString());
                        resultIntent.putExtra("rate", etRate.getText().toString());
                        resultIntent.putExtra("characters", etCharacters.getText().toString());
                        resultIntent.putExtra("director", etDirector.getText().toString());
                        resultIntent.putExtra("review", etReview.getText().toString());
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    } else {
                        Toast.makeText(this,"새로운 영화 추가 실패!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btnCancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.btnDate:
                DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, 2022, 6 - 1, 24);
                dialog.show();
                break;
        }
    }
}
