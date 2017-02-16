package com.evgeny.customitemleftmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Random random = new Random(0);

        RecyclerView rec = (RecyclerView) findViewById(R.id.rec);
        rec.setLayoutManager(new LinearLayoutManager(this));
        rec.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.item,parent,false);
                CustomItemView item = (CustomItemView) v.findViewById(R.id.list_item);
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setSelected(!v.isSelected());
                    }
                });
                RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder(v) {
                };
                return holder;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                if(position%2>0) {
                    ((CustomItemView) holder.itemView.findViewById(R.id.list_item)).setText(random.nextLong() + "");
                    ((CustomItemView) holder.itemView.findViewById(R.id.list_item)).setBold(true);
                } else {
                    String text = "";
                    for (int i = 0; i < position; i++) {
                        text += "9";
                    }
                    ((CustomItemView) holder.itemView.findViewById(R.id.list_item)).setText(text);
                    ((CustomItemView) holder.itemView.findViewById(R.id.list_item)).setBold(false);
                }
                ((CustomItemView) holder.itemView.findViewById(R.id.list_item)).setStatus(random.nextInt()%3);
                ((CustomItemView) holder.itemView.findViewById(R.id.list_item)).setMention(random.nextInt()%99);
            }

            @Override
            public int getItemCount() {
                return 100;
            }
        });
    }
}
