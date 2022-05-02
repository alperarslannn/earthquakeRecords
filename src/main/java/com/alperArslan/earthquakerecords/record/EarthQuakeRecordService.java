package com.alperArslan.earthquakerecords.record;

import constants.UsaStates;
import org.springframework.stereotype.Service;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EarthQuakeRecordService {

    public final Integer EARTHQUAKE_DOES_NOT_BELONG_TO_ANY_COUNTRY = 1;

    public List<EarthquakeRecord> getEarthquakeRecordsFromLastXDays(ArrayList<LinkedHashMap<String, Object>> rawData, String countryName){

        List<EarthquakeRecord> earthquakeRecords = new ArrayList<>();

        for(int i = 0; i<rawData.size(); i++){

            if(rawData.get(i).get("place") == null){
                continue;
            }

            String[] splittedPlace = ((String)rawData.get(i).get("place")).split(",");
            if(splittedPlace.length == EARTHQUAKE_DOES_NOT_BELONG_TO_ANY_COUNTRY){
                continue;
            } else {
                if(countryName.equals("United States") ){
                    if(UsaStates.getUsaStates().containsKey(splittedPlace[splittedPlace.length - 1].trim())
                        || UsaStates.getUsaStates().containsValue(splittedPlace[splittedPlace.length - 1].trim())){

                        addRecord(rawData.get(i), earthquakeRecords, countryName);

                    }
                } else {
                    if(splittedPlace[splittedPlace.length - 1].contains(countryName)){

                        addRecord(rawData.get(i), earthquakeRecords, countryName);

                    } else {
                        continue;
                    }
                }
            }
        }

        return earthquakeRecords;

    }

    public static void addRecord(LinkedHashMap<String, Object> rawData, List<EarthquakeRecord> earthquakeRecords, String countryName){

        DateFormat obj = new SimpleDateFormat("dd MMM yyyy HH:mm:ss Z");

        EarthquakeRecord earthQuakeRecord = new EarthquakeRecord();
        earthQuakeRecord.setPlace((String)rawData.get("place"));

        Double magnitude = 0.0;
        if(rawData.get("mag") instanceof Integer){
            magnitude = Double.valueOf((Integer)rawData.get("mag"));
        } else {
            magnitude = (Double) rawData.get("mag");
        }

        earthQuakeRecord.setMagnitude(magnitude);
        earthQuakeRecord.setCountry(countryName);
        Date unformattedDate = new Date((Long) rawData.get("time"));
        earthQuakeRecord.setDate(obj.format(unformattedDate));

        earthquakeRecords.add(earthQuakeRecord);
    }


}
