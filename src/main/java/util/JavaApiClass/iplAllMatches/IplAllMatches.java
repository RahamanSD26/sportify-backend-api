package util.JavaApiClass.iplAllMatches;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class IplAllMatches {
    private String apikey;
    private AllMatchesData data;
    private String status;
    private Info info;
}
