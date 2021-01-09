package com.example.fasalmandi;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class OrderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    int totalItems, scrolledOutItems;
    private LinearLayoutManager manager;
    Query query;
    private DocumentSnapshot lastDocumentSnapshot;
    Boolean isScrolling = false;
    SwipeRefreshLayout mSwipeRefreshLayout;
    OrderAdapter myAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = v.findViewById(R.id.my_recycler_view);
        if (mRecyclerView != null) {
            //to enable optimization of recyclerview
            mRecyclerView.setHasFixedSize(true);
        }
        lastDocumentSnapshot=null;
        manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        myAdapter= new OrderAdapter(mRecyclerView,getContext(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>());
        mRecyclerView.setAdapter(myAdapter);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);
                LoadData();
                // Fetching data from server
            }
        });


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItems = manager.getItemCount();
                scrolledOutItems =manager.findLastVisibleItemPosition();
                if(isScrolling && scrolledOutItems+1>=totalItems){
                    isScrolling = false;
                    LoadData();
                }
            }
        });
        return v;
    }
    private void LoadData() {
        mSwipeRefreshLayout.setRefreshing(true);
        if(lastDocumentSnapshot == null){
            query = db.collection("CropVariety").limit(10);
        }else{
            query = db.collection("CropVariety").startAfter(lastDocumentSnapshot).limit(10);
        }
        query.get().addOnSuccessListener(getActivity(), new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    lastDocumentSnapshot = documentSnapshot;
                    String key = documentSnapshot.getId();
                    String productName = documentSnapshot.getString("name");
                    String productImage = documentSnapshot.getString("productImage");

                    ((OrderAdapter)mRecyclerView.getAdapter()).update(key,productName,productImage);

                    //quantity-copies,
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }).addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        lastDocumentSnapshot =null;
        myAdapter= new OrderAdapter(mRecyclerView,getContext(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>());
        mRecyclerView.setAdapter(myAdapter);
        LoadData();
    }

    private class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
        RecyclerView recyclerView;
        Context context;
        ArrayList<String> productKeys=new ArrayList<>();
        ArrayList<String> productNames=new ArrayList<>();
        ArrayList<String> productImages= new ArrayList<>();

        public void update(String key,String productName,  String productImage){
            productKeys.add(key);
            productNames.add(productName);
            productImages.add(productImage);
            notifyDataSetChanged();  //refershes the recyler view automatically...

        }

        public OrderAdapter(RecyclerView recyclerView, Context context, ArrayList<String> productKeys,ArrayList<String> productNames,ArrayList<String> productImages) {
            this.recyclerView = recyclerView;
            this.context = context;
            this.productKeys = productKeys;
            this.productNames = productNames;
            this.productImages = productImages;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout_card, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.productName.setText(productNames.get(position));
            //   holder.productImage.setImageResource(productImages.get(position));


        }

        @Override
        public int getItemCount() {
            return productNames.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView  productName;
            ImageView productImage;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                productName=itemView.findViewById(R.id.productname);
                productImage = itemView.findViewById(R.id.productimage);

                //  cancel_button = itemView.findViewById(R.id.cancelButton);
            }
        }
    }

}