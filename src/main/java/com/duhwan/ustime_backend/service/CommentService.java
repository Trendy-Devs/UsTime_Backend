package com.duhwan.ustime_backend.service;

import com.duhwan.ustime_backend.dao.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;


}
