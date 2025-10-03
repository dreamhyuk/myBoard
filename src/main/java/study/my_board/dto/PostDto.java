package study.my_board.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import study.my_board.domain.Member;
import study.my_board.domain.Post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


public class PostDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        private Long id;
        @NotNull
        @Size(min = 2, max = 60, message = "제목은 2자 이상 60자 이하입니다.")
        private String title;

        @NotNull
        @Size(min = 2, message = "내용을 2자 이상 입력하세요.")
        private String content;
        private Member member;

        private final String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));;
        private final String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));;
        private int views;

        /* Dto -> Entity */
        public Post toEntity() {
            Post post = Post.builder()
                    .title(title)
                    .content(content)
                    .member(member)
                    .views(0)
                    .createdDate(createdDate)
                    .modifiedDate(modifiedDate)
                    .build();
            return post;
        }
    }

    @Data
    public static class Response {
        private Long id;
        private String title;
        private String content;
        private String author;
        private Long memberId;
        private List<CommentDto.Response> comments;
        private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));;
        private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));;
        private int views;

        /* Entity -> Dto */
        public Response(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.author = post.getMember().getUsername();
            this.memberId = post.getMember().getId();
            this.createdDate = post.getCreatedDate();
            this.modifiedDate = post.getModifiedDate();
            this.views = post.getViews();
        }
    }

}
