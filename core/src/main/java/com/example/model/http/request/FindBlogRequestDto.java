package com.example.model.http.request;

import com.example.enums.PageSize;
import com.example.enums.SortType;
import com.example.exception.CustomException;
import com.example.exception.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class FindBlogRequestDto {

    @NotBlank(message = "키워드 조건에 누락되었습니다.") // 카카오, 네이버 API 공백 시 에러로 인해 필수 값
    private String keyword;

    @NotBlank(message = "정렬 조건에 누락되었습니다.")
    private String sort;

    @NotBlank(message = "페이지 번호 조건에 누락되었습니다.")
    private String page;

    @NotBlank(message = "문서 개수 조건에 누락되었습니다.")
    private String size;

    public FindBlogRequestDto checkForgery() throws CustomException {
        if (!checkSort() || !checkNumber(this.page) || !checkNumber(this.size)) {
            throw new CustomException(ResultCode.PARAM_MANIPULATION);
        }
        return this;
    }

    private boolean checkSort() {
        return SortType.isSort(this.sort);
    }

    // 카카오기준 max값 50개
    private boolean checkNumber(String val) {
        try {
            Integer num = Integer.parseInt(val);
            if (num < PageSize.MIN_PAGE.getCode() ||  num > PageSize.MAX_PAGE.getCode()) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) { // 변조로 인한 포맷 에러 발생
            return false;
        }
    }
}
