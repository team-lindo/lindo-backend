package team.lindo.backend.presentation.common.response.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team.lindo.backend.presentation.common.response.ResponseCode;

import java.util.List;

public class SuccessResponse {  // 인터페이스로 만들어야 하나?
    // 단일 데이터에 대한 성공 응답 정의
    @Getter
    public static class Single<T> {
        private final T data;
        private final ResponseCode responseCode = ResponseCode.SUCCESS;

        public Single(T data) {
            this.data = data;
        }
    }

    // 복수 개의 데이터에 대한 성공 응답 정의
    @Getter
    public static class Multiple<T> {
        private final List<T> data;
        private final ResponseCode responseCode = ResponseCode.SUCCESS;

        public Multiple(List<T> data) {
            this.data = data;
        }
    }

    // 페이지네이션 된 성공 응답 정의
    @Getter
    public static class Paginated<T> {
        private final Integer totalPages;
        private final Long totalElements;
        private final Integer page;
        private final Integer elements;
        private final List<T> data;
        private final ResponseCode responseCode = ResponseCode.SUCCESS;

        public Paginated(Integer totalPages, Long totalElements, Integer page
                , Integer elements, List<T> data) {
            this.totalPages = totalPages;
            this.totalElements = totalElements;
            this.page = page;
            this.elements = elements;
            this.data = data;
        }
    }
}
