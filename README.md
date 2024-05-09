# Java Developer - Accenture

## Assignment
```
Write a program that retrieves the following information given the data from the API as described on https://gitlab.com/restcountries/restcountries:

Sorted list of countries by population density in descending order.
Country in Asia containing the most bordering countries of a different region.
```

## How to use

This project was written using the Java SDK 22 with Language level 22. The reason being that I wanted to use the newest available version for this little project. Running the application is best done within the IntelliJ IDE, where it should work out-of-the-box. In order to view the output, run the `Main` class. 

If an executable file is desired, go to the release tabs in GitHub to find the Jar file for this project.

The project also contains two very simple tests found within the `tests` folder. These were used to test the functionality of the assigned functions.

## Design choices 
There were some conscious design choices that affect performance. These choices were made because for this assignment I valued readability and maintainability over performance. In addition, there were no performance criteria or use case scenarios set for this assignment, so optimizing without any necessity seemed unnecessary (especially because we don't know how often this will be executed). However, I will quickly list the most obvious optimization choices:


### Looping over the countries to find country by code

When looping through the neighbors of a given country, we want to know what region they are from. To do this; we search for their country code in the entire list of countries by looping through it. This results in an (O(n)) lookup each time. Alternatively, we could store the countries in a Key-Value map and make it (O(1)).

### Manually filtering countries for Asian countries

Depending on the requirements, we could also retrieve the countries from the API by executing another HTTP request with the path `/region/asia`. Which could be quicker depending on the size of the dataset (although it's only 250 entries here). But then again, it also depends on the retrieval time.

### Both assignments looping seperately

In our implementation, both assignments are fully separated to maintain a single responsibility. However, both functions iterate through the entire list of countries separately. If performance was of concern, we could iterate through it once and perform both actions side by side, likely increasing performance.