package org.zerock.mreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

// import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieRepositoryTests {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieImageRepository movieImageRepository;

    @Commit
    @Transactional
    @Test
    public void insertMovies(){

        IntStream.rangeClosed(1, 100).forEach(i->{

            Movie movie = Movie.builder().title("movie Title..."+i).build();

            movieRepository.save(movie);

            System.out.println("---------------------------");

            int count = (int)(Math.random()*5)+1; // 1 에서 5까지

            for(int j =0; j<count; j++) { // 랜덤으로 돌린 수 1~5까지의 이미지가 하나의 Moive에 저장됨
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString()) // 고유한 UUID를 랜덤으로 생성하고 그 값을 String으로 변호나 후 uuid(멤버변수)에 넣어줌
                        .movie(movie)
                        .imgName("test"+j+".jpg")
                        .build();

                movieImageRepository.save(movieImage);
            }
            System.out.println("---------------------------");
        });
    }

    @Test
    public void testListPage() {

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "mno"));

        Page<Object[]> result = movieRepository.getListPage(pageRequest);

        for(Object[] objects : result.getContent()) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    public void testGetMovieWithAll() {
        List<Object[]> result = movieRepository.getMovieWithAll(94L);

        System.out.println(result);

        for(Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }
}