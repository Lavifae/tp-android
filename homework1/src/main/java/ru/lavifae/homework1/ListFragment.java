package ru.lavifae.homework1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list,
                container, false);

        final RecyclerView recyclerView;
        recyclerView = view.findViewById(R.id.my_list);

        // tries to fix orientation task
        final int columns = getResources().getInteger(R.integer.numOfCols);
        GridLayoutManager layout = new GridLayoutManager(getContext(), columns);
        recyclerView.setLayoutManager(layout);

        // first initialisation of data array
        ArrayList<String> strings = new ArrayList<>();
        fillList(strings);

        final ListFragment.MyAdapter adapter = new ListFragment.MyAdapter(strings);
        recyclerView.setAdapter(adapter);

        Button button = view.findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nextNum = (adapter.getItemCount() + 1) + "";
                adapter.addItem(nextNum);
            }
        });

        return view;

    }

    void fillList(List<String> toFill) {
        for (int i = 1; i <= 100; i++) {
            toFill.add(i + "");
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.number);
        }

    }

    class MyAdapter extends RecyclerView.Adapter<ListFragment.MyViewHolder> {
        private List<String> mData;

        MyAdapter(List<String> data) {
            mData = data;
        }

        @NonNull
        @Override
        public ListFragment.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View v = inflater.inflate(R.layout.list_element, viewGroup, false);
            Log.d("TAG", "onCreateViewHolder for element type " + i + "");
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
            Log.d("TAG", "binding element at position " + i);

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
            notifyItemInserted(mData.size());
        }
    }
}
