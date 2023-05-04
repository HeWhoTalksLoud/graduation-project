package ru.skypro.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.exception.ForbiddenException;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentsRepository;
import ru.skypro.homework.repository.UsersRepository;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final AdsRepository adsRepository;
    private final UsersRepository usersRepository;

    public ResponseWrapperCommentDTO getAdComments(Long adId) {
        List<Comment> comments = commentsRepository.findAllByAdIdOrderByCreationDateTimeAsc(adId);

        List<CommentDTO> commentDTOList = comments.stream()
                .map(CommentDTO::fromComment)
                .collect(Collectors.toList());
        ResponseWrapperCommentDTO responseDTO = new ResponseWrapperCommentDTO();
        responseDTO.setResults(commentDTOList);
        responseDTO.setCount(commentDTOList.size());
        return responseDTO;
    }

    public CommentDTO addComment(Long adId, CommentDTO commentDTO, Long userId) {
        Comment comment = commentDTO.toComment();
        Ad ad = adsRepository.findById(adId).orElseThrow(() -> new NotFoundException("Объявление "+ adId +
                " не найдено"));
        comment.setId(null);
        comment.setAd(ad);
        User user = usersRepository.findById(userId)
                        .orElseThrow(() -> new NotFoundException("Пользователь с ид. " + userId + " не найден"));
        comment.setAuthor(user);
        comment.setCreationDateTime(Instant.now());
        Comment savedComment = commentsRepository.save(comment);
        return CommentDTO.fromComment(savedComment);
    }

    public void deleteComment(Long adId, Long commentId) {
        commentsRepository.deleteById(commentId);
    }

    public CommentDTO updateComment(Long adId, Long commentId, CommentDTO commentDTO, Long userId) {
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ид. " + userId + " не найден"));

        Comment comment = commentsRepository.findById(commentId).orElseThrow(() ->
                new NotFoundException("Комментарий "+ commentId +
                " не найден"));

        boolean isAdmin = user.getRole() == Role.ADMIN;
        if (!Objects.equals(comment.getAuthor().getId(), userId) && !isAdmin) {
            throw new ForbiddenException("У пользователя с ид. " + userId + " нет прав редактировать " +
                    "данный комментарий");
        }
        String newText = commentDTO.getText();
        if (isAdmin) {
            newText = newText + "\n\nОтредактировано пользователем " + user.getFirstName() +
                    " в " + Instant.now();
        }
        comment.setText(newText);
        Comment savedComment = commentsRepository.save(comment);
        return CommentDTO.fromComment(savedComment);
    }

    public String getUsername(Long adId) {
        return commentsRepository.findById(adId).orElseThrow(() -> new NotFoundException(""))
                .getAuthor().getEmail();
    }

}
