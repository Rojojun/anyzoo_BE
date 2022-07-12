package com.finalproject.breeding.etc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.awt.print.Pageable;
import java.time.LocalDateTime;

public interface CommentMapping {


    Long getId();

    Long getUserId();

    String getComment();

    String getUserNickname();

    String getUserImg();

    LocalDateTime getCreatedAt();










}
