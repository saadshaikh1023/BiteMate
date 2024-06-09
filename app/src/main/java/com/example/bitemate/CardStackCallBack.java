package com.example.bitemate;
import java.util.List;
import androidx.recyclerview.widget.DiffUtil;

public class CardStackCallBack extends DiffUtil.Callback {


    private List<ItemModel> old, newest;

    public CardStackCallBack(List<ItemModel> old, List<ItemModel> newest) {
        this.old = old;
        this.newest = newest;
    }


    @Override
    public int getOldListSize() {
        return old.size();
    }

    @Override
    public int getNewListSize() {
        return newest.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition).getImage() == newest.get(newItemPosition).getImage();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition) == newest.get(newItemPosition);
    }
}
