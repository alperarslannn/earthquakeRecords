package com.alperArslan.earthquakerecords.record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("api/v1/earthquake-record")
public class EathquakeRecordController {

    private final EarthQuakeRecordService earthQuakeRecordService;

    @Autowired
    public EathquakeRecordController(EarthQuakeRecordService earthQuakeRecordService){
        this.earthQuakeRecordService = earthQuakeRecordService;
    }

    @PostMapping(
            path = "{countryName}/records",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<EarthquakeRecord> getEarthquakeRecordsFromLastXDays(@PathVariable("countryName") String country,
                                                          @RequestParam("dayCount") Integer dayCount,
                                                          @RequestParam("countryName") String countryName){
        LocalDate end = LocalDate.now();
        LocalDate start
                = end.minusDays(dayCount);

        String startDate = start.toString();
        String endDate = end.toString();

        ArrayList<LinkedHashMap<String, Object>> rawData =fetchApiRawData(startDate, endDate);

        List<EarthquakeRecord> earthquakeRecords = earthQuakeRecordService.getEarthquakeRecordsFromLastXDays(rawData, countryName);

        if(earthquakeRecords.size() > 0){
            return earthquakeRecords;
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping
    @ResponseBody
    public ArrayList<LinkedHashMap<String, Object>> fetchApiRawData(String start, String end){

        String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson";
        url += "&starttime=" + start + "&endtime=" + end + "&limit=12000";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> result2 = ((Map<String, Object>)restTemplate.getForObject(url, Object.class));
        ArrayList<LinkedHashMap<String, Object>> result3 = (ArrayList<LinkedHashMap<String, Object>>) result2.get("features");
        ArrayList<LinkedHashMap<String, Object>> result = new ArrayList<>();
        for(int i=0; i<result3.size(); i++){
            LinkedHashMap<String, Object> resultItems = (LinkedHashMap<String, Object>) result3.get(i).get("properties");
            result.add(resultItems);
        }

        return result;
    }

}
