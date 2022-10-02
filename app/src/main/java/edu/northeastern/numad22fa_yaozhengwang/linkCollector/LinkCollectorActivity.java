package edu.northeastern.numad22fa_yaozhengwang.linkCollector;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import edu.northeastern.numad22fa_yaozhengwang.R;

public class LinkCollectorActivity extends AppCompatActivity {

    private ArrayList<Link> links;
    private RecyclerView recyclerView;
    private LinkViewAdapter linkViewAdapter;
    private AlertDialog alertDialog;

    private EditText nameInput;
    private EditText urlInput;

    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);
        links = new ArrayList<>();
        init(savedInstanceState);
        FloatingActionButton addButton = findViewById(R.id.addLinkButton);
        addButton.setOnClickListener(l -> addLink());

        createAlertDialog();
        createRecyclerView();
        linkViewAdapter.setOnLinkClickListener(p -> links.get(p).launchUrl(this));
        // Different actions on links
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Snackbar.make(recyclerView, getString(R.string.remove_a_link),
                        Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                int position = viewHolder.getLayoutPosition();
                links.remove(position);
                linkViewAdapter.notifyDataSetChanged();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createAlertDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.activity_link_input, null);

        nameInput = view.findViewById(R.id.link_name_input);
        urlInput = view.findViewById(R.id.link_url_input);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(getString(R.string.Add), (dialog, id) -> {
                    Link link = new Link(nameInput.getText().toString(), urlInput.getText().toString());
                    if (link.checkLink()) {
                        links.add(0, link);
                        linkViewAdapter.notifyDataSetChanged();
                        Snackbar.make(recyclerView, getString(R.string.add_link_success),
                                Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    } else {
                        Snackbar.make(recyclerView, getString(R.string.invalid_link),
                                Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                })
                .setNegativeButton(getString(R.string.Cancel), (dialog, id) -> dialog.cancel());
        alertDialog = alertDialogBuilder.create();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        int size = links == null ? 0 : links.size();
        outState.putInt(NUMBER_OF_ITEMS, size);

        for (int i = 0; i < size; i++) {
            // put link name into instance
            outState.putString(KEY_OF_INSTANCE + i + "0", links.get(i).getLinkName());
            // put link url into instance
            outState.putString(KEY_OF_INSTANCE + i + "1", links.get(i).getLinkUrl());
        }
        super.onSaveInstanceState(outState);
    }

    private void init(Bundle savedInstanceState) {
        initialLink(savedInstanceState);
        createRecyclerView();
    }

    private void initialLink(Bundle savedInstanceState) {
        if(savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)){
            if(links == null || links.size() == 0) {
                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS);
                for(int i = 0; i < size; i++){
                    String linkName = savedInstanceState.getString(KEY_OF_INSTANCE + i + "0");
                    String linkUrl = savedInstanceState.getString(KEY_OF_INSTANCE + i + "1");
                    Link unit = new Link(linkName, linkUrl);
                    links.add(unit);
                }
            }
        }
    }

    public void createRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        linkViewAdapter = new LinkViewAdapter(links);
        recyclerView.setAdapter(linkViewAdapter);
    }

    private void addLink() {
        nameInput.getText().clear();
        urlInput.setText(getString(R.string.Http));
        nameInput.requestFocus();
        alertDialog.show();
    }
}
