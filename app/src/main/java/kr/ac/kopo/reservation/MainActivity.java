package kr.ac.kopo.reservation;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Chronometer chronometer;
    RadioGroup rg;
    DatePicker datePicker;
    TimePicker timePick;
    TextView textResult;
    Button btnDone; //

    int selectedYear, selectedMonth, selectedDay;
    int selectedHour, selectedMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        chronometer = findViewById(R.id.chronometer);
        rg = findViewById(R.id.rg);
        datePicker = findViewById(R.id.calendar);
        timePick = findViewById(R.id.time_picker);
        textResult = findViewById(R.id.text_result);
        btnDone = findViewById(R.id.btn_done); // 예약 완료 ID 연결

        // 초기 상태 숨김 처리
        rg.setVisibility(View.INVISIBLE);
        datePicker.setVisibility(View.INVISIBLE);
        timePick.setVisibility(View.INVISIBLE);

        // 크로노미터 클릭 시 예약 시작
        chronometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                chronometer.setTextColor(Color.RED);
                rg.setVisibility(View.VISIBLE);
            }
        });

        // [수정] '예약 완료' 버튼 클릭 시 이벤트 처리
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                chronometer.setTextColor(Color.BLUE);

                // 데이트피커와 타임피커에서 값 가져오기
                selectedYear = datePicker.getYear();
                selectedMonth = datePicker.getMonth() + 1;
                selectedDay = datePicker.getDayOfMonth();
                selectedHour = timePick.getHour();
                selectedMin = timePick.getMinute();

                // [핵심] 결과 텍스트뷰에 한 줄로 예약 정보 출력
                textResult.setText(selectedMonth + "월 " + selectedDay + "일 " +
                        selectedHour + "시 " + selectedMin + "분 예약됨");

                // 완료 후 입력창들 숨기기
                rg.setVisibility(View.INVISIBLE);
                datePicker.setVisibility(View.INVISIBLE);
                timePick.setVisibility(View.INVISIBLE);
            }
        });

        // 라디오 버튼 선택에 따른 피커 전환
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_date) {
                    datePicker.setVisibility(View.VISIBLE);
                    timePick.setVisibility(View.INVISIBLE);
                } else {
                    datePicker.setVisibility(View.INVISIBLE);
                    timePick.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}