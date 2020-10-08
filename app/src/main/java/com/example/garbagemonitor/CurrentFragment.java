package com.example.garbagemonitor;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

public class CurrentFragment extends Fragment {

    static CurrentFragment currentFragment;
    static Context mContext;

    FirebaseFirestore firebaseFirestore;

    private CircularProgressIndicator percentageBar;

    public CurrentFragment() {

    }

    public static CurrentFragment getInstance(Context context) {
        if (currentFragment==null) {
            currentFragment = new CurrentFragment();
            mContext=context;
        }
        return currentFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(getActivity());
        firebaseFirestore=FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.current_data_fragment,container,false);
        percentageBar=view.findViewById(R.id.percentage_bar);
        percentageBar.setMaxProgress(20);
        percentageBar.setCurrentProgress(0);
        return view;
    }

    public void setId(String id){
        Log.i("12345",id);
        firebaseFirestore.collection(id).orderBy("time", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Map<String,Object> curData=task.getResult().getDocuments().get(0).getData();
                    percentageBar.setMaxProgress(100);
                    if(Double.parseDouble(curData.get("percentage").toString())<=100)
                    percentageBar.setCurrentProgress(Double.parseDouble(curData.get("percentage").toString()));
                    else
                        percentageBar.setCurrentProgress(100);
                }
            }
        });
    }
}
