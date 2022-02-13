package org.zerock.mreview.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Data;

// 페이지 요청 결과를 담기 위한 클래스
@Data
public class PageResultDTO<DTO, EN> {
    // Page 결과 목록을 담기 위한 변수 
    // 결과가 가변적이기 때문에 List를 선택
    private List<DTO> dtoList;

    // Page navigation을 위한 변수들
    private int page; // 현재 페이지
    private int size; // 한번에 보여줄 사이즈
    private int totalPage; // 전체
    private int start, end;
    private boolean prev, next;
    private List<Integer> pageList; // Integer형태로 List에 담김

    // Page는 lsit와 동일하게 생각하면 된다.
    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages(); // 전체 페이지와 갯수만 알면 계산가
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1; // 0 부터 시작함
        this.size = pageable.getPageSize();

        int tempEnd = (int) (Math.ceil(page / (double) size)) * size;  // 임시로 끝 페이지를 계산함

        start = tempEnd - (size - 1); // 끝 페이지에서 전체사이즈 -1 을 하면 첫 페이지를 계산 가능

        end = totalPage > tempEnd ? tempEnd : totalPage; // end를 표기할 것인가에대한 삼항연산자

        prev = start > 1; // page가 1에 있는 경우에는 prev를 띄울 필요가 없으니까
        next = totalPage > tempEnd; // 전체 페이지보다 끝 페이지의 수가 작아야 next를 표출

        pageList = IntStream
                .rangeClosed(start, end)
                .boxed()
                .collect(Collectors.toList());  // 배열에 값을 한번에 넣기 위해서
        //System.out.println("PageResultDTO >> "+totalPage+","+tempEnd);

    }
}
