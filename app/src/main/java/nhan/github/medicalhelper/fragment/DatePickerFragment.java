package nhan.github.medicalhelper.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

import nhan.github.medicalhelper.utils.DateTimeUtil;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public static final String DATE_KEY = "date";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = null;
        Bundle arguments = getArguments();
        String dateStr;

        if (arguments != null && null != (dateStr = (String) arguments.get(DATE_KEY))) {
            c = DateTimeUtil.displayDateToCalendar(dateStr);
        }
        if (c == null) {
            c = Calendar.getInstance();
        }

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        ((OnDateSetListener) getActivity()).onDateSet(this.getTag(), year, month, dayOfMonth);
    }

    public interface OnDateSetListener {
        void onDateSet(String tag, int year, int month, int dayOfMonth);
    }
}
