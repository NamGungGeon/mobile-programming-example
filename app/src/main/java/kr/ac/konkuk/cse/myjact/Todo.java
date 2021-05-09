package kr.ac.konkuk.cse.myjact;


import com.github.geon.State;

public class Todo extends State {
    public String body;

    public Todo(String body) {
        this.body = body;
    }
}
