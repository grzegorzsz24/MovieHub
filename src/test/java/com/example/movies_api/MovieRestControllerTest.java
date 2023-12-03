package com.example.movies_api;

import com.example.movies_api.dto.MovieSaveDto;
import com.example.movies_api.model.Movie;
import com.example.movies_api.repository.GenreRepository;
import com.example.movies_api.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = MoviesApiApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles(profiles = "dev")
public class MovieRestControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private GenreRepository genreRepository;

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void whenValidInput_thenCreateMovie() throws Exception {
        MovieSaveDto movie = createMovie();
        MockMultipartFile file = new MockMultipartFile(
                "poster",
                "poster.jpg",
                MediaType.IMAGE_JPEG.toString(),
                "asdasdas".getBytes()
        );
        mvc.perform(MockMvcRequestBuilders.multipart("/admin/add-movie")
                        .file("poster", movie.getPoster().getBytes())
                        .param("title", movie.getTitle())
                        .param("originalTitle", movie.getOriginalTitle())
                        .param("shortDescription", movie.getShortDescription())
                        .param("promoted", String.valueOf(movie.isPromoted()))
                        .param("releaseYear", String.valueOf(movie.getReleaseYear()))
                        .param("description", movie.getDescription())
                        .param("youtubeTrailerId", movie.getYoutubeTrailerId())
                        .param("genre", movie.getGenre())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());
        Movie foundMovie = movieRepository.findByTitle(movie.getTitle()).get();
        assertThat(foundMovie).isNotNull();
        assertThat(foundMovie.getTitle()).isEqualTo(movie.getTitle());
        assertThat(foundMovie.getReleaseYear()).isEqualTo(movie.getReleaseYear());
    }

    @Test
    @WithMockUser
    public void testGetPromotedMovies() throws Exception {
        mvc.perform(get("/movies/promoted")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].promoted", everyItem(is(true))));
    }

    @Test
    @WithMockUser
    public void testFindMoviesWithFilters() throws Exception {
        Movie movie = new Movie();
        BeanUtils.copyProperties(createMovie(), movie);
        movie.setGenre(genreRepository.findByNameIgnoreCase("Drama").get());
        movieRepository.save(movie);
        ;
        mvc.perform(MockMvcRequestBuilders.get("/movies/filters")
                        .param("genre", "Drama")
                        .param("releaseYear", "1994")
                        .param("page", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].genre").value("Drama"))
                .andExpect(jsonPath("$[0].releaseYear").value(1994));
    }

    private MovieSaveDto createMovie() {
        MovieSaveDto movie = MovieSaveDto.builder()
                .title("The Shawshank Redemption")
                .originalTitle("The Shawshank Redemption")
                .shortDescription("In 1947, Andy Dufresne (Tim Robbins), a banker in Maine")
                .promoted(true)
                .releaseYear(1994)
                .description("In 1947, Andy Dufresne (Tim Robbins), a banker in Maine...")
                .youtubeTrailerId("NmzuHjWmXOc")
                .genre("Drama")
                .poster(new MockMultipartFile(
                        "poster",
                        "poster.jpg",
                        MediaType.IMAGE_JPEG.toString(),
                        "asdasdas".getBytes()))
                .build();
        return movie;
    }

}

