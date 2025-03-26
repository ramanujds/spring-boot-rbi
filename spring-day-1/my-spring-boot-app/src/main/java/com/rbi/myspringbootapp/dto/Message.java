package com.rbi.myspringbootapp.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Message(String content, String sender, LocalDateTime time) {
}
