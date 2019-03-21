package ru.lavifae.homework1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class ListFragment extends Fragment {
    private static final String ARGS_STRINGS = "args:strings";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list,
                container, false);

        final RecyclerView recyclerView;
        recyclerView = view.findViewById(R.id.my_list);

        // tries for orientation task
        final int columns = getResources().getInteger(R.integer.numOfCols);
        GridLayoutManager layout = new GridLayoutManager(getContext(), columns);
        recyclerView.setLayoutManager(layout);

        // restore or get strings
        ArrayList<String> strings;
        if (getArguments() != null) {
            strings = getArguments().getStringArrayList(ARGS_STRINGS);
        }
        else {
            strings = new ArrayList<>();
        }

        final MyAdapter myAdapter = new MyAdapter(strings);
        recyclerView.setAdapter(myAdapter);

        Button button = view.findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nextNum = (myAdapter.getItemCount() + 1) + "";
                myAdapter.addItem(nextNum);
            }
        });

        return view;

    }

    public static ListFragment newInstance(ArrayList<String> strings) {
        ListFragment listFragment = new ListFragment();

        Bundle bundle = new Bundle();
        bundle.putStringArrayList(ARGS_STRINGS, strings);
        listFragment.setArguments(bundle);

        return listFragment;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.number);
        }

    }

    class MyAdapter extends RecyclerView.Adapter<ListFragment.MyViewHolder> {
        private ArrayList<String> mData;

        MyAdapter(ArrayList<String> data) {
            mData = data;
        }

        @NonNull
        @Override
        public ListFragment.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View v = inflater.inflate(R.layout.list_element, viewGroup, false);
            return new ListFragment.MyViewHolder(v);
        }


        @Override
        public void onBindViewHolder(@NonNull final ListFragment.MyViewHolder myViewHolder, int i) {
            String str = mData.get(i);
            myViewHolder.mTextView.setText(str);

            /* color odd and even numbers */
            if (i % 2 == 0) {
                myViewHolder.mTextView.setTextColor(getResources().getColor(R.color.colorOdd));
            }
            else {
                myViewHolder.mTextView.setTextColor(getResources().getColor(R.color.colorEven));
            }

            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get number and color of clicked element
                    CharSequence number = myViewHolder.mTextView.getText();
                    int color = myViewHolder.mTextView.getCurrentTextColor();

                    NumberFragment numberFragment = NumberFragment.newInstance(number, color);
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.activity_main_frame, numberFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        void addItem(String newItem){
            mData.add(newItem);
            notifyItemInserted(getItemCount());
        }
    }
}
