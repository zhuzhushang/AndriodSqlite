package com.uyac.andriodsqlite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ShaoQuanwei on 2017/2/14.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context context;
    private List<PersonModel> list;

    public RecyclerAdapter(Context context, List<PersonModel> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<PersonModel> list) {
        this.list = list;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_person, null);
        RecyclerAdapter.ViewHolder viewHolder = new RecyclerAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {

        PersonModel model = list.get(position);
        holder.name.setText("名字：" + model.getName() + "(" + model.getId()+")");
        holder.address.setText("地址：" + model.getAddress());
        if (model.getIsBoy() == 1) {
            holder.sex.setText("性别：男");
        } else {
            holder.sex.setText("性别：女");
        }
        holder.age.setText("年龄：" + model.getAge());

//        ByteArrayInputStream bais = new ByteArrayInputStream(model.getPic(),0,model.getPic().length);
//        holder.pic.setImageDrawable(Drawable.createFromStream(bais,"img"));
        Bitmap b = BitmapFactory.decodeByteArray(model.getPic(), 0, model.getPic().length);
        holder.pic.setImageBitmap(b);

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView pic;
        private TextView name;
        private TextView sex;
        private TextView age;
        private TextView address;

        public ViewHolder(View itemView) {
            super(itemView);

            pic = (ImageView) itemView.findViewById(R.id.pic);
            name = (TextView) itemView.findViewById(R.id.name);
            sex = (TextView) itemView.findViewById(R.id.sex);
            age = (TextView) itemView.findViewById(R.id.age);
            address = (TextView) itemView.findViewById(R.id.address);

        }
    }


}
