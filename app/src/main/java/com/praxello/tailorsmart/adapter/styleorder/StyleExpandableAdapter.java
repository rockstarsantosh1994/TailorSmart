package com.praxello.tailorsmart.adapter.styleorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;

import com.praxello.tailorsmart.R;
import com.praxello.tailorsmart.model.StyleHeader;
import com.praxello.tailorsmart.model.StyleOrderItem;
import com.praxello.tailorsmart.widget.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.praxello.tailorsmart.widget.expandablerecyclerview.ParentViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StyleExpandableAdapter extends ExpandableRecyclerAdapter<StyleHeader, StyleOrderItem, StyleExpandableAdapter.ServicePViewHolder, StyleCViewHolder> {
    //    private static final int PARENT_VEGETARIAN = 0;
    private static final int PARENT_NORMAL = 1;
    private static final int CHILD_NORMAL = 3;

    private LayoutInflater mInflater;
    private Context mContext;
    private List<StyleHeader> mainList;

    public StyleExpandableAdapter(Context mContext, List<StyleHeader> mainList) {
        super(mainList);
        this.mContext = mContext;
        this.mainList = mainList;
        mInflater = LayoutInflater.from(mContext);
    }

    public List<StyleHeader> getMainList() {
        return mainList;
    }

    @UiThread
    @NonNull
    @Override
    public ServicePViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View recipeView;
        switch (viewType) {
            default:
            case PARENT_NORMAL:
                recipeView = mInflater.inflate(R.layout.row_parent, parentViewGroup, false);
                break;
        }
        return new ServicePViewHolder(recipeView);
    }

    @UiThread
    @NonNull
    @Override
    public StyleCViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View ingredientView;
        switch (viewType) {
            default:
            case CHILD_NORMAL:
                ingredientView = mInflater.inflate(R.layout.row_measurement, childViewGroup, false);
                break;
        }
        return new StyleCViewHolder(ingredientView, mContext);
    }

    @UiThread
    @Override
    public void onBindParentViewHolder(@NonNull ServicePViewHolder recipeViewHolder, int parentPosition, @NonNull StyleHeader recipe) {
        recipeViewHolder.bind(recipe, mContext);
    }

    @UiThread
    @Override
    public void onBindChildViewHolder(@NonNull StyleCViewHolder ingredientViewHolder, int parentPosition, int childPosition, @NonNull StyleOrderItem ingredient) {
        ingredientViewHolder.bind(ingredient, mainList.get(parentPosition));
    }

    public class ServicePViewHolder extends ParentViewHolder {
        private static final float INITIAL_POSITION = 0.0f;
        private static final float ROTATED_POSITION = 180f;

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        public ServicePViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(StyleHeader header, Context mContext) {
            tvTitle.setText(header.getTitle());
        }

        @Override
        public void onExpansionToggled(boolean expanded) {
            super.onExpansionToggled(expanded);
            RotateAnimation rotateAnimation;
            if (expanded) { // rotate clockwise
                rotateAnimation = new RotateAnimation(ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            } else { // rotate counterclockwise
                rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            }
            rotateAnimation.setDuration(200);
            rotateAnimation.setFillAfter(true);
        }
    }

    @Override
    public int getParentViewType(int parentPosition) {
        return PARENT_NORMAL;
    }

    @Override
    public int getChildViewType(int parentPosition, int childPosition) {
        return CHILD_NORMAL;
    }

    @Override
    public boolean isParentViewType(int viewType) {
        return viewType == PARENT_NORMAL;
    }
}