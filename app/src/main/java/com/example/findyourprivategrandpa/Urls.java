package com.example.findyourprivategrandpa;

import com.example.findyourprivategrandpa.controllerinterfaces.HostResolver;

import java.util.HashMap;

public class Urls
{
    //host

    public static final String HOST_URL = new HostResolver().findHost();

    //dirs

    private static final String ROOT_DIR=HOST_URL+"lord_of_the_quiz_backend/";
    private static final String UPLOAD_DIR = ROOT_DIR+"upload/";
    private static final String DOWNLOAD_DIR= ROOT_DIR+"download/";

    //mockup

    public static final String MOCKUP_URL = HOST_URL +"lord_of_the_quiz_backend/testing/mockup/mockup.php";

    //download

    public static final String QUESTION_IMAGE_URL = DOWNLOAD_DIR+"chadDaniels.jpg";
    public static final String THUMBNAIL_URL = ROOT_DIR+"images/";
    public static final String QUIZ_URL = DOWNLOAD_DIR+"quiz.php";
    public static final String QUIZZES_URL = DOWNLOAD_DIR+"quizzes.php";
    public static final String MY_SCORES_URL=DOWNLOAD_DIR+"myhighscores.php";
    public static final String MY_SCORES_BY_QUIZ_URL=DOWNLOAD_DIR+"myscores-by-quiz.php";
    public static final String HIGH_SCORES_BY_USER_URL=DOWNLOAD_DIR+"highscores-by-user.php";
    public static final String HIGH_SCORES_BY_QUIZ_URL=DOWNLOAD_DIR+"highscores-by-quiz.php";
    public static final String MY_QUIZES_URL=DOWNLOAD_DIR+"myquizes.php";
    public static final String USER_QUIZES_URL=DOWNLOAD_DIR+"userquizes.php";

    //upload

    public static final String EXPORT_URL = UPLOAD_DIR+"export.php";
    public static final String QUIZ_BUILDER_EXPORT_URL= UPLOAD_DIR+"userquizes.php";
    public static final String LOGIN_URL = UPLOAD_DIR+"login.php";
    public static final String REGISTER_URL=UPLOAD_DIR+"register.php";
    public static final String LOGOUT_URL=UPLOAD_DIR+"logout.php";
    public static final String DELETE_ACCOUNT_URL=UPLOAD_DIR+"delete-account.php";
    public static final String QUIZ_THUMBNAIL_UPLOAD_URL=UPLOAD_DIR+"quiz-upload-thumbnail.php";
    public static final String QUESTION_PICTURE_UPLOAD_URL=UPLOAD_DIR+"uploadimage.php";

}
