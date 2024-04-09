package util.JavaApiClass.iplAllMatches;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class SeriesInfo {
    private String id;
    private String name;
    private String startDate;
    private String endDate;
    private int odi;
    private int t20;
    private int test;
    private int squads;
    private int matches;
}

