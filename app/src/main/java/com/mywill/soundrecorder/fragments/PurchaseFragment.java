package com.mywill.soundrecorder.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.mywill.soundrecorder.R;
import com.mywill.soundrecorder.adapters.PurchaseListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Daniel on 5/22/2017.
 */

public class PurchaseFragment extends android.app.Fragment implements PurchasesUpdatedListener {
  private BillingClient mBillingClient;
  private RecyclerView mRecyclerView;
  private PurchaseListAdapter adapter;
  private List<SkuDetails> listData = new ArrayList<>();
  @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      mBillingClient = BillingClient.newBuilder(getActivity()).setListener(this).build();
      mBillingClient.startConnection(new BillingClientStateListener() {
        @Override
        public void onBillingSetupFinished(@BillingClient.BillingResponse int billingResponseCode) {
          if (billingResponseCode == BillingClient.BillingResponse.OK) {
            // The billing client is ready. You can query purchases here.
          }
        }
        @Override
        public void onBillingServiceDisconnected() {
          // Try to restart the connection on the next request to
          // Google Play by calling the startConnection() method.
        }
      });
    }
    private void getProductList(){
    String[] list= {"10_days_using", "10_days_using_high_quality_record",
    "10_records_storage", "5_days_using", "7_days_using", "7_days_using_high_quality_record","8_records_storage"};
      List<String> skuList = Arrays.asList(list);
      SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
      params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
      mBillingClient.querySkuDetailsAsync(params.build(),
          new SkuDetailsResponseListener() {
            @Override
            public void onSkuDetailsResponse(int responseCode, List<SkuDetails> skuDetailsList){
              if (responseCode == BillingClient.BillingResponse.OK
                  && skuDetailsList != null) {
                listData.clear();
                listData.addAll(skuDetailsList);
                adapter.notifyDataSetChanged();
              }
          }
    });

    }

  @Override
  public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {
    if (responseCode == BillingClient.BillingResponse.OK
        && purchases != null) {
      Toast.makeText(getActivity(), "Purchase Successfully", Toast.LENGTH_SHORT).show();
      for (Purchase purchase : purchases) {
        mBillingClient.consumeAsync(purchase.getPurchaseToken(), new ConsumeResponseListener() {
          @Override
          public void onConsumeResponse(@BillingClient.BillingResponse int responseCode, String outToken) {
            if (responseCode == BillingClient.BillingResponse.OK) {
              // Handle the success of the consume operation.
              // For example, increase the number of coins inside the user's basket.
              Toast.makeText(getActivity(), "Consume Successfully", Toast.LENGTH_SHORT).show();

            }
          }});
        }
    } else if (responseCode == BillingClient.BillingResponse.USER_CANCELED) {
      // Handle an error caused by a user cancelling the purchase flow.
    } else {
      // Handle any other error codes.
    }
  }
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_purchase, container, false);

    mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
    mRecyclerView.setHasFixedSize(true);
    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
    llm.setOrientation(LinearLayoutManager.VERTICAL);
    mRecyclerView.setLayoutManager(llm);
    adapter = new PurchaseListAdapter(getActivity(), listData);
    adapter.setClickListener(new PurchaseListAdapter.ItemClickListener() {
      @Override
      public void onItemClick(SkuDetails skuDetails) {
        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
           .setSkuDetails(skuDetails)
            .build();
        int responseCode = mBillingClient.launchBillingFlow(getActivity(), flowParams);
      }
    });
    mRecyclerView.setAdapter(adapter);

    getProductList();
    return v;
  }

}
