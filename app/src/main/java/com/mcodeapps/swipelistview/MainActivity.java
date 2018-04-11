package com.mcodeapps.swipelistview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SwipeMenuListView swipeListView;
    private CustomListView adapter;
    private ArrayList<String> name = new ArrayList<String>();
    private ArrayList<Integer> photo = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeListView = findViewById(R.id.swipeListView);

        //create data to listview
        setDataListView();

        //create swipe menu listview
        setSwipeListView();

        adapter = new CustomListView(this);
        swipeListView.setAdapter(adapter);

    }

    private void setDataListView() {
        name.add("android studio");
        name.add("xcode");
        name.add("xamarin");
        name.add("react native");
        name.add("ionic");
        name.add("phonegap");
        name.add("node js");
        name.add("visual studio code");
        name.add("cordova");
        name.add("flutter");

        photo.add(R.drawable.android);
        photo.add(R.drawable.xcode);
        photo.add(R.drawable.xamarin);
        photo.add(R.drawable.react);
        photo.add(R.drawable.ionic);
        photo.add(R.drawable.phonegap);
        photo.add(R.drawable.nodejs);
        photo.add(R.drawable.vscode);
        photo.add(R.drawable.cordova);
        photo.add(R.drawable.flutter);

    }

    private void setSwipeListView() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(MainActivity.this);
                // set item background
                deleteItem.setBackground(new ColorDrawable(getResources().getColor(R.color.red)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_bin);
                // set item title
                deleteItem.setTitle("Delete");
                // set item title fontsize
                deleteItem.setTitleSize(14);
                // set item title font color
                deleteItem.setTitleColor(getResources().getColor(R.color.gray));
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        swipeListView.setMenuCreator(creator);
        swipeListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.getWindow();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_data);
                TextView dialog_title = (TextView)dialog.findViewById(R.id.dialog_title);
                dialog_title.setText(String.valueOf("Delete List"));

                TextView dialog_description = (TextView)dialog.findViewById(R.id.dialog_description);
                dialog_description.setText(String.valueOf("You want delete this"+name.get(position)+"?"));

                Button buttonCancel = (Button)dialog.findViewById(R.id.buttonCancel);
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                Button buttonOK = (Button)dialog.findViewById(R.id.buttonOK);
                buttonOK.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        name.remove(position);
                        photo.remove(position);
                        adapter.notifyDataSetChanged();

                        dialog.cancel();
                    }
                });

                dialog.show();

                return false;
            }
        });
    }


    //item promotion
    private class CustomListView extends BaseAdapter {

        private Context mContext;
        private LayoutInflater mInflater;
        private CustomListView(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return name.size();
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

            ViewHolder holder = null;
            if(convertView == null) {
                convertView = mInflater.inflate(R.layout.custom_listview, null);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView.findViewById(R.id.imageView);
                holder.header = (TextView) convertView.findViewById(R.id.text_header);
                holder.description = (TextView) convertView.findViewById(R.id.text_description);

                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder) convertView.getTag();
            }

            holder.header.setText(name.get(position));
            holder.description.setText(String.valueOf("No. " + position));
            holder.image.setImageResource(photo.get(position));

            return convertView;
        }
    }
    private class ViewHolder {
        ImageView image;
        TextView header;
        TextView description;
    }


}
