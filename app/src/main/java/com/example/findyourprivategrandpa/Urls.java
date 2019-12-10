package com.example.findyourprivategrandpa;

import com.example.findyourprivategrandpa.controllerinterfaces.HostResolver;

public class Urls
{
    public static final String HOST_URL = new HostResolver().findHost();
    public static final String QUESTION_IMAGE_URL = HOST_URL +"lord_of_the_quiz_backend/chadDaniels.jpg";
    public static final String THUMBNAIL_URL = HOST_URL +"lord_of_the_quiz_backend/chadDaniels.jpg";
    public static final String MOCKUP_URL = HOST_URL +"lord_of_the_quiz_backend/testing/mockup/nockup.php";
    public static final String QUIZ_URL = HOST_URL + "lord_of_the_quiz_backend/testing/mockup/mockup.php";
    public static final String QUIZZES_URL = HOST_URL + "lord_of_the_quiz_backend/testing/mockup/quizzes.php";
    public static final String EXPORT_URL = HOST_URL + "lord_of_the_quiz_backend/testing/mockup/mockup.php";
    public static final String MY_SCORES_URL=HOST_URL+"lord_of_the_quiz_backend/testing/mockup/myhighscores.php";
    public static final String MY_SCORES_BY_QUIZ_URL=HOST_URL+"lord_of_the_quiz_backend/testing/mockup/myscores-by-quiz.php";
    public static final String HIGH_SCORES_BY_USER_URL=HOST_URL+"lord_of_the_quiz_backend/testing/mockup/highscores-by-user.php";
    public static final String HIGH_SCORES_BY_QUIZ_URL=HOST_URL+"lord_of_the_quiz_backend/testing/mockup/highscores-by-quiz.php";
    public static final String MY_QUIZES_URL=HOST_URL+"lord_of_the_quiz_backend/testing/mockup/myquizes.php";
    public static final String USER_QUIZES_URL=HOST_URL+"lord_of_the_quiz_backend/testing/mockup/userquizes.php";
    public static final String QUIZ_BUILDER_EXPORT_URL=HOST_URL+"lord_of_the_quiz_backend/testing/mockup/userquizes.php";
    public static final String LOGIN_URL=HOST_URL+"lord_of_the_quiz_backend/testing/mockup/login.php";
    public static final String REGISTER_URL=HOST_URL+"lord_of_the_quiz_backend/testing/mockup/register.php";
    public static final String LOGOUT_URL=HOST_URL+"lord_of_the_quiz_backend/testing/mockup/logout.php";
    public static final String DELETE_ACCOUNT_URL=HOST_URL+"lord_of_the_quiz_backend/testing/mockup/delete-account.php";
    public static final String QUIZ_THUMBNAIL_UPLOAD_URL=HOST_URL+"lord_of_the_quiz_backend/testing/mockup/quiz-upload-thumbnail.php";
    public static final String QUESTION_PICTURE_UPLOAD_URL=HOST_URL+"lord_of_the_quiz_backend/testing/mockup/question-upload-picture.php";

}
