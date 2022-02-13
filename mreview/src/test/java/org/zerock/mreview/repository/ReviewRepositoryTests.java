package org.zerock.mreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewRepositoryTests {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    ReviewRepository reviewRepository;

    @Test
    public void insertReview() {


        IntStream.rangeClosed(1, 200).forEach(i -> {
            // 영화 번호
            Long mno = (long) (Math.random() * 100) + 1;

            // 리뷰어 번호
            Long mid = (long) (Math.random() * 100) + 1;

            Movie movie = Movie.builder()
                    .mno(mno)
                    .build(); // builder는 인스턴스 생성 개념이니까 해당 번호의 인스턴스를 생성하는 것인가? save는 따로 안해주니까 불러오는거고?

            Member member = Member.builder().mid(mid).build();

            Review review = Review.builder()
                    .grade((int) (Math.random() * 5) + 1)
                    .member(member)
                    .movie(movie)
                    .text(i + "film review")
                    .build();

            reviewRepository.save(review);
        });
    }

    @Test
    public void testGetMovieReviews() {
        Movie movie = Movie.builder()
                .mno(40L).build();

        List<Review> result = reviewRepository.findByMovie(movie);

        result.forEach(moviereview -> {
            System.out.println(moviereview.getReviewnum());
            System.out.println(moviereview.getGrade());
            System.out.println(moviereview.getText());
            System.out.println(moviereview.getMember().getEmail());
            System.out.println("==========================");
        });
    }
}