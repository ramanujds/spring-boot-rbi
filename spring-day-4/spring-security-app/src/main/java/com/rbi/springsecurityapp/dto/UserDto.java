package com.rbi.springsecurityapp.dto;

import java.util.List;

public record UserDto(String username, String password, List<String> roles) {
}
