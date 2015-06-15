package com.dojoandroid.dojoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dojoandroid.dojoapp.R;
import com.dojoandroid.dojoapp.entity.Character;

import java.util.List;

public class CharacterAdapter extends BaseAdapter {

    private List<Character> mContent = null;
    private LayoutInflater mInflater = null;

    private static class ViewHolder {
        public ImageView image;
        public TextView text;
    }

    public CharacterAdapter(Context context, List<Character> content) {
        mContent = content;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mContent.size();
    }

    @Override
    public Character getItem(int position) {
        return mContent.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        Character character = mContent.get(i);

        if(view == null) {
            view = mInflater.inflate(R.layout.character_row, null);

            holder = new ViewHolder();
            holder.image = (ImageView) view.findViewById(R.id.image);
            holder.text = (TextView) view.findViewById(R.id.name);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.image.setImageResource(character.getResource());
        holder.text.setText(character.getName());

        return view;
    }
}
