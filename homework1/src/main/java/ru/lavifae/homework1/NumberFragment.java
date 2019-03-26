package ru.lavifae.homework1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NumberFragment extends Fragment {
    private static final String ARGS_NUMBER = "args:number";
    private static final String ARGS_COLOR = "args:color";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_number,
                container, false);

        TextView textView = view.findViewById(R.id.selected_number);

        if  (getArguments() != null) {
            CharSequence number = getArguments().getCharSequence(ARGS_NUMBER);
            int color  = getArguments().getInt(ARGS_COLOR);
            textView.setText(number);
            textView.setTextColor(color);
        }

        return view;
    }

    public static NumberFragment newInstance(CharSequence number, int color) {
        NumberFragment numberFragment = new NumberFragment();

        Bundle bundle = new Bundle();
        bundle.putCharSequence(ARGS_NUMBER, number);
        bundle.putInt(ARGS_COLOR, color);
        numberFragment.setArguments(bundle);

        return numberFragment;
    }

}
