package net.ivpn.vpnclient.di;

import net.ivpn.vpnclient.model.ServersListService;
import net.ivpn.vpnclient.viewmodel.SelectedServerViewModel;
import net.ivpn.vpnclient.viewmodel.ServersViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton @Component(modules = { AppModule.class }) public interface AppComponent {

  void inject(ServersListService service);

  void inject(ServersViewModel mainViewModel);

  void inject(SelectedServerViewModel mainViewModel);
}
