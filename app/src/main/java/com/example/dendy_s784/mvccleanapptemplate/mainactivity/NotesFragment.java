package com.example.dendy_s784.mvccleanapptemplate.mainactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dendy_s784.mvccleanapptemplate.R;
import com.example.dendy_s784.mvccleanapptemplate.addeditnotes.AddEditNoteActivity;
import com.example.dendy_s784.mvccleanapptemplate.addeditnotes.AddEditNoteFragment;
import com.example.dendy_s784.mvccleanapptemplate.base.BaseFragment;
import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.component.DaggerMainActivityComponent;
import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.component.MainActivityComponent;
import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.modules.MainActivityModule;
import com.example.dendy_s784.mvccleanapptemplate.model.Note;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class NotesFragment extends BaseFragment implements NotesContract.View, AlertDialogHelper.AlertDialogListener {

    @NonNull
    private static final int REQUEST_EDIT_NOTE = 1;

    @Inject
    NotesContract.Presenter presenter;

    @BindView(R.id.recycler_view)
    RecyclerView rvNotes;
    @BindView(R.id.tasksLL)
    LinearLayout mNotesView;
    @BindView(R.id.noTasks)
    View mNoNotesView;
    @BindView(R.id.noTasksIcon)
    ImageView mNoNotesIcon;
    @BindView(R.id.noTasksMain)
    TextView mNoNotesMainView;
    @BindView(R.id.noTasksAdd)
    TextView mNoNotesAddView;

    MultiSelectAdapter multiSelectAdapter;

    @BindView(R.id.tasksContainer)
    RelativeLayout rootLayout;

    private MainActivityComponent component;

    ArrayList<Note> noteList = new ArrayList<>();
    ArrayList<Note> selectedList = new ArrayList<>();
    boolean isMultiSelect = false;
    ActionMode mActionMode;
    Menu context_menu;
    AlertDialogHelper alertDialogHelper;

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_notes;
    }

    @Override
    protected void init() {
        initComponent();
        //NON-ACTIVITY BINDING
        //You can also perform binding on arbitrary objects by supplying your own view root.
        // Set up  no tasks view
        mNoNotesAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddNote();
            }
        });

        // Set up progress indicator
        /**/
        final ScrollChildSwipe swipeRefreshLayout =
                (ScrollChildSwipe) getActivity().findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );

        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(rvNotes);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadNotes();
            }
        });

        presenter.loadNotes();

        alertDialogHelper = new AlertDialogHelper(getActivity(), this);
        //set List view adapter and behavior
        multiSelectAdapter = new MultiSelectAdapter(getContext(), noteList, selectedList);
        //https://droidmentor.com/multi-select-like-whatsapp-android/
        rvNotes.setLayoutManager(new LinearLayoutManager(getContext()));
        rvNotes.setItemAnimator(new DefaultItemAnimator());
        rvNotes.setAdapter(multiSelectAdapter);
        rvNotes.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), rvNotes, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isMultiSelect) {
                    multi_select(position);
                } else {
                    showNoteDetails(noteList.get(position).getmId());
                    //Toast.makeText(getContext(), "Details Page", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                if (!isMultiSelect) {
                    selectedList = new ArrayList<Note>();
                    isMultiSelect = true;

                    if (mActionMode == null) {
                        mActionMode = getActivity().startActionMode(mActionModeCallback);
                    }
                }
                multi_select(position);
            }
        }));
        setHasOptionsMenu(true);
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_multi_select, menu);
            context_menu = menu;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    alertDialogHelper.showAlertDialog("", "Delete Contact", "DELETE", "CANCEL", 1, false);
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            isMultiSelect = false;
            selectedList = new ArrayList<Note>();
            refreshAdapter();
        }
    };

    public void multi_select(int position) {
        if (mActionMode != null) {
            if (selectedList.contains(noteList.get(position)))
                selectedList.remove(noteList.get(position));
            else
                selectedList.add(noteList.get(position));

            if (selectedList.size() > 0)
                mActionMode.setTitle("" + selectedList.size());
            else
                mActionMode.setTitle("");

            refreshAdapter();

        }
    }

    public void refreshAdapter() {
        multiSelectAdapter.selected_usersList = selectedList;
        multiSelectAdapter.usersList = noteList;
        multiSelectAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AddEditNoteActivity.REQUEST_ADD_EDIT_NOTE) {
                String result = data.getStringExtra(AddEditNoteFragment.ARGUMENT_MESSAGE);
                /*
                Snackbar.make(rootLayout, "Success saving Note", Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getActivity(), "Undo saving note "+result+"!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                        */
                Toast.makeText(getActivity(), "Saving note Success!", Toast.LENGTH_SHORT).show();
                presenter.loadNotes();
            }
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            if (requestCode == AddEditNoteActivity.REQUEST_ADD_EDIT_NOTE) {
                if (data != null) {
                    //Write your code if there's no result
                    String message = data.getStringExtra(AddEditNoteFragment.ARGUMENT_MESSAGE);
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    private void initComponent() {
        if (component == null) {
            component = DaggerMainActivityComponent.builder()
                    .applicationComponent(getApplicationComponent())
                    .mainActivityModule(new MainActivityModule(this))
                    .build();
        }
        component.inject(this);

        registerPresenter(presenter);
    }

    @Override
    public void showNotes(List<Note> noteList) {
        //mListAdapter.replaceData(noteList);
        this.noteList.clear();
        for (int i = 0; i < noteList.size(); i++) {
            this.noteList.add(new Note(noteList.get(i).getmId(), noteList.get(i).getmTitle(), noteList.get(i).getmDescription()));
        }
        multiSelectAdapter.notifyDataSetChanged();

        mNotesView.setVisibility(View.VISIBLE);
        mNoNotesView.setVisibility(View.GONE);
    }

    @Override
    public void showAddNote() {
        Intent intent = new Intent(getContext(), AddEditNoteActivity.class);
        startActivityForResult(intent, AddEditNoteActivity.REQUEST_ADD_EDIT_NOTE);
    }

    @Override
    public void showNoteDetails(String noteId) {
        Intent intent = new Intent(getContext(), AddEditNoteActivity.class);
        intent.putExtra(AddEditNoteFragment.ARGUMENT_DETAILS_NOTE_ID, noteId);
        startActivityForResult(intent, AddEditNoteActivity.REQUEST_ADD_EDIT_NOTE);
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl = (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void showLoadingNotesError() {
        showMessage(getString(R.string.loading_notes_error));
    }

    @Override
    public void showNoNotes() {
        showNoTasksViews(
                getResources().getString(R.string.no_notes_all),
                R.drawable.ic_assignment_turned_in_24dp,
                false
        );
    }

    private void showNoTasksViews(String mainText, int iconRes, boolean showAddView) {
        mNotesView.setVisibility(View.GONE);
        mNoNotesView.setVisibility(View.VISIBLE);

        mNoNotesMainView.setText(mainText);
        mNoNotesIcon.setImageDrawable(getResources().getDrawable(iconRes));
        mNoNotesAddView.setVisibility(showAddView ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showMarkedNotesDelete() {
        showMessage(getString(R.string.marked_notes_delete));
    }

    @Override
    public void onNoteDelete(ArrayList<String> mid) {
        if(mid.size()>0)
        {
            for (int i = 0; i < mid.size(); i++)
            {
                for (int j = 0; j < noteList.size(); j++)
                {
                    if (noteList.get(j).getmId().equalsIgnoreCase(mid.get(i))) {
                        //todo : show message that notes already deleted
                        //Toast.makeText(getActivity(), "Delete " + noteList.get(i).getmTitle(), Toast.LENGTH_SHORT).show();
                        //send message the id if note are found and ready to delete
                        noteList.remove(j);
                    }
                }
            }
            multiSelectAdapter.notifyDataSetChanged();
        }

        if (noteList.size() == 0) {
            showNoNotes();
        }
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onPositiveClick(int from) {
        switch (from) {
            case 1:
                if (selectedList.size() > 0) {
                    presenter.deleteNote(selectedList);
                    for (int i = 0; i < selectedList.size(); i++) {
                        //presenter.deleteNote(selectedList.get(i));
                    }

                    if (mActionMode != null) {
                        mActionMode.finish();
                    }
                }
                break;
            case 2:
                if (mActionMode != null) {
                    mActionMode.finish();
                }

                multiSelectAdapter.notifyDataSetChanged();
                break;
            default:
        }
    }

    @Override
    public void onNegativeClick(int from) {

    }

    @Override
    public void onNeutralClick(int from) {

    }
}
