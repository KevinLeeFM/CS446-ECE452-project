package ca.uwaterloo.cs446.journeytogether;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import ca.uwaterloo.cs446.journeytogether.common.InAppNotice;
import ca.uwaterloo.cs446.journeytogether.schema.Trip;
import ca.uwaterloo.cs446.journeytogether.user.TripAdapter;

public class TripListFragment extends Fragment {

    private ArrayList<Trip> trips = new ArrayList<>();
    private View rootView;
    private RecyclerView recyclerView;
    private TripAdapter tripAdapter;
    private InAppNotice inAppNotice;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public TripListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_trip_list, container, false);
        inAppNotice = new InAppNotice(rootView);

        // placeholder: currently it make query about everything
        Trip.firestore.makeQuery(
                c -> c,
                (arr) -> {
                    this.trips = arr;
                    recyclerView = rootView.findViewById(R.id.tripListRecyclerView);
                    tripAdapter = new TripAdapter(trips, getContext());
                    recyclerView.setAdapter(tripAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                },
                () -> { inAppNotice.onError("Cannot fetch the trips. Please try again later."); }
        );

        return rootView;
    }
}
