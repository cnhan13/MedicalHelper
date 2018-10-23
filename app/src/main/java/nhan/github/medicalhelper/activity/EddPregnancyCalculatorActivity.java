package nhan.github.medicalhelper.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

import nhan.github.medicalhelper.Constant;
import nhan.github.medicalhelper.R;
import nhan.github.medicalhelper.fragment.DatePickerFragment;
import nhan.github.medicalhelper.utils.DateTimeUtil;
import nhan.github.medicalhelper.utils.Strings;

public class EddPregnancyCalculatorActivity extends AppCompatActivity
        implements DatePickerFragment.OnDateSetListener {

    Button btCurrentDate;
    Button btLastMenstrualPeriod;
    Button btUltraSoundDate;
    Button btEmbryoTransferDate;
    EditText etGestationalAgeByUSWeeks;
    EditText etGestationalAgeByUSDays;
    EditText etEmbryoAgeAtTransferDays;
    EditText etCurrentGestationalAgeByLMPResult;
    EditText etEDDByLMPResult;
    EditText etCurrentGestationalAgeByUSResult;
    EditText etEDDByUSResult;
    EditText etCurrentGestationalAgeByEmbryoResult;
    EditText etEDDByEmbryoResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edd_pregnancy_calculator);

        btCurrentDate = findViewById(R.id.btCurrentDate);
        btLastMenstrualPeriod = findViewById(R.id.btLastMenstrualPeriod);
        btUltraSoundDate = findViewById(R.id.btUltraSoundDate);
        btEmbryoTransferDate = findViewById(R.id.btEmbryoTransferDate);
        etGestationalAgeByUSWeeks = findViewById(R.id.etGestationalAgeByUSWeeks);
        etGestationalAgeByUSDays = findViewById(R.id.etGestationalAgeByUSDays);
        etEmbryoAgeAtTransferDays = findViewById(R.id.etEmbryoAgeAtTransferDays);
        etCurrentGestationalAgeByLMPResult = findViewById(R.id.etCurrentGestationalAgeByLMPResult);
        etEDDByLMPResult = findViewById(R.id.etEDDByLMPResult);
        etCurrentGestationalAgeByUSResult = findViewById(R.id.etCurrentGestationalAgeByUSResult);
        etEDDByUSResult = findViewById(R.id.etEDDByUSResult);
        etCurrentGestationalAgeByEmbryoResult = findViewById(R.id.etCurrentGestationalAgeByEmbryoResult);
        etEDDByEmbryoResult = findViewById(R.id.etEDDByEmbryoResult);

        btCurrentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v, String.valueOf(R.id.btCurrentDate));
            }
        });
        btLastMenstrualPeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v, String.valueOf(R.id.btLastMenstrualPeriod));
            }
        });
        btUltraSoundDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v, String.valueOf(R.id.btUltraSoundDate));
            }
        });
        btEmbryoTransferDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v, String.valueOf(R.id.btEmbryoTransferDate));
            }
        });
        etGestationalAgeByUSWeeks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setCurrentGestationalAgeByUSResult();
                setEDDByUSResult();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etGestationalAgeByUSDays.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setCurrentGestationalAgeByUSResult();
                setEDDByUSResult();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etEmbryoAgeAtTransferDays.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setCurrentGestationalAgeByEmbryoResult();
                setEDDByEmbryoResult();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btCurrentDate.setText(DateTimeUtil.calendarToDisplayDate(Calendar.getInstance()));
    }

    private void showDatePickerDialog(View v, String tag) {
        String dateStr = ((Button) v).getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(DatePickerFragment.DATE_KEY, dateStr);

        DialogFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(), tag);
    }

    @Override
    public void onDateSet(String tag, int year, int month, int dayOfMonth) {
        Calendar setCal = Calendar.getInstance();
        setCal.set(year, month, dayOfMonth);
        DateTimeUtil.clearTimeInDay(setCal);
        String dateStr = DateTimeUtil.calendarToDisplayDate(setCal);

        int tagInt = Integer.parseInt(tag);
        switch (tagInt) {
            case R.id.btCurrentDate:
                btCurrentDate.setText(dateStr);
                btCurrentDate.setTag(setCal);
                onSetCurrentDate(setCal);
                break;
            case R.id.btLastMenstrualPeriod:
                btLastMenstrualPeriod.setText(dateStr);
                btLastMenstrualPeriod.setTag(setCal);
                onSetLastMenstrualPeriod(setCal);
                break;
            case R.id.btUltraSoundDate:
                btUltraSoundDate.setText(dateStr);
                btUltraSoundDate.setTag(setCal);
                onSetUltraSoundDate(setCal);
                break;
            case R.id.btEmbryoTransferDate:
                btEmbryoTransferDate.setText(dateStr);
                btEmbryoTransferDate.setTag(setCal);
                onSetEmbryoTransferDate(setCal);
        }
    }

    private void onSetCurrentDate(Calendar curCal) {
        // Update Current Gestational Age by LMP
        String lmp = btLastMenstrualPeriod.getText().toString();
        Calendar lmpCal = DateTimeUtil.displayDateToCalendar(lmp);

        etCurrentGestationalAgeByLMPResult.setText(getCurrentGestationalAgeByLMP(curCal, lmpCal));

        // Update Current Gestational Age by US
        String us = btUltraSoundDate.getText().toString();
        Calendar usCal = DateTimeUtil.displayDateToCalendar(us);

        setCurrentGestationalAgeByUSResult(curCal, usCal);

        // Update Current Embryo Age by Embryo
        String embryo = btEmbryoTransferDate.getText().toString();
        Calendar embryoCal = DateTimeUtil.displayDateToCalendar(embryo);

        setCurrentGestationalAgeByEmbryoResult(curCal, embryoCal);
    }

    private void onSetLastMenstrualPeriod(Calendar lmpCal) {
        // Update Current Gestational Age by LMP
        String cur = btCurrentDate.getText().toString();
        Calendar curCal = DateTimeUtil.displayDateToCalendar(cur);

        etCurrentGestationalAgeByLMPResult.setText(getCurrentGestationalAgeByLMP(curCal, lmpCal));

        // Update EDD by LMP
        etEDDByLMPResult.setText(getEDDByLMP(lmpCal));
    }

    private void onSetUltraSoundDate(Calendar usCal) {
        // Update Current Gestational Age by US
        String cur = btCurrentDate.getText().toString();
        Calendar curCal = DateTimeUtil.displayDateToCalendar(cur);

        setCurrentGestationalAgeByUSResult(curCal, usCal);

        // Update EDD by US
        etEDDByUSResult.setText(getEDDByUS(usCal));
    }

    private void onSetEmbryoTransferDate(Calendar embryoCal) {
        // Update Current Embryo Age by Embryo Date
        String cur = btCurrentDate.getText().toString();
        Calendar curCal = DateTimeUtil.displayDateToCalendar(cur);

        setCurrentGestationalAgeByEmbryoResult(curCal, embryoCal);

        // Update EDD by Embryo
        etEDDByEmbryoResult.setText(getEDDByEmbryo(embryoCal));
    }

    private void setCurrentGestationalAgeByUSResult(Calendar curCal, Calendar usCal) {
        String weeksStr = etGestationalAgeByUSWeeks.getText().toString();
        int gestationalAgeByUSWeeks = Strings.isNullOrEmpty(weeksStr) ? 0
                : Integer.parseInt(weeksStr);

        String daysStr = etGestationalAgeByUSDays.getText().toString();
        int gestationalAgeByUSDays = Strings.isNullOrEmpty(daysStr) ? 0
                : Integer.parseInt(daysStr);

        etCurrentGestationalAgeByUSResult.setText(getCurrentGestationalAgeByUS(curCal, usCal,
                gestationalAgeByUSWeeks, gestationalAgeByUSDays));
    }

    private void setCurrentGestationalAgeByUSResult() {
        String cur = btCurrentDate.getText().toString();
        Calendar curCal = DateTimeUtil.displayDateToCalendar(cur);

        String us = btUltraSoundDate.getText().toString();
        Calendar usCal = DateTimeUtil.displayDateToCalendar(us);

        setCurrentGestationalAgeByUSResult(curCal, usCal);
    }

    private String getCurrentGestationalAgeByLMP(Calendar curCal, Calendar lmpCal) {
        String result;
        if (curCal == null || lmpCal == null || curCal.compareTo(lmpCal) < 0) {
            // curCal < lmpCal
            result = getString(R.string.not_available);
        } else {
            long timeDiffInMillis = curCal.getTimeInMillis() - lmpCal.getTimeInMillis();
            long weeksDiff = DateTimeUtil.millisToWeeksQuotient(timeDiffInMillis);
            long weeksDaysRemainderDiff = DateTimeUtil.millisToWeeksDaysRemainder(timeDiffInMillis);
            Resources res = getResources();
            result =
                    res.getQuantityString(R.plurals.number_of_weeks, (int) weeksDiff, weeksDiff)
                            + " "
                            + res.getQuantityString(R.plurals.number_of_days, (int) weeksDaysRemainderDiff,
                            weeksDaysRemainderDiff);
        }
        return result;
    }

    private String getEDDByLMP(Calendar lmpCal) {
        String result;
        if (lmpCal == null) {
            result = getString(R.string.not_available);
        } else {
            // Avoid changing lmpCal
            Calendar eddCal = (Calendar) lmpCal.clone();
            eddCal.add(Calendar.DAY_OF_YEAR, Constant.CARRY_DAYS);
            result = DateTimeUtil.calendarToDisplayDate(eddCal);
        }
        return result;
    }

    private String getCurrentGestationalAgeByUS(Calendar curCal, Calendar usCal,
                                                int ageByUSWeeks, int ageByUSDays) {
        String result;
        if (curCal == null || usCal == null) {
            result = getString(R.string.not_available);
        } else {
            // Avoid changing usCal
            // Get LMP by US
            Calendar estLmpCal = (Calendar) usCal.clone();
            estLmpCal.add(Calendar.DAY_OF_YEAR, -ageByUSWeeks * DateTimeUtil.DAYS_PER_WEEK
                    - ageByUSDays);

            if (curCal.compareTo(estLmpCal) < 0) {
                result = getString(R.string.not_available);
            } else {
                long timeDiffInMillis = curCal.getTimeInMillis() - estLmpCal.getTimeInMillis();
                long weeksDiff = DateTimeUtil.millisToWeeksQuotient(timeDiffInMillis);
                long weeksDaysRemainderDiff = DateTimeUtil.millisToWeeksDaysRemainder(timeDiffInMillis);
                Resources res = getResources();
                result =
                        res.getQuantityString(R.plurals.number_of_weeks, (int) weeksDiff, weeksDiff)
                                + " "
                                + res.getQuantityString(R.plurals.number_of_days, (int) weeksDaysRemainderDiff,
                                weeksDaysRemainderDiff);
            }
        }
        return result;
    }

    private String getEDDByUS(Calendar usCal) {
        String result;
        if (usCal == null) {
            result = getString(R.string.not_available);
        } else {
            String weeksStr = etGestationalAgeByUSWeeks.getText().toString();
            int gestationalAgeByUSWeeks = Strings.isNullOrEmpty(weeksStr) ? 0
                    : Integer.parseInt(weeksStr);

            String daysStr = etGestationalAgeByUSDays.getText().toString();
            int gestationalAgeByUSDays = Strings.isNullOrEmpty(daysStr) ? 0
                    : Integer.parseInt(daysStr);

            // Avoid changing usCal
            Calendar eddCal = (Calendar) usCal.clone();
            eddCal.add(Calendar.DAY_OF_YEAR,
                    -gestationalAgeByUSWeeks * DateTimeUtil.DAYS_PER_WEEK
                            - gestationalAgeByUSDays + Constant.CARRY_DAYS);
            result = DateTimeUtil.calendarToDisplayDate(eddCal);
        }
        return result;
    }

    private void setEDDByUSResult() {
        String us = btUltraSoundDate.getText().toString();
        Calendar usCal = DateTimeUtil.displayDateToCalendar(us);

        etEDDByUSResult.setText(getEDDByUS(usCal));
    }

    private void setCurrentGestationalAgeByEmbryoResult(Calendar curCal, Calendar embryoCal) {
        String daysStr = etEmbryoAgeAtTransferDays.getText().toString();
        int embryoAgeAtTransferDays = Strings.isNullOrEmpty(daysStr) ? 0
                : Integer.parseInt(daysStr);

        etCurrentGestationalAgeByEmbryoResult.setText(getCurrentGestationalAgeByEmbryo(curCal, embryoCal,
                embryoAgeAtTransferDays));
    }

    private void setCurrentGestationalAgeByEmbryoResult() {
        String cur = btCurrentDate.getText().toString();
        Calendar curCal = DateTimeUtil.displayDateToCalendar(cur);

        String embryo = btEmbryoTransferDate.getText().toString();
        Calendar embryoCal = DateTimeUtil.displayDateToCalendar(embryo);

        setCurrentGestationalAgeByEmbryoResult(curCal, embryoCal);
    }

    private String getCurrentGestationalAgeByEmbryo(Calendar curCal, Calendar embryoCal,
                                                    int embryoAgeAtTransferDays) {
        String result;
        if (curCal == null || embryoCal == null) {
            result = getString(R.string.not_available);
        } else {
            // Avoid changing embryoCal
            // Get LMP by Embryo Transfer Date & Embryo age at Transfer Date
            Calendar estLmpCal = (Calendar) embryoCal.clone();
            estLmpCal.add(Calendar.DAY_OF_YEAR, -embryoAgeAtTransferDays);

            if (curCal.compareTo(estLmpCal) < 0) {
                result = getString(R.string.not_available);
            } else {
                long timeDiffInMillis = curCal.getTimeInMillis() - estLmpCal.getTimeInMillis();
                long weeksDiff = DateTimeUtil.millisToWeeksQuotient(timeDiffInMillis);
                long weeksDaysRemainderDiff = DateTimeUtil.millisToWeeksDaysRemainder(timeDiffInMillis);
                Resources res = getResources();
                result =
                        res.getQuantityString(R.plurals.number_of_weeks, (int) weeksDiff, weeksDiff)
                                + " "
                                + res.getQuantityString(R.plurals.number_of_days, (int) weeksDaysRemainderDiff,
                                weeksDaysRemainderDiff);
            }
        }
        return result;
    }

    private String getEDDByEmbryo(Calendar embryoCal) {
        String result;
        if (embryoCal == null) {
            result = getString(R.string.not_available);
        } else {
            String daysStr = etEmbryoAgeAtTransferDays.getText().toString();
            int embryoAgeByEmbryoDays = Strings.isNullOrEmpty(daysStr) ? 0
                    : Integer.parseInt(daysStr);

            // Avoid changing embryoCal
            Calendar eddCal = (Calendar) embryoCal.clone();
            eddCal.add(Calendar.DAY_OF_YEAR, -embryoAgeByEmbryoDays
                    - Constant.LMP_TO_EMBRYO_DAYS + Constant.CARRY_DAYS);
            result = DateTimeUtil.calendarToDisplayDate(eddCal);
        }
        return result;
    }

    private void setEDDByEmbryoResult() {
        String embryo = btEmbryoTransferDate.getText().toString();
        Calendar embryoCal = DateTimeUtil.displayDateToCalendar(embryo);

        etEDDByEmbryoResult.setText(getEDDByEmbryo(embryoCal));
    }
}
