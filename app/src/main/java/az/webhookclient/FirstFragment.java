package az.webhookclient;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import az.webhookclient.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(container.getContext());

        String smsUrl = prefs.getString(SMSBroadcastReceiver.SMSWebhookURLKey, SMSBroadcastReceiver.SMSWebhookDefault);
        binding.SMSwhURL.setText(smsUrl);
        binding.SMSwhURL.setOnClickListener(view -> {
            String text = String.valueOf(binding.SMSwhURL.getText());
            prefs.edit().putString(SMSBroadcastReceiver.SMSWebhookURLKey, text).apply();
        });
        String phoneUrl = prefs.getString(PhoneCallBroadcastReceiver.PhoneCallWebhookURLKey, PhoneCallBroadcastReceiver.PhoneCallWebhookDefault);
        binding.CallwhURL.setText(phoneUrl);
        binding.CallwhURL.setOnClickListener(view -> {
            String text = String.valueOf(binding.CallwhURL.getText());
            prefs.edit().putString(PhoneCallBroadcastReceiver.PhoneCallWebhookURLKey, text).apply();
        });
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}