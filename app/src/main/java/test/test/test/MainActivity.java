package test.test.test;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import test.test.test.network.CityService;
import test.test.test.network.RetrofitHelper;
import test.test.test.network.model.CityResponse;
import test.test.test.network.model.Geoname;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }


    @NonNull
    private CityService mCityService;/**
     * Collects all subscriptions to unsubscribe later
     */

    @NonNull
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private TextView mOutputTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  mOutputTextView = (TextView) findViewById(R.id.output);  // Initialize the city endpoint
        mCityService = new RetrofitHelper().getCityService();  // Trigger our request and display afterwards
        requestGeonames();
    }

    @Override
    protected void onDestroy() {
        // DO NOT CALL .dispose()
        mCompositeDisposable.clear();
        super.onDestroy();
    }

    private void displayGeonames(@NonNull final List<Geoname> geonames) {
        // Cheap way to display a list of Strings — I was too lazy to   implement a RecyclerView
        final StringBuilder output = new StringBuilder();
        for (final Geoname geoname : geonames) {
            output.append(geoname.name).append("\n");
        }  mOutputTextView.setText(output.toString());
    }

    private void requestGeonames() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            mCompositeDisposable.add((Disposable) mCityService.queryGeonames(44.1, -9.9, -22.4, 55.2, "de")
                    .subscribeOn(Schedulers.io()) // "work" on io thread
                    .observeOn(AndroidSchedulers.mainThread()) // "listen" on UIThread

            );
        }
    }


}
