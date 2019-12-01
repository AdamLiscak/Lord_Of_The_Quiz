package com.example.findyourprivategrandpa;

import com.example.findyourprivategrandpa.controllerinterfaces.HostResolver;

public class Urls {
    public static final String HOST_URL = new HostResolver().findHost();
    public static final String QUESTION_IMAGE_URL = HOST_URL +"lord_of_the_quiz_backend/chadDaniels.jpg";
    public static final String THUMBNAIL_URL = HOST_URL +"lord_of_the_quiz_backend/chadDaniels.jpg";
    public static final String MOCKUP_URL = HOST_URL +"lord_of_the_quiz_backend/testing/mockup/mockup.php";
}
