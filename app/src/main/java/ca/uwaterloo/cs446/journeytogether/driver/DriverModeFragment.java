package ca.uwaterloo.cs446.journeytogether.driver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import ca.uwaterloo.cs446.journeytogether.R;

public class DriverModeFragment extends Fragment {

    private TextView textViewMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_mode, container, false);

        textViewMessage = view.findViewById(R.id.textViewMessage);
        
        startDriverModeService();

        return view;
    }

    private void startDriverModeService() {
        Intent serviceIntent = new Intent(requireContext(), DriverModeService.class);
        ContextCompat.startForegroundService(requireContext(), serviceIntent);
    }

    private BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("LOCATION_UPDATE")) {
                String message = intent.getStringExtra("message");
                textViewMessage.setText(message);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("LOCATION_UPDATE");
        requireActivity().registerReceiver(locationReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        requireActivity().unregisterReceiver(locationReceiver);
    }

}

