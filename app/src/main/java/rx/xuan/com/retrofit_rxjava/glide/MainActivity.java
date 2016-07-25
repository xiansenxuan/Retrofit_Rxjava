package rx.xuan.com.retrofit_rxjava.glide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.xuan.com.retrofit_rxjava.R;
import rx.xuan.com.retrofit_rxjava.rxjava.RxJavaActivity;


public class MainActivity extends Activity implements OnClickListener {


    public ImageView iv_image;
    private RecyclerView rv_image;

    private ArrayList<String> itemList;

    public String[] eatFoodyImages = {
//			"http://i.imgur.com/rFLNqWI.jpg",
//			"http://i.imgur.com/C9pBVt7.jpg",
            "http://i.imgur.com/rFL.jpg",
            "http://i.imgur.com/C9pBV7.jpg",
            "http://i.imgur.com/rT5vXE1.jpg",
            "http://i.imgur.com/aIy5R2k.jpg",
            "http://i.imgur.com/MoJs9pT.jpg",
            "http://i.imgur.com/S963yEM.jpg",
            "http://i.imgur.com/rLR2cyc.jpg",
            "http://i.imgur.com/SEPdUIx.jpg",
            "http://i.imgur.com/aC9OjaM.jpg",
            "http://i.imgur.com/76Jfv9b.jpg",
            "http://i.imgur.com/fUX7EIB.jpg",
            "http://i.imgur.com/syELajx.jpg",
            "http://i.imgur.com/COzBnru.jpg",
            "http://i.imgur.com/Z3QjilA.jpg",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_image = (ImageView) findViewById(R.id.iv_image);
        rv_image = (RecyclerView) findViewById(R.id.rv_image);

        iv_image.setOnClickListener(this);

        setRecycler();
    }

    private void setRecycler() {
        List<String> list=Arrays.asList(eatFoodyImages);
        itemList=new ArrayList<String>();
        for (int i = 0; i < 2; i++) {
            itemList.addAll(list);
        }


        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        rv_image.setLayoutManager(layoutManager);

        GlideManager.setRoundImage(this, R.drawable.splash3, iv_image);

        ImageAdapter adapter=new ImageAdapter();
        rv_image.setAdapter(adapter);
    }

    public class ImageAdapter extends
            RecyclerView.Adapter<ImageAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView iv_image;

            ViewHolder(View itemView) {
                super(itemView);
                iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            }
        }

        @Override
        public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
            View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_image, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ImageAdapter.ViewHolder holder, int position) {
            if(position>10){
                GlideManager.setNormalImage(MainActivity.this, itemList.get(position), holder.iv_image);
            }else{
                GlideManager.setRoundImage(MainActivity.this, itemList.get(position), holder.iv_image);
            }
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_image:
                startActivity(new Intent(MainActivity.this, RxJavaActivity.class));
                break;

            default:
                break;
        }
    }
}
