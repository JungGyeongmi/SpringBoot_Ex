package org.zerock.mreview.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
@AllArgsConstructor // 기본생성자를 쓸 수 있다
@Data
public class PageRequestDTO { // Page를 요청할 때 단순히 페이지 번호보다 객체로 처리하기 위함
    private int page;
    private int size;
    private String type;
    private String keyword;

    // 기본생성자
    public PageRequestDTO() {
        this.page = 1; // 첫 페이지 부터 나오기 때문에 1에서 10이다
        this.size = 10;
    }

    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page - 1, size, sort); // pageable에서는 0이 1page
    }
}