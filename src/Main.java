import classes.Country;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class Main {
    static String RESOURCE_URI = "https://restcountries.com/v3.1/all";

    public static void main(String[] args) {
        try {
            // Retrieve the countries from the API
            Collection<Country> countries = getRestCountries();

            // Assignment: Sorted list of countries by population density in descending order.
                List<Country> countriesByPopulationDensity = sortCountriesByPopulationDensityDescending(countries);
            System.out.println("Sorted list of countries by population density in descending order:");
            for (Country country : countriesByPopulationDensity) {
                System.out.println(country.getName());
            }

            // Assignment: Country in Asia containing the most bordering countries of a different region.
            Country asianCountriesWithMostBorders = findAsianCountryWithMostBorderingCountriesOfOtherRegion(countries);
            System.out.println("Country in Asia containing the most bordering countries of a different region:");
            System.out.println(asianCountriesWithMostBorders.getName());

        } catch (IOException | NoSuchElementException | InterruptedException e) {
            System.err.println(STR."Error: \{e.getMessage()}");
            e.printStackTrace(System.out);
        }
    }

    /**
     * Sort countries by population density in descending order
     *
     * @param countries the collection of countries to be sorted
     * @return the sorted ArrayList of countries based on population density in descending order
     */
    public static List<Country> sortCountriesByPopulationDensityDescending(Collection<Country> countries) {
        // Cast to ArrayList to maintain order
        List<Country> sortableCountries = new ArrayList<> (countries);
        sortableCountries.sort(Comparator.comparingDouble(o -> {
            if (o.getArea() == 0) {
                // Handle divide by 0 case by placing it at the start of the list
                return Double.MAX_VALUE;
            } else {
                // Get population density by dividing population by area
                return (double) o.getPopulation() / o.getArea();
            }
        }));
        // Reverse to get descending order
        return sortableCountries.reversed();
    }

    /**
     * Find the asian country with the most bordering countries of other regions
     *
     * @param countries the countries to be searched through
     * @return the asian country with the most bodering countries of other regions
     */
    public static Country findAsianCountryWithMostBorderingCountriesOfOtherRegion(Collection<Country> countries) throws NoSuchElementException {
        int currentMostBorders = 0;
        Country countryMostBorders = null;

        // Loop through Asian countries
        for (Country country : countries) {
            if ("Asia".equals(country.getRegion())) {
                int nonAsianBorderCount = 0;

                // If country does not have any bordering countries, continue with next country
                if(country.getBorders() == null) continue;

                // For each of the country's bordering countries, validate that they're from a different one
                for (String border : country.getBorders()) {
                    Country neighbor = findCountryByCode(countries, border);
                    if (!"Asia".equals(neighbor.getRegion())) {
                        nonAsianBorderCount++;
                    }
                }

                // If count is bigger than current most update
                if (nonAsianBorderCount > currentMostBorders) {
                    currentMostBorders = nonAsianBorderCount;
                    countryMostBorders = country;
                }
            }
        }

        return countryMostBorders;
    }

    /**
     * Find a country in a list of countries by their country code
     *
     * @param countries the collection of countries to be searched through
     * @param code the country code searched for
     * @return the country
     */
    private static Country findCountryByCode(Collection<Country> countries, String code) throws NoSuchElementException {
        for (Country country : countries) {
            if (country.getCountryCode().equals(code)) {
                return country;
            }
        }

        throw new NoSuchElementException(STR."Country with code \{code} not found.");
    }

    /**
     * Execute Fetch request to retrieve countries from API
     *
     * @return the
     * @throws IOException
     * @throws InterruptedException
     */
    public static Collection<Country> getRestCountries() throws IOException, InterruptedException {
        Gson g = new Gson();

        // Make use of try-with-resources functionality to auto close the client the end of the try block
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(RESOURCE_URI))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // If the response is not as expected, throw an error
            if (response.statusCode() != 200) {
                throw new IOException(STR."Failed to retrieve data from the API. Status code: \{response.statusCode()}");
            }

            // Deserialize JSON to Country object
            Type collectionType = new TypeToken<Collection<Country>>(){}.getType();
            return g.fromJson(response.body(), collectionType);
        } catch (InterruptedException e) {
            throw new InterruptedException("Application interrupted while retrieving the countries from the API");
        }
    }
}
