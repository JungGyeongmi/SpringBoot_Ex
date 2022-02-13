package org.zerock.mreview.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
  /*
  * select m.mno, max(mi.inum), avg(coalesce(r.grade,0)),
    count(distinct r.reviewnum)
    from movie m left outer join movie_image mi
    on m.mno=mi.movie_mno left outer join review r
    on m.mno = r.movie_mno group by m.mno order by m.mno desc;
  * */

//  n+1문제를 해결하려면 mi에 적용되었던 max(mi)를 걷어내고 mi만 쓰는 것이다.
//  @Query("select m, mi, avg(coalesce(r.grade,0)), count(distinct r) " +
//          "from Movie m left outer join MovieImage mi on mi.movie = m " +
//          "left outer join Review r on r.movie = m group by m ")

    // 조금의 성능을 포기하고 서브쿼리를 사용하게 되면 max를 적용할 수 있다.
//  @Query("select m, i, count(r) from Movie m left join MovieImage i " +
//          "on m=i.movie and i.inum=(select max(i2.inum) from MovieImage i2 " +
//          "where i2.movie = m ) " +
//          "left outer join Review r on r.movie = m group by m")
//  Page<Object[]> getListPage(Pageable pageable);
    @Query("select m, mi, avg(coalesce(r.grade,0)), count(r) " +
            " from Movie m left outer join MovieImage mi on mi.movie=m " +
            " left outer join Review r on r.movie = m group by m ")
    Page<Object[]> getListPage(Pageable pageable);

    /*@Query("select m, mi from Movie m left outer join MovieImage mi " +
            "on mi.movie = m where m.mno =:mno ")*/
    @Query("select m, mi,avg(coalesce(r.grade,0)),count(r) " +
            "from Movie m left outer join MovieImage mi on mi.movie = m " +
            "left outer join Review r on r.movie = m " +
            "where m.mno =:mno group by mi")
    List<Object[]> getMovieWithAll(Long mno);
}
