package com.db.pmb_unas.Common;

import android.os.CountDownTimer;

import com.db.pmb_unas.Model.Category;
import com.db.pmb_unas.Model.CurrentQuestion;
import com.db.pmb_unas.Model.Question;
import com.db.pmb_unas.Fragment.QuestionFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class Common {
    public static final int TOTAL_TIME = 60*60*1000; //20 menit
    public static List<Question> questionList = new ArrayList<>();
    public static List<CurrentQuestion> answerSheetList = new ArrayList<>();
    public static Category selectedCategory = new Category();

    public static CountDownTimer countDownTimer;
    public static final long COUNTDOWN_IN_MILLS = 50000;
    public static long timeleftinMills;
    public static int right_answer_count = 0;
    public static int wrong_answer_count = 0;
    public static ArrayList<QuestionFragment> fragmentsList = new ArrayList<>();
    public static TreeSet<String> selected_values = new TreeSet<>();

    public static int no_answer_count = 0;
    public static int timer = 0;
    public static  StringBuilder data_question = new StringBuilder();

    public static final String KEY_GO_TO_QUESTION = "GO_TO_QUESTION";
    public static final String KEY_BACK_FROM_RESULT = "BACK_FROM_RESULT";


    public enum ANSWER_TYPE{
        NO_ANSWER,
        WRONG_ANSWER,
        RIGHT_ANSWER
    }
}
