package com.backend.model.request;

import com.backend.exception.CustomException;
import com.backend.exception.ResultCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class FindBlogRequestDto {

    private String keyword;

    @NotBlank(message = "정렬 조건에 누락되었습니다.")
    private String sort; // accuracy, recency

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
        if (this.sort == null || !(this.sort.equals("accuracy") || this.sort.equals("recency"))) {
            return false;
        }
        return true;
    }

    private boolean checkNumber(String val) {
        try {
            Integer num = Integer.parseInt(val);
            if (num < 1 ||  num > 50) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
