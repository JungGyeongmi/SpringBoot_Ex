package org.zerock.ex2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex2.entity.Memo;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testInsertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..." + i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testSelect() {
        // 데이터 베이스에 존재하는 mno
        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("------------------");

        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    @Transactional
    @Test
    public void testSelect2() {
        // 데이터 베이스에 존재하는 mno
        Long mno = 100L;

        Memo memo = memoRepository.getOne(mno);

        System.out.println("------------------");

        System.out.println(memo);
    }

    // test 수정
    @Test
    public void testUpdate() {
        // 100번을 찾아서
        Long mno = 100L;
        Optional<Memo> result = memoRepository.findById(mno);
        if (result.isPresent()) {
            Memo memo = result.get();
            // 수정 - private이기 때문에 getter와 setter(builder)가 있어야 함.
            memo.changeMemoText("Update Text");
            memoRepository.save(memo);
        }
    }

    // test 삭제
    @Test
    public void testDelete() {
        // 먼저 100번을 찾아서
        Long mno = 100L;
        Optional<Memo> result = memoRepository.findById(mno);
        // 삭제
        if (result.isPresent()) {
            memoRepository.deleteById(mno);
        }
    }

    @Test
    public void testPageDefault() {
        // 1페이지 10개
        Pageable pageable = PageRequest.of(0, 10);
        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println(result);
        System.out.println("-------------------------------");
        System.out.println(result.getTotalPages()); // 전체 페이지
        System.out.println(result.getTotalElements()); // 전체 갯수
        System.out.println(result.getNumber()); // 현재 페이지 번호
        System.out.println(result.getSize()); // 페이지 당 데이터 갯수
        System.out.println(result.hasNext()); // 다음 페이지
        System.out.println(result.isFirst());  // 시작 페이지(0) 여부
        System.out.println("-------------------------------");

        for (Memo memo : result.getContent()) System.out.println(memo);
    }

    @Test
    public void testSort() {
        Sort sort1 = Sort.by("mno").descending();

        Pageable pageable = PageRequest.of(0, 10, sort1);
        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println(memo);
        });

    }

    @Test
    public void testSort2() {
        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2); // and 를 이용한 연결

        Pageable pageable = PageRequest.of(0, 10, sortAll); // 결합된 정렬 조건 사용

        Page<Memo> result = memoRepository.findAll(pageable);
        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Test
    public void testQueryMethods() {
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);

        for (Memo memo : list) {
            System.out.println(memo);
        }
    }

    @Test
    public void testQueryMethodWithPageable() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);
        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Commit
    @Transactional
    @Test
    public void testDeleteQueryMethods() {
        memoRepository.deleteMemoByMnoLessThan(10L);
    }

}