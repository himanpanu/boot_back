package rating.app.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    private String country;
    private String state;
    private String city;
    private String postalCode;


    public String getCityStateCountry() {
        if(StringUtils.hasText(city)) {
            return city + ", " + state + ", " + country;
        } else {
            return null;
        }
    }

    public String getStateCountry() {
        if(StringUtils.hasText(state)) {
            return state + ", " + country;
        } else {
            return null;
        }
    }

    public void setCountry(String country) {
        if (StringUtils.hasText(country)) {
            this.country = country.toLowerCase();
        }
    }

    public void setState(String state) {
        if (StringUtils.hasText(state)) {
            this.state = state.toLowerCase();
        }
    }

    public void setCity(String city) {
        if (StringUtils.hasText(city)) {
            this.city = city.toLowerCase();
        }
    }

    public void setPostalCode(String postalCode) {
        if (StringUtils.hasText(postalCode)) {
            this.postalCode = postalCode.toLowerCase();
        }
    }

    public Set<String> getValidLocations(){
        Set<String> searchServiceLocations = new HashSet<>();
        if(StringUtils.hasText(getCityStateCountry())){
            searchServiceLocations.add(getCityStateCountry());
        }
        if(StringUtils.hasText(getStateCountry())){
            searchServiceLocations.add(getStateCountry());
        }
        searchServiceLocations.add(getCountry());
        return searchServiceLocations;
    }


}
