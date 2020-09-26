//for result of product
//RA

package com.geforce.vijai.efarmeradmin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;

    List<GetDataAdapter> getDataAdapter;

     ImageLoader imageLoader1;

    public RecyclerViewAdapter(List<GetDataAdapter> getDataAdapter, Context context){

        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {

        final GetDataAdapter getDataAdapter1 =  getDataAdapter.get(position);

        imageLoader1 = ServerImageParseAdapter.getInstance(context).getImageLoader();

        imageLoader1.get(getDataAdapter1.getImageServerUrl(),
                ImageLoader.getImageListener(
                        Viewholder.networkImageView,//Server Image
                        R.mipmap.ic_launcher,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );

        Viewholder.networkImageView.setImageUrl(getDataAdapter1.getImageServerUrl(), imageLoader1);

        Viewholder.ImageTitleNameView.setText(getDataAdapter1.getImageTitleName());

        Viewholder.ImagePriceView.setText(getDataAdapter1.getImagePrice());
        Viewholder.ImageLocationView.setText(getDataAdapter1.getImageDistrict());
        Viewholder.ImageBalanceView.setText(getDataAdapter1.getImageRem());
        Viewholder.ImageDescriptionView.setText(getDataAdapter1.getImageDesc());

        /*Viewholder.ImagePhoneView.setOnClickListener(new View.OnClickListener() {
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
                Toast.makeText(context,"click"+getDataAdapter1.getImageTitleName(),Toast.LENGTH_SHORT).show();
                Intent i=new Intent(context,pd_result_view.class);
                i.putExtra("product_image",getDataAdapter1.getImageServerUrl());//this is doubt
                i.putExtra("product_name",getDataAdapter1.getImageTitleName());
                i.putExtra("product_city",getDataAdapter1.getImageCity());
                i.putExtra("product_desc",getDataAdapter1.getImageDesc());
                i.putExtra("product_price",getDataAdapter1.getImagePrice());
                i.putExtra("product_userid",getDataAdapter1.getImageUserId());
                i.putExtra("product_district",getDataAdapter1.getImageDistrict());
                i.putExtra("product_city",getDataAdapter1.getImageCity());
                i.putExtra("product_postal",getDataAdapter1.getImagePostal());
                i.putExtra("product_id",getDataAdapter1.getImageId());
                i.putExtra("product_update",getDataAdapter1.getImageUpdate());
                i.putExtra("product_rem",getDataAdapter1.getImageRem());
                i.putExtra("product_phone",getDataAdapter1.getImagePhone());
                context.startActivity(i);

            }
        });*/



    }

    @Override
    public int getItemCount() {

        return getDataAdapter.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView ImageTitleNameView;
        public NetworkImageView networkImageView ;
        public TextView ImagePriceView;
        public TextView ImageLocationView;
        public TextView ImageBalanceView;
        public TextView ImagePhoneView;
        public TextView ImageDescriptionView;

        public ConstraintLayout constraintLayout;
        public ViewHolder(View itemView) {

            super(itemView);

            ImageTitleNameView = (TextView) itemView.findViewById(R.id.textView_item) ;

            networkImageView = (NetworkImageView) itemView.findViewById(R.id.VollyNetworkImageView1) ;
            ImagePriceView=(TextView)itemView.findViewById(R.id.price_view);
            ImageBalanceView=(TextView)itemView.findViewById(R.id.balance_view);
            ImageLocationView=(TextView)itemView.findViewById(R.id.location_view);
            //ImagePhoneView=(TextView)itemView.findViewById(R.id.pcall);
            ImageDescriptionView=(TextView)itemView.findViewById(R.id.description);

            constraintLayout=(ConstraintLayout)itemView.findViewById(R.id.constraint);

        }
    }
}