package com.common;


public class AppConstants {

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_TIME_DTO_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String DATE_TIME_FORM_PATTERN = "yyyy-MM-ddTHH:mm";
    public static final String VALID_POST_CODE_PATTERN = ("[0-9]{2}-[0-9]{3}");
    public static final String VALID_EMAIL_ADDRESS = ("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$");
    public static final String VALID_BIG_FIRST_LETTER_PATTERN = ("^[A-Z]+[a-z+\\p{Ll}]+$");
    public static final String VALID_PASSWORD = (".{8,16}");

    public static final String T_USER = ("users");
    public static final String T_STUDENT = ("students");
    public static final String T_TUTOR = ("tutors");
    public static final String T_MESSAGE = ("messages");
    public static final String T_FIELD = ("fields");
    public static final String T_GRADE = ("grades");



    private AppConstants() {

    }


}
