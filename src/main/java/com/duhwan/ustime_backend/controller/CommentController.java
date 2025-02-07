package com.duhwan.ustime_backend.controller;

import com.duhwan.ustime_backend.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comment")
@RequiredArgsConstructor
@Tag(name="사진첩 댓글 API", description = "사진첩 댓글 관련 기능을 제공하는 API")
public class CommentController {

    private final CommentService commentService;


}
