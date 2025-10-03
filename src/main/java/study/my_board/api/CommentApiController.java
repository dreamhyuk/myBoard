package study.my_board.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.my_board.authentication.CustomUserDetails;
import study.my_board.domain.Comment;
import study.my_board.dto.CommentDto;
import study.my_board.repository.CommentRepository;
import study.my_board.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentApiController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final SimpMessagingTemplate messagingTemplate;

    /* 댓글 작성 */
    @PostMapping("/board/view/{postId}/comments")
    public ResponseEntity<CommentDto.Response> write(@PathVariable Long postId,
                                                     @RequestBody @Valid CommentDto.Request request,
                                                     @AuthenticationPrincipal CustomUserDetails currentUser) {

        Long savedCommentId = commentService.save(currentUser.getId(), postId, request);
        Comment savedComment = commentService.findOne(savedCommentId);
        CommentDto.Response response = new CommentDto.Response(savedComment);

        // WebSocket 구독자에게 전송
        messagingTemplate.convertAndSend("/topic/comments", savedCommentId);

        return ResponseEntity.ok(response);
    }

    /* 페이징 조회 */
    @GetMapping("/board/view/{postId}/pagedComments")
    public Page<CommentDto.Response> getPagedComment(@PathVariable Long postId,
                                                     @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return commentService.findPagedComment(postId, pageable);
    }

    /* 전체 조회 */
    @GetMapping("/board/view/{postId}/comments")
    public List<CommentDto.Response> getAllComment(@PathVariable Long postId) {
        return commentService.findAll(postId);
    }

    /* 댓글 수정 */
    @PutMapping("/board/view/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto.Response> updateComment(@PathVariable Long postId, @PathVariable Long commentId,
                                                             @RequestBody CommentDto.Request request) {
        commentService.update(postId, commentId, request);
        CommentDto.Response response = new CommentDto.Response(request.toEntity());

        return ResponseEntity.ok(response);
    }

    /* 삭제 */
    @DeleteMapping("/board/view/{postId}/comments/{commentId}")
    public ResponseEntity<Long> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.delete(postId, commentId);

        return ResponseEntity.ok(commentId);
    }

    @GetMapping("/board/view/{postId}/commentCount")
    public ResponseEntity<Long> getCommentCount(@PathVariable Long postId) {
        long commentCount = commentRepository.countByPostId(postId);
        return ResponseEntity.ok(commentCount);
    }

}
