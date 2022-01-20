package org.zerock.ex2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.ex2.entity.Memo;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testInsertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i->{
            Memo memo = Memo.builder().memoText("Sample..."+i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testSelect() {
        // 데이터 베이스에 존재하는 mno
        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("------------------");

        if(result.isPresent()) {
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


}