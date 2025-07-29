//package study.my_board.service;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//import study.my_board.domain.Comment;
//import study.my_board.domain.Member;
//import study.my_board.domain.Post;
//import study.my_board.dto.CommentDto;
//import study.my_board.repository.CommentRepository;
//import study.my_board.repository.MemberRepository;
//import study.my_board.repository.PostRepository;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
//public class CommentServiceTest {
//
//    @Autowired
//    private CommentService commentService;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private PostRepository postRepository;
//
//    @Autowired
//    private CommentRepository commentRepository;
//
//    @Test
//    public void saveComment_Success() throws Exception{
//        // Given
//        Member member = memberRepository.save(new Member(1L, "username", "password", true));
//        Post post = postRepository.save(new Post(member, "title", "content"));
//        CommentDto.Request request = new CommentDto.Request("This is a comment");
//
//        // When
//        Long commentId = commentService.save(member.getId(), post.getId(), request);
//
//        // Then
//        Comment savedComment = commentRepository.findById(commentId).orElseThrow();
//        assertNotNull(savedComment);
//        assertEquals(request.getComment(), savedComment.getComment());
//        assertEquals(member, savedComment.getMember());
//        assertEquals(post, savedComment.getPost());
//    }
//
//
//
//}