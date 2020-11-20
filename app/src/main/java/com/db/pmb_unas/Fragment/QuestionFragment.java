package com.db.pmb_unas.Fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.db.pmb_unas.Common.Common;
import com.db.pmb_unas.Model.Question;
import com.db.pmb_unas.Interface.IQuestion;
import com.db.pmb_unas.Model.CurrentQuestion;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.db.pmb_unas.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment implements IQuestion {

    TextView txt_question_text;
    RadioGroup radioGroup1;
    RadioButton rbA, rbB, rbC, rbD;
    FrameLayout layout_image;
    ProgressBar progressBar;
    ImageView img_question;

    Question question;
    int questionIndex=-1;

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_question, container, false);

        //get question
        questionIndex = getArguments().getInt("index",-1);
        question = Common.questionList.get(questionIndex);

        if(question != null){
            layout_image = itemView.findViewById(R.id.layout_image);
            progressBar = itemView.findViewById(R.id.progress_bar);
            if(question.isImageQuestion()){
                img_question = itemView.findViewById(R.id.img_question);
                Picasso.with(getContext()).load(question.getQuestionImage()).centerCrop().fit().into(img_question, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(getContext(),"gambar tidak dapat dimuat", Toast.LENGTH_SHORT).show();
                    }
                });
            }else
                layout_image.setVisibility(View.GONE);

            //View
            rbA = itemView.findViewById(R.id.rbA);
            rbB = itemView.findViewById(R.id.rbB);
            rbC = itemView.findViewById(R.id.rbC);
            rbD = itemView.findViewById(R.id.rbD);

            txt_question_text = itemView.findViewById(R.id.txt_question_text);
            txt_question_text.setText(question.getQuestionText());

            rbA.setText(question.getAnswerA());
            rbA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                        Common.selected_values.add(rbA.getText().toString());
                    else
                        Common.selected_values.remove(rbA.getText().toString());
                }
            });

            rbB.setText(question.getAnswerB());
            rbB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                        Common.selected_values.add(rbB.getText().toString());
                    else
                        Common.selected_values.remove(rbB.getText().toString());
                }
            });

            rbC.setText(question.getAnswerC());
            rbC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                        Common.selected_values.add(rbC.getText().toString());
                    else
                        Common.selected_values.remove(rbC.getText().toString());
                }
            });

            rbD.setText(question.getAnswerD());
            rbD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                        Common.selected_values.add(rbD.getText().toString());
                    else
                        Common.selected_values.remove(rbD.getText().toString());
                }
            });
        }
        return  itemView;
    }

    @Override
    public CurrentQuestion getSelectedAnswer() {
        CurrentQuestion currentQuestion = new CurrentQuestion(questionIndex,Common.ANSWER_TYPE.NO_ANSWER);
        StringBuilder result = new StringBuilder();
        if(Common.selected_values.size() > 1){
            //if multichoise
            //Split answer to array
            //arr[0] = A.Paris
            Object[] arrayAnswer = Common.selected_values.toArray();
            result.append((String)arrayAnswer[0]).substring(0,1);
        }
        else if(Common.selected_values.size() == 1){
            Object[] arrayAnswer = Common.selected_values.toArray();
            for(int i=0; i<arrayAnswer.length; i++)
                if (i<arrayAnswer.length-1)
                    result.append(new StringBuilder(((String)arrayAnswer[i]).substring(0,1)).append(","));
                else
                    result.append(new StringBuilder((String)arrayAnswer[i]).substring(0, 1));
        }

        if(question != null){
            //compare correct answer with user answer
            if(!TextUtils.isEmpty(result)){
                if (result.toString().equals(question.getCorrectAnswer()))
                    currentQuestion.setType(Common.ANSWER_TYPE.RIGHT_ANSWER);
                else
                    currentQuestion.setType(Common.ANSWER_TYPE.WRONG_ANSWER);
            }
            else
                currentQuestion.setType(Common.ANSWER_TYPE.NO_ANSWER);
        }
        else{
            Toast.makeText(getContext(), "Cannot get question", Toast.LENGTH_SHORT).show();
            currentQuestion.setType(Common.ANSWER_TYPE.NO_ANSWER);
        }
        Common.selected_values.clear();//always clear selected_value when compare done
        return currentQuestion;
    }

    @Override
    public void showCorrectAnswer() {
        //bold correct answer
        String[] correctAnswer = question.getCorrectAnswer().split(",");
        for(String answer:correctAnswer){
            if(answer.equals("A")){
                rbA.setTypeface(null, Typeface.NORMAL);
                rbA.setTextColor(Color.BLACK);
            }
            else if(answer.equals("B")){
                rbB.setTypeface(null, Typeface.NORMAL);
                rbB.setTextColor(Color.BLACK);
            }
            else if(answer.equals("C")){
                rbC.setTypeface(null, Typeface.NORMAL);
                rbC.setTextColor(Color.BLACK);
            }
            else if(answer.equals("D")){
                rbD.setTypeface(null, Typeface.NORMAL);
                rbD.setTextColor(Color.BLACK);
            }
        }
    }

    @Override
    public void disableAnswer() {
        //rbA.setEnabled(false);
        //rbB.setEnabled(false);
        //rbC.setEnabled(false);
        //rbD.setEnabled(false);
    }

    @Override
    public void resetQuestion() {
        //Enable
        rbA.setEnabled(true);
        rbB.setEnabled(true);
        rbC.setEnabled(true);
        rbD.setEnabled(true);

        //Remove all selected
        rbA.setChecked(false);
        rbB.setChecked(false);
        rbC.setChecked(false);
        rbD.setChecked(false);

        //Remove all bold on text
        rbA.setTypeface(null, Typeface.NORMAL);
        rbA.setTextColor(Color.BLACK);
        rbB.setTypeface(null, Typeface.NORMAL);
        rbB.setTextColor(Color.BLACK);
        rbC.setTypeface(null, Typeface.NORMAL);
        rbC.setTextColor(Color.BLACK);
        rbD.setTypeface(null, Typeface.NORMAL);
        rbD.setTextColor(Color.BLACK);
    }
}