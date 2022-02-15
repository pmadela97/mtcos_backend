package com.tutor.application;

import com.user.application.UserDto;

public class TutorDto {

    private final long id;
    private final String title;
    private final UserDto userDto;

    public TutorDto(long id, String title, UserDto userDto) {

        this.id = id;
        this.title = title;
        this.userDto = userDto;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public UserDto getUserDto() {
        return userDto;
    }
}
