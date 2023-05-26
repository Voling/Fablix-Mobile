package edu.uci.ics.fabflixmobile.data.model;

/**
 * Movie class that captures movie information for movies retrieved from MovieListActivity
 */
public class Movie {
    private final String name;
    private final short year;
    private final String director;
    private final float price;


    public Movie(String name, short year, String director, float price) {
        this.name = name;
        this.year = year;
        this.director = director;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public short getYear() {
        return year;
    }
    public String getDirector() { return director; }
    public float getPrice() { return price; }
}