package com.mouse.study.test.geo;

import com.fasterxml.jackson.databind.JsonNode;
import com.maxmind.db.Reader;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Subdivision;
import com.mouse.study.utils.JackJsonUtil;

import java.io.File;
import java.net.InetAddress;

/**
 * @author lwf
 * @date 2017/10/23
 * use:
 */
public class CountryGeo {

    @org.junit.Test
    public void getCountryForCity() throws Exception{
        File database = new File("E:/conf/0906/data/GeoLite2-City.mmdb");
        Reader reader = new Reader(database);
        InetAddress address = InetAddress.getByName("27.150.159.222");
        JsonNode response = reader.get(address);
        System.out.println(response);
        reader.close();
    }

    @org.junit.Test
    public void getCountry() throws Exception {
        // A File object pointing to your GeoIP2 or GeoLite2 database
        //File database = new File("E:/conf/0906/data/GeoLite2-City.mmdb");
        File database = new File("E:/conf/0906/data/GeoLite2-Country.mmdb");
        DatabaseReader reader = new DatabaseReader.Builder(database).build();
        InetAddress ipAddress = InetAddress.getByName("116.228.62.38"); //27.150.159.222
        CityResponse response = reader.city(ipAddress);

        Country country = response.getCountry();
        System.out.println("country:" + JackJsonUtil.objToStr(country)); // '美国'
        System.out.println("国家:" + country.getNames().get("zh-CN")); // '美国'

        Subdivision subdivision = response.getMostSpecificSubdivision();
        System.out.println("province:" + JackJsonUtil.objToStr(subdivision));
        System.out.println("省份："+subdivision.getNames().get("zh-CN"));

        City city = response.getCity();
        System.out.println("city" + JackJsonUtil.objToStr(city));
        System.out.println("城市：" + city.getNames().get("zh-CN"));
    }
}
