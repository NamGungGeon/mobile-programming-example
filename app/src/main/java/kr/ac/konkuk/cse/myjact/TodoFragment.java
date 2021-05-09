package kr.ac.konkuk.cse.myjact;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import kr.ac.konkuk.cse.R;

public class TodoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=  inflater.inflate(R.layout.fragment_todo, container, false);

        return rootView;
    }
    public void renderTodo(List<Todo> todoList){
        List<String> strings= new ArrayList<>();
        for (Todo todo : todoList) {
            strings.add(todo.body);
        }

        ListView todoListView = getView().findViewById(R.id.todoListView);
        todoListView.setAdapter(
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, strings)
        );
        todoListView.setOnItemClickListener((parent, view, position, id) -> {
            ((Exam1Activity)getActivity()).removeTodo(position);
            ((Exam1Activity)getActivity()).renderTodo();
        });
    }
}