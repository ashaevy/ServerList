package net.ivpn.vpnclient;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.ivpn.vpnclient.model.ServerItem;
import net.ivpn.vpnclient.model.ServersListService;
import net.ivpn.vpnclient.viewmodel.SelectedServerViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Observer<ServerItem>,
        LifecycleRegistryOwner {

    private SelectedServerViewModel selectedServerViewModel;

    @BindView(R.id.tv_selected_server) TextView selectedServerTextView;
    @BindView(R.id.btn_select_server) Button selectServer;

    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectedServerViewModel = ViewModelProviders.of(this).get(SelectedServerViewModel.class);

        ButterKnife.bind(this);
        selectServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SelectServerActivity.class));
            }
        });

        subscribeUiUpdates();
    }

    private void subscribeUiUpdates() {
        selectedServerViewModel.getSelectedServer().observe(this, this);
    }

    @Override
    public void onChanged(@Nullable ServerItem selectedServer) {
        if (selectedServer != null) {
            selectedServerTextView.setText(StringUtils.formatServerName(selectedServer));
        } else {
            // if there is no selected server
            // request data from ivpn service
            selectedServerTextView.setText(getString(R.string.updating_placeholder));
            selectedServerViewModel.requestDataFromServer();
        }
    }
}
