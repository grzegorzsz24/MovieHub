package com.example.movies_api.repository;

import com.example.movies_api.model.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findAllByPromotedIsTrue();

    List<Movie> findAllByGenre_NameIgnoreCase(String genre);

    @Query("SELECT m FROM Movie m WHERE (:genre IS NULL OR m.genre.name = :genre) AND (:releaseYear IS NULL OR m.releaseYear = :releaseYear)")
    List<Movie> findAllByGenre_NameAndReleaseYear(@Param("genre") String genre, @Param("releaseYear") Integer releaseYear, Pageable page);

    @Query("select m from Movie m join m.ratings r group by m order by avg(r.rating) desc")
    List<Movie> findTopByRating(Pageable page);

    Optional<Movie> findByTitle(String title);
}
