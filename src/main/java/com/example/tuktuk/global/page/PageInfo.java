package com.example.tuktuk.global.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
@Builder
public class PageInfo {
    private int pageNumber;
    private int pageSize;
    private int totalElements;
    private int totalPage;

    public static PageInfo from(Page page){
        return PageInfo.builder()
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements((int) page.getTotalElements())
                .totalPage(page.getTotalPages())
                .build();
    }
}
