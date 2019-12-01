package com.example.findyourprivategrandpa;

import com.example.findyourprivategrandpa.controllerinterfaces.HostResolver;

public class Urls
{
    public static final String HOST_URL = new HostResolver().findHost();
    public static final String QUESTION_IMAGE_URL = HOST_URL +"lord_of_the_quiz_backend/chadDaniels.jpg";
    public static final String THUMBNAIL_URL = HOST_URL +"lord_of_the_quiz_backend/chadDaniels.jpg";
    public static final String MOCKUP_URL = HOST_URL +"lord_of_the_quiz_backend/testing/mockup/mockup.php";
    public static final String QUIZ_URL = HOST_URL + "lord_of_the_quiz_backend/testing/mockup/mockup.php";
    public static final String EXPORT_URL = HOST_URL + "lord_of_the_quiz_backend/testing/mockup/mockup.php";
    public static final String MY_SCORES_URL=HOST_URL+"lord_of_the_quiz_backend/testing/mockup/myhighscores.php";
    public static final String HIGH_SCORES_BY_USER_URL=HOST_URL+"lord_of_the_quiz_backend/testing/mockup/highscores-by-user.php";
    public static final String HIGH_SCORES_BY_QUIZ_URL=HOST_URL+"lord_of_the_quiz_backend/testing/mockup/highscores-by-quiz.php";
}
