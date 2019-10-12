package test.test.test.network;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import test.test.test.network.model.CityResponse;

public interface CityService {

    @GET("citiesJSON")
    Single<CityResponse> queryGeonames(
            @Query("north") double north,
            @Query("south") double south,
            @Query("east") double east,
            @Query("west") double west,
            @Query("lang") String lang);


}
