package com.example.android.miwok.model;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.ColorRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.android.miwok.R;

import java.util.List;

/**
 * Custom Adapter for showing our model.Word type
 * Created by stefox2 on 6/25/16.
 */
public class WordAdapter extends ArrayAdapter<Word> {

    private static final String TAG = WordAdapter.class.getSimpleName();
    private int mItemBackgroundColor = -1;

    public WordAdapter( Context context, List<Word> words ) {
        super(context, 0, words);
    }

    public WordAdapter( Context context, List<Word> words, @ColorRes int itemBackgroundColor) {
        super(context, 0, words);
        mItemBackgroundColor = itemBackgroundColor;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent ) {

        View _listItemView = convertView;
        if (_listItemView == null) {
            _listItemView = LayoutInflater.from(getContext()).
                    inflate(R.layout.vocab_list_item, parent, false);
        }

        Word _word = getItem(position);

        Log.d(TAG, "getView: word = " + _word);

        if (this.mItemBackgroundColor > -1) {
            LinearLayout _textLayout = (LinearLayout) _listItemView.findViewById(R.id.textVocabLayout);
            // we need a new background
            _textLayout.setBackgroundResource(mItemBackgroundColor);
        }

        // Get the first TextView
        TextView foreignTextView = (TextView) _listItemView.findViewById(R.id.foreignText);
        // Set its text
        foreignTextView.setText(_word.getMiwokTranslation());

        // Get the other TextView
        TextView nativeTextView = (TextView) _listItemView.findViewById(R.id.nativeText);
        // Set its text
        nativeTextView.setText(_word.getDefaultTranslation());

        // The image that is part of this view
        ImageView _imageView = (ImageView) _listItemView.findViewById(R.id.vocabImage);

        _imageView.setImageResource(_word.getImageResourceId());

        if (!_word.hasImage()) {
            _imageView.setVisibility(View.GONE);
        }

        if (_word.hasAudio()) bindClickToExistentAudio(_listItemView, _word);

        return _listItemView;
    }

    private void bindClickToExistentAudio( View itemView, final Word word ) {
        itemView.setOnClickListener(new View.OnClickListener() {
            MediaPlayer mPlayer = MediaPlayer.create(WordAdapter.this.getContext(), word.getAudioResourceId());
            @Override
            public void onClick( View v ) {
                mPlayer.start();
//                try {
////                    mPlayer.prepare();
//                }
//                catch (IOException e) {
//                    Log.e(TAG, "onClick: mPlayer.prepare() threw", e);
//                }
            }
        });
    }
}
