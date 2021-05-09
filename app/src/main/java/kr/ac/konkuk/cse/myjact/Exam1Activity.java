package kr.ac.konkuk.cse.myjact;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import kr.ac.konkuk.cse.R;

public class Exam1Activity extends AppCompatActivity {

    private List<Todo> todoList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam1);

        init();
    }

    private void init() {
        EditText newTodoInput = findViewById(R.id.newTodoInput);
        Button newTodoCreateBtn = findViewById(R.id.newTodoCreateBtn);
        newTodoCreateBtn.setOnClickListener(v -> {
            String newTodoBody = newTodoInput.getText().toString();
            Todo newTodo = new Todo(newTodoBody);
            addTodoList(newTodo);
            renderTodo();
        });
    }

    public void renderTodo() {
        TodoFragment fragment1 = (TodoFragment) getSupportFragmentManager().findFragmentById(R.id.frag_todo1);
        fragment1.renderTodo(todoList);

        TodoFragment fragment2 = (TodoFragment) getSupportFragmentManager().findFragmentById(R.id.frag_todo2);
        fragment2.renderTodo(todoList);
    }

    public void addTodoList(Todo todo) {
        todoList.add(todo);
    }

    public void removeTodo(int idx) {
        todoList.remove(idx);
    }
}