package test.test.test.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;



public class RetrofitHelper {


    public CityService getCityService() {
        final Retrofit retrofit = createRetrofit();
        return retrofit.create(CityService.class);
    }


    private OkHttpClient createOkHttpClient() {


        final OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {

            @Override
            public Response intercept(Chain chain) throws IOException {

                final Request original = chain.request();

                final HttpUrl originalHttpUrl = original.url();


                final HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("username", "demo")
                        .build();      // Request customization: add request headers



                final Request.Builder requestBuilder = original.newBuilder().url(url);

                final Request request = requestBuilder.build();

                return chain.proceed(request);

            }
        });
        return httpClient.build();
    }

    private Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://api.geonames.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(createOkHttpClient())
                            .build();
    }


}
