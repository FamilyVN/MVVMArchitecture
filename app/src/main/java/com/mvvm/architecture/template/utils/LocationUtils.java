package com.mvvm.architecture.template.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationUtils {
    /**
     * @param context
     * @param latitude
     * @param longitude
     * @return
     */
    public static Address getAddress(Context context, double latitude, double longitude) {
        // neu co loi Service not Available thi reset lai may test
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            if (addressList == null || addressList.size() == 0) return null;
            return addressList.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
