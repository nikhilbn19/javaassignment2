package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class DataManager {
    private Map<String, Movie> movies = new HashMap<>();
    private Map<String, Actor> actors = new HashMap<>();
    private Map<String, Director> directors = new HashMap<>();

    public void loadMovies(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                List<String> actorIds = Arrays.asList(values[7].split(";"));
                Movie movie = new Movie(values[0], values[1], Integer.parseInt(values[2]), values[3],
                        Double.parseDouble(values[4]), Integer.parseInt(values[5]), values[6], actorIds);
                movies.put(values[0], movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadActors(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Actor actor = new Actor(values[0], values[1], values[2], values[3]);
                actors.put(values[0], actor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadDirectors(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Director director = new Director(values[0], values[1], values[2], values[3]);
                directors.put(values[0], director);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Movie getMovieById(String movieId) {
        return movies.get(movieId);
    }

    public List<Movie> getMoviesByGenre(String genre) {
        return movies.values().stream()
                .filter(movie -> movie.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }

    public List<Movie> getMoviesByDirector(String directorName) {
        return movies.values().stream()
                .filter(movie -> directors.containsKey(movie.getDirectorId()) &&
                        directors.get(movie.getDirectorId()).getName().equalsIgnoreCase(directorName))
                .collect(Collectors.toList());
    }

    public List<Movie> getMoviesByReleaseYear(int year) {
        return movies.values().stream()
                .filter(movie -> movie.getReleaseYear() == year)
                .collect(Collectors.toList());
    }

    public List<Movie> getMoviesByYearRange(int startYear, int endYear) {
        return movies.values().stream()
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                .collect(Collectors.toList());
    }

    public void addMovie(String movieId, String title, int releaseYear, String genre, double rating,
                         int duration, String directorName, List<String> actorIds) {
        String directorId = getOrCreateDirectorId(directorName);
        Movie movie = new Movie(movieId, title, releaseYear, genre, rating, duration, directorId, actorIds);
        movies.put(movieId, movie);
    }

    private String getOrCreateDirectorId(String directorName) {
        for (Map.Entry<String, Director> entry : directors.entrySet()) {
            if (entry.getValue().getName().equalsIgnoreCase(directorName)) {
                return entry.getKey(); // Return existing director ID
            }
        }
        // Create new director entry with auto-generated ID
        String newDirectorId = "D" + (directors.size() + 1);
        directors.put(newDirectorId, new Director(newDirectorId, directorName, "Unknown", "Unknown"));
        return newDirectorId;
    }

    public void updateMovieRating(String movieId, double newRating) {
        if (movies.containsKey(movieId)) {
            movies.get(movieId).setRating(newRating);
        }
    }

    public void deleteMovie(String movieId) {
        movies.remove(movieId);
    }

    public List<Movie> getTopRatedMovies() {
        return movies.values().stream()
                .sorted(Comparator.comparingDouble(Movie::getRating).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<Movie> getSortedMoviesByYear() {
        return movies.values().stream()
                .sorted(Comparator.comparingInt(Movie::getReleaseYear))
                .limit(15)
                .collect(Collectors.toList());
    }

    public List<String> getTopDirectors() {
        Map<String, Long> directorMovieCount = movies.values().stream()
                .collect(Collectors.groupingBy(Movie::getDirectorId, Collectors.counting()));

        return directorMovieCount.entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .map(entry -> {
                    Director director = directors.get(entry.getKey());
                    return (director != null) ? director.getName() + " (" + entry.getValue() + " movies)" : "Unknown Director";
                })
                .limit(5)
                .collect(Collectors.toList());
    }

    public List<String> getMostFrequentActors() {
        Map<String, Long> actorMovieCount = movies.values().stream()
                .flatMap(movie -> movie.getActorIds().stream())
                .collect(Collectors.groupingBy(id -> id, Collectors.counting()));

        return actorMovieCount.entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .map(entry -> {
                    Actor actor = actors.get(entry.getKey());
                    return (actor != null) ? actor.getName() + " (" + entry.getValue() + " movies)" : "Unknown Actor (ID: " + entry.getKey() + ")";
                })
                .collect(Collectors.toList());
    }

    public List<Movie> getMoviesOfYoungestActor() {
        Optional<Actor> youngestActor = actors.values().stream()
                .min(Comparator.comparing(Actor::getAge));

        if (youngestActor.isPresent()) {
            Actor actor = youngestActor.get();
            System.out.println("Youngest Actor: " + actor.getName() + " (Age: " + actor.getAge() + ")");

            return movies.values().stream()
                    .filter(movie -> movie.getActorIds().contains(actor.getActorId()))
                    .collect(Collectors.toList());
        } else {
            System.out.println("No valid actor data available.");
            return new ArrayList<>();
        }
    }
}
