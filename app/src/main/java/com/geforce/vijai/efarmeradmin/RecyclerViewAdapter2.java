//for Main activity
//RA2
package com.geforce.vijai.efarmeradmin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder> {


    Context context;

    List<GetDataAdapter> getDataAdapter;

    ImageLoader imageLoader1;

    public RecyclerViewAdapter2(List<GetDataAdapter> getDataAdapter, Context context){

        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_for_user_list, parent, false);
        RecyclerViewAdapter2.ViewHolder viewHolder = new RecyclerViewAdapter2.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder Viewholder, int position) {
        final GetDataAdapter getDataAdapter1 =  getDataAdapter.get(position);
        Viewholder.ImageUserNameView.setText(getDataAdapter1.getImageTitleName());

        Viewholder.ImageCityView.setText(getDataAdapter1.getImageCity());
        Viewholder.ImageDistrictView.setText(getDataAdapter1.getImageDistrict());
        Viewholder.ImageUserIdView.setText(getDataAdapter1.getImageUserId());
        Viewholder.ImagePostalView.setText(getDataAdapter1.getImagePostal());
        Viewholder.ImagePhoneView.setText(getDataAdapter1.getImagePhone());

        Viewholder.ImagePhoneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+getDataAdapter1.getImagePhone()));
                context.startActivity(intent);

            }
        });
        Viewholder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,""+getDataAdapter1.getImageTitleName(),Toast.LENGTH_SHORT).show();
                Intent i=new Intent(context,pd_result_view.class);
                i.putExtra("product_userid",getDataAdapter1.getImageUserId());
                i.putExtra("user_phone",getDataAdapter1.getImagePhone());
                context.startActivity(i);

            }
        });

    }




        //imageLoader1 = ServerImageParseAdapter.getInstance(context).getImageLoader();

       /* imageLoader1.get(getDataAdapter1.getImageServerUrl(),
                ImageLoader.getImageListener(
                        Viewholder.networkImageView,//Server Image
                        R.mipmap.ic_launcher,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );*/

        //Viewholder.networkImageView.setImageUrl(getDataAdapter1.getImageServerUrl(), imageLoader1);



        /**/



    @Override
    public int getItemCount() {
        return getDataAdapter.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView ImageUserNameView;
        public TextView ImageCityView;
        public TextView ImageDistrictView;
        public TextView ImageUserIdView;
        public TextView ImagePhoneView;
        public TextView ImagePostalView;
        public ConstraintLayout constraintLayout;
        public ViewHolder(View itemView) {

            super(itemView);

            ImageUserNameView= (TextView) itemView.findViewById(R.id.uname) ;
            ImageCityView=(TextView)itemView.findViewById(R.id.ucity);
            ImageDistrictView=(TextView)itemView.findViewById(R.id.udistricts);
            ImagePhoneView=(TextView)itemView.findViewById(R.id.umobile);
            ImageUserIdView=(TextView)itemView.findViewById(R.id.uid);
            ImagePostalView=(TextView)itemView.findViewById(R.id.upin);
            constraintLayout=(ConstraintLayout)itemView.findViewById(R.id.constraint);

        }
    }
}

