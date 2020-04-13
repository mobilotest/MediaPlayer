package com.example.mediaplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final String header = "Parable of the"; //getString(R.string.header_3_parable);

    final String contractors = "Workers in the Vineyard"; //getString(R.string.proverb_contractors);
    final String talent = "Talents"; //getString(R.string.proverb_dig_talent);
    final String virgins = "Ten Virgins"; //getString(R.string.proverb_ten_virgins);
    final String prodigal = "Prodigal Son"; //getString(R.string.proverb_prodigal_son);
    final String wedding = "Wedding Feast"; //getString(R.string.proverb_invited_chosen);
    final String sower = "Sower"; //getString(R.string.proverb_sower);
    final String samaritan = "Good Samaritan"; //getString(R.string.proverb_good_samaritan);

    ListView listView;

    final int[] parableImages = {R.drawable.nayomniki, R.drawable.talent, R.drawable.virgins, R.drawable.prodigal, R.drawable.pir, R.drawable.sower, R.drawable.good};
    final String[] parableHeader = {header,header,header,header,header,header,header};
    final String[] parableNames = {contractors, talent, virgins, prodigal, wedding, sower, samaritan};

    final List<Proverb> listItems = new ArrayList<>();

    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //finding listview
        listView = findViewById(R.id.list);

        for (int i = 0; i < parableNames.length; i++) {
            Proverb proverb = new Proverb(parableImages[i], parableHeader[i], parableNames[i]);
            listItems.add(proverb);
        }

        customAdapter = new CustomAdapter(listItems, this);
        listView.setAdapter(customAdapter);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu.menu);
//        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
//        SearchView searchView = (SearchView) menuItem.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                customAdapter.getFilter().filter(newText);
//                return true;
//            }
//        });
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.app_bar_search){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class CustomAdapter extends BaseAdapter { //implements Filterable {

        private List<Proverb> itemsProverbList;
        private List<Proverb> itemsProverbListFiltered;
        private Context context;

        public CustomAdapter(List<Proverb> itemsProverbList, Context context) {
            this.itemsProverbList = itemsProverbList;
            this.itemsProverbListFiltered = itemsProverbList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return itemsProverbListFiltered.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.list_item, null);

            //getting view in list_item
            ImageView image = view.findViewById(R.id.imageView);
            TextView proverb = view.findViewById(R.id.proverb_text_view);
            TextView name = view.findViewById(R.id.name_text_view);

            //set the data
            image.setImageResource(itemsProverbListFiltered.get(position).getmImage());
            proverb.setText(itemsProverbListFiltered.get(position).getmProverb());
            name.setText(itemsProverbListFiltered.get(position).getmName());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, ItemViewActivity.class).putExtra("item", itemsProverbListFiltered.get(position)));
                }
            });
            return view;
        }

//        @Override
//        public Filter getFilter() {
//            Filter filter = new Filter() {
//                @Override
//                protected FilterResults performFiltering(CharSequence constraint) {
//                    FilterResults filterResults = new FilterResults();
//                    if(constraint == null || constraint.length() == 0){
//                        filterResults.count = itemsProverbList.size();
//                        filterResults.values = itemsProverbList;
//                    }else{
//                        String searchStr = constraint.toString().toLowerCase();
//                        List<Proverb> resultData = new ArrayList<>();
//                        for(Proverb proverb: itemsProverbList){
//                            if(itemsProverbList.getmName().contains(searchStr) || itemsProverbList.getmProverb().contains(searchStr)){
//                                resultData.add(itemsProverbList);
//                            }
//                            filterResults.count = resultData.size();
//                            filterResults.values = resultData;
//                        }
//                    }
//                    return filterResults;
//                }
//
//                @Override
//                protected void publishResults(CharSequence constraint, FilterResults results) {
//                    itemsProverbListFiltered = (List<Proverb>) results.values;
//                    notifyDataSetChanged();
//                }
//            }
//            return filter;
//        }
    }
}