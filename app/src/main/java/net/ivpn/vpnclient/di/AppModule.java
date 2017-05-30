package net.ivpn.vpnclient.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import net.ivpn.vpnclient.api.IVPNService;
import net.ivpn.vpnclient.model.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module public class AppModule {

  private Application application;

  public AppModule(Application application) {
    this.application = application;
  }

  @Provides @Singleton Context providesAppContext() {
    return application.getApplicationContext();
  }

  @Provides @Singleton
  AppDatabase providesDatabase(Context context) {
    return Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class).build();
  }


  @Provides @Singleton
  IVPNService providesIvpnService() {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.ivpn.net/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    return retrofit.create(IVPNService.class);
  }
}
