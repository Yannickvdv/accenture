import classes.Country;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MainTest {

    @org.junit.Test
    public void sortCountriesByPopulationDensityDescending() {

        List<Country> countries = new ArrayList<>();
        countries.add(new Country(new Country.Name("Canada"), "CAN", 20, 30, "North America", null));
        countries.add(new Country(new Country.Name("United States"), "USA", 2, 5, "North America", null));
        countries.add(new Country(new Country.Name("Mexico"), "MEX", 30, 40, "North America", null));
        countries.add(new Country(new Country.Name("Netherlands"), "NLD", 60, 40, "Europe", null));


        List<Country> sortedCountries = Main.sortCountriesByPopulationDensityDescending(countries);
        assertNotNull(sortedCountries);

        // Verify that each country's population density is greater than or equal to the next country's
        for (int i = 0; i < sortedCountries.size() - 1; i++) {
            Country country1 = sortedCountries.get(i);
            double density1 = country1.getPopulation() / country1.getArea();

            Country country2 = sortedCountries.get(i+1);
            double density2 = country2.getPopulation() / country2.getArea();
            assertTrue(density1 >= density2);
        }
    }

    @org.junit.Test
    public void findAsianCountryWithMostBorderingCountriesOfOtherRegion() {
        List<Country> countries = new ArrayList<>();

        // Asian country with the highest bordering non-Asian countries
        Country expectedCountry = new Country(new Country.Name("Asian country with 1 asian neighbor and 2 non-asian neighbors "), "HIG", 0, 0, "Asia", List.of("NN1", "NN2", "AN1"));
        countries.add(expectedCountry);

        // Non-asian country with the highest bordering non-Asian countries
        countries.add(new Country(new Country.Name("Non-Asian country with 1 asian neighbor 1 non-asian neighbor"), "HI2", 0, 0, "Europe", List.of("NN1", "NN2", "NN3")));

        // Countries in the Asian region
        countries.add(new Country(new Country.Name("Asian country with 2 asian neighbors 1 non-asian neighbor"), "LOW", 0, 0, "Asia", List.of("AN1", "AN2", "NN1")));
        countries.add(new Country(new Country.Name("Asian country with 1 asian neighbor 1 non-asian neighbor"), "LO2", 0, 0, "Asia", List.of("NN1", "AN1")));

        // Neighboring countries
        countries.add(new Country(new Country.Name("Asian neighbor 1"), "AN1", 0, 0, "Asia", null));
        countries.add(new Country(new Country.Name("Asian neighbor 2"), "AN2", 0, 0, "Asia", null));
        countries.add(new Country(new Country.Name("Non Asian Neighbor 1"), "NN1", 0, 0, "Europe", null));
        countries.add(new Country(new Country.Name("Non Asian Neighbor 2"), "NN2", 0, 0, "Europe", null));

        // Get response from method
        Country actualCountry = Main.findAsianCountryWithMostBorderingCountriesOfOtherRegion(countries);
        assertNotNull(actualCountry);

        // Verify that it's the asian country with the highest non-Asian bordering countries
        assertEquals(actualCountry, expectedCountry);
    }
}