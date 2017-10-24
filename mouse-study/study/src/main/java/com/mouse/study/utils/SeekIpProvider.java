package com.mouse.study.utils;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Subdivision;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.InetAddress;

/**
 * @author lwf
 * @date 2017/10/23
 * use: ip 处理器
 */
@Data
public class SeekIpProvider {


    private static Logger log = LogManager.getLogger(SeekIpProvider.class);

    private static DatabaseReader reader;

    public static void main(String[] args) throws Exception {
        File database = new File("E:/conf/0906/data/GeoLite2-City-2.mmdb");
        DatabaseReader reader = new DatabaseReader.Builder(database).build();
        SeekIpProvider seekIpProvider = new SeekIpProvider();
        GeoIp convert = seekIpProvider.convert(reader, "183.250.108.171");
        System.out.println(JackJsonUtil.objToStr(convert));

    }

    public GeoIp convert(DatabaseReader reader, String ip) {
        //27.150.159.222
        CityResponse response = null;
        try {
            InetAddress ipAddress = InetAddress.getByName(ip);
            response = reader.city(ipAddress);
        } catch (Exception e) {
            log.error("ipAddress error", e);
        }
        Country country = response.getCountry();
        GeoIp geoIp = new GeoIp();
        geoIp.setCountry(country.getNames().get("zh-CN"));

        Subdivision subdivision = response.getMostSpecificSubdivision();
        geoIp.setCity(subdivision.getNames().get("zh-CN"));

        City city = response.getCity();
        geoIp.setRegion(city.getNames().get("zh-CN"));
        log.info("ip:{},convert:{}", ip, JackJsonUtil.objToStr(geoIp));
        return geoIp;
    }

    @Data
    public class GeoIp {
        /**
         * 国家
         */
        private String country;

        /**
         * 相当于省份
         */
        private String city;

        /**
         * 地区，城市
         */
        private String region;
    }
}


