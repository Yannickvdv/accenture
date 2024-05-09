package classes;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Country {
    @SerializedName("name")
    private Name name;
    @SerializedName("cca3")
    private String countryCode;
    private double area;
    private int population;
    private String region;
    private List<String> borders;

    public Country(Name name, String countryCode, double area, int population, String region, List<String> borders) {
        this.name = name;
        this.countryCode = countryCode;
        this.area = area;
        this.population = population;
        this.region = region;
        this.borders = borders;
    }

    public String getName() {
        return name != null ? name.common : null;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public double getArea() {
        return area;
    }

    public int getPopulation() {
        return population;
    }

    public String getRegion() {
        return region;
    }

    public List<String> getBorders() {
        return borders;
    }

    // Used to access the 'common' Name property
    public static class Name {
        private String common;

        public Name(String common) { this.common = common; }
    }
}