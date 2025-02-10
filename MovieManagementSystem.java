package org.example;

import java.util.List;
import java.util.Scanner;

public class MovieManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DataManager dataManager = new DataManager();

        // Load data from CSV files
        dataManager.loadMovies("src/main/resources/movies_large.csv");
        dataManager.loadActors("src/main/resources/actors_large.csv");
        dataManager.loadDirectors("src/main/resources/directors_large.csv");

        while (true) {
            System.out.println("\n===== Movie Data Management System =====");
            System.out.println("1. Get Movie Information");
            System.out.println("2. Get Top 10 Rated Movies");
            System.out.println("3. Get Movies by Genre");
            System.out.println("4. Get Movies by Director");
            System.out.println("5. Get Movies by Release Year");
            System.out.println("6. Get Movies by Release Year Range");
            System.out.println("7. Add a New Movie");
            System.out.println("8. Update Movie Rating");
            System.out.println("9. Delete a Movie");
            System.out.println("10. Sort and return 15 movies by Release Year");
            System.out.println("11. Get Directors with Most Movies");
            System.out.println("12. Get Actor Who Has Worked in Multiple Movies");
            System.out.println("13. Get Movies of the Youngest Actor as of 10-02-2025");
            System.out.println("14. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        System.out.print("Enter Movie ID: ");
                        String movieId = scanner.nextLine().trim();
                        Movie movie = dataManager.getMovieById(movieId);
                        System.out.println((movie != null) ? movie : "Movie not found.");
                        break;

                    case 2:
                        List<Movie> topMovies = dataManager.getTopRatedMovies();
                        topMovies.forEach(System.out::println);
                        break;

                    case 3:
                        System.out.print("Enter Genre: ");
                        String genre = scanner.nextLine().trim();
                        List<Movie> moviesByGenre = dataManager.getMoviesByGenre(genre);
                        if (!moviesByGenre.isEmpty()) {
                            moviesByGenre.forEach(System.out::println);
                        } else {
                            System.out.println("No movies found in this genre.");
                        }
                        break;

                    case 4:
                        System.out.print("Enter Director Name: ");
                        String directorName = scanner.nextLine().trim();
                        List<Movie> moviesByDirector = dataManager.getMoviesByDirector(directorName);
                        if (!moviesByDirector.isEmpty()) {
                            moviesByDirector.forEach(System.out::println);
                        } else {
                            System.out.println("No movies found for director: " + directorName);
                        }
                        break;

                    case 5:
                        System.out.print("Enter Release Year: ");
                        int year = Integer.parseInt(scanner.nextLine().trim());
                        List<Movie> moviesByYear = dataManager.getMoviesByReleaseYear(year);
                        if (!moviesByYear.isEmpty()) {
                            moviesByYear.forEach(System.out::println);
                        } else {
                            System.out.println("No movies found for year: " + year);
                        }
                        break;

                    case 6:
                        System.out.print("Enter Year Range (start-end): ");
                        String[] range = scanner.nextLine().trim().split("-");
                        if (range.length == 2) {
                            int startYear = Integer.parseInt(range[0].trim());
                            int endYear = Integer.parseInt(range[1].trim());
                            List<Movie> moviesByYearRange = dataManager.getMoviesByYearRange(startYear, endYear);
                            if (!moviesByYearRange.isEmpty()) {
                                moviesByYearRange.forEach(System.out::println);
                            } else {
                                System.out.println("No movies found in the given range.");
                            }
                        } else {
                            System.out.println("Invalid format! Use: YYYY-YYYY (e.g., 2010-2020)");
                        }
                        break;

                    case 7:
                        System.out.print("Enter Movie ID: ");
                        String newMovieId = scanner.nextLine().trim();
                        System.out.print("Enter Title: ");
                        String title = scanner.nextLine().trim();
                        System.out.print("Enter Release Year: ");
                        int releaseYear = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("Enter Genre: ");
                        String newGenre = scanner.nextLine().trim();
                        System.out.print("Enter Rating: ");
                        double rating = Double.parseDouble(scanner.nextLine().trim());
                        System.out.print("Enter Duration (minutes): ");
                        int duration = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("Enter Director Name: ");
                        String inputDirectorName = scanner.nextLine().trim();
                        System.out.print("Enter Actor IDs (comma-separated): ");
                        List<String> actorIds = List.of(scanner.nextLine().trim().split(","));
                        dataManager.addMovie(newMovieId, title, releaseYear, newGenre, rating, duration, inputDirectorName , actorIds);
                        break;

                    case 8:
                        System.out.print("Enter Movie ID to Update: ");
                        String updateMovieId = scanner.nextLine().trim();
                        System.out.print("Enter New Rating: ");
                        double newRating = Double.parseDouble(scanner.nextLine().trim());
                        dataManager.updateMovieRating(updateMovieId, newRating);
                        break;

                    case 9:
                        System.out.print("Enter Movie ID to Delete: ");
                        String deleteMovieId = scanner.nextLine().trim();
                        dataManager.deleteMovie(deleteMovieId);
                        break;

                    case 10:
                        List<Movie> sortedMovies = dataManager.getSortedMoviesByYear();
                        sortedMovies.forEach(System.out::println);
                        break;

                    case 11:
                        List<String> topDirectors = dataManager.getTopDirectors();
                        topDirectors.forEach(System.out::println);
                        break;

                    case 12:
                        List<String> topActors = dataManager.getMostFrequentActors();
                        topActors.forEach(System.out::println);
                        break;

                    case 13:
                        List<Movie> moviesOfYoungestActor = dataManager.getMoviesOfYoungestActor();
                        moviesOfYoungestActor.forEach(System.out::println);
                        break;

                    case 14:
                        System.out.println("Exiting...");
                        scanner.close();
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }
}
