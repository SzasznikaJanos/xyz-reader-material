package com.example.xyzreader.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.xyzreader.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A fragment representing a single Article detail screen. This fragment is
 * either contained in a {@link ArticleListActivity} in two-pane mode (on
 * tablets) or a {@link ArticleDetailActivity} on handsets.
 */

public class ArticleDetailFragment extends Fragment {

    private static final String ARTICLE_KEY = "article_content";

    private Unbinder unBinder;

    @BindView(R.id.article_textview)
    TextView articleContentTextView;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArticleDetailFragment() {
    }


    /**
     * NewInstance constructor for creating fragment with arguments
     */
    public static ArticleDetailFragment newInstance(String article) {
        ArticleDetailFragment articleDetailFragment = new ArticleDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARTICLE_KEY, article);
        articleDetailFragment.setArguments(bundle);
        return articleDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String article = "";
        View mRootView = inflater.inflate(R.layout.fragment_article_detail, container, false);
        unBinder = ButterKnife.bind(this, mRootView);

        if (getArguments() != null && getArguments().containsKey(ARTICLE_KEY)) {
            article = getArguments().getString(ARTICLE_KEY);
        }


        prepareArticleText(article);
        return mRootView;
    }


    /**
     * Method to format raw article content before displaying on screen
     */
    public void prepareArticleText(String articleText) {
        String a = articleText.replaceAll(">", "&gt;");
        String a1 = a.replaceAll("(\r\n){2}(?!(&gt;))", "<br><br>");
        String a2 = a1.replaceAll("(\r\n)", " ");

        //remove all text between [ and ]
        String a3 = a2.replaceAll("\\[.*?\\]", "");

        //put new line after i.e 1. Ebooks aren't marketing.
        String a4 = a3.replaceAll("(\\d\\.\\s.*?\\.)", "$1<br>");

        //make text between * * bold
        String a5 = a4.replaceAll("\\*(.*?)\\*", "<b>$1</b>");

        //remove all '>' from text such as 'are >'  but leave the first '>' in tact
        String a6 = a5.replaceAll("(\\w\\s)&gt;", "$1");

        // replace double hyphen with single hyphen
        String a7 = a6.replaceAll("--", " - ");

        Spanned a8 = Html.fromHtml(a7);
        articleContentTextView.setText(a8.toString());
        if(getContext()!= null) {
            articleContentTextView.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.slide_in_right));
        }

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBinder.unbind();
    }
}
