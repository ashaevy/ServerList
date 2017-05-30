package net.ivpn.vpnclient;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.ivpn.vpnclient.model.ServerItem;
import net.ivpn.vpnclient.viewmodel.ServersViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectServerActivity extends AppCompatActivity implements Observer<List<ServerItem>>,
        LifecycleRegistryOwner {

    @BindView(R.id.lv_servers)
    ListView listViewServers;
    private ServersViewModel serversViewModel;

    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_server);
        serversViewModel = ViewModelProviders.of(this).get(ServersViewModel.class);

        ButterKnife.bind(this);
        listViewServers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ServerItem selectedServer = ((ServerItem) listViewServers.getItemAtPosition(position));
                serversViewModel.setSelectedServer(selectedServer);
                finish();
            }
        });

        subscribeUiUpdates();
    }

    private void subscribeUiUpdates() {
        serversViewModel.getServerItemListLiveData().observe(this, this);
    }

    @Override
    public void onChanged(@Nullable List<ServerItem> serverItems) {
        ServersListAdapter serversAdapter = new ServersListAdapter(this, serverItems);
        listViewServers.setAdapter(serversAdapter);
    }

    static class ViewHolder {
        @BindView(android.R.id.text1)
        TextView tvName;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private class ServersListAdapter extends ArrayAdapter<ServerItem> {
        public ServersListAdapter(Context context, List<ServerItem> servers) {
            super(context, 0, servers);
        }

        @NonNull
        @Override public View getView(int position, View convertView, ViewGroup parent) {
            ServerItem serverItem = getItem(position);
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(android.R.
                        layout.simple_list_item_1, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }

            holder = (ViewHolder) convertView.getTag();
            String serverName = StringUtils.formatServerName(serverItem);
            holder.tvName.setText(serverName);
            return convertView;
        }
    }
}
